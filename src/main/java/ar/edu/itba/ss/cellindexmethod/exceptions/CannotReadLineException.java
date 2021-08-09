package ar.edu.itba.ss.cellindexmethod.exceptions;

public class CannotReadLineException extends Exception{
    private static final String ERROR_MSG = "Error found in line %d in file '%s'";

    public CannotReadLineException(String path, long line){
        super(String.format(ERROR_MSG, line, path));
    }
}
