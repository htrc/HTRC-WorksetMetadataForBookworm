package edu.indiana.d2i.htrc.bookworm.worksetmetadata;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

interface RegExtSettings {
	public String getRegExtEndpoint();
	public String getPublicWorksetsQuery();
	public String getPublicWorksetVolsQuery();	
}

public class RegistryClient {
	private String regExtEndPoint;
	private String publicWorksetsQuery;
	private String publicWorksetVolsQuery;
	
	public RegistryClient(RegExtSettings settings) {
		this.regExtEndPoint = settings.getRegExtEndpoint();
		this.publicWorksetsQuery = settings.getPublicWorksetsQuery();
		this.publicWorksetVolsQuery = settings.getPublicWorksetVolsQuery();
	}

	public String getPublicWorksets() throws Exception {
		String result = "";
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {			
			HttpGet httpGet = new HttpGet(regExtEndPoint + publicWorksetsQuery);
			httpGet.setHeader("Accept", "application/vnd.htrc-workset+xml");

			StringResponseHandler rh = new StringResponseHandler();

			result = httpClient.execute(httpGet, rh);
   
		    return result;
		} finally {
			httpClient.close();
		}
	}
	
	public String getWorksetVolumes(String worksetName, String author) throws Exception {
		String result = "";
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			String volumesQuery = String.format(publicWorksetVolsQuery, worksetName, author);
			
			HttpGet httpGet = new HttpGet(regExtEndPoint + volumesQuery);
			httpGet.setHeader("Accept", "application/xml");

			StringResponseHandler rh = new StringResponseHandler();

			result = httpClient.execute(httpGet, rh);
		    return result;
		} finally {
			httpClient.close();
		}
	}
}
