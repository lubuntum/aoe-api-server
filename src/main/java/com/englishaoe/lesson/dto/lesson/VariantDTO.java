package com.englishaoe.lesson.dto.lesson;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
public class VariantDTO {
    private String theme;
    private String imagePath;
    private String creationDate;
    private MultipartFile variantImg;

}
