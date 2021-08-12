package ar.edu.itba.ss.cellindexmethod;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Set;

public class OutputToCSV {
    private static OutputToCSV outputToCSV;

    private OutputToCSV(){}

    public static OutputToCSV getInstance() {
        if (outputToCSV == null)
            outputToCSV = new OutputToCSV();
        return outputToCSV;
    }

    public boolean printToCSV(Map<Particle, Set<Particle>> output, String outputFileName){
        return printNeighbors(output, outputFileName);
    }

    private static final String SEPARATOR = ",";
    private static final String HEAD_NEIGHBORS = "id,p";
    private static final String NEIGHBOR_SEPARATOR = " ";

    private boolean printNeighbors(Map<Particle, Set<Particle>> output, String outputFileName){
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(Paths.get(outputFileName))){

            // Print HEAD of CSV
            CSVPrintingUtilities.printColumnWithoutSeparator(bufferedWriter, HEAD_NEIGHBORS);
            bufferedWriter.newLine();

            // Print each particle id and neighbors' ids
            for(Map.Entry<Particle, Set<Particle>> particle : output.entrySet()){
                CSVPrintingUtilities.printColumnWithSeparator(bufferedWriter, Long.toString(particle.getKey().getId()), SEPARATOR);
                CSVPrintingUtilities.printMultipleColumns(bufferedWriter, NEIGHBOR_SEPARATOR,
                        particle.getValue().stream().map(p -> Long.toString(p.getId())).toArray(String[]::new));
                bufferedWriter.newLine();
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
