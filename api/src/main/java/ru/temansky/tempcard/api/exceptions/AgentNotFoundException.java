package ru.temansky.tempcard.api.exceptions;

public class AgentNotFoundException  extends RuntimeException{
    public AgentNotFoundException(Long id){
        super("Could not find employee " + id);
    }
}
