package it.uniroma3.siw.spring.model;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
@Entity
public class Proiezione {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false)
	private String inizio;
	
	@Column(nullable = false)
	private String fine;
	
	@OneToOne
	private Sala sala;
	
	@OneToOne
	private Film film;
	
	@ManyToMany(mappedBy = "proiezioni", cascade = CascadeType.ALL)
	private List<Posto> occupati;
	
	@OneToMany(mappedBy = "proiezione" ,cascade = CascadeType.REMOVE)
	private List<Prenotazione> prenotazioni;
	
	public Proiezione(){
		this.prenotazioni= new ArrayList<Prenotazione>();
		this.occupati=new ArrayList<Posto>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getInizio() {
		return inizio;
	}

	public void setInizio(String inizio) {
		this.inizio = inizio;
	}

	public String getFine() {
		return fine;
	}

	public void setFine(String fine) {
		this.fine = fine;
	}

	public Sala getSala() {
		return sala;
	}

	public void setSala(Sala sala) {
		this.sala = sala;
	}

	public Film getFilm() {
		return film;
	}

	public void setFilm(Film film) {
		this.film = film;
	}

	public List<Posto> getOccupati() {
		return occupati;
	}

	public void setOccupati(List<Posto> occupati) {
		this.occupati = occupati;
	}

	public List<Prenotazione> getPrenotazioni() {
		return prenotazioni;
	}

	public void setPrenotazioni(List<Prenotazione> prenotazioni) {
		this.prenotazioni = prenotazioni;
	}
	
	
}
