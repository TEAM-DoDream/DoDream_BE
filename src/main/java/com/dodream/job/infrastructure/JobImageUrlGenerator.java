package com.dodream.job.infrastructure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JobImageUrlGenerator {

    @Value("${ncp.object-storage.endpoint}")
    private String endpoint;

    @Value("${ncp.object-storage.bucket-name}")
    private String bucketName;

    private final String FOLDER_NAME = "job";

    public String getImageUrl(Long id){
        return endpoint + "/" + bucketName + "/" + FOLDER_NAME + "/" + id  + ".png";
    }
}
