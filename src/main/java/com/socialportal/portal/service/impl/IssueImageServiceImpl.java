package com.socialportal.portal.service.impl;

import com.socialportal.portal.dto.ImageUploadResponse;
import com.socialportal.portal.exception.issue.NoIssueFoundException;
import com.socialportal.portal.model.image.ImageData;
import com.socialportal.portal.model.issues.IssueImage;
import com.socialportal.portal.repository.IssueImageRepository;
import com.socialportal.portal.repository.IssueRepository;
import com.socialportal.portal.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class IssueImageServiceImpl extends CommonImageService implements ImageService {
    private final IssueImageRepository issueImageRepository;
    private final IssueRepository issueRepository;
    @Override
    public ImageUploadResponse uploadImage(MultipartFile file, long ownerId) throws IOException {
        ImageData imageData = this.buildImage(file);
        IssueImage issueImage = new IssueImage();
        var issue = this.issueRepository.findById(ownerId).orElseThrow(()->new NoIssueFoundException("No issue in db"));
        issueImage.setIssue(issue);
        this.imageMapper(imageData, imageData);
        this.issueImageRepository.save(issueImage);
        return new ImageUploadResponse("Image uploaded successfully: " +
                file.getOriginalFilename());
    }

    @Override
    public List<byte[]> getImages(Long id) {
        var dbImages = this.issueImageRepository.findAllByOwnerId(id).orElseThrow(() -> new NoIssueFoundException("No"));
        return dbImages.stream().map(this::getImage).toList();
    }
}
