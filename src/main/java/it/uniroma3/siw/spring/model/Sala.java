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
import javax.persistence.OneToMany;

import antlr.collections.impl.LList;
import lombok.Data;


@Entity
public class Sala {

	

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false)
	private String nome;
	
	@OneToMany(mappedBy = "sala", cascade = {CascadeType.REMOVE})
	private List<Posto> posti;
	
	public Sala() {
		this.posti= new ArrayList<Posto>();
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

	public List<Posto> getPosti() {
		return posti;
	}

	public void setPosti(List<Posto> posti) {
		this.posti = posti;
	}
	
	@Override
	public boolean equals(Object arg0) {
		Sala that=(Sala)arg0;
		return this.getId()==that.getId();
	}
}
