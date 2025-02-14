package com.englishaoe.lesson.database.services;

import com.englishaoe.lesson.database.entity.variants.Task;
import com.englishaoe.lesson.database.entity.variants.Variant;
import com.englishaoe.lesson.database.repository.TaskRepository;
import com.englishaoe.lesson.database.repository.TaskTypeRepository;
import com.englishaoe.lesson.database.repository.VariantRepository;
import com.englishaoe.lesson.dto.lesson.TaskDTO;
import com.englishaoe.lesson.dto.lesson.variant.VariantDTO;
import com.englishaoe.lesson.dto.lesson.variant.VariantMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VariantService {
    @Autowired
    private VariantRepository variantRepository;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private TaskTypeRepository taskTypeRepository;
    @Autowired
    private VariantMapper variantMapper;
    public Integer getVisibleVariantsCount(){
        return variantRepository.countVisibleVariants();
    }
    public List<VariantDTO> getAvailableVariantsDTO(int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        return variantRepository.findAllVariantsAvailable(pageable, true);
    }

    public List<VariantDTO> getAllVariantsDTO(){
        return variantRepository.findAllVariantsThemesData();
    };
    public List<VariantDTO> getVisibleVariantsDTO(){
        return variantRepository.findVisibleVariants();
    }
    public Page<VariantDTO> getVariantsByPage(int pageNumber, int size) {
        Pageable pageable = PageRequest.of(pageNumber, size);
        return variantRepository.findVariantsAvailableByPage(pageable, true);
    }
    public VariantDTO updateVariantVisibility(Long variantId, Boolean visibility){
        Variant variant = variantRepository.findById(variantId).orElseThrow();
        variant.setIsVisible(visibility);
        return variantMapper.toDTO(variantRepository.save(variant));
    }
    @Transactional(readOnly = true)
    public Variant getVariantById(Long id) {
        Variant variant = variantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("variant not found"));
        variant.getVariantTasks().forEach((task -> {
            task.getTaskType();
        }));
        //TaskType test = variant.getVariantTasks().get(0).getTaskType();
        return variant;
    }
    public List<Variant> getVariantsByIds(List<Long> variantsIds){
        return variantRepository.findAllById(variantsIds);
    }
    public List<TaskDTO> getTasksByVariantId(Long variantId){
        return variantRepository.findTasksByVariantId(variantId);
    }
    public Variant saveVariant(Variant variant) {
        return variantRepository.save(variant);
    }
    @Transactional
    public void deleteVariantById(Long variantId) {
        variantRepository.deleteById(variantId);
    }
    public void saveTasksByVariant(List<TaskDTO> tasks, Long variantId) {
        for(TaskDTO taskDTO : tasks)  {
            Task task = new Task();
            task.setVariantId(variantId);
            task.setTaskContent(taskDTO.getTaskContent());
            task.setTaskType(taskTypeRepository.findByType(taskDTO.getTaskType()));
            //task.setTaskTypeId(taskType.getId());
            taskRepository.save(task);
        }
    }
    public Variant assembleVariant(String imagePath, String theme, String creationDate) {
        Variant variant = new Variant();
        variant.setTheme(theme);
        variant.setImagePath(imagePath);
        variant.setCreationDate(creationDate);
        variant.setIsVisible(false);
        return variant;
    }
    public List<TaskDTO> assembleTasks(String taskJson){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(
                    taskJson, new TypeReference<List<TaskDTO>>() {});
        } catch (JsonProcessingException e) {
            return null;
        }
    }
    public void saveTask(Task task){
        taskRepository.save(task);
    }
    public Task getTaskById(Long taskId) {
        return taskRepository.findById(taskId).orElse(null);
    }

}
