package ar.edu.itba.ss.cellindexmethod;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class InputToCSV {
    private static InputToCSV inputToCSV;
    private static final String OUTPUT_STATIC = "output_static.csv";
    private static final String OUTPUT_PARTICLES_STATIC = "output_particles_static.csv";
    private static final String OUTPUT_PARTICLES_DYNAMIC = "output_particles_dynamic.csv";

    private InputToCSV(){}

    public static InputToCSV getInstance() {
        if(inputToCSV == null)
            inputToCSV = new InputToCSV();
        return inputToCSV;
    }

    public boolean printToCSV(Input input){
        return printStatic(input.getN(), input.getL(), input.getM(), input.getRc()) &&
                printParticlesStatic(input.getParticles()) &&
                printParticlesDynamic(input.getParticles());
    }

    private static final String SEPARATOR = ",";
    private static final String HEAD_STATIC = "N,L,M,rc";

    private boolean printStatic(long N, double L, int M, double rc){
        try(BufferedWriter bufferedWriter = Files.newBufferedWriter(Paths.get(OUTPUT_STATIC))) {
            bufferedWriter.write(HEAD_STATIC);
            bufferedWriter.newLine();

            CSVPrintingUtilities.printMultipleColumns(bufferedWriter,
                    SEPARATOR,
                    Long.toString(N),
                    Double.toString(L),
                    Integer.toString(M),
                    Double.toString(rc));
            bufferedWriter.newLine();
        } catch (IOException e){
            return false;
        }
        return true;
    }

    private static final String HEAD_PARTICLES_STATIC = "id,r";

    private boolean printParticlesStatic(Iterable<Particle> particles){
        try(BufferedWriter bufferedWriter = Files.newBufferedWriter(Paths.get(OUTPUT_PARTICLES_STATIC))) {
            if (!CSVPrintingUtilities.printColumnWithoutSeparator(bufferedWriter, HEAD_PARTICLES_STATIC))
                return false;
            bufferedWriter.newLine();

            for (Particle p : particles) {
                if(!CSVPrintingUtilities.printMultipleColumns(bufferedWriter, SEPARATOR, Long.toString(p.getId()), Double.toString(p.getRadius()))){
                    return false;
                }
                bufferedWriter.newLine();
            }
        } catch (IOException e){
            return false;
        }
        return true;
    }

    private static final String HEAD_PARTICLES_DYNAMIC = "id,x,y";

    private boolean printParticlesDynamic(Iterable<Particle> particles){
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(Paths.get(OUTPUT_PARTICLES_DYNAMIC))){
            if (!CSVPrintingUtilities.printColumnWithoutSeparator(bufferedWriter, HEAD_PARTICLES_DYNAMIC))
                return false;
            bufferedWriter.newLine();

            for (Particle p : particles) {
                if(!CSVPrintingUtilities.printMultipleColumns(bufferedWriter, SEPARATOR, Long.toString(p.getId()), Double.toString(p.getX()), Double.toString(p.getY()))){
                    return false;
                }
                bufferedWriter.newLine();
            }
        } catch (IOException e){
            return false;
        }
        return true;
    }
}
