package com.file_manager.File_Manager.services;

import io.minio.*;
import io.minio.errors.MinioException;
import io.minio.messages.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

@Service
public class MinioService {

    private static final Logger logger = LoggerFactory.getLogger(MinioService.class);

    private final MinioClient minioClient;

    public MinioService() {
        this.minioClient = MinioClient.builder()
                .endpoint("http://127.0.0.1:9000")
                .credentials("minioadmin", "minioadmin")
                .build();
    }

    public void uploadFile(String bucketName, String objectName, byte[] fileData, String contentType){
        try{
            ByteArrayInputStream bais = new ByteArrayInputStream(fileData);

            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .stream(bais, bais.available(), -1)
                            .contentType(contentType)
                            .build()
            );

            logger.info("File '{}' uploaded successfully to bucket '{}'.", objectName, bucketName);
        }
        catch (Exception e){
            logger.error("Error uploading file '{}' to bucket '{}'.", objectName, bucketName, e);
        }
    }

    public InputStream downloadFile(String bucketName, String id){
        try{
            String objectName = findFileNameById(bucketName, id);

            if(objectName == null){
                logger.error("File with ID '{}' not found in bucket '{}'.", id, bucketName);
                return null;
            }
            logger.info("Downloading file '{}' from bucket '{}'.", objectName, bucketName);
            return minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .build()
            );
        }
        catch (MinioException me){
            logger.error("Error downloading file with ID '{}' from bucket '{}'.", id, bucketName, me);
            return null;
        }
        catch (Exception e){
            logger.error("General error occurred during file download.", e);
            return null;
        }
    }

    public void deleteFile(String bucketName, String fileID) throws Exception {
        try {
            Iterable<Result<Item>> results = minioClient.listObjects(
                    ListObjectsArgs.builder().bucket(bucketName).build()
            );

            for (Result<Item> result : results) {
                Item item = result.get();
                String objectName = item.objectName();

                if (objectName.startsWith(fileID)) {
                    minioClient.removeObject(
                            RemoveObjectArgs.builder().bucket(bucketName).object(objectName)
                                    .build()
                    );
                    return;
                }
            }
            throw new Exception("File with ID " + fileID + " not found in bucket " + bucketName);
        } catch (Exception e) {
            throw new Exception("Error deleting file with ID " + fileID + " in bucket " + bucketName, e);
        }
    }

    public String findFileNameById(String bucketName, String id) throws Exception {
        Iterable<Result<Item>> results = minioClient.listObjects(
                ListObjectsArgs.builder().bucket(bucketName).build()
        );

        for (Result<Item> result : results) {
            Item item = result.get();
            if (item.objectName().startsWith(id)) {
                return item.objectName();
            }
        }
        throw new Exception("File not found");
    }

}
