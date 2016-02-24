package com.gcit.lms;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gcit.lms.domain.Branch;
import com.gcit.lms.service.AdministratorService;

/**
 * Handles requests for author
 */
@Controller
public class LibrarianController {

	@Autowired
	AdministratorService admin;
	
	@RequestMapping(value = "librarian", method = RequestMethod.GET)
	public String getLibrarian(Locale locale, Model model) {

		List<Branch> branches = new ArrayList<Branch>();
		try {
			branches = (List<Branch>)admin.getService("branch","",0,0);
			model.addAttribute("branches", branches);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return "librarian";
	}
}
