package ar.edu.itba.ss.cellindexmethod.exceptions;

public class BadSameRadiusException extends Exception{
    private static final String ERROR_MSG = "Wrong sameRadius syntax. Should provide 'sameRadius=true', 'sameRadius=false' or none at all";

    public BadSameRadiusException(){
        super(ERROR_MSG);
    }
}
