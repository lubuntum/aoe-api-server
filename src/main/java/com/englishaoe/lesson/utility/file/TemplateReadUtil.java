package com.englishaoe.lesson.utility.file;

import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TemplateReadUtil {

    public static String readTemplate(String folder, String filename) throws IOException {
        Path templatePath = Paths.get(folder, filename);
        return Files.readString(templatePath);
    }
}
