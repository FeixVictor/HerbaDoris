package br.com.unisc.chatbot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.unisc.chatbot.model.ChatHistory;
import br.com.unisc.chatbot.repository.ChatHistoryRepository;

@Service
public class ChatHistoryService {
	
	@Autowired
    private ChatHistoryRepository chatHistoryRepository;

    public ChatHistory saveChatHistory(ChatHistory chatHistory) {
        return chatHistoryRepository.save(chatHistory);
    }
    
}
