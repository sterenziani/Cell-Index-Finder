package ar.edu.itba.ss.cellindexmethod.exceptions;

public class MissingAttributeException extends Exception{
    private static final String ERROR_MSG = "Missing '%s' in line %d in file '%s'";

    public MissingAttributeException(String path, long line, String attr){
        super(String.format(ERROR_MSG, attr, line, path));
    }
}
