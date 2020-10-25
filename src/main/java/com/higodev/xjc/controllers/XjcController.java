package com.higodev.xjc.controllers;

import java.io.IOException;
import java.util.List;

import javax.xml.stream.XMLStreamException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.higodev.xjc.dtos.XjcRequestDTO;
import com.higodev.xjc.services.XjcService;

@RequestMapping("/xjc")
@RestController
public class XjcController {
	
	@Autowired
	private XjcService xjcService;

	@PostMapping("/converter")
	public String getJsonConvertido(@RequestBody List<XjcRequestDTO> xjcRequests) throws IOException, XMLStreamException {
		return xjcService.converter(xjcRequests).toString();

	}

}
