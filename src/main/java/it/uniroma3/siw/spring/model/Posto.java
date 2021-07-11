package it.uniroma3.siw.spring.model;

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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Data;


@Entity
public class Posto {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column()
	private char riga;
	private int colonna;
	
	@ManyToMany
	private List<Proiezione> proiezioni;
	
	@OneToMany(mappedBy = "posto",cascade = CascadeType.REMOVE)
	private List<Prenotazione>prenotazioni;
	
	@ManyToOne
	private Sala sala;
	
	public Posto() {
		this.proiezioni=new ArrayList<Proiezione>();
		this.prenotazioni=new ArrayList<Prenotazione>();
		
	}
	
	

	public Posto(char riga, int colonna, Sala sala) {
		this.proiezioni=new ArrayList<Proiezione>();
		this.proiezioni=new ArrayList<Proiezione>();
		this.riga = riga;
		this.colonna = colonna;
		this.sala = sala;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public char getRiga() {
		return riga;
	}

	public void setRiga(char riga) {
		this.riga = riga;
	}

	public int getColonna() {
		return colonna;
	}

	public void setColonna(int colonna) {
		this.colonna = colonna;
	}

	public Sala getSala() {
		return sala;
	}

	public void setSala(Sala sala) {
		this.sala = sala;
	}



	public List<Proiezione> getProiezioni() {
		return proiezioni;
	}



	public void setProiezioni(List<Proiezione> proiezioni) {
		this.proiezioni = proiezioni;
	}



	public List<Prenotazione> getPrenotazioni() {
		return prenotazioni;
	}



	public void setPrenotazioni(List<Prenotazione> prenotazioni) {
		this.prenotazioni = prenotazioni;
	}


	
}
