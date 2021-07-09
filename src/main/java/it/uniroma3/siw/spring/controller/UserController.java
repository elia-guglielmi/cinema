package it.uniroma3.siw.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.RedirectView;

import it.uniroma3.siw.spring.model.Credentials;
import it.uniroma3.siw.spring.model.Prenotazione;
import it.uniroma3.siw.spring.model.Sala;
import it.uniroma3.siw.spring.model.User;
import it.uniroma3.siw.spring.service.CredentialsService;
import it.uniroma3.siw.spring.service.UserService;

@Controller
public class UserController {
	
	@Autowired
	private UserService userSerivce;
	@Autowired
	private CredentialsService credentialsService;
	
	
	@RequestMapping(value = "/profilo", method = RequestMethod.GET)
    public String getPrenotazioneForm(Model model) {
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
		User user= credentials.getUser();
		model.addAttribute("user",user);
		if (credentials.getRole().equals(Credentials.ADMIN_ROLE)) {
    		model.addAttribute("autorizzato",true);
        }else {
        	model.addAttribute("autorizzato",false);
        }
    	return "profilo.html";
    }
	
	 @RequestMapping(value = "/modificaUser/{id}", method = RequestMethod.GET)
		public String modificaSala(@PathVariable("id") Long id, Model model) {
			model.addAttribute("user", this.userSerivce.getUser(id));
			return "modificaUser.html";
		}
		
		@RequestMapping(value = "/modificaUser/{id}", method = RequestMethod.POST)
		public RedirectView modificaUser(@PathVariable("id") Long id,Model model,String nome,String cognome,String email) {
			User user=this.userSerivce.getUser(id); 
			
			if(nome!=null&&!nome.equals("")) {
				user.setNome(nome);
			}
			if(cognome!=null&&!cognome.equals("")) {
				user.setCognome(cognome);
			}
			if(email!=null&&!email.equals("")) {
				user.setEmail(email);
			}
			this.userSerivce.saveUser(user);
			
			return new RedirectView("/profilo");
		}

}
