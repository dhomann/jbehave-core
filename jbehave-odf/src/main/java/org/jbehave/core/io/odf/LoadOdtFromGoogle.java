package org.jbehave.core.io.odf;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import com.google.gdata.client.docs.DocsService;
import com.google.gdata.data.MediaContent;
import com.google.gdata.data.docs.DocumentListEntry;
import com.google.gdata.data.docs.DocumentListFeed;
import com.google.gdata.data.media.MediaSource;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;

public class LoadOdtFromGoogle extends LoadOdtFromURL {
	
	private final DocsService client;

	public LoadOdtFromGoogle(String username, String password) {
		try {
		    client = new DocsService("jbehave");
			client.setUserCredentials(username, password);
		} catch (AuthenticationException e) {
			throw new GoogleAccessFailed(username, e);
		}
	}

	protected InputStream resourceAsStream(String title) throws IOException, MalformedURLException {
		try {
            return documentAsStream(exportURL(title));
        } catch (ServiceException e) {
            throw new IOException(e);
        }
	}

    private String exportURL(String title) throws IOException, ServiceException, MalformedURLException {
        URL documentURL = documentURL(title);
        List<DocumentListEntry> entries = client.getFeed(documentURL, DocumentListFeed.class).getEntries();
        if ( entries.isEmpty() ){
            throw new GoogleDocumentNotFound(documentURL);
        }
        return ((MediaContent) entries.get(0).getContent()).getUri() + "&exportFormat=odt";
    }

	protected URL documentURL(String title) throws MalformedURLException {
		return new URL("https://docs.google.com/feeds/default/private/full/-/document?title=" + title);
	}

    private InputStream documentAsStream(String url) throws IOException, MalformedURLException {
        try {
            MediaContent mc = new MediaContent();
            mc.setUri(url);
            MediaSource ms;
            ms = client.getMedia(mc);
            return ms.getInputStream();
        } catch (ServiceException e) {
            throw new GoogleMediaExportFailed(url, e);
        }
	}

    @SuppressWarnings("serial")
    public static class GoogleAccessFailed extends RuntimeException {

        public GoogleAccessFailed(String username, Throwable cause) {
            super("Google access failed for user "+username, cause);
        }
        
    }
    
    @SuppressWarnings("serial")
    public static class GoogleDocumentNotFound extends RuntimeException {

        public GoogleDocumentNotFound(URL url) {
            super("Failed to find Google document from "+url);
        }
        
    }

	@SuppressWarnings("serial")
    public static class GoogleMediaExportFailed extends RuntimeException {

        public GoogleMediaExportFailed(String url, Throwable cause) {
            super("Failed to export Google media from "+url, cause);
        }
	    
	}
}
