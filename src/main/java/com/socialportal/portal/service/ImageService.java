package com.socialportal.portal.service;

import com.socialportal.portal.dto.ImageUploadResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ImageService {
    ImageUploadResponse uploadImage(MultipartFile file, long onwerId) throws IOException;
    List<byte[]> getImages(Long id);
}
