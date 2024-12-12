package com.englishaoe.lesson.dto.lesson;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * Using for common variants info
 * like show them in list for picking
 * */
@Data
@NoArgsConstructor
public class VariantThemeDTO {
    private Long id;
    private String theme;
    private String imagePath;
    public VariantThemeDTO(Long id, String theme, String imagePath) {
        this.id = id;
        this.theme = theme;
        this.imagePath = imagePath;
    }

}
