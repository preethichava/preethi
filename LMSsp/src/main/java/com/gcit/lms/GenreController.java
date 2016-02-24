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

import com.gcit.lms.domain.Book;
import com.gcit.lms.domain.Genre;
import com.gcit.lms.service.AdministratorService;

@Controller
public class GenreController {
	
	@Autowired
	AdministratorService admin;

	/**
	 * Method will take the basic screen of Genres.
	 * 
	 * @param locale
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "genre", method = RequestMethod.GET)
	public String genre(Locale locale, Model model) {
		return "genre";
	}
	
	/**
	 * This method will load the Add Genre screen.
	 * 
	 * @param locale
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "addgenre", method = RequestMethod.GET)
	public String addGenre(Locale locale, Model model) {
		return "addgenre";
	}
	
	/**
	 * This method will add new Genre.
	 * 
	 * @param locale
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "addGenre", method = RequestMethod.POST)
	public String addGenre(@RequestParam(value="genreName")String genreName, Locale locale, Model model) {

		Genre genre = new Genre();
		genre.setGenreName(genreName);
		int i=0;
		try {
			i= admin.createService(genre);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		
		if(i != 0)
			model.addAttribute("message", "Genre Added Sucessfully");
		else
			model.addAttribute("message", "Genre was not Added, Opertation Failed!!");
		return "genre";
	}
	
	/**
	 * This method will display all genres.
	 * 
	 * @param locale
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "viewgenre", method = RequestMethod.GET)
	public String viewGenres(Locale locale, Model model) {
		List<Genre> genres = new ArrayList<Genre>();
		try {
			genres = (List<Genre>)admin.getService("genres");
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("genres", genres);
		return "viewgenre";
	}
}
