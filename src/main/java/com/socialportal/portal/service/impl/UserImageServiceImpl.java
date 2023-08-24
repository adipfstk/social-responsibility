package com.socialportal.portal.service.impl;

import com.socialportal.portal.dto.ImageUploadResponse;
import com.socialportal.portal.model.image.ImageData;
import com.socialportal.portal.model.user.UserEntity;
import com.socialportal.portal.model.user.UserImage;
import com.socialportal.portal.repository.UserEntityRepository;
import com.socialportal.portal.repository.UserImageRepository;
import com.socialportal.portal.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserImageServiceImpl extends CommonImageService implements ImageService {
    private final UserImageRepository userImageRepository;
    private final UserEntityRepository userEntityRepository;

    @Override
    public ImageUploadResponse uploadImage(MultipartFile file, long ownerId) throws IOException {
        Optional<UserImage> userImage = userImageRepository.findByOwnerId(ownerId);
        ImageData imageData = buildImage(file);

        UserImage updatedUserImage = userImage.orElseGet(() -> createAndSaveUserImage(ownerId));

        imageMapper(imageData, updatedUserImage);
        userImageRepository.save(updatedUserImage);

        return new ImageUploadResponse("Image uploaded successfully: " +
                file.getOriginalFilename());
    }

    @Override
    public List<byte[]> getImages(Long id) {
        UserImage dbImg = userImageRepository.findByOwnerId(id)
                .orElseThrow(() -> new RuntimeException("Example"));
        return List.of(getImage(dbImg));
    }

    private UserImage createAndSaveUserImage(long ownerId) {
        UserImage submitImage = new UserImage();
        submitImage.setUserEntity(getUserEntity(ownerId));
        return userImageRepository.save(submitImage);
    }

    private UserEntity getUserEntity(long ownerId) {
        return userEntityRepository.findById(ownerId)
                .orElseThrow(() -> new UsernameNotFoundException("No user in db"));
    }
}
