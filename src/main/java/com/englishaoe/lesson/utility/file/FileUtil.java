package com.englishaoe.lesson.utility.file;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtil {
    public static String saveFileToDir(MultipartFile file, String folderPath){
        try{
            File directory = new File(folderPath);
            if (!directory.exists())
                directory.mkdirs();
            String filePath = folderPath + file.getOriginalFilename();
            File destinationFile = new File(filePath);
            file.transferTo(destinationFile);
            return getStaticPath(folderPath) + file.getOriginalFilename();
        } catch (IOException e) {
            return e.getMessage();
        }
    }
    private static String getStaticPath(String absolutePath){
        String path = Paths.get(absolutePath).getFileName().toString();
        return (!path.endsWith("/")) ? path+"/" : path;
    }
}
