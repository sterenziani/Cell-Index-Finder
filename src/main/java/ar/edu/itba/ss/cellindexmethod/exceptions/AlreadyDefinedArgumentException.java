package ar.edu.itba.ss.cellindexmethod.exceptions;

public class AlreadyDefinedArgumentException extends Exception{
    private static final String ERROR_MSG = "Duplicate '%s' as it was previously defined as '%s'. Please remove unwanted definition";

    public AlreadyDefinedArgumentException(String secondDefinition, String alreadySetValue){
        super(String.format(ERROR_MSG, secondDefinition, alreadySetValue));
    }
}
