package com.englishaoe.lesson.database.services;

import com.englishaoe.lesson.database.entity.variants.TaskType;
import com.englishaoe.lesson.database.entity.variants.Variant;
import com.englishaoe.lesson.database.repository.VariantRepository;
import com.englishaoe.lesson.dto.lesson.TaskDTO;
import com.englishaoe.lesson.dto.lesson.VariantThemeDTO;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VariantService {
    @Autowired
    private VariantRepository variantRepository;

    public List<VariantThemeDTO> getAllVariantsDTO(){
        return variantRepository.findAllVariantsThemesData();
    };
    @Transactional(readOnly = true)
    public Variant getVariantById(Long id) {
        Variant variant = variantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("variant not found"));
        variant.getVariantTasks().forEach((task -> {
            task.getTaskType();
        }));
        TaskType test = variant.getVariantTasks().get(0).getTaskType();
        System.out.print(test.getType());
        return variant;
    }
    public List<TaskDTO> getTasksByVariantId(Long variantId){
        return variantRepository.findTasksByVariantId(variantId);
    }

}
