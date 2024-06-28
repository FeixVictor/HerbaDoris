package br.com.unisc.chatbot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.unisc.chatbot.model.ChatMessageRequest;
import br.com.unisc.chatbot.service.WatsonAssistantService;

@RestController
public class ChatBotController {

	@Autowired
	WatsonAssistantService watsonService = new WatsonAssistantService();
	
	@PostMapping("/api/chat")
    public String sendMessageToWatson(@RequestBody ChatMessageRequest request) {
		String sessionId = request.getSessionId();
	    String message = request.getMessage();
		
        // Chamar o Watson Assistant para processar a mensagem e obter a resposta
        String responseFromWatson = watsonService.sendMessageToAssistant(message, sessionId);
        return responseFromWatson;
    }
	
	@PostMapping("/api/initializer")
    @ResponseBody
	public String initializeAssistant() {
		return watsonService.initializeAssistant();
	}
	
}
