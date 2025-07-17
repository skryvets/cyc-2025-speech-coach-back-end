package com.skryvets.voice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.audio.transcription.AudioTranscriptionPrompt;
import org.springframework.ai.openai.OpenAiAudioTranscriptionModel;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
class TranscribeService {

    private final OpenAiAudioTranscriptionModel openAiAudioTranscriptionModel;

    public String transcribe(Resource audioFile) {

        var request = new AudioTranscriptionPrompt(audioFile);
        var response = openAiAudioTranscriptionModel.call(request);

        log.info("Transcribed: {}", response.getResults());
        return response.getResult().getOutput();
    }

}

