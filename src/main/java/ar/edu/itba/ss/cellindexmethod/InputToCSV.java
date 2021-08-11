package ar.edu.itba.ss.cellindexmethod;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Iterator;

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

    private boolean printColumnWithoutSeparator(BufferedWriter bufferedWriter, String colValue){
        try {
            bufferedWriter.write(colValue);
        } catch (IOException e){
            return false;
        }
        return true;
    }

    private boolean printColumnWithSeparator(BufferedWriter bufferedWriter, String colValue){
        try {
            if(!printColumnWithoutSeparator(bufferedWriter, colValue))
                return false;
            bufferedWriter.write(SEPARATOR);
        } catch (IOException e){
            return false;
        }
        return true;
    }

    private boolean printMultipleColumns(BufferedWriter bufferedWriter, String... colValues){
        Iterator<String> iterator = Arrays.stream(colValues).iterator();
        while (iterator.hasNext()){
            String next = iterator.next();
            if(iterator.hasNext()){
                if(!printColumnWithSeparator(bufferedWriter, next)){
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

    private static final String HEAD_STATIC = "N,L,M,rc";

    private boolean printStatic(long N, double L, int M, double rc){
        try(BufferedWriter bufferedWriter = Files.newBufferedWriter(Paths.get(OUTPUT_STATIC))) {
            bufferedWriter.write(HEAD_STATIC);
            bufferedWriter.newLine();

            printMultipleColumns(bufferedWriter,
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
            if (!printColumnWithoutSeparator(bufferedWriter, HEAD_PARTICLES_STATIC))
                return false;
            bufferedWriter.newLine();

            for (Particle p : particles) {
                if(!printMultipleColumns(bufferedWriter, Long.toString(p.getId()), Double.toString(p.getRadius()))){
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
            if (!printColumnWithoutSeparator(bufferedWriter, HEAD_PARTICLES_DYNAMIC))
                return false;
            bufferedWriter.newLine();

            for (Particle p : particles) {
                if(!printMultipleColumns(bufferedWriter, Long.toString(p.getId()), Double.toString(p.getX()), Double.toString(p.getY()))){
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
