package com.englishaoe.lesson.api.transcribe.services;

import com.assemblyai.api.AssemblyAI;
import com.assemblyai.api.AssemblyAIBuilder;
import com.assemblyai.api.resources.files.types.UploadedFile;
import com.assemblyai.api.resources.transcripts.requests.TranscriptParams;
import com.assemblyai.api.resources.transcripts.types.Transcript;
import com.assemblyai.api.resources.transcripts.types.TranscriptStatus;
import com.englishaoe.lesson.api.transcribe.APITranscribe;
import com.englishaoe.lesson.api.transcribe.TranscribeFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Paths;

@Service("assemblyai")
public class AssemblyAITranscribe implements APITranscribe {
    @Value("${TRANSCRIPTION_API_KEY}")
    private String transcriptionApiKey;
    @Value("${audio.folderDir}")
    private String voicesDir;
    @Override
    public String transcribe(String relativePath) throws Exception  {
        try {
            AssemblyAI client = AssemblyAI.builder()
                    .apiKey(transcriptionApiKey)
                    .build();
            String fullPath = voicesDir + Paths.get(relativePath).getFileName().toString();;
            File file = new File(fullPath);
            UploadedFile uploadedFile = client.files().upload(file);
            String fileUrl = uploadedFile.getUploadUrl();
            TranscriptParams transcriptParams = TranscriptParams.builder()
                    .audioUrl(fileUrl)
                    .build();
            Transcript transcript = client.transcripts().transcribe(transcriptParams);
            if (transcript.getStatus() == TranscriptStatus.ERROR) {
                throw new Exception("Transcript failed with error: " + transcript.getError().get());
            }
            return transcript.getText().orElse(null);
        } catch (Exception e) {
            throw e;//just pass to parent method
        }
    }
}
