package com.higodev.xjc.services;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.List;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.higodev.xjc.dtos.XjcRequestDTO;

@Service
public class XjcService {

	private Object getResponse(final String url, final String method) {

		final RestTemplate restTemplate = new RestTemplate();

		Object response = null;

		switch (method) {

		case "GET":
			response = restTemplate.getForObject(url, String.class);
			break;
		case "POST":
			response = restTemplate.postForObject(url, null, String.class);
			break;
		}

		return response;
	}

	private XMLStreamReader getXmlReader(Object response) throws XMLStreamException {
		String text = response.toString();
		Reader reader = new StringReader(text);
		XMLInputFactory factory = XMLInputFactory.newInstance();
		XMLStreamReader xmlReader = factory.createXMLStreamReader(reader);
		return xmlReader;
	}

	private String getJSON(XMLStreamReader xmlReader) throws IOException {
		XmlMapper xmlMapper = new XmlMapper();
		Object obj = xmlMapper.readValue(xmlReader, Object.class);
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(obj);
		return json;
	}

	public StringBuilder converter(List<XjcRequestDTO> xjcRequests) throws XMLStreamException, IOException {
		StringBuilder str = new StringBuilder("");
		for (XjcRequestDTO xjcRequest : xjcRequests) {
			Object response = getResponse(xjcRequest.getUrl(), xjcRequest.getMethod());
			XMLStreamReader xmlReader = getXmlReader(response);
			String json = getJSON(xmlReader);
			str.append("URL: " + xjcRequest.getUrl() + "\n");
			str.append("JSON: " + json + "\n");
		}
		return str;
	}

}
