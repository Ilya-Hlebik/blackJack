package com.blackJack.service;


import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.blackJack.configuration.StorageProperties;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;


@Service
public class StorageService
{
    private final Path rootLocation;


    @Autowired
    public StorageService(StorageProperties properties)
    {
        this.rootLocation = Paths.get(properties.getLocation());
    }


    @SneakyThrows
    public Resource loadAsResource(String filename)
    {
        try
        {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable())
            {
                return resource;
            }
            else
            {
                throw new FileNotFoundException();
            }
        }
        catch (FileNotFoundException e)
        {
            throw new FileNotFoundException("Could not read file: " + filename);
        }
    }


    public Path load(String filename)
    {
        return rootLocation.resolve(filename);
    }

}
