package edu.indiana.d2i.htrc.bookworm.worksetmetadata;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import com.martiansoftware.jsap.JSAPResult;

public class Config implements RegExtSettings {
	private String regExtEndpoint;
	private String publicWorksetsQuery;
	private String publicWorksetVolsQuery;
	private String outputFilename;
	
	Config(String[] args) throws IOException, CmdLineException {
		loadProperties(); // obtain values of properties from the default properties file
		parseCmdLineArgs(args); // override values of properties with any defined on the command line
	}
	
	private void loadProperties() throws IOException, CmdLineException {
		Properties props = new Properties();
		String propFileName = Constants.PROPERTIES_FILENAME;

		try (InputStream is = getClass().getClassLoader().getResourceAsStream(propFileName)) {
			if (is != null) {
				props.load(is);
				regExtEndpoint = props.getProperty(Constants.REG_EXT_ENDPOINT, Constants.DEFAULT_REG_EXT_ENDPOINT);
				publicWorksetsQuery = props.getProperty(Constants.REG_EXT_PUBLIC_WORKSETS, Constants.DEFAULT_REG_EXT_PUBLIC_WORKSETS);
				publicWorksetVolsQuery = props.getProperty(Constants.REG_EXT_PUBLIC_WORKSET_VOLUMES, Constants.DEFAULT_REG_EXT_PUBLIC_WORKSET_VOLUMES);
				outputFilename = props.getProperty(Constants.OUTPUT_FILENAME, Constants.DEFAULT_OUTPUT_FILENAME);
			}
			else {
				throw new FileNotFoundException("Property file " + propFileName + " not found.");
			}
		} 
		catch (IOException e) {
			throw new IOException(e.getMessage() + "\nError while reading properties file " + propFileName);
		}
	}
	
	private void parseCmdLineArgs(String[] args) throws CmdLineException {
		// parse command line arguments
		JSAPResult cmdLine = ArgumentParser.parseArguments(args);
		if (cmdLine == null) {
			throw new CmdLineException("Error in command line arguments.");
		}
		else {
			URL newRegEndpoint = cmdLine.getURL("regExtEndpoint");
			if (newRegEndpoint != null) {
				regExtEndpoint = newRegEndpoint.toString();
			}
			
			File newOutputFile = cmdLine.getFile("outputFile");
			if (newOutputFile != null) {
				outputFilename = newOutputFile.toString();
			}
		}
	}
	
	@Override
	public String getRegExtEndpoint() {
		return this.regExtEndpoint;
	}
	
	@Override
	public String getPublicWorksetsQuery() {
		return this.publicWorksetsQuery;
	}
	
	@Override
	public String getPublicWorksetVolsQuery() {
		return this.publicWorksetVolsQuery;
	}
	
	public String getOutputFilename() {
		return this.outputFilename;
	}
	
	public static void printUsage() {
		System.out.println("Usage: java -jar HTRCWorksetMetadata.jar [-r regExtURL] [-o outputFilePath]");
	}
}
