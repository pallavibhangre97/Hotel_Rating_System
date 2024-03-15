package com.user_service.helper;

import org.hibernate.annotations.Comment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Component
public class FileUploadHelper {
    //static path
    public final String UPLOAD_DIR = "C:\\Users\\sagar\\IdeaProjects\\Hotel-Rating-microservice\\UserService\\UserService\\src\\main\\resources\\static\\file";
    //dynamic path
  //  public final String UPLOAD_DIR = new ClassPathResource("static/file/").getFile().getAbsolutePath();

    public FileUploadHelper() throws IOException { //getFile() throws exception
    }

    public boolean uploadFile(MultipartFile file) {
        boolean f = false;
        try {
           /* InputStream inputStream = file.getInputStream();
            byte data[] = new byte[inputStream.available()];
            inputStream.read(data);

            //write
            FileOutputStream fos= new FileOutputStream(UPLOAD_DIR+"\\"+file.getOriginalFilename());
            fos.write(data);
            fos.flush();
            inputStream.close();*/

            //using nio package
            Files.copy(file.getInputStream(), Paths.get(UPLOAD_DIR + "\\" + file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);


            f = true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return f;
    }
}
