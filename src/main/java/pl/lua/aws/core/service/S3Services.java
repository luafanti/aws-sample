package pl.lua.aws.core.service;

import org.springframework.web.multipart.MultipartFile;
import pl.lua.aws.core.domain.UploadS3Object;

public interface S3Services {
    byte[] downloadFile(String keyName);
    void uploadFile(MultipartFile file,String bucketName);
    UploadS3Object uploadPlayerAvatar(MultipartFile file);
    void deletePlayerAvatar(String fileName);
}
