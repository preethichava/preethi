package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.gcit.lms.domain.Author;
import com.gcit.lms.domain.Book;

public class AuthorDAO extends BaseDAO implements ResultSetExtractor<List<Author>> {

	@Autowired
	BookDAO bdao;
	
	public AuthorDAO(){
		
	}
	

	public void createAuthor(Author author) throws ClassNotFoundException,
			SQLException {
		template.update("insert into tbl_author (authorName) values(?)",
				new Object[] { author.getAuthorName() });
	}

	public void updateAuthor(Author author) throws ClassNotFoundException,
			SQLException {
		template.update("update tbl_author set authorName = ? where authorId = ?",
				new Object[] { author.getAuthorName(), author.getAuthorId() });
	}

	public int deleteAuthor(Author author) throws ClassNotFoundException, SQLException {
		
		if(checkAuthor(author) == 0)
			return template.update("delete from tbl_author where authorId=?",
				new Object[] { author.getAuthorId() });
		return 0;
	}
	
	public int deleteAuthor(int authorId) throws ClassNotFoundException, SQLException {
		return template.update("delete from tbl_author where authorId=?", new Object[] { authorId });
	}
	
	private int checkAuthor(Author author) throws ClassNotFoundException, SQLException {
		return template.queryForObject("select count(*) from tbl_book_authors where authorId=?", new Object[] { author.getAuthorId() },Integer.class);
	}

	/**
	 * This method will return the Authors based on given serach string.
	 * If search string is null or empty, it will return all the authors from the system.
	 * 
	 * @param searchString
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<Author> getAllAuthors(String searchString, int pageNo, int pageSize) throws ClassNotFoundException, SQLException {
		setPageNo(pageNo);
		setPageSize(pageSize);
		StringBuilder query = new StringBuilder("select * from tbl_author ");
		boolean searchStringExist = false;
		List<Author> authors =null;
		
		if(searchString!="" && !searchString.isEmpty() && searchString!=null){
			searchStringExist = true;
			searchString = "%"+searchString+"%";
			query.append("where authorName like ?");
		}/*else{
			query.append("where authorName not like ?");
		}*/
	
		if(pageNo >-1){
			int start = (pageNo-1)*getPageSize();
			if(start >= 0){
				query.append(" LIMIT "+start+" , "+getPageSize());

			}else{
				query.append(" LIMIT "+getPageSize());
			}
		}
		if(searchStringExist){
			authors = (List<Author>)template.query(query.toString(),new Object[] { searchString },this);
		}else{
			authors = (List<Author>)template.query(query.toString(),this);
		}
		return authors;		
	}
	
	public int getCount(String searchString) throws ClassNotFoundException, SQLException{
		searchString = "%"+searchString+"%";
		return template.queryForObject("select count(*) as count from tbl_author where authorName like ?", new Object[] { searchString }, Integer.class);
	}
	
	public List<Author> getAuthorsByName(String searchString) throws ClassNotFoundException, SQLException {
		searchString = "%"+searchString+"%";
		
		return (List<Author>)template.query("select * from tbl_author where authorName like ?", new Object[] { searchString},this);
		
	}

	public Author getAuthor(Author author) throws ClassNotFoundException,
			SQLException {
		List<Author> authors = new ArrayList<Author>();
		authors = (List<Author>)template.query("select * from tbl_author where authorId = ?",
				new Object[] { author.getAuthorId() },this);

		if (authors != null && authors.size() > 0) {
			return authors.get(0);
		}
		return null;
	}

	@Override
	public List<Author> extractData(ResultSet rs) {
		List<Author> authors = new ArrayList<Author>();
		
		try {
			while (rs.next()) {
				Author a = new Author();
				a.setAuthorId(rs.getInt("authorId"));
				a.setAuthorName(rs.getString("authorName"));
				
				List<Book> books = (List<Book>)bdao.template.query("select * from tbl_book where bookId in (select bookId from tbl_book_authors where authorId = ?)",
						new Object[] { a.getAuthorId() }, bdao);
				a.setBooks(books);

				authors.add(a);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 

		return authors;
	}

//	@Override
//	public List<Author> extractDataFirstLevel(ResultSet rs) {
//		List<Author> authors = new ArrayList<Author>();
//
//		try {
//			while (rs.next()) {
//				Author a = new Author();
//				a.setAuthorId(rs.getInt("authorId"));
//				a.setAuthorName(rs.getString("authorName"));
//				authors.add(a);
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//
//		return authors;
//	}
}
