package pl.lua.aws.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import pl.lua.aws.core.repository.UploadFileRepository;
import pl.lua.aws.core.model.UploadEntity;
import pl.lua.aws.core.service.S3Services;

import java.io.IOException;

@Controller
public class S3Controller {

    @Autowired
    S3Services s3Services;


    @RequestMapping(path = "/download", method = RequestMethod.GET)
    public ResponseEntity<byte[]> download(String param) throws IOException {

        byte[] result = s3Services.downloadFile(param);
        if (result.length > 0) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("application/octet-stream"));

            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
            return new ResponseEntity<>(result, headers, HttpStatus.OK);

        }
        return null;
    }


    @RequestMapping(value="upload", method = RequestMethod.GET )
    public String upload(){
        return "/upload.html";
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String uploadFile(@RequestParam("file") MultipartFile file) {

        try {
            byte[] result =  file.getBytes();
            s3Services.uploadFile(file,result);
        }
        catch (Exception e) {
            return "redirect:/upload";
        }

        return "redirect:/upload";
    }

}
