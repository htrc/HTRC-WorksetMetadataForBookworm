package edu.indiana.d2i.htrc.bookworm.worksetmetadata;

public class Constants {
	public static final String PROPERTIES_FILENAME = "config.properties";
	
	// names of properties 
	public static final String REG_EXT_ENDPOINT = "reg-ext.endpoint";
	public static final String REG_EXT_PUBLIC_WORKSETS = "reg-ext.public.worksets";
	public static final String REG_EXT_PUBLIC_WORKSET_VOLUMES = "reg-ext.public.workset.volumes";
	public static final String OUTPUT_FILENAME = "output.file";
	
	// default values of properties
	public static final String DEFAULT_REG_EXT_ENDPOINT = "https://silvermaple.pti.indiana.edu:9443/ExtensionAPI";
	public static final String DEFAULT_REG_EXT_PUBLIC_WORKSETS = "/public/worksets";
	public static final String DEFAULT_REG_EXT_PUBLIC_WORKSET_VOLUMES = "/public/worksets/%s/volumes?author=%s";
	public static final String DEFAULT_OUTPUT_FILENAME = "HTRCPublicWorksets.json";
	
	private Constants() {
		throw new AssertionError();
	}
}
