package com.englishaoe.lesson.utility.file;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
public class FileUtil {
    public static String saveFileToDir(MultipartFile file, String folderPath, String staticPath){
        try{
            File directory = new File(folderPath);
            if (!directory.exists())
                directory.mkdirs();
            String filePath = folderPath + file.getOriginalFilename();
            File destinationFile = new File(filePath);
            file.transferTo(destinationFile);
            return staticPath + file.getOriginalFilename();
        } catch (IOException e) {
            return e.getMessage();
        }
    }
}
