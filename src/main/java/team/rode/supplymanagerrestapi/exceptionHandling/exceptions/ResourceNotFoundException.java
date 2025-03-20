package team.rode.supplymanagerrestapi.exceptionHandling.exceptions;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String message){
        super(message);
    }
}
