package com.englishaoe.lesson.dto.lesson.variant;

import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * Using for common variants info
 * like show them in list for picking
 * */
@Data
@NoArgsConstructor
public class VariantDTO {
    private Long id;
    private String theme;
    private String imagePath;
    private String creationDate;
    private Boolean isVisible;
    public VariantDTO(Long id, String theme, String imagePath, String creationDate, Boolean isVisible) {
        this.id = id;
        this.theme = theme;
        this.imagePath = imagePath;
        this.isVisible = isVisible;
    }

}
