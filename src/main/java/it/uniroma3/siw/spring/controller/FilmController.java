package it.uniroma3.siw.spring.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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

import it.uniroma3.siw.spring.controller.validator.FilmValidator;
import it.uniroma3.siw.spring.model.Commento;
import it.uniroma3.siw.spring.model.Credentials;
import it.uniroma3.siw.spring.model.Film;
import it.uniroma3.siw.spring.model.Proiezione;
import it.uniroma3.siw.spring.service.CredentialsService;
import it.uniroma3.siw.spring.service.FilmService;
import it.uniroma3.siw.spring.service.PostoService;
import it.uniroma3.siw.spring.service.ProiezioneService;
import it.uniroma3.siw.spring.service.SalaService;
import it.uniroma3.siw.spring.utils.FileUploadUtil;
import lombok.*;

@Controller
public class FilmController {
	
	@Autowired
	private FilmService filmservice;
	@Autowired
	private SalaService salaService;
	@Autowired
	private ProiezioneService proiezioneService;
	@Autowired
	private CredentialsService credentialsService;
	
	@Autowired
	private FilmValidator filmValidator;
	
	 @RequestMapping(value = "/admin/film", method = RequestMethod.GET)
	    public String getAdminMenu(Model model) {
		 	model.addAttribute("film", new Film());
	    	return "filmForm.html";
	    }
	 
	 @RequestMapping(value = "/spettacoli", method = RequestMethod.GET)
	    public String getSpettacoli(Model model) {
		 	model.addAttribute("films", filmservice.tutti());
	    	return "spettacoli.html";
	    }
	 
	 @RequestMapping(value = "/admin/film", method = RequestMethod.POST)
	 public String addFilm(@ModelAttribute("film") Film film,@RequestParam("file")MultipartFile file,
				Model model, BindingResult bindingResult) throws IOException {
		String fileName = "";
		if(file!=null) {
		fileName = StringUtils.cleanPath(file.getOriginalFilename());
	    }
		 film.setCopertina(fileName);
		this.filmValidator.validate(film, bindingResult);
		if (!bindingResult.hasErrors()) {
			Film savedFilm=this.filmservice.inserisci(film);
		    String uploadDir = "src/main/resources/static/images";
		    FileUploadUtil.saveFile(uploadDir, fileName, file);
			model.addAttribute("films", this.filmservice.tutti());
		 	model.addAttribute("sale", salaService.tutti());
		 	model.addAttribute("proiezioni", this.proiezioneService.tutti());
			return "admin/home.html";
		}
		
		return "filmForm.html";
		}
	 
	 @RequestMapping(value = "/admin/rimuovifilm/{id}", method = RequestMethod.GET)
		public RedirectView rimuoviFilm(@PathVariable("id") Long id, Model model) {
			String fileName=filmservice.filmPerId(id).getCopertina();
			String uploadDir = "src/main/resources/static/images";
			try {
				FileUploadUtil.deleteFile(uploadDir, fileName);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			filmservice.cancellaFilmPerId(id);
			return new RedirectView("/admin");
		}
	 
	 @RequestMapping(value = "/admin/modificaFilm/{id}", method = RequestMethod.GET)
		public String modificaFilm(@PathVariable("id") Long id, Model model) {
			model.addAttribute("film", this.filmservice.filmPerId(id));
			return "modificaFilm.html";
		}
		
		@RequestMapping(value = "/admin/modificaFilm/{id}", method = RequestMethod.POST)
		public RedirectView modificaFilm(@PathVariable("id") Long id,Model model,String titolo,String nomeregista,String cognomeregista,
				String descrizione,@RequestParam("file")MultipartFile file) {
			Film film=this.filmservice.filmPerId(id);
			if(file!=null&&!file.isEmpty()) {
				String uploadDir = "src/main/resources/static/images";
				try {
					FileUploadUtil.deleteFile(uploadDir, film.getCopertina());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String fileName = StringUtils.cleanPath(file.getOriginalFilename());
				 film.setCopertina(fileName);
				 try {
					FileUploadUtil.saveFile(uploadDir, fileName, file);
				} catch (IOException e) {
					e.printStackTrace();
				}
			    }
			if(titolo!=null&&!titolo.equals("")) {
				film.setTitolo(titolo);
			}
			if(descrizione!=null&&!descrizione.equals("")) {
				film.setDescrizione(descrizione);
			}
			if(nomeregista!=null&&!nomeregista.equals("")) {
				film.setNomeregista(nomeregista);
			}
			if(cognomeregista!=null&&!cognomeregista.equals("")) {
				film.setCognomeregista(descrizione);
			}
			
			this.filmservice.inserisci(film);
			
			return new RedirectView("/admin");
		}
	 
	 
	 
	 @RequestMapping(value = "/mattina", method = RequestMethod.GET)
	    public String getSpettacoliMattina(Model model) {
		 List<Film> films=new ArrayList<Film>();
		 List<Proiezione> proiezioni=new ArrayList<Proiezione>();
		 	for(Proiezione p: proiezioneService.tutti()) {
		 		if(p.getInizio().compareTo("14:00")<0) {
		 			if(!films.contains(p.getFilm()))
		 			films.add(p.getFilm());
		 			proiezioni.add(p);
		 		}
		 	}
		 	model.addAttribute("films", films);
		 	model.addAttribute("proiezioneOrario", proiezioni);
	    	return "spettacoli.html";
	    }
	 
	 @RequestMapping(value = "/pomeriggio", method = RequestMethod.GET)
	    public String getSpettacoliPomeriggio(Model model) {
		 List<Film> films=new ArrayList<Film>();
		 List<Proiezione> proiezioni=new ArrayList<Proiezione>();
		 	for(Proiezione p: proiezioneService.tutti()) {
		 		if(p.getInizio().compareTo("14:00")>0&&p.getInizio().compareTo("18:00")<0) {
		 			if(!films.contains(p.getFilm()))
		 			films.add(p.getFilm());
		 			proiezioni.add(p);
		 		}
		 	}
		 	model.addAttribute("films", films);
		 	model.addAttribute("proiezioneOrario", proiezioni);
	    	return "spettacoli.html";
	    }
	 
	 @RequestMapping(value = "/sera", method = RequestMethod.GET)
	    public String getSpettacoliSera(Model model) {
		 List<Film> films=new ArrayList<Film>();
		 List<Proiezione> proiezioni=new ArrayList<Proiezione>();
		 	for(Proiezione p: proiezioneService.tutti()) {
		 		if(p.getInizio().compareTo("18:00")>0) {
		 			if(!films.contains(p.getFilm()))
		 			films.add(p.getFilm());
		 			proiezioni.add(p);
		 		}
		 	}
		 	model.addAttribute("films", films);
		 	model.addAttribute("proiezioneOrario", proiezioni);
	    	return "spettacoli.html";
	    }
	 
	 @RequestMapping(value = "/film/{id}", method = RequestMethod.GET)
		public String getFilm(@PathVariable("id") Long id, Model model) {
			model.addAttribute("film", this.filmservice.filmPerId(id));
			model.addAttribute("nuovoCommento", new Commento());
			UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
			if (credentials.getRole().equals(Credentials.ADMIN_ROLE)) {
	    		model.addAttribute("autorizzato",true);
	        }else {
	        	model.addAttribute("autorizzato",false);
	        }
			return "film.html";
		}
	 
	 @RequestMapping(value = "/cerca", method = RequestMethod.GET)
		public String cercaFilm(String keyword, Model model) {
			model.addAttribute("films", this.filmservice.fillmPerKeyword(keyword));
			return "spettacoli.html";
		}

}
