package br.com.unisc.chatbot.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class ChatHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long messageId;
	private String sessionId;
	@Column(length = 2048)
	private String userMessage;
	@Column(length = 2048)
	private String chatbotMessage;
	
	public ChatHistory() {
		
	}
	
	public ChatHistory(Long messageId, String sessionId, String userMessage, String chatbotMessage) {
		super();
		this.messageId = messageId;
		this.sessionId = sessionId;
		this.userMessage = userMessage;
		this.chatbotMessage = chatbotMessage;
	}
	
	public Long getMessageId() {
		return messageId;
	}
	
	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}
	
	public String getSessionId() {
		return sessionId;
	}
	
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	
	public String getUserMessage() {
		return userMessage;
	}
	
	public void setUserMessage(String userMessage) {
		this.userMessage = userMessage;
	}
	
	public String getChatbotMessage() {
		return chatbotMessage;
	}
	
	public void setChatbotMessage(String chatbotMessage) {
		this.chatbotMessage = chatbotMessage;
	}
	
}
