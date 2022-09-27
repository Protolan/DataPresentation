package Exceptions;

public class WrongPositionException extends RuntimeException{
    public WrongPositionException(String errorMessage){
        super(errorMessage);
    }

}
