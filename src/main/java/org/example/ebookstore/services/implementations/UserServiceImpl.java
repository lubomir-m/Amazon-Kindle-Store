package org.example.ebookstore.services.implementations;

import jakarta.annotation.PostConstruct;
import org.example.ebookstore.entities.Picture;
import org.example.ebookstore.entities.User;
import org.example.ebookstore.repositories.PictureRepository;
import org.example.ebookstore.repositories.UserRepository;
import org.example.ebookstore.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PictureRepository pictureRepository;
    private Picture defaultPicture;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PictureRepository pictureRepository) {
        this.userRepository = userRepository;
        this.pictureRepository = pictureRepository;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }

    @Override
    public User save(User user) {
        if (user.getPicture() == null) {
            user.setPicture(this.defaultPicture);
        }
        return this.userRepository.save(user);
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
    private void init() {
        this.defaultPicture = this.pictureRepository.findById(Long.valueOf(1))
                .orElseGet(() -> {
                    Picture picture = new Picture();
                    picture.setData(loadDefaultImageData());
                    picture.setName("default-profile-picture");
                    return this.pictureRepository.save(picture);
                });
    }
}
