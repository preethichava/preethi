package com.gcit.lms;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.gcit.lms.domain.Publisher;
import com.gcit.lms.service.AdministratorService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class PublisherController {
	
	private static final Logger logger = LoggerFactory.getLogger(PublisherController.class);
	
	@Autowired
	AdministratorService admin;
	
	@RequestMapping(value = "publisher", method = RequestMethod.GET)
	public String publisher(Locale locale, Model model) {
		return "publisher";
	}
	
	@RequestMapping(value = "addpublisher", method = RequestMethod.GET)
	public String addPublisher(Locale locale, Model model) {
		return "addpublisher";
	}
	
	@RequestMapping(value = "addPublisher", method = RequestMethod.POST)
	public String addPublisher(@RequestParam(value="publisherName")String pubName, @RequestParam(value="publisherAddr")String pubAddress, 
			@RequestParam(value="publisherPhone")String publisherPhone,  Locale locale, Model model) {
		Publisher pub = new Publisher();
		pub.setPublisherName(pubName);
		pub.setPublisherAddress(pubAddress);
		pub.setPublisherPhone(publisherPhone);
		int i=0;
	
		try {
			i= admin.createService(pub);
		} catch (Exception e) {
			e.printStackTrace();
		}
			
		if(i != 0)
			model.addAttribute("message", "Publisher Added Sucessfully");
		else
			model.addAttribute("message", "Publisher was not Added, Opertation Failed!!");
		return "addpublisher";
	}
	
	
	@RequestMapping(value = "viewpublisher", method = RequestMethod.GET)
	public String viewPublisher(Locale locale, Model model) {
		List<Publisher> pubs = new ArrayList<Publisher>();
		try {
			pubs = (List<Publisher>)admin.getService("pubs","",0,0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("pubs", pubs);
		return "viewpublisher";
	}
}
