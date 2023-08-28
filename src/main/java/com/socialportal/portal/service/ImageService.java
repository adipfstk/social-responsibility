package com.socialportal.portal.service;

import com.socialportal.portal.model.image.ImageData;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

public interface ImageService {
    <T extends ImageData> byte[] getPayload(T dbImage);

    ImageData buildImage(MultipartFile file) throws IOException;

    <T extends ImageData> void imageMapper(ImageData imageData, T mappedObj);


}
