package it.uniroma3.siw.spring.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Entity
@Getter @Setter @NoArgsConstructor 
public class Film {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false)
	private String titolo;
	
	@Lob
	@Column(nullable = false)
	private String descrizione;
	
	@Column(length = 64)
	private String copertina;
	
	@Column(nullable = false)
	private String nomeregista;
	
	@Column(nullable = false)
	private String cognomeregista;
	
	@OneToMany(mappedBy = "film", cascade = {CascadeType.REMOVE})
	private List<Proiezione> proiezioni;
	
	@OneToMany(mappedBy = "film", cascade = {CascadeType.REMOVE})
	private List<Commento> commenti;
	
	public Film() {
		this.proiezioni=new ArrayList<Proiezione>();
		this.commenti=new ArrayList<Commento>();
	}

	public List<Commento> getCommenti() {
		return commenti;
	}

	public void setCommenti(List<Commento> commenti) {
		this.commenti = commenti;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getCopertina() {
		return copertina;
	}

	public void setCopertina(String copertina) {
		this.copertina = copertina;
	}

	public String getNomeregista() {
		return nomeregista;
	}

	public void setNomeregista(String nomeregista) {
		this.nomeregista = nomeregista;
	}

	public String getCognomeregista() {
		return cognomeregista;
	}

	public void setCognomeregista(String cognomeregista) {
		this.cognomeregista = cognomeregista;
	}

	public List<Proiezione> getProiezioni() {
		return proiezioni;
	}

	public void setProiezioni(List<Proiezione> proiezioni) {
		this.proiezioni = proiezioni;
	}
	
	
	

}
