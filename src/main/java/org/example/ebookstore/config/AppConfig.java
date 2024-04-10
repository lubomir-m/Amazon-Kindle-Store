package org.example.ebookstore.config;

import org.example.ebookstore.entities.Publisher;
import org.example.ebookstore.entities.dtos.PublisherDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

//        // Explicit mapping for Publisher to PublisherDto
//        TypeMap<Publisher, PublisherDto> publisherTypeMap = modelMapper.createTypeMap(Publisher.class, PublisherDto.class);
//        publisherTypeMap.addMappings(mapper -> {
//            mapper.map(Publisher::getId, PublisherDto::setId);
//            // Add other mappings if Publisher has more fields that need to be mapped
//        });
//
//        // Converter from Set<Author> to Set<AuthorDto>
//        Converter<Set<Author>, Set<AuthorDto>> authorsConverter = ctx -> ctx.getSource().stream()
//                .map(author -> modelMapper.map(author, AuthorDto.class))
//                .collect(Collectors.toSet());
//
//        // Explicit mapping for Book to BookDto
//        TypeMap<Book, BookDto> bookTypeMap = modelMapper.createTypeMap(Book.class, BookDto.class);
//        bookTypeMap.addMappings(mapper -> {
//            mapper.using(authorsConverter).map(Book::getAuthors, BookDto::setAuthors);
//            mapper.map(Book::getPublisher, BookDto::setPublisher);
//            // Add other mappings if necessary
//        });

        return modelMapper;
    }
}
