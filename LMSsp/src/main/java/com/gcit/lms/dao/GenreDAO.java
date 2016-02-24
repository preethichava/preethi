/**
 * 
 */
package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.gcit.lms.domain.Author;
import com.gcit.lms.domain.Book;
import com.gcit.lms.domain.Genre;

/**
 * @author prethi
 *
 */
public class GenreDAO extends BaseDAO implements ResultSetExtractor<List<Genre>>{

	@Autowired
	BookDAO bdao;

	public int createGenre(Genre genre) throws ClassNotFoundException,
			SQLException {
		return template.update("insert into tbl_genre (genre_name) values(?)",
				new Object[] { genre.getGenreName() });
	}

	public int updateGenre(Genre genre) throws ClassNotFoundException,
			SQLException {
		return template.update("update tbl_genre set genre_name = ? where genre_id = ?",
				new Object[] { genre.getGenreName(), genre.getGenreId() });
	}

	public int deleteGenre(int genreId) throws ClassNotFoundException,
			SQLException {
		if(checkGenre(genreId)==0)
		return template.update("delete from tbl_genre where genre_id=?",
				new Object[] { genreId });
		return 0;
	}
	
	public int checkGenre(int genreId) throws ClassNotFoundException,
	SQLException {
	return template.queryForObject("select Count(*) from tbl_book_genres where genre_id=?",
		new Object[] {genreId},Integer.class);
	}

	public List<Genre> getAllGenres() throws ClassNotFoundException,
			SQLException {
		return template.query("select * from tbl_genre", this);
	}

	public Genre getGenre(Genre genre) throws ClassNotFoundException,
			SQLException {
		List<Genre> genres = new ArrayList<Genre>();
		genres = template.query("select * from tbl_genre where genre_id = ?",
				new Object[] { genre.getGenreId() },this);

		if (genres != null && genres.size() > 0) {
			return genres.get(0);
		}
		return null;
	}

	@Override
	public List<Genre> extractData(ResultSet rs) {
		List<Genre> genres = new ArrayList<Genre>();
		
		try {
			while (rs.next()) {
				Genre a = new Genre();
				a.setGenreId(rs.getInt("genre_id"));
				a.setGenreName(rs.getString("genre_name"));
				List<Book> books = (List<Book>)bdao.template.query("select * from tbl_book where bookId in (select bookId from tbl_book_genres where genre_id = ?)",
						new Object[] { a.getGenreId() },bdao);
				genres.add(a);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 

		return genres;
	}

//	@Override
//	public List extractDataFirstLevel(ResultSet rs) {
//		// TODO Auto-generated method stub
//		List<Genre> genres = new ArrayList<Genre>();
//		
//		try {
//			while (rs.next()) {
//				Genre a = new Genre();
//				a.setGenreId(rs.getInt("genre_id"));
//				a.setGenreName(rs.getString("genre_name"));
//				
//				genres.add(a);
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//
//		return genres;
//	}
}
