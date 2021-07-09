package it.uniroma3.siw.spring.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.spring.model.Commento;
import it.uniroma3.siw.spring.model.Credentials;
import it.uniroma3.siw.spring.model.Film;
import it.uniroma3.siw.spring.model.User;

public interface CommentoRepository extends CrudRepository<Commento, Long> {
	
	public List<Commento> findByFilm(Film film);
	
	public List<Commento> findByUtente(User utente);
	
	public Optional<Commento> findById(Long id);
	
	public void deleteById(Long id);

}
