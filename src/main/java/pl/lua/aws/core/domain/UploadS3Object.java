package pl.lua.aws.core.domain;

import lombok.Data;

@Data
public class UploadS3Object {
    private Long id;
    private String fileSize;
    private String fileName;
}
