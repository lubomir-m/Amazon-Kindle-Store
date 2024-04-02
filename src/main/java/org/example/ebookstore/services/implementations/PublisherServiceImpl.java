package org.example.ebookstore.services.implementations;

import jakarta.annotation.PostConstruct;
import org.example.ebookstore.entities.Picture;
import org.example.ebookstore.entities.Publisher;
import org.example.ebookstore.repositories.PictureRepository;
import org.example.ebookstore.repositories.PublisherRepository;
import org.example.ebookstore.services.interfaces.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class PublisherServiceImpl implements PublisherService {
    private final PublisherRepository publisherRepository;


    @Autowired
    public PublisherServiceImpl(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    @Override
    public Publisher save(Publisher publisher) {
        return this.publisherRepository.save(publisher);
    }

}
