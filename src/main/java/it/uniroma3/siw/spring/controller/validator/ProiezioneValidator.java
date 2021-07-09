package it.uniroma3.siw.spring.controller.validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import it.uniroma3.siw.spring.model.Proiezione;
import it.uniroma3.siw.spring.service.ProiezioneService;



@Component
public class ProiezioneValidator implements Validator{
	
	@Autowired
	private ProiezioneService proiezioneService;
	
    private static final Logger logger = LoggerFactory.getLogger(ProiezioneValidator.class);

	@Override
	public void validate(Object o, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "inizio", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "fine", "required");
		if (!errors.hasErrors()) {
			logger.debug("confermato: valori non nulli");
			Proiezione proiezione=(Proiezione)o;
			if (this.proiezioneService.alreadyExists((Proiezione)o)) {
				logger.debug("e' un duplicato");
				errors.reject("duplicato");
				}
		}
		}
		@Override
		public boolean supports(Class<?> aClass) {
			return Proiezione.class.equals(aClass);
		}

}
