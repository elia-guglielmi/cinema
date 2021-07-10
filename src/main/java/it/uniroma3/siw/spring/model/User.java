package it.uniroma3.siw.spring.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import it.uniroma3.siw.spring.service.CredentialsService;

@Entity
@Table(name = "users") // cambiamo nome perchè in postgres user e' una parola riservata
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String nome;
	private String cognome;
	private String email;
	
	@OneToMany(mappedBy = "spettatore")
	private List<Prenotazione> prenotazioni;
	
	@OneToMany(mappedBy = "utente")
	private List<Commento> commenti;
	
	public User() {
		this.prenotazioni=new ArrayList<Prenotazione>();
		this.commenti=new ArrayList<Commento>();
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getCognome() {
		return cognome;
	}
	
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public List<Prenotazione> getPrenotazioni() {
		return prenotazioni;
	}

	public void setPrenotazioni(List<Prenotazione> prenotazioni) {
		this.prenotazioni = prenotazioni;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Commento> getCommenti() {
		return commenti;
	}

	public void setCommenti(List<Commento> commenti) {
		this.commenti = commenti;
	}
	
	

}
