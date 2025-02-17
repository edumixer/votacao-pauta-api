package com.votacaopauta.validation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = { RespostaVotoValidation.class })
public @interface RespostaVoto {

	String message() default "Voto inválido. Informe Sim ou Não.";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };

}
