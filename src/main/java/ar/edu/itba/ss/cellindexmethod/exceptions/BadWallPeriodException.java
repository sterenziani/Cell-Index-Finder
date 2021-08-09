package ar.edu.itba.ss.cellindexmethod.exceptions;

public class BadWallPeriodException extends Exception {
    private static final String ERROR_MSG = "Wrong wallPeriod syntax. Should provide 'wallPeriod=true', 'wallPeriod=false' or none at all";

    public BadWallPeriodException(){
        super(ERROR_MSG);
    }
}
