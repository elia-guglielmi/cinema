package it.uniroma3.siw.spring.repository;

import java.time.LocalTime;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.spring.model.Film;
import it.uniroma3.siw.spring.model.Proiezione;

public interface ProiezioneRepository extends CrudRepository <Proiezione, Long>{
	
	public List<Proiezione> findByFilm(Film film);
	
	public List<Proiezione> findByInizio(LocalTime inizio);
	
	@Override
	public void deleteById(Long id);
}
