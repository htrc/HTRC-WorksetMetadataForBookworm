package edu.indiana.d2i.htrc.bookworm.worksetmetadata;

import java.io.StringReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import gov.loc.repository.pairtree.Pairtree;

public class WorksetsMetadataProcessor {
	private RegistryClient registryClient;
	private String outputFilename;
	
	// maps a vol id to the set of public HTRC worksets that contain the volume
	private Map<String, Set<String>> volumesToPublicHTRCWorksets;
	private Pairtree pairtree = null;

	public WorksetsMetadataProcessor(RegistryClient registryClient, String outputFilename) {
		this.registryClient = registryClient;
		this.outputFilename = outputFilename;
		this.volumesToPublicHTRCWorksets = new HashMap<String, Set<String>>();
		this.pairtree = new Pairtree();
	}
	
	// obtain the list of HTRC public worksets, and the volumes contained in them, and write this out in the required JSON form
	public void process() throws Exception {
		String publicWorksetsXML = registryClient.getPublicWorksets();
		processWorksetsXML(publicWorksetsXML);
		JSONWriter jsonWriter = new JSONWriter(outputFilename);
		jsonWriter.print(volumesToPublicHTRCWorksets);
	}
	
	// for a list of worksets, obtains the vol ids in each workset, and adds the mapping of vol id to workset to volumesToPublicHTRCWorksets
	public void processWorksetsXML(String worksetsMetadata) throws Exception {
		DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		InputSource is = new InputSource();
		is.setCharacterStream(new StringReader(worksetsMetadata));

		Document doc = db.parse(is);
		Element root = doc.getDocumentElement();
		
	    NodeList params = root.getElementsByTagName("workset");
	    System.out.println("No. of public worksets = " + params.getLength());
	    for (int i = 0 ; i < params.getLength() ; i++) {
	    	Element elem = (Element) params.item(i);
	    	String name = Utils.extractTextContent(elem, "name");
	    	String author = Utils.extractTextContent(elem, "author");

	    	// int volCount = Integer.parseInt(Utils.extractTextContent(elem, "volumeCount"));
	    	// System.out.println(name + "@" + author + ", numVols = " + volCount);
	    	
	    	try {
	    		String volumesXML = registryClient.getWorksetVolumes(name, author);
	    		processVolumesXML(volumesXML, name + "@" + author);
	    	} catch (Exception e) {
	    		System.out.println("WARN: Exception while trying to get volumes of " + name + "@" + author + 
	    				": " + e + ". Ignoring the workset.");
	    	}
	    }
	    System.out.println("No. of unique volumes in public worksets = " + volumesToPublicHTRCWorksets.size());
	}
	
	// for a list of volumes in XML form, and a workset name, add a mapping each vol id in the list to the given workset name to volumesToPublicHTRCWorksets 
	public void processVolumesXML(String volumesXML, String worksetName) throws Exception {
		DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		InputSource is = new InputSource();
		is.setCharacterStream(new StringReader(volumesXML));

		Document doc = db.parse(is);
		Element root = doc.getDocumentElement();
				
	    NodeList params = root.getElementsByTagName("volume");

	    for (int i = 0 ; i < params.getLength() ; i++) {
	    	Element elem = (Element) params.item(i);
	    	String id = Utils.extractTextContent(elem, "id");
	    	
	    	String volId = cleanVolumeId(id);
	    	if (volumesToPublicHTRCWorksets.containsKey(volId)) {
	    		volumesToPublicHTRCWorksets.get(volId).add(worksetName);
	    	}
	    	else {
	    		Set<String> worksets = new HashSet<String>();
	    		worksets.add(worksetName);
	    		volumesToPublicHTRCWorksets.put(volId, worksets);
	    	}
	    }
	}
	
	private String cleanVolumeId(String volId) {
		String separator = ".";
		int i = volId.indexOf(".");
		
		if (i < 0) {
			// this happens if there is an incorrect volume id that just has the base component, and is not of 
			// the form base.ppath
			return volId;
		}
		
		String base = volId.substring(0, i);
		String ppath = volId.substring(i+1);
		
		return (base + separator + pairtree.cleanId(ppath));
	}
	
	private void printVolumesMap() {
		for (String volId : volumesToPublicHTRCWorksets.keySet()) {
			System.out.print(volId + " : ");
			printSet(volumesToPublicHTRCWorksets.get(volId));
			System.out.println();
		}
	}
	
	private void printSet(Set<String> s) {
		System.out.print("{");
		boolean firstIteration = true;
		for (String str : s) {
			if (firstIteration) {
				firstIteration = false;
			}
			else {
				System.out.print(", ");
			}
			System.out.print(str);
		}
		System.out.print("}");
	}
}
