package org.example.ebookstore.config;

import org.example.ebookstore.entities.Author;
import org.example.ebookstore.entities.Picture;
import org.example.ebookstore.entities.Publisher;
import org.example.ebookstore.entities.User;
import org.example.ebookstore.entities.dtos.AuthorDto;
import org.example.ebookstore.entities.dtos.PublisherDto;
import org.example.ebookstore.entities.dtos.UserDto;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Base64;

@Configuration
@EnableScheduling
public class AppConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        // Converter from byte[] to Base64 encoded string
        Converter<byte[], String> toBase64String = ctx -> Base64.getEncoder().encodeToString(ctx.getSource());

        // Converter from Picture to Base64 encoded string with contentType
        Converter<Picture, String> pictureToBase64WithContentType = ctx -> {
            if (ctx.getSource() == null) {
                return null; // or some default value if needed
            }
            Picture picture = ctx.getSource();
            String contentType = picture.getContentType(); // Ensure Picture class has getContentType()
            byte[] data = picture.getData();
            String base64Data = Base64.getEncoder().encodeToString(data);
            return String.format("data:%s;base64,%s", contentType, base64Data);
        };

        // Define property mappings for User to UserDto
        modelMapper.createTypeMap(User.class, UserDto.class).addMappings(mapper -> {
            mapper.using(pictureToBase64WithContentType).map(User::getPicture, UserDto::setPictureBase64);
        });

        // Define property mappings for Author to AuthorDto
        modelMapper.createTypeMap(Author.class, AuthorDto.class).addMappings(mapper -> {
            mapper.using(pictureToBase64WithContentType).map(Author::getPicture, AuthorDto::setPictureBase64);
        });

        return modelMapper;
    }
}
