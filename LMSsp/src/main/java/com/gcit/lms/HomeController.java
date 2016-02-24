/*
 * Copyright (c) 2016, GCIT and/or its affiliates. All rights reserved.
 * GCIT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 */
package com.gcit.lms;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * The <code>HomeController</code> class represents the Spring controller. 
 * The class <code>HomeController</code> includes methods for loading 
 * the home page of application.
 *
 * @author  Preethi.
 * @since   V1.0
 */
@Controller
public class HomeController {
	
	/**
	 * Logger 
	 */
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Root mapping method for LMS Application.
	 * 
	 * @param locale
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String loadPage(Locale locale, Model model) {
		return "index";
	}
}
