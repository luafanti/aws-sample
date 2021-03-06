package pl.lua.aws.core.service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.IOUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.lua.aws.core.domain.UploadS3Object;
import pl.lua.aws.core.model.UploadEntity;
import pl.lua.aws.core.repository.UploadFileRepository;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@Service
@Slf4j
public class S3ServicesImpl implements S3Services {

    private Logger logger = LoggerFactory.getLogger(S3ServicesImpl.class);

    private static final byte[] NO_FILE = {};

    @Autowired
    private AmazonS3 s3client;

    @Value("${aws.s3.bucket}")
    private String mainBucketName;

    @Value("${aws.s3.avatar-bucket}")
    private String avatarBucketName;

    @Value("${aws.s3.tournament-gallery-bucket}")
    private String tournamentGalleryBucketName;



    @Autowired
    UploadFileRepository uploadFileRepository;


    @Override
    public UploadS3Object uploadPlayerAvatar(MultipartFile file) {
        uploadFile(file,avatarBucketName);
        UploadS3Object avatar= new UploadS3Object();
        avatar.setFileName(file.getOriginalFilename());
        avatar.setFileSize(String.valueOf(file.getSize()));
        return avatar;
    }

    @Override
    public UploadS3Object uploadTournamentPhoto(MultipartFile file) {
        uploadFile(file,tournamentGalleryBucketName);
        UploadS3Object photo= new UploadS3Object();
        photo.setFileName(file.getOriginalFilename());
        return photo;
    }

    @Override
    public void deletePlayerAvatar(String fileName) {
        if(fileName!=null && !fileName.isEmpty()){
            deleteFile(fileName,avatarBucketName);
        }
    }



    @Override
    public byte[] downloadFile(String keyName) {

        try {

            System.out.println("Downloading an object");
            S3Object s3object = s3client.getObject(new GetObjectRequest(mainBucketName, keyName));
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
    public void uploadFile(MultipartFile file,String bucketName) {

        if(bucketName==null){
            bucketName = mainBucketName;
        }

        try {
            byte[] bytes =  file.getBytes();
            File newFile = new File(file.getOriginalFilename());
            FileUtils.writeByteArrayToFile(newFile, bytes)    ;
            s3client.putObject(new PutObjectRequest(bucketName, file.getOriginalFilename(), newFile));

//            UploadEntity uploadEntity = new UploadEntity();
//            uploadEntity.setFileName(file.getOriginalFilename());
//            uploadEntity.setFileSize(String.valueOf(file.getSize()));
//            uploadFileRepository.save(uploadEntity);

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

    }


    private void deleteFile(String fileName, String bucketName) {

        try {
            s3client.deleteObject(new DeleteObjectRequest(bucketName, fileName));
            logger.info("===================== Delete File - Done! =====================");

        } catch (AmazonServiceException ase) {
            logger.info("Caught an AmazonServiceException from DELETE requests, rejected reasons:");
            logger.info("Error Message:    " + ase.getMessage());
            logger.info("HTTP Status Code: " + ase.getStatusCode());
            logger.info("AWS Error Code:   " + ase.getErrorCode());
            logger.info("Error Type:       " + ase.getErrorType());
            logger.info("Request ID:       " + ase.getRequestId());
        } catch (AmazonClientException ace) {
            logger.info("Caught an AmazonClientException: ");
            logger.info("Error Message: " + ace.getMessage());

        }
    }


}
