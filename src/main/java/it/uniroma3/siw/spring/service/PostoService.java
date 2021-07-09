package it.uniroma3.siw.spring.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.spring.model.Posto;
import it.uniroma3.siw.spring.repository.PostoRepository;

@Service
public class PostoService {

	@Autowired
	private PostoRepository postoRepository;
	
	@Transactional
	public Posto inserisci(Posto posto) {
		 return this.postoRepository.save(posto);
	}
}
