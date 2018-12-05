package pl.lua.aws.core.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "upload_files")
public class UploadEntity {

    @Id
    @GeneratedValue
    private Long id;
    private String fileSize;
    private String fileName;
}
