package it.uniroma3.siw.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.spring.service.FilmService;
import it.uniroma3.siw.spring.service.ProiezioneService;
import it.uniroma3.siw.spring.service.SalaService;

@Controller
public class MainController {
	
	@Autowired
	private FilmService filmservice;
	@Autowired
	private SalaService salaService;
	@Autowired
	private ProiezioneService proiezioneService;
	
	
	
	 @RequestMapping(value = "/", method = RequestMethod.GET)
	    public String getHome(Model model) {
	    		return "index.html";
	    }
	 
	 @RequestMapping(value = "/admin", method = RequestMethod.GET)
	    public String getAdminMenu(Model model) {
		 	model.addAttribute("films", filmservice.tutti());
		 	model.addAttribute("sale", salaService.tutti());
		 	model.addAttribute("proiezioni", this.proiezioneService.tutti());
	    	return "admin/home.html";
	    }

}
