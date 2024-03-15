package com.user_service.controller;

import com.user_service.helper.FileUploadHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileUploadController {
    @Autowired
    private FileUploadHelper fileUploadHelper;

            @PostMapping("/upload-file")//https://youtube.com/watch?v=Sntm1cNLIFk
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
                //in postman select body -form data-in key column right side select file-in value select file from laptop
                //in key column type "file" it is folder name where we want to upload file it will be saved into resources-static-file
        try {
            if (file.isEmpty()) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("file is empty");
            }
            if (!file.getContentType().equals("image/jpeg")) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("file type must be image/jpeg");

            }

//file upload code
            boolean f = fileUploadHelper.uploadFile(file);
            if (f) {
                return ResponseEntity.status(HttpStatus.CREATED).body("File successfully uploaded ");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("something went wrong try again");
    }
}
