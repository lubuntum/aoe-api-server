package com.englishaoe.lesson.dto.lesson.variant;

import com.englishaoe.lesson.database.entity.variants.Variant;
import org.springframework.stereotype.Component;

@Component
public class VariantMapper {
    public VariantDTO toDTO(Variant variant) {
        return new VariantDTO(
                variant.getId(),
                variant.getTheme(),
                variant.getImagePath(),
                variant.getCreationDate(),
                variant.getIsVisible());
    }
}
