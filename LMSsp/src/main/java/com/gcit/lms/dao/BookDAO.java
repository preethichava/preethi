/**
 * 
 */
package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.gcit.lms.domain.Author;
import com.gcit.lms.domain.Book;
import com.gcit.lms.domain.Genre;
import com.mysql.jdbc.PreparedStatement;

/**
 * @book Preethi
 * Book DAO
 *
 */
@SuppressWarnings("unchecked")
public class BookDAO extends BaseDAO implements ResultSetExtractor<List<Book>>{

	@Autowired
	PublisherDAO pdao;
	@Autowired
	AuthorDAO adao;
	@Autowired
	GenreDAO gdao;
	@Autowired
	BasicDataSource ds;
	
	public void createBook(final Book book) throws ClassNotFoundException, SQLException {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		final String query = "insert into tbl_book (title, pubId) values(?, ?)";
		
		SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(template);
        jdbcInsert.withTableName("tbl_book").usingGeneratedKeyColumns("bookid");
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("title", book.getTitle());
        parameters.put("pubId", book.getPublisher().getPublisherId());
        // execute insert
        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        int bookId = ((Number) key).intValue();
		/*PreparedStatementCreator psc = new PreparedStatementCreator() {           

            @Override
            public PreparedStatement createPreparedStatement(Connection connection)
                    throws SQLException {
                PreparedStatement ps = (PreparedStatement) ds.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, book.getTitle());
                ps.setInt(2, book.getPublisher().getPublisherId());
                
                return ps;
            }
        };
        
		template.update(psc,keyHolder);
		
		int bookId = keyHolder.getKey().intValue();*/
		if(bookId>0)
		{
			if(null != book.getAuthors() && !book.getAuthors().isEmpty()){
				for (Author a : book.getAuthors()) {
					template.update("insert into tbl_book_authors (bookId, authorId) values (?,?)",
							new Object[] { bookId, a.getAuthorId() });
				}
			}
	
			if(null != book.getGenres() && !book.getGenres().isEmpty()){
				for (Genre g : book.getGenres()) {
					template.update("insert into tbl_book_genres (bookId, genreId) values (?,?)",
							new Object[] { bookId, g.getGenreId() });
				}
			}
		}

	}

	public List<Book> getAllBooks() throws ClassNotFoundException,
			SQLException {
		return template.query("select * from tbl_book", this);
	}

	
	@Override
	public List<Book> extractData(ResultSet rs) {
		List<Book> books = new ArrayList<Book>();
		
		try {
			while (rs.next()) {
				Book b = new Book();
				b.setBookId(rs.getInt("bookId"));
				b.setTitle(rs.getString("title"));
/*				b.setPublisher(pdao.getPublisherById(rs.getInt("pubId")));

				List<Author> authors = (List<Author>)adao.template.query("select * from tbl_author where authorId in (select authorId from tbl_book_authors where bookId = ?)",
						new Object[] { b.getBookId() },adao);
				b.setAuthors(authors);
				
				List<Genre> genres = (List<Genre>) gdao.template.query("select * from tbl_genre where genre_id in (select genre_id from tbl_book_genres where bookId = ?)",
						new Object[] { b.getBookId() },gdao);
				b.setGenres(genres);*/
				
				books.add(b);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return books;
	}
	 
	public int updateBook(Book book) throws ClassNotFoundException, SQLException {
		return template.update("update tbl_book set title = ? where bookId = ?", new Object[] { book.getTitle(), book.getBookId() });
	}

	public int deleteBook(Book book) throws ClassNotFoundException, SQLException {
		int i=0;
		if(checkBook(book) == 0)
			i=template.update("delete from tbl_book where bookId=?",
			new Object[] { book.getBookId() });
		if(i>0)
			return deleteBookAuthors(book);
		else
			return 0;
	}

	public int checkBook(Book book) throws ClassNotFoundException,SQLException {
		return template.queryForObject("select Count(*) from tbl_book_loans where bookId=? and dateIn IS NOT NULL", new Object[] { book.getBookId() },Integer.class);
	}


	public int deleteBookAuthors(Book book) throws ClassNotFoundException, SQLException {
		return template.update("delete from tbl_book_authors where bookId=?", new Object[] { book.getBookId() });
	}

	public Book getBook(int bookId) throws ClassNotFoundException, SQLException {
		List<Book> books = new ArrayList<Book>();
		books = template.query("select * from tbl_book where bookId = ?", new Object[] { bookId },this);
		if (books != null && books.size() > 0) {
			return books.get(0);
		}
		return null;
	}
}
