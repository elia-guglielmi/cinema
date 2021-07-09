package it.uniroma3.siw.spring.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.spring.model.Sala;
import it.uniroma3.siw.spring.repository.SalaRepository;
@Service
public class SalaService {
	@Autowired
	private SalaRepository salaRepository;

	@Transactional
	public List<Sala> tutti(){
		return (List<Sala>)salaRepository.findAll();
	}
	
	@Transactional
	public List<Sala> salaPerNome(String nome){
		return (List<Sala>)salaRepository.findByNome(nome);
	}
	
	@Transactional
	public Sala inserisci(Sala sala) {
		 return this.salaRepository.save(sala);
	}
	
	public boolean alreadyExists(Sala s) {
		List<Sala> sale = this.salaRepository.findByNome(s.getNome());
		if (sale.size() > 0)
			return true;
		else 
			return false;
	}
	
	@Transactional
	public Sala salaPerId(Long id) {
		Optional<Sala> optional = salaRepository.findById(id);
		if (optional.isPresent())
			return optional.get();
		else 
			return null;
	}
	
	@Transactional
	public void cancellaSalaPerId(Long id){
		this.salaRepository.deleteById(id);
	}
}
