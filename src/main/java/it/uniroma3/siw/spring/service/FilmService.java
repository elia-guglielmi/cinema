package it.uniroma3.siw.spring.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.spring.model.Film;
import it.uniroma3.siw.spring.repository.FilmRepository;

@Service
public class FilmService {
	@Autowired
	private FilmRepository filmRepository;
	
	@Transactional
	public List<Film> tutti(){
		return (List<Film>)filmRepository.findAll();
	}
	
	@Transactional
	public List<Film> fillmPerTitolo(String titolo){
		return (List<Film>)filmRepository.findByTitolo(titolo);
	}
	
	@Transactional
	public List<Film> fillmPerKeyword(String keyword){
		return (List<Film>)filmRepository.findByKeyword(keyword);
	}

	@Transactional
	public Film inserisci(Film film) {
		 return this.filmRepository.save(film);
	}
	
	public boolean alreadyExists(Film f) {
		List<Film> films = this.filmRepository.findByTitolo(f.getTitolo());
		if (films.size() > 0)
			return true;
		else 
			return false;
	}
	
	@Transactional
	public Film filmPerId(Long id) {
		Optional<Film> optional = filmRepository.findById(id);
		if (optional.isPresent())
			return optional.get();
		else 
			return null;
	}
	
	@Transactional
	public void cancellaFilmPerId(Long id){
		this.filmRepository.deleteById(id);
	}
	
	
}
