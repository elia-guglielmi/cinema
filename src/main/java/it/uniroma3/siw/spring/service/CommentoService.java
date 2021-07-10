package it.uniroma3.siw.spring.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.spring.model.Commento;
import it.uniroma3.siw.spring.model.Film;
import it.uniroma3.siw.spring.repository.CommentoRepository;

@Service
public class CommentoService {
	
	@Autowired
	private CommentoRepository commentoRepository;
	
	@Transactional
	public List<Commento> commentiPerFilm(Film film){
		return (List<Commento>)commentoRepository.findByFilm(film);
	}
	
	@Transactional
	public Commento inserisci(Commento commento) {
		 return this.commentoRepository.save(commento);
	}
	
	@Transactional
	public void cancellaCommentoPerId(Long id){
		this.commentoRepository.deleteById(id);
	}
	

}
