package ar.edu.itba.ss.cellindexmethod.exceptions;

public class BadArgumentFormatException extends Exception{
    private static final String ERROR_MSG = "Argument '%s' is not correctly defined. Try 'argument=value'";

    public BadArgumentFormatException(String badArgument){
        super(String.format(ERROR_MSG, badArgument));
    }
}
