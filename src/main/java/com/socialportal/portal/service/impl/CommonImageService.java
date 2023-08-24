package com.socialportal.portal.service.impl;

import com.socialportal.portal.model.image.ImageData;
import com.socialportal.portal.service.utils.ImageUtil;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class CommonImageService {

    public <T extends ImageData> byte[] getImage(T dbImage) {
        return ImageUtil.decompressImage(dbImage.getImageData());
    }

    public ImageData buildImage(MultipartFile file) throws IOException {
        ImageData imageData = new ImageData();
        imageData.setName(file.getOriginalFilename());
        imageData.setType(file.getContentType());
        imageData.setImageData(ImageUtil.compressImage(file.getBytes()));
        return imageData;
    }

    public <T extends ImageData> void imageMapper(ImageData imageData, T mappedObj) {
        mappedObj.setName(imageData.getName());
        mappedObj.setType(imageData.getType());
        mappedObj.setImageData(imageData.getImageData());
    }

}
