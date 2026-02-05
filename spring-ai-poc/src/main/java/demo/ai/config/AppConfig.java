package demo.ai.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.ChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class AppConfig {
	
	@Value("${chat.memory.max-messages}")
	int maxMessages;
	
	@Autowired Environment env;
	
	@Bean 
	ChatClient chatClient(
	        ChatClient.Builder chatClientBuilder, 
	        VectorStore vectorStore,
	        ChatMemory chatMemory
	        ) {
	  
	  return chatClientBuilder
	      .defaultAdvisors(
	          MessageChatMemoryAdvisor.builder(chatMemory).build(),
	          QuestionAnswerAdvisor.builder(vectorStore)
	          		.searchRequest(SearchRequest.builder().build()).build()	          		
	      )
	      .build();
	}
	
	@Bean
	ChatMemory chatMemory(ChatMemoryRepository chatMemoryRepository) {
	  return MessageWindowChatMemory.builder()
	      .chatMemoryRepository(chatMemoryRepository)
	      .maxMessages(maxMessages)
	      .build();
	}
}
