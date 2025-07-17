package com.skryvets.voice;

import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
@Slf4j
class AnalyzerService {

    private final ChatClient chatClient;

    AnalyzerService(ChatClient.Builder chatClient) {
        this.chatClient = chatClient.build();
    }

    public AiAnalysisResponse analyze(String speech) {

        log.info("Speech: {}", speech);

        var analysis = chatClient
            .prompt()
            .system(Constants.ANALYZER_SYSTEM_PROMPT)
            .user(speech)
            .call()
            .entity(AiAnalysisResponse.class);

        log.info("Analysis: {}", analysis);

        return analysis;
    }
}

record AiAnalysisResponse(
    List<String> crutchWords,
    Map<String, Integer> frequency,
    Map<String, List<Integer>> crutchWordOrdinals,
    Integer fluencyScore
) {}