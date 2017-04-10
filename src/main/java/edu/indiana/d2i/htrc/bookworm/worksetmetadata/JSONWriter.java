package edu.indiana.d2i.htrc.bookworm.worksetmetadata;

import java.io.PrintStream;
import java.util.Map;
import java.util.Set;

public class JSONWriter {
	private PrintStream printStream = null;
	
	public JSONWriter() {
		printStream = System.out;
	}
	
	public JSONWriter(String outputFilename) {
		try {
			this.printStream = new PrintStream(outputFilename, "UTF-8");
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("Writing JSON output to stdout");
			this.printStream = System.out;
		}
	}
	
	public void print(Map<String, Set<String>> volumesToPublicHTRCWorksets) {
		for (String volId: volumesToPublicHTRCWorksets.keySet()) {
			printStream.print(singleEntryToJSON(volId, volumesToPublicHTRCWorksets.get(volId)));
		}
	}
	
	private String singleEntryToJSON(String volId, Set<String> worksets) {
		return ("{" + volIdToJSON(volId) + ", " + worksetListToJSON(worksets) + "}\n");
		
	}
	
	private String volIdToJSON(String volId) {
		return (stringToJSON("filename") + ": " + stringToJSON(volId));
	}

	private String worksetListToJSON(Set<String> worksets) {
		StringBuffer sb = new StringBuffer();
		sb.append(stringToJSON("HTRC_public_worksets") + ": [");
		boolean firstIteration = true;
		for (String w: worksets) {
			if (firstIteration) {
				firstIteration = false;
			}
			else {
				sb.append(", ");
			}
			sb.append(stringToJSON(w));
		}
		sb.append("]");
		return sb.toString();
	}
	
	private String stringToJSON(String str) {
		return ("\"" + str + "\"");
	}
}
