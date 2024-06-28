package br.com.unisc.chatbot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ibm.cloud.sdk.core.http.HttpStatus;
import com.ibm.cloud.sdk.core.security.IamAuthenticator;
import com.ibm.cloud.sdk.core.service.exception.NotFoundException;
import com.ibm.watson.assistant.v2.Assistant;
import com.ibm.watson.assistant.v2.model.CreateSessionOptions;
import com.ibm.watson.assistant.v2.model.MessageInput;
import com.ibm.watson.assistant.v2.model.MessageOptions;
import com.ibm.watson.assistant.v2.model.MessageResponse;
import com.ibm.watson.assistant.v2.model.SessionResponse;

import br.com.unisc.chatbot.model.ChatHistory;

@Service
public class WatsonAssistantService {

	private String version;
	private String apiKey;
    private String assistantId;
    private String serviceUrl;
    
    private Assistant assistant;
    
    @Autowired
    private ChatHistoryService chatHistoryService;
    
    public WatsonAssistantService() {
    	initializeEnvironment();
    }
    
    private void initializeEnvironment () {
    	try {
    	this.apiKey = System.getenv("API_KEY");
    	this.assistantId = System.getenv("ASSISTANT_ID");
    	this.serviceUrl = System.getenv("SERVICE_URL");
    	this.version = System.getenv("VERSION");
    	} catch (NotFoundException nfex) {
    	    // Log the error for debugging
    	    System.out.println("Watson Assistant resource not found: " + nfex.getMessage());

    	}
    }
    
    public String initializeAssistant() {
        IamAuthenticator authenticator = new IamAuthenticator.Builder().apikey(apiKey).build();
        this.assistant = new Assistant(version, authenticator);
        this.assistant.setServiceUrl(serviceUrl);
        
        // Criar uma nova sessão
        CreateSessionOptions createSessionOptions = new CreateSessionOptions.Builder().assistantId(assistantId).build();
        SessionResponse sessionResponse = assistant.createSession(createSessionOptions).execute().getResult();
        
        return sessionResponse.getSessionId();
    }

    public String sendMessageToAssistant(String messageText, String sessionId) {
    	
    	MessageResponse response = null;
    	
        try {
        	
    	// Enviar uma mensagem para o assistente
        MessageInput input = new MessageInput.Builder().text(messageText).build();
        MessageOptions options = new MessageOptions.Builder(assistantId, sessionId).input(input).build();
        response = assistant.message(options).execute().getResult();
        response.getOutput().getGeneric().get(0).text();
        chatHistoryService.saveChatHistory(setChatHistoryModel(sessionId, messageText, response));
        
        } catch (Exception e) {
        	e.printStackTrace();
		}
        
        if(response.getOutput().getGeneric() == null || response == null || response.getOutput() == null) {
        	return "Sem resposta do assistente, por favor reinicie o ChatBot!";
    	}else if (response.getOutput().getGeneric().get(0).text() == null) {
        	return "Por favor reformule a pergunta. Lembre-se que estou sempre aprendendo!";
        } else if (response != null && response.getOutput() != null && !response.getOutput().getGeneric().isEmpty()) {
            return response.getOutput().getGeneric().get(0).text();
        } else {
            return "Sem resposta do assistente, por favor reinicie o ChatBot!";
        }
        
    }
    
    private ChatHistory setChatHistoryModel (String sessionId, String messageInput, MessageResponse response) {
    	
    	ChatHistory chatHistoryModel = new ChatHistory();
    	
    	chatHistoryModel.setSessionId(sessionId);
    	chatHistoryModel.setUserMessage(messageInput);
    	chatHistoryModel.setChatbotMessage(response.getOutput().getGeneric().get(0).text());
    	
		return chatHistoryModel;
    	
    }
}
