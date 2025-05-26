package com.dodream.core.application;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.dodream.member.exception.MemberErrorCode;
import com.dodream.todo.exception.TodoErrorCode;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ObjectStorageService {

    private final AmazonS3Client amazonS3Client;

    @Value("${ncp.object-storage.bucket-name}")
    private String bucketName;

    public String uploadMemberProfileImage(MultipartFile file, Long memberId) {

        String originalFilename = file.getOriginalFilename();
        if (isAllowedImageExtension(originalFilename)) {
            throw MemberErrorCode.UNSUPPORTED_FILE_EXTENSION.toException();
        }

        String dirName = "profile/" + memberId;
        String fileName = dirName + "/" + UUID.randomUUID() + "_" + originalFilename;

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());

        try {
            amazonS3Client.putObject(bucketName, fileName, file.getInputStream(), metadata);
        } catch (IOException e) {
            throw MemberErrorCode.FILE_UPLOAD_FAILED.toException();
        }

        return amazonS3Client.getUrl(bucketName, fileName).toString();
    }


    public void deleteMemberProfileImage(String imageUrl) {
        String fileKey = extractFileKeyFromUrl(imageUrl);

        try {
            if (amazonS3Client.doesObjectExist(bucketName, fileKey)) {
                amazonS3Client.deleteObject(bucketName, fileKey);
            }
        } catch (SdkClientException e) {
            throw MemberErrorCode.FILE_DELETE_FAILED.toException();
        }
    }

    public List<String> uploadTodoMemoImages(List<MultipartFile> files, Long todoItemId) {
        String dirName = "todomemo/" + todoItemId;
        List<String> uploadedUrls = new ArrayList<>();

        for (MultipartFile file : files) {
            try {
                String originalFilename = file.getOriginalFilename();
                if (isAllowedImageExtension(originalFilename)) {
                    throw TodoErrorCode.UNSUPPORTED_FILE_EXTENSION.toException();
                }

                String fileName =
                    dirName + "/" + UUID.randomUUID() + "_" + originalFilename;

                ObjectMetadata metadata = new ObjectMetadata();
                metadata.setContentLength(file.getSize());
                metadata.setContentType(file.getContentType());

                amazonS3Client.putObject(bucketName, fileName, file.getInputStream(), metadata);
                String fileUrl = amazonS3Client.getUrl(bucketName, fileName).toString();
                uploadedUrls.add(fileUrl);

            } catch (IOException e) {
                throw TodoErrorCode.FILE_UPLOAD_FAILED.toException();
            }
        }
        return uploadedUrls;
    }


    private String extractFileKeyFromUrl(String url) {
        int idx = url.indexOf(".com/");
        if (idx == -1) {
            throw new IllegalArgumentException("잘못된 URL 형식입니다: " + url);
        }
        return url.substring(idx + 5);
    }

    private boolean isAllowedImageExtension(String filename) {
        String lowerName = filename.toLowerCase();
        return lowerName.endsWith(".jpg") || lowerName.endsWith(".jpeg") || lowerName.endsWith(
            ".png");
    }

}
