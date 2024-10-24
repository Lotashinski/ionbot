package com.github.lotashinski.ionbot.qbittorent.api;

import java.io.IOException;
import java.io.OutputStream;
import java.net.CookieHandler;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublisher;
import java.net.http.HttpRequest.BodyPublishers;
import java.util.List;

import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.StringBody;


public class QbittorentApi implements AutoCloseable {
	
	private static final String URL_LOGIN = "/api/v2/auth/login"; 
	
	private static final String URL_TORRENTS = "/api/v2/torrents/info";
	
	
	private final Config config;
	
	private final HttpClient client;

	{
		client = HttpClient.newBuilder()
				.cookieHandler(CookieHandler.getDefault())
				.build();
	}
	

	public QbittorentApi(Config config) {
		this.config = config;
	}
	
	
	public List<Torrent> getTorrents() {
		
	}

	
	private HttpRequest generateTorrentsRequest() throws URISyntaxException {
		return HttpRequest.newBuilder(new URI(buildUri(URL_TORRENTS)))
				.GET()
				.build();
	}
	
	
	private HttpRequest generateLoginRequest() throws UnsupportedOperationException, IOException, URISyntaxException {
		var mpentity = MultipartEntityBuilder.create()
				.addTextBody("username", config.getUsername())
				.addTextBody("password", config.getPassword())
				.build();
		
		var bytearr = mpentity.getContent().readAllBytes();
		
		return HttpRequest.newBuilder(new URI(buildUri(URL_LOGIN)))
				.setHeader("content-type", mpentity.getContentType().getValue())
				.POST(BodyPublishers.ofByteArray(bytearr))
				.build();
	}
	
	
	private String buildUri(String url) {
		return config.getHost() + url;
	}

	@Override
	public void close() throws Exception {
		client.close();
	}
	
}
