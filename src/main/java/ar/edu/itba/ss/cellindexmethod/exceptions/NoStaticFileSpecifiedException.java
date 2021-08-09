package ar.edu.itba.ss.cellindexmethod.exceptions;

public class NoStaticFileSpecifiedException extends Exception {
    private static final String ERROR_MSG = "Must provide correct 'staticFile=value'";

    public NoStaticFileSpecifiedException(){
        super(ERROR_MSG);
    }
}
