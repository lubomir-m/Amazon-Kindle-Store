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
    private final PictureRepository pictureRepository;
    private Picture defaultPicture;

    @Autowired
    public PublisherServiceImpl(PublisherRepository publisherRepository, PictureRepository pictureRepository) {
        this.publisherRepository = publisherRepository;
        this.pictureRepository = pictureRepository;
    }

    @Override
    public Publisher save(Publisher publisher) {
        if (publisher.getPicture() == null) {
            publisher.setPicture(this.defaultPicture);
        }
        return this.publisherRepository.save(publisher);
    }

    private byte[] loadDefaultImageData() {
        try {
            Resource resource = new ClassPathResource("src/main/resources/static/images/default-profile-picture.jpg");
            BufferedImage bImage = ImageIO.read(resource.getInputStream());
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ImageIO.write(bImage, "jpg", bos);
            return bos.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @PostConstruct
    public void init() {
        this.defaultPicture = this.pictureRepository.findById(1L)
                .orElseGet(() -> {
                    Picture picture = new Picture();
                    picture.setData(loadDefaultImageData());
                    picture.setName("default-profile-picture");
                    return this.pictureRepository.save(picture);
                });
    }
}
