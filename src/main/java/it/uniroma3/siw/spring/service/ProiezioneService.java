package it.uniroma3.siw.spring.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.spring.model.Film;
import it.uniroma3.siw.spring.model.Proiezione;
import it.uniroma3.siw.spring.repository.ProiezioneRepository;

@Service
public class ProiezioneService {

	@Autowired
	private ProiezioneRepository proiezioneRepository; 
	
	@Transactional
	public List<Proiezione> tutti(){
		return (List<Proiezione>)proiezioneRepository.findAll();
	}
	
	@Transactional
	public List<Proiezione> proiezionePerFilm(Film film){
		return (List<Proiezione>)proiezioneRepository.findByFilm(film);
	}
	
	@Transactional
	public Proiezione proiezionePerId(Long id) {
		Optional<Proiezione> optional = proiezioneRepository.findById(id);
		if (optional.isPresent())
			return optional.get();
		else 
			return null;
	}
	
	@Transactional
	public Proiezione inserisci(Proiezione proiezione) {
		 return this.proiezioneRepository.save(proiezione);
	}
	
	public boolean alreadyExists(Proiezione p) {
		List<Proiezione> proiezioni = this.proiezioneRepository.findByFilm(p.getFilm());
		if (proiezioni.size() > 0) {
			for(Proiezione pro: proiezioni) {
				if(pro.getInizio().equals(p.getInizio())&&pro.getSala().equals(p.getSala())) {
					return true;
				}
			}
		}
		return false;
	}
	
	@Transactional
	public Proiezione filmPerId(Long id) {
		Optional<Proiezione> optional = proiezioneRepository.findById(id);
		if (optional.isPresent())
			return optional.get();
		else 
			return null;
	}
	
	@Transactional
	public void cancellaProiezionePerId(Long id){
		this.proiezioneRepository.deleteById(id);
	}

}
