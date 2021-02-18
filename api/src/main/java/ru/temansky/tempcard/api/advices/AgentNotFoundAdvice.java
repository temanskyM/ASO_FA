package ru.temansky.tempcard.api.advices;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.temansky.tempcard.api.exceptions.AgentNotFoundException;

@ControllerAdvice
public class AgentNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(AgentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String agentNotFoundHandler(AgentNotFoundException ex) {
        return ex.getMessage();
    }
}
