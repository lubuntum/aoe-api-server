package com.englishaoe.lesson.services.file;

import com.englishaoe.lesson.database.entity.variants.Task;
import com.englishaoe.lesson.database.entity.variants.TaskTypeEnum;
import com.englishaoe.lesson.database.entity.variants.Variant;
import com.englishaoe.lesson.database.services.VariantService;
import com.englishaoe.lesson.exceptions.RegularException;
import com.englishaoe.lesson.utility.JsonUtil;
import com.englishaoe.lesson.utility.file.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

import static com.englishaoe.lesson.database.entity.variants.TaskTypeEnum.*;
@Service
public class VariantTasksFilesHandlerService {
    @Value("${image.folderDir}")
    private String imageFolderPath;
    @Value("${speaker.folderDir}")
    private String speakerFolderPath;
    @Autowired
    private VariantService variantService;
    public String saveFileForVariantTask(String taskJson, String value, String path, MultipartFile file) {
        try {
            return taskJson.replace(value, FileUtil.saveFileToDir(file, path, true));
        } catch (Exception e) {
            throw new RegularException("Error occurred while adding images", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }
    public String saveImagesForVariantTask(String tasksJson,
                                           MultipartFile secondTaskImage,
                                           MultipartFile fourthTaskImageFirst,
                                           MultipartFile fourthTaskImageSecond){
        try {
            tasksJson = tasksJson.replace("%img", FileUtil.saveFileToDir(secondTaskImage, imageFolderPath, true));
            tasksJson = tasksJson.replace("%firstImg", FileUtil.saveFileToDir(fourthTaskImageFirst, imageFolderPath, true));
            tasksJson = tasksJson.replace("%secondImg", FileUtil.saveFileToDir(fourthTaskImageSecond, imageFolderPath, true));
            return tasksJson;
        }catch (Exception e) {
            throw new RegularException("Error occurred while adding images", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }

    }
    public String saveSpeakerRecordsForVariantTask(String taskJson,
                                                   MultipartFile speakerRecord,
                                                   List<MultipartFile> questionsRecords) {
        try {
            taskJson = taskJson.replace("%speakerRecord", FileUtil.saveFileToDir(speakerRecord, speakerFolderPath, true));
            for(int i = 0; i < questionsRecords.size();i++){//just add if (questionsRecords.get(i) == null) continue;
                if (questionsRecords.get(i) == null) continue;
                taskJson = taskJson.replace("%questionRecord"+i,
                        FileUtil.saveFileToDir(questionsRecords.get(i), speakerFolderPath, true ));
            }
            return taskJson;
        } catch (Exception e) {
            throw new RegularException("Error occurred while adding records", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }
    /**
     * Remove files associated with Variant when Variant is ready for deleting
     * @param variant contains tasks which have needed json
     * @param key name from json which contains needed value
     * @param taskType which task contain needed json (total 4 tasks)
     * @param path absolute path where file located
     * */
    public void deleteFileFromVariantTask(Variant variant, String key, Integer taskType, String path) {
        try {
            Task task = variantService.findTaskFromVariantByType(variant, taskType);
            if (task == null) return;
            String fileName = FileUtil.extractFilename(JsonUtil.getValueByKeyFromJson(task.getTaskContent(), key));
            if(fileName.isBlank()) return;
            FileUtil.deleteFileFromDir(fileName, path);
        } catch (Exception e) {
            System.err.println("Error occurred while deleting file " + e.getMessage());
        }
        //remove img file (also remember path stored like images/fileName, that mean need to delete images/ part
    }
    public void deleteListFilesFromVariantTask(Variant variant, String key, Integer taskType, String path) {
        try {
            Task task = variantService.findTaskFromVariantByType(variant, taskType);
            if (task == null) return;
            List<String> filesPath = JsonUtil.getArrayByKeyFromJson(task.getTaskContent(), key);
            if (filesPath == null || filesPath.isEmpty()) return;
            for (String filePath: filesPath)
                FileUtil.deleteFileFromDir(FileUtil.extractFilename(filePath), path);
        } catch (Exception e) {
            System.err.println("Error occurred while deleting a list of files: " + e.getMessage());
        }

    }
    /**
     * Using for encapsulating variant deleting logic
     * */
    public void deleteAllFilesFromVariantTasks(Variant variant) {
        FileUtil.deleteFileFromDir(FileUtil.extractFilename(variant.getImagePath()), imageFolderPath);
        deleteFileFromVariantTask(variant, "img", SECOND.getTaskType(), imageFolderPath);
        deleteFileFromVariantTask(variant, "firstImg", FOURTH.getTaskType(), imageFolderPath);
        deleteFileFromVariantTask(variant, "secondImg", FOURTH.getTaskType(), imageFolderPath);
        deleteFileFromVariantTask(variant, "speakerRecord", THIRD.getTaskType(), speakerFolderPath);
        deleteListFilesFromVariantTask(variant, "questionsRecords", THIRD.getTaskType(), speakerFolderPath);
    }
    public String updateFileForVariantTask(Variant variant, String tasksJson,
                                           String key, Integer taskType,
                                           String path, MultipartFile file){
        try {
            if (file == null) return tasksJson;
            try {
                deleteFileFromVariantTask(variant, key, taskType, path);
            } catch (Exception e) {
                System.err.println("Can't delete file: " + path);
            }

            return saveFileForVariantTask(tasksJson, JsonUtil.getValueByKeyFromJsonArray(tasksJson, key), path, file);
        } catch (Exception e) {
            System.err.println("Error while updating file: " + e.getMessage());
            return tasksJson;
        }
    }
    public String updateAllFilesForVariantTasks(Variant variant, String tasksJson, MultipartFile secondTaskImage,
                                              MultipartFile fourthTaskImageFirst, MultipartFile fourthTaskImageSecond,
                                              MultipartFile speakerRecord, List<MultipartFile> questionsRecords){
        tasksJson = updateFileForVariantTask(variant, tasksJson, "img",
                TaskTypeEnum.SECOND.getTaskType(), imageFolderPath, secondTaskImage);
        tasksJson = updateFileForVariantTask(variant, tasksJson, "firstImg",
                TaskTypeEnum.FOURTH.getTaskType(), imageFolderPath, fourthTaskImageFirst);
        tasksJson = updateFileForVariantTask(variant, tasksJson, "secondImg",
                TaskTypeEnum.FOURTH.getTaskType(), imageFolderPath, fourthTaskImageSecond);
        tasksJson = updateFileForVariantTask(variant, tasksJson, "speakerRecord",
                THIRD.getTaskType(), speakerFolderPath, speakerRecord);
        Task task = variantService.findTaskFromVariantByType(variant, THIRD.getTaskType());
        List<String> originalFilesPath = JsonUtil.getArrayByKeyFromJson(task.getTaskContent(), "questionsRecords");
        for(int i = 0; i < questionsRecords.size();i++) {
            if (questionsRecords.get(i).getSize() == 0) continue;
            try {
                FileUtil.deleteFileFromDir(FileUtil.extractFilename(originalFilesPath.get(i)), speakerFolderPath);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
            tasksJson = tasksJson.replace("%questionRecord"+i,
                    FileUtil.saveFileToDir(questionsRecords.get(i), speakerFolderPath, true ));
        }
        return tasksJson;
    }


}
