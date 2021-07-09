package it.uniroma3.siw.spring.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.RedirectView;

import it.uniroma3.siw.spring.model.Credentials;
import it.uniroma3.siw.spring.model.Posto;
import it.uniroma3.siw.spring.model.Prenotazione;
import it.uniroma3.siw.spring.model.Proiezione;
import it.uniroma3.siw.spring.model.User;
import it.uniroma3.siw.spring.service.CredentialsService;
import it.uniroma3.siw.spring.service.PostoService;
import it.uniroma3.siw.spring.service.PrenotazioneService;
import it.uniroma3.siw.spring.service.ProiezioneService;
import it.uniroma3.siw.spring.service.UserService;

@Controller
public class PrenotazioneController {
	
	@Autowired
	private ProiezioneService proiezioneService;
	@Autowired
	private PostoService postoService;
	
	@Autowired
	private PrenotazioneService prenotazioneService;
	
	@Autowired
	private UserService userSerivce;
	@Autowired
	private CredentialsService credentialsService;
	
	 private final Logger logger = LoggerFactory.getLogger(this.getClass());

	
	@RequestMapping(value = "/prenotazione/{id}", method = RequestMethod.GET)
    public String getPrenotazioneForm(@PathVariable("id") Long id, Model model) {
    	model.addAttribute("proiezione", this.proiezioneService.proiezionePerId(id));
    	model.addAttribute("prenotazione",new Prenotazione());
    	return "prenotazione.html";
    }
	
	@RequestMapping(value = "/prenotazione/{id}", method = RequestMethod.POST)
    public RedirectView prenota(@ModelAttribute("prenotazione") Prenotazione prenotazione,@PathVariable("id") Long id,Model model) {
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
		User user= credentials.getUser();
		prenotazione.setSpettatore(user);
		prenotazione.setProiezione(this.proiezioneService.filmPerId(id));
		prenotazione.getProiezione().getOccupati().add(prenotazione.getPosto());
		prenotazione.getPosto().getProiezioni().add(prenotazione.getProiezione());
		this.postoService.inserisci(prenotazione.getPosto());
		this.proiezioneService.inserisci(prenotazione.getProiezione());
		this.prenotazioneService.inserisci(prenotazione);
    	return new RedirectView ("/");
    }
}