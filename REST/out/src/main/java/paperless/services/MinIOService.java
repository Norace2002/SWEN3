package paperless.services;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import paperless.config.MinIOConfig;
import io.minio.*;
import io.minio.errors.*;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
public class MinIOService {
    private final MinIOConfig minIOConfig;
    private final MinioClient minioClient;

    private final Logger logger = LogManager.getLogger();

    @Autowired
    MinIOService(MinIOConfig minIOConfig, MinioClient minioClient) {
        this.minIOConfig = minIOConfig;
        this.minioClient = minioClient;
    }

    public void upload(String objectName, byte[] file) {
        try {
            boolean hasBucketWithName =
                    minioClient.bucketExists(
                            BucketExistsArgs
                                    .builder()
                                    .bucket(minIOConfig.getBucketName())
                                    .build()
                    );
            if (!hasBucketWithName) {
                minioClient.makeBucket(
                        MakeBucketArgs
                                .builder()
                                .bucket(minIOConfig.getBucketName())
                                .build()
                );
            }

            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(minIOConfig.getBucketName())
                            .object(objectName)
                            .stream(new ByteArrayInputStream(file), file.length, -1)
                            .build()
            );

        } catch (MinioException e) {
            logger.error("Error occurred: " + e);
            logger.error("HTTP trace: " + e.httpTrace());
        } catch (IOException | NoSuchAlgorithmException | InvalidKeyException e) {
            logger.error("MinIO-Service-REST failed to upload. See detailed stacktrace: " + e);
            throw new RuntimeException(e);
        }
    }

    public byte[] download(String objectName) {
        try {
            return IOUtils.toByteArray(minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(minIOConfig.getBucketName())
                            .object(objectName)
                            .build()));
        } catch (ServerException | InvalidResponseException | InsufficientDataException | IOException |
                 NoSuchAlgorithmException | InvalidKeyException | ErrorResponseException | XmlParserException |
                 InternalException e) {
            logger.error("MinIO-Service-REST failed to download. See detailed stacktrace: " + e);
            throw new RuntimeException(e);
        }
    }

    public void delete(String objectName){
        try{
            minioClient.removeObject(RemoveObjectArgs.builder().bucket(
                    minIOConfig.getBucketName())
                    .object(objectName)
                    .build()
            );
        }catch(MinioException | IOException | NoSuchAlgorithmException | InvalidKeyException e){
            logger.error("MinIO-Service-REST failed to delete. See detailed stacktrace: " + e);
            throw new RuntimeException(e);
        }
    }
}
