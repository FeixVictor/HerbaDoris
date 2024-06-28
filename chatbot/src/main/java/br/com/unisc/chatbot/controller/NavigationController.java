package br.com.unisc.chatbot.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NavigationController {

	@GetMapping("/index")
    public String index() {
        return "index";
    }
	
	@GetMapping("/start-chatbot")
    public String iniciar() {
        return "start-chatbot";
    }

    @GetMapping("/tutorial")
    public String tutorial() {
        return "tutorial";
    }
	
}
