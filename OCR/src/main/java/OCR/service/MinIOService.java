package OCR.service;


import io.minio.*;
import io.minio.errors.*;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import OCR.config.MinIOConfig;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
public class MinIOService {
    private final MinIOConfig minIOConfig;
    private final MinioClient minioClient;

    Logger logger = LogManager.getLogger();

    @Autowired
    MinIOService(MinIOConfig minIOConfig, MinioClient minioClient) {
        this.minIOConfig = minIOConfig;
        this.minioClient = minioClient;
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
            logger.error("MinIO-Service failed. See detailed stacktrace: " + e);
            throw new RuntimeException(e);
        }
    }
}
