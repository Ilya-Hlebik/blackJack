package com.blackJack.service;


import java.security.Principal;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.blackJack.dbo.BlackList;
import com.blackJack.dbo.EmailConfirmation;
import com.blackJack.dbo.User;
import com.blackJack.dbo.UserInfo;
import com.blackJack.dto.ForgotPasswordRequestDto;
import com.blackJack.dto.PasswordUpdateRequestDto;
import com.blackJack.dto.SignInRequestDTO;
import com.blackJack.dto.SignUpRequestDTO;
import com.blackJack.exception.CustomException;
import com.blackJack.repository.AbstractRepository;
import com.blackJack.repository.BlackListRepository;
import com.blackJack.repository.EmailRepository;
import com.blackJack.repository.UserRepository;
import com.blackJack.security.JwtTokenProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import static com.blackJack.security.JwtTokenProvider.REFRESH_TOKEN;


@Service
public class UserService extends AbstractService<User> {

    public static final String IMAGE_PREFIX = "/backend/storage/files/";

    private final BlackListRepository blackListRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtTokenProvider jwtTokenProvider;

    private final AuthenticationManager authenticationManager;

    private final EmailRepository emailRepository;

    private final EmailService emailService;

    private final UserInfoService userInfoService;

    private final StorageService storageService;

    public UserService(final AbstractRepository<User> repository, final ModelMapper modelMapper,
            final BlackListRepository blackListRepository, final PasswordEncoder passwordEncoder,
            final JwtTokenProvider jwtTokenProvider, final AuthenticationManager authenticationManager,
            final EmailRepository emailRepository, final EmailService emailService,
            @Lazy final UserInfoService userInfoService, final StorageService storageService) {
        super(repository, modelMapper);
        this.blackListRepository = blackListRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
        this.emailRepository = emailRepository;
        this.emailService = emailService;
        this.userInfoService = userInfoService;
        this.storageService = storageService;
    }

    public User signIn(final SignInRequestDTO signInRequestDTO, final HttpServletResponse res) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequestDTO.getUsername(), signInRequestDTO.getPassword()));
            final User user = ((UserRepository) repository).findByUsername(signInRequestDTO.getUsername());
            if (!user.isActive()) {
                throw new CustomException("Invalid password, please change", HttpStatus.NON_AUTHORITATIVE_INFORMATION);
            }
            jwtTokenProvider.createTokens(signInRequestDTO.getUsername(), user.getRoles(), res);
            return user;
        } catch (final AuthenticationException e) {
            throw new CustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    public User signUp(final SignUpRequestDTO signUpRequestDTO, final MultipartFile image,
            final boolean active) {
        if (!((UserRepository) repository).existsByUsername(signUpRequestDTO.getUsername())){
            final User user = save(
                    new User(signUpRequestDTO.getRoles(), signUpRequestDTO.getUsername(), signUpRequestDTO.getEmail(),
                            passwordEncoder.encode(signUpRequestDTO.getPassword()), active, null));
            final String imageUrl = IMAGE_PREFIX + storageService.store(image);
            final UserInfo userInfo = userInfoService
                    .save(new UserInfo(signUpRequestDTO.getUsername(), signUpRequestDTO.getPhone(),
                            signUpRequestDTO.getCity(), signUpRequestDTO.getStreetAddress(), 0,
                            user, imageUrl));
            user.setUserInfo(userInfo);
            return save(user);
        }
        else
        {
            throw new CustomException("Username is already in use", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    public User save(final User user) {
        return repository.save(user);
    }

    public void delete(final String username) {
        ((UserRepository) repository).deleteByUsername(username);
    }

    public User search(final String username) {
        final User user = ((UserRepository) repository).findByUsername(username);
        if (user == null) {
            throw new CustomException("The user doesn't exist", HttpStatus.NOT_FOUND);
        }
        return user;
    }

    public void logOut(final HttpServletRequest req) {
        final Map<String, String> stringStringMap = jwtTokenProvider.resolveToken(req);
        final String refreshToken = stringStringMap.get(REFRESH_TOKEN);
        final Jws<Claims> claimsJws = Jwts.parser().setSigningKey(jwtTokenProvider.refreshSecretKey).parseClaimsJws(refreshToken);
        final Date expiration = claimsJws.getBody().getExpiration();
        final Date now = new Date();
        final long liveTime = expiration.getTime() - now.getTime();
        blackListRepository.save(new BlackList(refreshToken, liveTime));
    }

    public User updatePassword(final PasswordUpdateRequestDto updateRequestDto) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(updateRequestDto.getUsername(), updateRequestDto.getOldPassword()));
            if (!updateRequestDto.getNewPasswordFirstEntry().equals(updateRequestDto.getNewPasswordSecondEntry())) {
                throw new CustomException("Passwords do not match", HttpStatus.BAD_REQUEST);
            }
            if (updateRequestDto.getNewPasswordFirstEntry().equals(updateRequestDto.getOldPassword())) {
                throw new CustomException("Old password and new password shouldn't be the same", HttpStatus.BAD_REQUEST);
            }
            final User user = ((UserRepository) repository).findByUsername(updateRequestDto.getUsername());
            user.setPassword(passwordEncoder.encode(updateRequestDto.getNewPasswordFirstEntry()));
            user.setActive(true);
            return repository.save(user);
        } catch (final AuthenticationException e) {
            throw new CustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    public ResponseEntity<User> updatePassword(final ForgotPasswordRequestDto forgotPasswordRequestDto) {
        final boolean valid = emailService.verifyToken(forgotPasswordRequestDto.getToken());
        if (valid) {
            final EmailConfirmation confirmationByToken = emailService.findConfirmationByToken(forgotPasswordRequestDto.getToken());
            final Optional<User> userOptional = ((UserRepository) repository)
                    .findByEmail(confirmationByToken.getId());
            if (userOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            final User user = userOptional.get();
            user.setPassword(passwordEncoder.encode(forgotPasswordRequestDto.getNewPassword()));
            repository.save(user);
            emailRepository.delete(confirmationByToken);
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    public User findMe(final Principal req) {
        return ((UserRepository) repository).findByUsername(req.getName());
    }
}
