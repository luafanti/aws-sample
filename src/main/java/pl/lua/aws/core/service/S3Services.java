package pl.lua.aws.core.service;

public interface S3Services {
    public  byte[] downloadFile(String keyName);
    public void uploadFile(String keyName, byte[] bytes);
}
