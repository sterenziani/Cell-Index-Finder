package ar.edu.itba.ss.cellindexmethod.exceptions;

public class BadRandomizeException extends Exception {
    private static final String ERROR_MSG = "Wrong randomize syntax. Should provide 'randomize=true', 'randomize=false' or none at all";

    public BadRandomizeException(){
        super(ERROR_MSG);
    }
}
