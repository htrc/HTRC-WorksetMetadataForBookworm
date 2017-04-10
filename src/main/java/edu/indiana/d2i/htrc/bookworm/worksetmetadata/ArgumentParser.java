package edu.indiana.d2i.htrc.bookworm.worksetmetadata;

import com.martiansoftware.jsap.FlaggedOption;
import com.martiansoftware.jsap.JSAPException;
import com.martiansoftware.jsap.JSAPResult;
import com.martiansoftware.jsap.Parameter;
import com.martiansoftware.jsap.SimpleJSAP;
import com.martiansoftware.jsap.stringparsers.FileStringParser;
import com.martiansoftware.jsap.stringparsers.URLStringParser;

public class ArgumentParser {
    private static Parameter[] getApplicationParameters() {
        Parameter regExtEndpoint = new FlaggedOption("regExtEndpoint")
        		.setStringParser(URLStringParser.getParser())
				.setShortFlag('r')
				.setRequired(false)
				.setHelp("Specifies the registry extension endpoint that will be contacted to get the list of public worksets and their volumes.");

        Parameter outputFile = new FlaggedOption("outputFile")
        .setStringParser(FileStringParser.getParser()
                        .setMustBeFile(true))
        .setShortFlag('o')
        .setRequired(false)
        .setHelp("Specifies the full path of the output file.");

        return new Parameter[] { regExtEndpoint, outputFile };
    }

    private static String getApplicationHelp() {
        return "Tool to retrieve information about HTRC public worksets and write out the same in the JSON format needed to add as metadata to Bookworm.";
    }

    public static JSAPResult parseArguments(String[] args) {
    	try {
    		SimpleJSAP jsap = new SimpleJSAP("Main", getApplicationHelp(), getApplicationParameters());
        	JSAPResult result = jsap.parse(args);
        	if (jsap.messagePrinted()) {
        		return null;
        	}
        	else return result;
        } catch (JSAPException e) {
        	System.out.println("Exception in parsing cmd line args: " + e);
        	return null;
        }
    }

}
