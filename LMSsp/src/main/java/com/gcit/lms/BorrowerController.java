package com.gcit.lms;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import com.gcit.lms.domain.Borrower;
import com.gcit.lms.domain.Branch;
import com.gcit.lms.domain.Loans;
import com.gcit.lms.service.AdministratorService;
import com.gcit.lms.service.BorrowerService;
import com.gcit.lms.service.LibrarianService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class BorrowerController {
	
	private static final Logger logger = LoggerFactory.getLogger(BorrowerController.class);
	
	@Autowired
	AdministratorService admin;
	
	@Autowired
	BorrowerService borrow;
	
	@Autowired
	LibrarianService libser;

	/**
	 * 
	 * @param locale
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "users", method = RequestMethod.GET)
	public String getUser(Locale locale, Model model) {
		return "borrower";
	}

	@RequestMapping(value = "borrower", method = RequestMethod.GET)
	public String getBorrower(Locale locale, Model model) {
		return "borrower";
	}
	
	@RequestMapping(value = "verifyCard", method = RequestMethod.POST)
	public String verifyCard(@RequestParam(value="cardNo")String cardNum, Locale locale, Model model) {
		Borrower borrower = null;
		try {
			borrower = borrow.verifyCard(Integer.parseInt(cardNum));
			if(null != borrower){
				model.addAttribute("borrower", borrower);
				return "bindex";
			}
			else {
				model.addAttribute("message", "Borrower not existed");
			}
		} catch (NumberFormatException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			model.addAttribute("message", "Borrower not existed");
		}
		return "borrower";
	}
	
	
	@RequestMapping(value = "bookLoan", method = RequestMethod.POST)
	public String selectBranch(@RequestParam(value="cardNum")String cardNum, @RequestParam(value="loan")String loan,
			Locale locale, Model model) {
		
		Borrower borrower = null;
		List<Branch> branches = new ArrayList<Branch>();
		try {
			borrower = borrow.verifyCard(Integer.parseInt(cardNum));
			if(null != borrower){
				model.addAttribute("borrower", borrower);
			}
			branches = (List<Branch>)admin.getService("branch");
			model.addAttribute("branches", branches);
			model.addAttribute("selectedOption", loan);
		} catch (NumberFormatException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			model.addAttribute("message", "Borrower not existed");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "selectbranch";
	}
	
	@RequestMapping(value = "selectBranch", method = RequestMethod.POST)
	public String checkInOrCheckout(@RequestParam(value="cardNum")String cardNum, 
			@RequestParam(value="selectedOpt")String checkIorOut, @RequestParam(value="branchId")String branchId,
			Locale locale, Model model) {
		
		Borrower borrower = null;
		Branch branch = null;
		List<BCopies> copies = new ArrayList<BCopies>();
		List<Loans> loans = new ArrayList<Loans>();
		try {
			borrower = borrow.verifyCard(Integer.parseInt(cardNum));
			if(null != borrower){
				model.addAttribute("borrower", borrower);
			}
			branch = libser.getBranch(Integer.parseInt(branchId));
			if(branch != null){
				model.addAttribute("branch", branch);
				copies = libser.getAvailableBooks(branch.getBranchId());
				model.addAttribute("copies", copies);
			}
			loans = admin.getLoans(Integer.parseInt(cardNum));
			model.addAttribute("loans", loans);
			
		} catch (NumberFormatException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			model.addAttribute("message", "Borrower not existed");
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(null != checkIorOut && checkIorOut.equals("checkout")){
			return "checkout";
		}
		else {
			return "checkin";
		}
	}
	
	@RequestMapping(value = "checkOut", method = RequestMethod.POST)
	public String checkOutBook(@RequestParam(value="cardNo")String cardNo, 
			@RequestParam(value="branchId")String branchId, @RequestParam(value="bookId")String bookId,
			@RequestParam(value="noOfCopies")String noOfCopies, Locale locale, Model model) {
		Loans loan = new Loans();
		try {
			Book book = (Book)admin.getServiceById("book", Integer.parseInt(bookId));
			loan.setBook(book);
			Branch branch = (Branch)admin.getServiceById("branch", Integer.parseInt(branchId));
			loan.setBranch(branch);
			Borrower borrower = (Borrower)admin.getServiceById("cardNo", Integer.parseInt(cardNo));
			loan.setBorrower(borrower);
			loan.setDateOut(new Date());
			loan.setDueDate(addDaystoDate(30));
			
			borrow.checkOut(loan);
			model.addAttribute("message", "Checkedout Sucessfully");
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", "Checkout not Sucessfull!!, Please contact Librarian.");
		}
		return "borrower";
	}
	
	@RequestMapping(value = "returnBook", method = RequestMethod.POST)
	public String checkInBook(@RequestParam(value="cardNo")String cardNo, 
			@RequestParam(value="branchId")String branchId, @RequestParam(value="bookId")String bookId,
			Locale locale, Model model) {
		Loans loan = new Loans();
		try {
			Book book = (Book)admin.getServiceById("book", Integer.parseInt(bookId));
			loan.setBook(book);
			Branch branch = (Branch)admin.getServiceById("branch", Integer.parseInt(branchId));
			loan.setBranch(branch);
			Borrower borrower = (Borrower)admin.getServiceById("cardNo", Integer.parseInt(cardNo));
			loan.setBorrower(borrower);
			loan.setDateIn(new Date());
			
			borrow.returnBook(loan);
			model.addAttribute("message", "Check in completed Sucessfully");
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", "Checkin not Sucessfull!!, Please contact Librarian.");
		}
		return "borrower";
	}
	
	private Date addDaystoDate(final int noOfDays){
		 
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, noOfDays); 
		Date date = cal.getTime();
		return date;
	}
}
