package ar.edu.itba.ss.cellindexmethod.exceptions;

public class CannotOpenFileException extends Exception{
    private static final String ERROR_MSG = "Failed to open path: '%s'";

    public CannotOpenFileException(String path){
        super(String.format(ERROR_MSG, path));
    }
}
