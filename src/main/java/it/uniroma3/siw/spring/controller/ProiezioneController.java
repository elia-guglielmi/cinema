package it.uniroma3.siw.spring.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.RedirectView;

import it.uniroma3.siw.spring.controller.validator.ProiezioneValidator;
import it.uniroma3.siw.spring.model.Proiezione;
import it.uniroma3.siw.spring.model.Sala;
import it.uniroma3.siw.spring.service.FilmService;
import it.uniroma3.siw.spring.service.ProiezioneService;
import it.uniroma3.siw.spring.service.SalaService;

@Controller
public class ProiezioneController {
	
	@Autowired
	private FilmService filmservice;
	@Autowired
	private SalaService salaService;
	@Autowired
	private ProiezioneService proiezioneService;
	@Autowired
	private ProiezioneValidator proiezioneValidator;
	
	@RequestMapping(value = "/admin/proiezione", method = RequestMethod.GET)
    public String proiezioneForm(Model model) {
	 	model.addAttribute("proiezione", new Proiezione());
	 	model.addAttribute("sale", this.salaService.tutti());
	 	model.addAttribute("films", this.filmservice.tutti());
    	return "proiezioneForm.html";
    }

	@RequestMapping(value = "/admin/proiezione", method = RequestMethod.POST)
	 public String addProiezione(@ModelAttribute("proiezione") Proiezione proiezione,
				Model model, BindingResult bindingResult) throws IOException {
		this.proiezioneValidator.validate(proiezione, bindingResult);
		if (!bindingResult.hasErrors()) {
			this.proiezioneService.inserisci(proiezione);
			model.addAttribute("films", this.filmservice.tutti());
			model.addAttribute("sale", this.salaService.tutti());
			model.addAttribute("proiezioni", this.proiezioneService.tutti());
			return "admin/home.html";
		}
		proiezione.setFilm(null);
		proiezione.setSala(null);
		model.addAttribute("films", this.filmservice.tutti());
		model.addAttribute("sale", this.salaService.tutti());
		return "proiezioneForm.html";
		}
	
	 @RequestMapping(value = "/admin/rimuoviproiezione/{id}", method = RequestMethod.GET)
		public RedirectView rimuoviSala(@PathVariable("id") Long id, Model model) {
			proiezioneService.cancellaProiezionePerId(id);
			return new RedirectView("/admin");
		}
	 
	 @RequestMapping(value = "/admin/modificaProiezione/{id}", method = RequestMethod.GET)
		public String modificaProiezione(@PathVariable("id") Long id, Model model) {
			model.addAttribute("proiezione", this.proiezioneService.proiezionePerId(id));
			return "modificaProiezione.html";
		}
		
		@RequestMapping(value = "/admin/modificaProiezione/{id}", method = RequestMethod.POST)
		public RedirectView modificaProiezione(@PathVariable("id") Long id,Model model,String inizio,String fine,String film,String sala) {
			Proiezione proiezione=this.proiezioneService.proiezionePerId(id); 
			
			if(inizio!=null&&!inizio.equals("")) {
				proiezione.setInizio(inizio);
			}
			if(fine!=null&&!fine.equals("")) {
				proiezione.setInizio(fine);
			}
			if(film!=null&&!film.equals("")) {
				if(!this.filmservice.fillmPerTitolo(film).isEmpty())
				proiezione.setFilm(this.filmservice.fillmPerTitolo(film).get(0));
			}
			if(sala!=null&&!sala.equals("")) {
				if(!this.salaService.salaPerNome(sala).isEmpty())
				proiezione.setSala(this.salaService.salaPerNome(sala).get(0));
			}
			this.proiezioneService.inserisci(proiezione);
			
			return new RedirectView("/admin");
		}
	 
}
