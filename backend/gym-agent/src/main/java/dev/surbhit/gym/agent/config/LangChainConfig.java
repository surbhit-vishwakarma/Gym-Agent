package dev.surbhit.gym.agent.config;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.service.AiServices;
import dev.surbhit.gym.agent.agent.TrainerBrain;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LangChainConfig {

    @Bean
    public ChatModel chatModel(
            @Value("${ollama.base-url}") String baseUrl,
            @Value("${ollama.model}") String model,
            @Value("${ollama.temperature}") double temperature
    ) {
        return OllamaChatModel.builder()
                .baseUrl(baseUrl)
                .modelName(model)
                .temperature(temperature)
                .build();
    }

    @Bean
    TrainerBrain trainerBrain(ChatModel chatModel) {
        return AiServices.builder(TrainerBrain.class)
                .chatModel(chatModel)
                .build();
    }
}