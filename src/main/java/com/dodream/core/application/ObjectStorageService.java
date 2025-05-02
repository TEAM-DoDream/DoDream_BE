package com.dodream.core.application;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
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
        String dirName = "profile/" + memberId;
        String fileName = dirName + "/" + UUID.randomUUID() + "_" + file.getOriginalFilename();

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());

        try {
            amazonS3Client.putObject(bucketName, fileName, file.getInputStream(), metadata);
        } catch (IOException e) {
            throw new RuntimeException("프로필 이미지를 업로드하는 중 오류가 발생했습니다.", e);
        }

        return amazonS3Client.getUrl(bucketName, fileName).toString();
    }


    public void deleteMemberProfileImage(String imageUrl) {
        String fileKey = extractFileKeyFromUrl(imageUrl);

        if (amazonS3Client.doesObjectExist(bucketName, fileKey)) {
            amazonS3Client.deleteObject(bucketName, fileKey);
        }
    }

    public List<String> uploadTodoMemoImages(List<MultipartFile> files, Long todoItemId) {
        String dirName = "todomemo/" + todoItemId;
        List<String> uploadedUrls = new ArrayList<>();

        for (MultipartFile file : files) {
            try {
                String fileName =
                    dirName + "/" + UUID.randomUUID() + "_" + file.getOriginalFilename();

                ObjectMetadata metadata = new ObjectMetadata();
                metadata.setContentLength(file.getSize());
                metadata.setContentType(file.getContentType());

                amazonS3Client.putObject(bucketName, fileName, file.getInputStream(), metadata);
                String fileUrl = amazonS3Client.getUrl(bucketName, fileName).toString();
                uploadedUrls.add(fileUrl);

            } catch (IOException e) {
                throw new RuntimeException("이미지 업로드 중 오류가 발생했습니다: " + file.getOriginalFilename(),
                    e);
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

}
