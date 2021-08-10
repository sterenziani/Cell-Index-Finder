package ar.edu.itba.ss.cellindexmethod;

import ar.edu.itba.ss.cellindexmethod.exceptions.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Parser{
	private static Parser parser = null;
	private Parser() {}
	
	public static Parser getInstance() {
		if(parser == null)
			parser = new Parser();
		return parser;
	}

	private static class ArgumentInput {
		private final String staticFile;
		private final String dynamicFile;
		private final boolean wallPeriod;
		private final boolean randomize;
		private final boolean sameRadius;

		ArgumentInput(String staticFile, String dynamicFile, boolean wallPeriod, boolean randomize, boolean sameRadius){
			this.staticFile = staticFile;
			this.dynamicFile = dynamicFile;
			this.wallPeriod = wallPeriod;
			this.randomize = randomize;
			this.sameRadius = sameRadius;
		}
	}

	private ArgumentInput parseArguments(String[] args) throws AlreadyDefinedArgumentException,
			BadArgumentFormatException, NoStaticFileSpecifiedException, NoDynamicFileSpecifiedException,
			BadWallPeriodException, BadRandomizeException, BadSameRadiusException {
		Map<String, String> arguments = new HashMap<>();
		for (String arg: args) {
			String[] attr = arg.split("=", 2);
			if(attr.length >= 2) {
				if (arguments.containsKey(attr[0])) {
					throw new AlreadyDefinedArgumentException(attr[1], arguments.get(attr[0]));
				}
				arguments.put(attr[0], attr[1]);
			} else {
				throw new BadArgumentFormatException(attr[0]);
			}
		}
		String staticFile = null;
		String dynamicFile = null;
		Boolean wallPeriod = false;
		Boolean randomize = false;
		Boolean sameRadius = false;
		for(Map.Entry<String, String> argument : arguments.entrySet()){
			switch (argument.getKey()){
				case "staticFile":
					staticFile = argument.getValue();
					break;
				case "dynamicFile":
					dynamicFile = argument.getValue();
					break;
				case "wallPeriod":
					if(argument.getValue().equalsIgnoreCase("true")){
						wallPeriod = true;
					} else if (argument.getValue().equalsIgnoreCase("false")){
						wallPeriod = false;
					} else {
						wallPeriod = null;
					}
					break;
				case "randomize":
					if(argument.getValue().equalsIgnoreCase("true")){
						randomize = true;
					} else if(argument.getValue().equalsIgnoreCase("false")) {
						randomize = false;
					} else {
						randomize = null;
					}
					break;
				case "sameRadius":
					if(argument.getValue().equalsIgnoreCase("true")){
						sameRadius = true;
					} else if(argument.getValue().equalsIgnoreCase("false")) {
						sameRadius = false;
					} else {
						sameRadius = null;
					}
					break;
			}
		}
		if(staticFile == null){
			throw new NoStaticFileSpecifiedException();
		}
		if(dynamicFile == null){
			throw new NoDynamicFileSpecifiedException();
		}
		if(wallPeriod == null){
			throw new BadWallPeriodException();
		}
		if(randomize == null){
			throw new BadRandomizeException();
		}
		if(sameRadius == null){
			throw new BadSameRadiusException();
		}
		return new ArgumentInput(staticFile, dynamicFile, wallPeriod, randomize, sameRadius);
	}

	public Input parse(String[] args) throws Exception{
		ArgumentInput argumentInput = parseArguments(args);
		FileInput fileInput = parseFiles(argumentInput.staticFile, argumentInput.dynamicFile, argumentInput);
		return new Input(
				fileInput.staticInput.N,
				fileInput.staticInput.L,
				fileInput.staticInput.M,
				fileInput.staticInput.rc,
				argumentInput.wallPeriod,
				getParticles(fileInput.staticInput.N, fileInput.staticInput.particleRadiusesMap, fileInput.dynamicInput.particlePositionsMap)
		);
	}

	public Input parseMock(String[] args) throws Exception {
		int N = 7;
		double L = 5;
		int M = 3;
		double rc = 0.45;
		boolean wallPeriod = true;
		List<Particle> list = new LinkedList<Particle>();

		list.add(new Particle(1, 1.5, 1.5, 0.25));
		list.add(new Particle(2, 2.1, 1.5, 0.25));
		list.add(new Particle(3, 3.5, 1.05, 0.25));
		list.add(new Particle(4, 2.1, 2.1, 0.25));
		list.add(new Particle(5, 0.95, 0.95, 0.25));
		list.add(new Particle(6, 2.01, 0.95, 0.25));
		list.add(new Particle(7, 4.01, 2.05, 0.25));
		list.add(new Particle(8, 4.6, 4.5, 0.25));
		list.add(new Particle(9, 4.6, 0.4, 0.25));

		/*
		// OVERLAPPING PARTICLES:
		list.add(new Particle(11, 1.5, 1.5, 0.25));
		list.add(new Particle(12, 1.5, 1.6, 0.25));
		list.add(new Particle(13, 2.5, 1.95, 0.25));
		list.add(new Particle(14, 2.5, 2.05, 0.25));
		*/
		return new Input(N, L, M, rc, wallPeriod, list);
	}

	private static class FileInput {
		private final StaticInput staticInput;
		private final DynamicInput dynamicInput;

		FileInput(StaticInput staticInput, DynamicInput dynamicInput){
			this.staticInput = staticInput;
			this.dynamicInput = dynamicInput;
		}
	}
	
	private FileInput parseFiles(String staticPath, String dynamicPath, ArgumentInput args) throws Exception {
		StaticInput staticInput = parseFileStatic(staticPath, args);
		DynamicInput dynamicInput = parseFileDynamic(dynamicPath, staticInput.N, staticInput.L, args, staticInput.particleRadiusesMap);
		return new FileInput(staticInput, dynamicInput);
	}

	private List<Particle> getParticles(long N, Map<Long, Double> particleRadiusesMap, Map<Long, Point> particlePositionsMap){
		List<Particle> particles = new LinkedList<>();
		for (Map.Entry<Long, Double> particleRadius: particleRadiusesMap.entrySet()) {
			Point particlePosition = particlePositionsMap.get(particleRadius.getKey());
			particles.add(new Particle(particleRadius.getKey(), particlePosition.getX(), particlePosition.getY(), particleRadius.getValue()));
		}
		return particles;
	}

	private static class StaticInput {
		private final int N;
		private final double L;
		private final int M;
		private final double rc;
		private final Map<Long, Double> particleRadiusesMap;

		StaticInput(int N, double L, int M, double rc, Map<Long, Double> particleRadiusesMap){
			this.N = N;
			this.L = L;
			this.M = M;
			this.rc = rc;
			this.particleRadiusesMap = particleRadiusesMap;
		}

		@Override
		public String toString() {
			return "StaticInput{" +
					"N=" + N +
					", L=" + L +
					", M=" + M +
					", rc=" + rc +
					", particleRadiusesMap=" + particleRadiusesMap +
					'}';
		}
	}

	private StaticInput parseFileStatic(String path, ArgumentInput args) throws CannotOpenFileException, CannotReadLineException{
		/*
		 * File format:
		 * N
		 * L
		 * M
		 * rc
		 * r1
		 * r2
		 * ...
		 * rN
		 *
		 */
		try(BufferedReader bufferedReader = Files.newBufferedReader(Paths.get(path))){
			int N;
			double L;
			int M;
			double rc;

			long currentLine = 1;
			try {

				//Line 1 should be N
				N = Integer.parseInt(bufferedReader.readLine());
				currentLine++;

				//Line 2 should be L
				L = Double.parseDouble(bufferedReader.readLine());
				currentLine++;

				//Line 3 should be M
				M = Integer.parseInt(bufferedReader.readLine());
				currentLine++;

				//Line 4 should be rc
				rc = Double.parseDouble(bufferedReader.readLine());
				currentLine++;

				//Now, line 5 through N+5 should be each particle (r1)
				Map<Long, Double> particleRadiusesMap = new HashMap<>();
				if (args.sameRadius){
					double radius = Double.parseDouble(bufferedReader.readLine());
					currentLine++;
					for(long i = 0; i < N; i++){
						particleRadiusesMap.put(i+1, radius);
					}
				} else if (args.randomize){
					for(long i = 0; i < N; i++)
						particleRadiusesMap.put(i+1, Math.random()*rc); // Particles won't have a radius larger than rc
				} else {
					for(long i = 0; i < N; i++){
						particleRadiusesMap.put(i+1, Double.parseDouble(bufferedReader.readLine()));
						currentLine++;
					}
				}
				
				return new StaticInput(N, L, M, rc, particleRadiusesMap);
			} catch (IOException | NumberFormatException e) {
				throw new CannotReadLineException(path, currentLine);
			}
		} catch (IOException e){
			throw new CannotOpenFileException(path);
		}
	}

	private static class DynamicInput {
		private final Map<Long, Point> particlePositionsMap;

		DynamicInput(Map<Long, Point> particlePositionsMap){
			this.particlePositionsMap = particlePositionsMap;
		}

		@Override
		public String toString() {
			return "DynamicInput{" +
					"particlePositionsMap=" + particlePositionsMap +
					'}';
		}
	}
	private static final String COORDINATE_Y = "coordinate y";
	
	private DynamicInput parseFileDynamic(String path, int N, double L, ArgumentInput args, Map<Long, Double> particleRadiusesMap)
			throws CannotOpenFileException, CannotReadLineException, MissingAttributeException {

		/*
		 * File format:
		 * x1 y1
		 * x2 y2
		 * ...
		 * xN yN
		 */
		try(BufferedReader bufferedReader = Files.newBufferedReader(Paths.get(path))){
			long currentLine = 1;
			try {
				Map<Long, Point> particlePositionsMap = new HashMap<>();
				if(args.randomize){
					ParticleGenerator particleGenerator = ParticleGenerator.getInstance();
					particleGenerator.generateRandomPoints(N, L, particleRadiusesMap, particlePositionsMap);
				}
				else
				{
					// Read from file
					for(long i = 0; i < N; i++){
						String[] particleAttrs = bufferedReader.readLine().split(" ");

						// Attributes should include coordinate x and coordinate y
						if(particleAttrs.length < 2) {
							throw new MissingAttributeException(path, currentLine, COORDINATE_Y);
						}

						double x = Double.parseDouble(particleAttrs[0]);
						double y = Double.parseDouble(particleAttrs[1]);
						particlePositionsMap.put(i+1, new Point(x, y));
						currentLine++;
					}
				}
				return new DynamicInput(particlePositionsMap);

			} catch (IOException | NumberFormatException e){
				throw new CannotReadLineException(path, currentLine);
			}
		} catch (IOException e){
			throw new CannotOpenFileException(path);
		}
	}
}
