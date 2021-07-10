package it.uniroma3.siw.spring.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.RedirectView;

import it.uniroma3.siw.spring.controller.validator.CommentoValidator;
import it.uniroma3.siw.spring.model.Commento;
import it.uniroma3.siw.spring.model.Credentials;
import it.uniroma3.siw.spring.model.Film;
import it.uniroma3.siw.spring.model.User;
import it.uniroma3.siw.spring.service.CommentoService;
import it.uniroma3.siw.spring.service.CredentialsService;
import it.uniroma3.siw.spring.service.FilmService;

@Controller
public class CommentoController {
	
	@Autowired
	private CommentoService commentoService;
	@Autowired
	private CommentoValidator commentoValidator;
	@Autowired
	private FilmService filmService;
	@Autowired
	private CredentialsService credentialsService;
	
	 @RequestMapping(value = "/commento/{id}", method = RequestMethod.POST)
		public String nuovoCommento(@ModelAttribute("commento") Commento commento,@PathVariable("id") Long id, Model model, BindingResult bindingResult) throws IOException{
			Film film = this.filmService.filmPerId(id);
			UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
			this.commentoValidator.validate(commento, bindingResult);
			if (!bindingResult.hasErrors()) {
				commento.setFilm(film);
				User user= credentials.getUser();
				commento.setUtente(user);
				this.commentoService.inserisci(commento);
			}
			model.addAttribute("film", film);
			model.addAttribute("nuovoCommento", new Commento());
			if (credentials.getRole().equals(Credentials.ADMIN_ROLE)) {
	    		model.addAttribute("autorizzato",true);
	        }else {
	        	model.addAttribute("autorizzato",false);
	        }
			return "film.html";
		}
	 
	 @RequestMapping(value = "/rimuovicommento/{idFilm}/{id}", method = RequestMethod.GET)
		public String rimuoviCommento(@ModelAttribute("commento") Commento commento,@PathVariable("id") Long id,@PathVariable("idFilm") Long idFilm, Model model, BindingResult bindingResult) throws IOException{
			Film film = this.filmService.filmPerId(idFilm);
			UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
			this.commentoService.cancellaCommentoPerId(id);
			model.addAttribute("film", film);
			model.addAttribute("nuovoCommento", new Commento());
			if (credentials.getRole().equals(Credentials.ADMIN_ROLE)) {
	    		model.addAttribute("autorizzato",true);
	        }else {
	        	model.addAttribute("autorizzato",false);
	        }
			return "film.html";
		}
	 

}
