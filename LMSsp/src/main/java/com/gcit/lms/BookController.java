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

import com.gcit.lms.domain.Author;
import com.gcit.lms.domain.Book;
import com.gcit.lms.domain.Publisher;
import com.gcit.lms.service.AdministratorService;
import com.gcit.lms.service.LibrarianService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class BookController {
	
	private static final Logger logger = LoggerFactory.getLogger(BookController.class);
	
	@Autowired
	AdministratorService admin;
	
	@Autowired
	LibrarianService libs;
	
	@RequestMapping(value = "book", method = RequestMethod.GET)
	public String book(Locale locale, Model model) {
		return "book";
	}
	
	/**
	 * Will load screen for new book addition.
	 * 
	 * @param locale
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "addbook", method = RequestMethod.GET)
	public String addBook(Locale locale, Model model) {

		List<Author> authors = new ArrayList<Author>();
		try {
			authors = (List<Author>)admin.getService("authors","",-1,0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		List<Publisher> pubs = new ArrayList<Publisher>();
		try {
			pubs = (List<Publisher>)admin.getService("pubs","",-1,0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		model.addAttribute("authors", authors);
		model.addAttribute("pubs", pubs);
		return "addbook";
	}
	
	/**
	 * This will add the book information to LMSystem.
	 * 
	 * @param authorName
	 * @param locale
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "addBook", method = RequestMethod.POST)
	public String addBook(@RequestParam(value="authorId")String authorId, @RequestParam(value="pubId")String pubId, 
			@RequestParam(value="authorName")String authorName, @RequestParam(value="pubName")String pubName,
			@RequestParam(value="pubAddr")String pubAddr, @RequestParam(value="pubPhone")String pubPhone, 
			@RequestParam(value="title")String bookTittle, Locale locale, Model model) {
		Book b = new Book();
		if(null != authorId && authorId.equals("new")) {
			addAuthor(authorName);
		}
		else {
			try {
				Author auth = (Author) admin.getServiceById("authorId", Integer.parseInt(authorId));
				List<Author> authors = new ArrayList<Author>();
				authors.add(auth);
				b.setAuthors(authors);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if(null != pubId && pubId.equals("new")) {
			addPublisher(pubName, pubAddr, pubPhone);
		}
		else {
			try {
				Publisher pub = (Publisher) admin.getServiceById("pubById", Integer.parseInt(pubId));
				b.setPublisher(pub);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		b.setTitle(bookTittle);
		int i=0;
		try {
			i = admin.createService(b);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(i != 0)
			model.addAttribute("message", "Book Added Sucessfully");
		else
			model.addAttribute("message", "Book was not Added, Opertation Failed!!");
		return "book";
	}
	
	/**
	 * 
	 * @param locale
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "viewbooks", method = RequestMethod.GET)
	public String viewBooks(Locale locale, Model model) {
		List<Book> books = new ArrayList<Book>();
		try {
			books = (List<Book>)admin.getService("books");
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("books", books);
		return "viewbooks";
	}
	
	/**
	 * 
	 * @param locale
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "editbook", method = RequestMethod.GET)
	public String editBook(@RequestParam(value="bookId") String bookId, Locale locale, Model model) {
		Book book = new Book();
		try {
			book = (Book)admin.getServiceById("book", Integer.parseInt(bookId));
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("book", book);
		return "editbook";
	}
	
	/**
	 * 
	 * @param locale
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "editBook", method = RequestMethod.POST)
	public String editBookInfo(@RequestParam(value="bookId") String bookId, 
			@RequestParam(value="bookName") String bookName,  Locale locale, Model model) {
		Book book = new Book();
		book.setBookId(Integer.parseInt(bookId));
		book.setTitle(bookName);
		int i = 0;
		try {
			i = admin.updateService(book);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("book", book);
		if(i != 0)
			model.addAttribute("message", "Book Update Sucessfully");
		else
			model.addAttribute("message", "Book was not Updated, Opertation Failed!!");
		List<Book> books = new ArrayList<Book>();
		try {
			books = (List<Book>)admin.getService("books");
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("books", books);
		return "viewbooks";
	}
	
	/**
	 * Delete book information.
	 * 
	 * @param locale
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "deleteBook", method = RequestMethod.GET)
	public String deleteBook(@RequestParam(value="bookId") String bookId, Locale locale, Model model) {
		Book book = new Book();
		int i = 0;
		try {
			book = (Book)admin.getServiceById("book", Integer.parseInt(bookId));
			// Loans
			// Book Authors
			// Copies
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("book", book);
		if(i != 0)
			model.addAttribute("message", "Book Deleted Sucessfully");
		else
			model.addAttribute("message", "Book was not Deleted, Opertation Failed!!");
		List<Book> books = new ArrayList<Book>();
		try {
			books = (List<Book>)admin.getService("books");
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("books", books);
		return "viewbooks";
	}
	
	/**
	 * Add author information.
	 * 
	 * @param authorName
	 */
	private void addAuthor(String authorName) {
		Author author = new Author();
		author.setAuthorName(authorName);
		int i=0;
		try {
			i = admin.createService(author);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Add publisher information.
	 * 
	 * @param pubName
	 * @param pubAddress
	 * @param publisherPhone
	 * @param locale
	 * @param model
	 * @return
	 */
	private void addPublisher(String pubName, String pubAddress, String publisherPhone) {
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
	}
}
