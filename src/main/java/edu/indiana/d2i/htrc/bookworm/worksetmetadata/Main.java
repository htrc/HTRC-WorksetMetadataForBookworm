package edu.indiana.d2i.htrc.bookworm.worksetmetadata;

import java.io.IOException;

public class Main {
	public static void main(String[] args) {
		Config config = createConfig(args);
		printRegExtUrl(config);
		
		final long startTime = System.currentTimeMillis();
		
		RegistryClient regClient = new RegistryClient(config);
		try {
		  new WorksetsMetadataProcessor(regClient, config.getOutputFilename()).process();
		  printOutputFilename(config);
		} catch (Exception e) {
			System.err.println("Error while obtaining worksets using the registry extension: " + e);
		}
		
		final long endTime = System.currentTimeMillis();
		double timeInSec = (endTime - startTime)/1000.0;
		double timeInMin = timeInSec/60.0;
		System.out.println(String.format("Execution time = %.2f seconds = %.2f minutes", timeInSec, timeInMin));
	}
	
	public static Config createConfig(String[] args) {
		try {
			Config config = new Config(args);
			return config;
		} catch (IOException e) {
			System.err.println(e);
			System.exit(1);
		} catch (CmdLineException e) {
			System.err.println(e);
			Config.printUsage();
			System.exit(1);
		}
		return null;
	}
	
	private static void printRegExtUrl(Config config) {
		System.out.println("Generating metadata for public worksets using " + config.getRegExtEndpoint() + ".");
	}

	private static void printOutputFilename(Config config) {
		System.out.println("Output file location: " + config.getOutputFilename());
	}
}
