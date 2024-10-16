package com.englishaoe.lesson.database.services;

import com.englishaoe.lesson.database.repository.VariantRepository;
import com.englishaoe.lesson.dto.lesson.VariantDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VariantService {
    @Autowired
    private VariantRepository variantRepository;

    public List<VariantDTO> getAllVariantsDTO(){
        return variantRepository.findAllVariants();
    };

}
