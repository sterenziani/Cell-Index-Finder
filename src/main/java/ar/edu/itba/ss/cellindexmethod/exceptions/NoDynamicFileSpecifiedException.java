package ar.edu.itba.ss.cellindexmethod.exceptions;

public class NoDynamicFileSpecifiedException extends Exception{
    private static final String ERROR_MSG = "Must provide correct 'dynamicFile=value'";

    public NoDynamicFileSpecifiedException(){
        super(ERROR_MSG);
    }
}
