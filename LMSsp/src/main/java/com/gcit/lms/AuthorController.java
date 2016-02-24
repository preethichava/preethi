/*
 * Copyright (c) 2016, GCIT and/or its affiliates. All rights reserved.
 * GCIT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 */
package com.gcit.lms;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gcit.lms.domain.Author;
import com.gcit.lms.domain.Book;
import com.gcit.lms.service.AdministratorService;

/**
 * The <code>AuthorController</code> class represents the Spring controller. 
 * The class <code>AuthorController</code> includes methods for author management.
 *
 * @author  Preethi.
 * @since   V1.0
 */
@Controller
public class AuthorController {
	
	/**
	 * Administration service.
	 */
	@Autowired
	AdministratorService admin;

	/**
	 * This method will load the author management home page.
	 * 
	 * @param locale
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "author", method = RequestMethod.GET)
	public String author(Locale locale, Model model) {
		return "author";
	}
	
	/**
	 * This will load the add authors screen.
	 * 
	 * @param locale
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "addauthor", method = RequestMethod.GET)
	public String addAuthors(Locale locale, Model model) {
		return "addauthor";
	}
	
	/**
	 * This will add the author information to system.
	 * 
	 * @param authorName
	 * @param locale
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "addAuthor", method = RequestMethod.POST)
	public String addAuthor(@RequestParam(value="authorName")String authorName, Locale locale, Model model) {
		Author author = new Author();
		author.setAuthorName(authorName);
		int i=0;
		try {
			i = admin.createService(author);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(i != 0)
			model.addAttribute("message", "Author Added Sucessfully");
		else
			model.addAttribute("message", "Author was not Added, Opertation Failed!!");
		return "author";
	}
	
	/**
	 * Will Update the author information.
	 * 
	 * @param authorName
	 * @param locale
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "editAuthor", method = RequestMethod.POST)
	public String editAuthor(@RequestParam(value="authorId")String authorId, 
			@RequestParam(value="authorName")String authorName, Locale locale, Model model) {
		Author author = new Author();
		author.setAuthorName(authorName);
		
		int i=0;
		try {
			author.setAuthorId(Integer.parseInt(authorId));
			i = admin.updateService(author);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(i != 0)
			model.addAttribute("message", "Author Update Sucessfully");
		else
			model.addAttribute("message", "Author was not Updated, Opertation Failed!!");
		return "author";
	}
	
	/**
	 * Will Delete the author information.
	 * 
	 * @param authorName
	 * @param locale
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "deleteAuthor", method = RequestMethod.GET)
	public String deleteAuthor(@RequestParam(value="authorId")String authorId,  Locale locale, Model model) {
		int i=0;
		try {
			i = admin.deleteService("author", Integer.parseInt(authorId) );
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(i != 0)
			model.addAttribute("message", "Author Deleted Sucessfully");
		else
			model.addAttribute("message", "Author was not Deleted, Opertation Failed!!");
		return "author";
	}
	
	@RequestMapping(value = "searchAuthors", method = RequestMethod.GET)
	public @ResponseBody String searchAuthors(@RequestParam(value="searchString")String searchString,Locale locale, Model model) throws Exception{
		List<Author> authors = new ArrayList<Author>();
		authors = (List<Author>)admin.getService("authors",searchString,1, 10);
		int count = admin.getCount("authors", searchString);
		int pages = 1;

		if(count%10==0){
			pages = count/10;
		}else{
			pages = (count/10)+1;
		}
		StringBuilder str = new StringBuilder();
		str.append("<nav><ul class='pagination'>");
		 
		for(int i=1;i<=pages;i++){
		    str.append("<li><a href='javascript:pageAuthors("+i+");'>"+i+"</a></li>");
		    }
			
			
			str.append("</ul></nav><div class='table-responsive'>"
					+ "<table class='table table-striped' id='authorsTable'>"
					+ "<thead><tr><th>Author Name</th><th>Books Written</th><th>Edit Author</th>"
					+ "<th>Delete Author</th></tr></thead>");
			for(Author a: authors){ 
				str.append("<tr><td><input type='text'  id='id' name='id' value='"+a.getAuthorId()+"' style='display:none'>"
			+a.getAuthorName()+"</td><td ><select>");
				if(a.getBooks()!=null){for(Book b:a.getBooks()){
								str.append("<option>"+b.getTitle() +"</option>");
				                }}
				str.append("</select></td><td ><button type='button' class='btn btn-sm btn-info' "
						+ "onclick='update("+a.getAuthorId() +",\""+a.getAuthorName()+"\")'  "
								+ "data-toggle='modal'>EDIT</button></td><td >"
								+ "<button type='button' class='btn btn-sm btn-danger'"
								+ "  onclick='javascript:location.href='deleteAuthor?authorId="+a.getAuthorId()
								+"'>DELETE</button></td></tr>");
					}
			str.append("</table></div></div>");
			return new String(str);
			
		
		
	}
	
	/**
	 * Page navigation.
	 * 
	 * @param pageNo
	 * @param searchString
	 * @param locale
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "pageAuthors", method = RequestMethod.GET)
	public @ResponseBody String pageAuthors(@RequestParam(value="pageNo")int pageNo,@RequestParam(value="searchString")String searchString,Locale locale, Model model)throws Exception{
		List<Author> authors = new ArrayList<Author>();
		authors = (List<Author>)admin.getService("authors",searchString, pageNo, 10);
		StringBuilder str = new StringBuilder();
		str.append("<thead><tr><th>Author Name</th><th>Books Writeen</th><th>Edit Author</th><th>Delete Author</th></tr></thead>");
		for(Author a: authors){ 
			str.append("<tr><td><input type='text'  id='id' name='id' value='"+a.getAuthorId()+"' style='display:none'>"
		+a.getAuthorName()+"</td><td ><select>");
		if(a.getBooks()!=null){for(Book b:a.getBooks()){
						str.append("<option>"+b.getTitle() +"</option>");
		                }}
		str.append("</select></td><td ><button type='button' class='btn btn-sm btn-info' "
				+ "onclick='update("+a.getAuthorId() +",\""+a.getAuthorName()+"\")'  "
						+ "data-toggle='modal'>EDIT</button></td><td >"
						+ "<button type='button' class='btn btn-sm btn-danger'"
						+ "  onclick='javascript:location.href='deleteAuthor?authorId="+a.getAuthorId()
						+"'>DELETE</button></td></tr>");
			}
		return new String(str);
	}
	
	/**
	 * Will load all the authors.
	 * 
	 * @param locale
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "viewauthors", method = RequestMethod.GET)
	public String viewauthor(Locale locale, Model model) throws Exception {
		List<Author> authors = new ArrayList<Author>();
		authors = (List<Author>)admin.getService("authors","", 0, 10);
		model.addAttribute("authors", authors);
		int count = admin.getCount("authors", "");
		int pages = 1;
		if(count%10==0){
			pages = count/10;
		}else{
			pages = (count/10)+1;
		}
		model.addAttribute("pages", pages);
		return "viewauthors";
	}
}
