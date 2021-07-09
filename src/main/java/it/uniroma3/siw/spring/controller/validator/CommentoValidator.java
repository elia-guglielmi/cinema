package it.uniroma3.siw.spring.controller.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import it.uniroma3.siw.spring.model.Commento;
import it.uniroma3.siw.spring.service.CommentoService;

@Component
public class CommentoValidator  implements Validator{
	@Autowired
	private CommentoService commentoService;
	
    private static final Logger logger = LoggerFactory.getLogger(CommentoValidator.class);

	@Override
	public void validate(Object o, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "testo", "required");
		if (!errors.hasErrors()) {
			logger.debug("confermato: valori non nulli");
			Commento commento=(Commento)o;
			if(commento.getTesto().length()>200) {
				errors.reject("size");
			}
		}
		}
		@Override
		public boolean supports(Class<?> aClass) {
			return Commento.class.equals(aClass);
		}

}
