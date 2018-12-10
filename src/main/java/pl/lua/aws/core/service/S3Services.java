package pl.lua.aws.core.service;

import org.springframework.web.multipart.MultipartFile;

public interface S3Services {
    public  byte[] downloadFile(String keyName);
    public void uploadFile(MultipartFile file, byte[] bytes);
}
