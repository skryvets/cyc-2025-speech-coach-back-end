package com.skryvets.voice;

import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@Slf4j
@RequiredArgsConstructor
public class VoiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(VoiceApplication.class, args);
    }

    @RestController
    @CrossOrigin
    @RequiredArgsConstructor
    static class ApiController {

        private final TranscribeService transcribeService;
        private final AnalyzerService analyzerService;

        @PostMapping(value = "/transcribe", consumes = "audio/webm;codecs=opus")
        public AppResponse transcribe(@RequestBody byte[] audioStream) {
            log.info("Received {} bytes", audioStream.length);
            var wrappedResource = new AppByteArrayResource(audioStream);
            log.info("Content length: {}", wrappedResource.contentLength());
            var transcription = transcribeService.transcribe(wrappedResource);
            var analysis = analyzerService.analyze(transcription);
            return
                new AppResponse(
                    transcription,
                    analysis.crutchWords(),
                    analysis.frequency(),
                    analysis.crutchWordOrdinals(),
                    analysis.fluencyScore()
                );
        }
    }
}

record AppResponse(
    String transcription,
    List<String> crutchWords,
    Map<String, Integer> frequency,
    Map<String, List<Integer>> crutchWordOrdinals,
    Integer fluencyScore
) {}
