package com.englishaoe.lesson.utility;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Component
public class AudioFileUtil {
    @Value("${audio.folderDir}")
    private String folderDir;

    public String saveAudioFile(MultipartFile file) {
        try{
            File directory = new File(folderDir);
            if (!directory.exists())
                directory.mkdirs();

            String filePath = folderDir + file.getOriginalFilename();
            File destinationFile = new File(filePath);
            file.transferTo(destinationFile);
            return "voices/" + file.getOriginalFilename();
        } catch (IOException e) {
            return e.getMessage();
        }
    }
}
