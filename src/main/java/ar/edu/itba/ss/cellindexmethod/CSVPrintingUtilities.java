package ar.edu.itba.ss.cellindexmethod;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;

public class CSVPrintingUtilities {
    public static boolean printColumnWithoutSeparator(BufferedWriter bufferedWriter, String colValue){
        try {
            bufferedWriter.write(colValue);
        } catch (IOException e){
            return false;
        }
        return true;
    }

    public static boolean printColumnWithSeparator(BufferedWriter bufferedWriter, String colValue, String separator){
        try {
            if(!printColumnWithoutSeparator(bufferedWriter, colValue))
                return false;
            bufferedWriter.write(separator);
        } catch (IOException e){
            return false;
        }
        return true;
    }

    public static boolean printMultipleColumns(BufferedWriter bufferedWriter, String separator, String... colValues){
        Iterator<String> iterator = Arrays.stream(colValues).iterator();
        while (iterator.hasNext()){
            String next = iterator.next();
            if(iterator.hasNext()){
                if(!printColumnWithSeparator(bufferedWriter, next, separator)){
                    return false;
                }
            } else {
                if (!printColumnWithoutSeparator(bufferedWriter, next)) {
                    return false;
                }
            }
        }
        return true;
    }
}
