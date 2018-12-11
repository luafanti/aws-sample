package pl.lua.aws.core.service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.IOUtils;
import com.amazonaws.xray.AWSXRay;
import com.amazonaws.xray.entities.Subsegment;
import com.amazonaws.xray.spring.aop.XRayEnabled;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.lua.aws.core.model.UploadEntity;
import pl.lua.aws.core.repository.UploadFileRepository;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@Service
@Slf4j
@XRayEnabled
public class S3ServicesImpl implements S3Services {

    private Logger logger = LoggerFactory.getLogger(S3ServicesImpl.class);

    private static final byte[] NO_FILE = {};

    @Autowired
    private AmazonS3 s3client;

    @Value("${aws.s3.bucket}")
    private String bucketName;

    @Autowired
    UploadFileRepository uploadFileRepository;

    @Override
    public byte[] downloadFile(String keyName) {

        try {

            System.out.println("Downloading an object");
            S3Object s3object = s3client.getObject(new GetObjectRequest(bucketName, keyName));
            InputStream in = s3object.getObjectContent();
            byte[] bytes = IOUtils.toByteArray(in);

            logger.info("SIZE {} ", bytes.length);
            logger.info("===================== Import File - Done! =====================");
            return bytes;

        } catch (AmazonServiceException ase) {
            logger.info("Caught an AmazonServiceException from GET requests, rejected reasons:");
            logger.info("Error Message:    " + ase.getMessage());
            logger.info("HTTP Status Code: " + ase.getStatusCode());
            logger.info("AWS Error Code:   " + ase.getErrorCode());
            logger.info("Error Type:       " + ase.getErrorType());
            logger.info("Request ID:       " + ase.getRequestId());
            return NO_FILE;
        } catch (AmazonClientException ace) {
            logger.info("Caught an AmazonClientException: ");
            logger.info("Error Message: " + ace.getMessage());
            return NO_FILE;
        } catch (IOException e) {
            e.printStackTrace();
            return NO_FILE;
        }


    }

    @Override
    public void uploadFile(MultipartFile file, byte[] bytes) {
        log.info("run - start");
        AWSXRay.beginSegment("Upload-File-Segment");

        try {
            Subsegment subsegment1 = AWSXRay.beginSubsegment("Sleep 1");
            File newFile = new File(file.getOriginalFilename());
            FileUtils.writeByteArrayToFile(newFile, bytes)    ;
            s3client.putObject(new PutObjectRequest(bucketName, file.getOriginalFilename(), newFile));

            AWSXRay.endSubsegment();

            Subsegment subsegment2 = AWSXRay.beginSubsegment("Sleep 2");
            UploadEntity uploadEntity = new UploadEntity();
            uploadEntity.setFileName(file.getOriginalFilename());
            uploadEntity.setFileSize(String.valueOf(file.getSize()));
            uploadFileRepository.save(uploadEntity);
            AWSXRay.endSubsegment();

            logger.info("===================== Upload File - Done! =====================");

        } catch (AmazonServiceException ase) {
            logger.info("Caught an AmazonServiceException from PUT requests, rejected reasons:");
            logger.info("Error Message:    " + ase.getMessage());
            logger.info("HTTP Status Code: " + ase.getStatusCode());
            logger.info("AWS Error Code:   " + ase.getErrorCode());
            logger.info("Error Type:       " + ase.getErrorType());
            logger.info("Request ID:       " + ase.getRequestId());
        } catch (AmazonClientException ace) {
            logger.info("Caught an AmazonClientException: ");
            logger.info("Error Message: " + ace.getMessage());
        } catch (IOException e) {
            logger.info("Error during save files: " + e.getMessage());

        }

        log.info("run - end ");
        AWSXRay.endSegment();
    }

}
