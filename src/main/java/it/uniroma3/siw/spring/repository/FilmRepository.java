package it.uniroma3.siw.spring.repository;

import java.util.List;

import org.hibernate.annotations.OrderBy;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import it.uniroma3.siw.spring.model.Film;


public interface FilmRepository extends CrudRepository <Film, Long>{

	public List<Film> findByTitolo(String titolo);

	public void deleteById(Long id);
	
	@Query(value="select f.* from Film f where f.titolo like %:keyword% or f.nomeregista like %:keyword% or f.cognomeregista like %:keyword% ", nativeQuery = true)
	public List<Film> findByKeyword(@Param("keyword") String keyword);
	
}
