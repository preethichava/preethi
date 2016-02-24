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

import com.gcit.lms.domain.BCopies;
import com.gcit.lms.domain.Book;
import com.gcit.lms.domain.Branch;
import com.gcit.lms.service.AdministratorService;
import com.gcit.lms.service.LibrarianService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class BranchController {
	
	private static final Logger logger = LoggerFactory.getLogger(BranchController.class);
	
	@Autowired
	AdministratorService admin;
	
	@Autowired
	LibrarianService libs;
	
	@RequestMapping(value = "branchdetails", method = RequestMethod.GET)
	public String branchDetails(Locale locale, Model model) {
		return "branchdetails";
	}
	
	@RequestMapping(value = "addbranch", method = RequestMethod.GET)
	public String addBranch(Locale locale, Model model) {
		return "addbranch";
	}
	
	@RequestMapping(value = "addBranch", method = RequestMethod.POST)
	public String addBranchInfo(@RequestParam(value="branchName")String branchName, 
			@RequestParam(value="branchAddress")String branchAddress, Locale locale, Model model) {
		Branch branch = new Branch();
		branch.setBranchName(branchName);
		branch.setBranchAddress(branchAddress);
		int i=0;
		
		try {
			i= admin.createService(branch);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
			
		if(i != 0)
			model.addAttribute("message", "Branch created Sucessfully");
		else
			model.addAttribute("message", "Branch was not created, Opertation Failed!!");
		return "addbranch";
	}
	
	@RequestMapping(value = "viewbranch", method = RequestMethod.GET)
	public String viewBranch(Locale locale, Model model) {
		List<Branch> branches = new ArrayList<Branch>();
		try {
			branches = (List<Branch>)admin.getService("branch");
			model.addAttribute("branches", branches);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "viewbranch";
	}
	
	
	@RequestMapping(value = "branch", method = RequestMethod.GET)
	public String viewBranch(@RequestParam(value="branchId")String branchId, Locale locale, Model model) {
		Branch branch = new Branch();
		try {
			branch = (Branch)admin.getServiceById("branch", Integer.parseInt(branchId));
			model.addAttribute("branch", branch);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "branch";
	}
	
	
	@RequestMapping(value = "updatebranch", method = RequestMethod.GET)
	public String updateBranch(@RequestParam(value="branchId")String branchId, Locale locale, Model model) {
		Branch branch = new Branch();
		try {
			branch = (Branch)admin.getServiceById("branch", Integer.parseInt(branchId));
			model.addAttribute("branch", branch);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "updatebranch";
	}
	
	
	@RequestMapping(value = "updatecopies", method = RequestMethod.GET)
	public String updateCopies(@RequestParam(value="branchId")String branchId, Locale locale, Model model) {
		Branch branch = new Branch();
		try {
			branch = (Branch)admin.getServiceById("branch", Integer.parseInt(branchId));
			model.addAttribute("branch", branch);
			
			List<BCopies> bookCopies = libs.getCopies(Integer.parseInt(branchId));
			model.addAttribute("bookCopies", bookCopies);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "updatecopies";
	}
	
	@RequestMapping(value = "updateCopies", method = RequestMethod.POST)
	public String updateCopies(@RequestParam(value="branchId")String branchId, @RequestParam(value="bookId")String bookId, 
			@RequestParam(value="noOfCopies")String noOfCopies, @RequestParam(value="addCopies")String addCopies, 
			Locale locale, Model model) {
		BCopies bcopies = new BCopies();
		int i =0;
		try {
			Book book = (Book)admin.getServiceById("book", Integer.parseInt(bookId));
			Branch branch = (Branch)admin.getServiceById("branch", Integer.parseInt(branchId));
			bcopies.setBook(book);
			bcopies.setBranch(branch);
			bcopies.setAddCopies(Integer.parseInt(addCopies));
			bcopies.setNoofCopies(Integer.parseInt(noOfCopies));
			i = libs.updateCopies(bcopies);
			model.addAttribute("branch", branch);
			if(i != 0)
				model.addAttribute("message", "Book copies Sucessfully");
			else
				model.addAttribute("message", "Book copies was not updated, Opertation Failed!!");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "branch";
	}
	
	@RequestMapping(value = "updateBranch", method = RequestMethod.POST)
	public String updateBranch(@RequestParam(value="branchId")String branchId, @RequestParam(value="branchName")String branchName, 
			@RequestParam(value="branchAddr")String branchAddr, Locale locale, Model model) {
		int i =0;
		try {
			Branch branch = (Branch)admin.getServiceById("branch", Integer.parseInt(branchId));
			model.addAttribute("branch", branch);
			branch.setBranchAddress(branchAddr);
			branch.setBranchName(branchName);
			i = admin.updateService(branch);
			if(i != 0)
				model.addAttribute("message", "Branch details Sucessfully");
			else
				model.addAttribute("message", "Branch details was not updated , Opertation Failed!!");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "branch";
	}
	
}
