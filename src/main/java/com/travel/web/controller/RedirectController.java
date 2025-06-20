package com.travel.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RedirectController {
	
	@GetMapping("/")
	public String defaultPage() {
		return "redirect:/public";
	}

}
