package com.blackJack.service;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Random;

import com.blackJack.configuration.StorageProperties;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;


@Service
public class StorageService
{
    private final Path rootLocation;


    @Autowired
    public StorageService(final StorageProperties properties)
    {
        this.rootLocation = Paths.get(properties.getLocation());
    }


    public String store(final MultipartFile file)
    {
        final String filename = new Random().nextInt() + StringUtils.cleanPath(file.getOriginalFilename());
        try
        {
            if (file.isEmpty())
            {
                throw new RuntimeException("Failed to store empty file " + filename);
            }
            if (filename.contains(".."))
            {
                // This is a security check
                throw new RuntimeException(
                        "Cannot store file with relative path outside current directory "
                                + filename);
            }
            try (final InputStream inputStream = file.getInputStream())
            {
                Files.copy(inputStream, this.rootLocation.resolve(filename),
                        StandardCopyOption.REPLACE_EXISTING);
            }
            return filename;
        }
        catch (final IOException e)
        {
            throw new RuntimeException("Failed to store file " + filename, e);
        }
    }


    @SneakyThrows
    public Resource loadAsResource(final String filename, final String directory)
    {
        try
        {
            final Path file = load(filename, directory);
            final Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable())
            {
                return resource;
            }
            else
            {
                throw new FileNotFoundException();
            }
        }
        catch (final FileNotFoundException e)
        {
            throw new FileNotFoundException("Could not read file: " + filename);
        }
    }


    @SneakyThrows
    public Resource loadAsResource(final String filename)
    {
        return loadAsResource(filename, null);
    }


    public Path load(final String filename, final String directory)
    {
        if (directory != null)
        {
            return Paths.get(rootLocation.toString() + directory).resolve(filename);
        }
        return rootLocation.resolve(filename);
    }

}
