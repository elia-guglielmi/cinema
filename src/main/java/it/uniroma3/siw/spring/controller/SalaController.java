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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import it.uniroma3.siw.spring.controller.validator.SalaValidator;
import it.uniroma3.siw.spring.model.Film;
import it.uniroma3.siw.spring.model.Posto;
import it.uniroma3.siw.spring.model.Sala;
import it.uniroma3.siw.spring.service.FilmService;
import it.uniroma3.siw.spring.service.PostoService;
import it.uniroma3.siw.spring.service.ProiezioneService;
import it.uniroma3.siw.spring.service.SalaService;
import it.uniroma3.siw.spring.utils.FileUploadUtil;

@Controller
public class SalaController {
	
	@Autowired
	private SalaService salaService;
	@Autowired
	private FilmService filmService;
	@Autowired
	private PostoService postoService;
	@Autowired
	private ProiezioneService proiezioneService;
	
	@Autowired
	private SalaValidator salaValidator;
	
	 @RequestMapping(value = "/admin/sala", method = RequestMethod.GET)
	    public String getAdminMenu(Model model) {
		 	model.addAttribute("sala", new Sala());
	    	return "salaForm.html";
	    }
	 
	 @RequestMapping(value = "/admin/sala", method = RequestMethod.POST)
	    public String addSala(@ModelAttribute("sala") Sala sala,int numPosti,
	    									Model model, BindingResult bindingResult) {
	    	this.salaValidator.validate(sala, bindingResult);
	        if (!bindingResult.hasErrors()) {
	        	Sala salaSalvata=this.salaService.inserisci(sala);
	        	char riga='A';
	        	int colonna=1;
	        	for(int i=0;i<numPosti;i++) {
	        		if(colonna==11) {
	        			riga=(char) ((char)riga+1);
	        			colonna=1;
	        		}
	        		this.postoService.inserisci(new Posto(riga, colonna, salaSalvata));
	        		colonna++;
	        	}
	        	model.addAttribute("films", this.filmService.tutti());
			 	model.addAttribute("sale", salaService.tutti());
			 	model.addAttribute("proiezioni", this.proiezioneService.tutti());
	            return "admin/home.html";
	        }
	        return "salaForm.html";
	    }
	 
	 @RequestMapping(value = "/admin/rimuovisala/{id}", method = RequestMethod.GET)
		public RedirectView rimuoviSala(@PathVariable("id") Long id, Model model) {
			salaService.cancellaSalaPerId(id);
			return new RedirectView("/admin");
		}
	 
	 @RequestMapping(value = "/admin/modificaSala/{id}", method = RequestMethod.GET)
		public String modificaSala(@PathVariable("id") Long id, Model model) {
			model.addAttribute("sala", this.salaService.salaPerId(id));
			return "modificaSala.html";
		}
		
		@RequestMapping(value = "/admin/modificaSala/{id}", method = RequestMethod.POST)
		public RedirectView modificaSala(@PathVariable("id") Long id,Model model,String nome) {
			Sala sala=this.salaService.salaPerId(id); 
			
			if(nome!=null&&!nome.equals("")) {
				sala.setNome(nome);
			}
			this.salaService.inserisci(sala);
			
			return new RedirectView("/admin");
		}

}
