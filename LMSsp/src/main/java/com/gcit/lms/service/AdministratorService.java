package com.gcit.lms.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.gcit.lms.dao.AuthorDAO;
import com.gcit.lms.dao.BCopiesDAO;
import com.gcit.lms.dao.BookDAO;
import com.gcit.lms.dao.BorrowerDAO;
import com.gcit.lms.dao.BranchDAO;
import com.gcit.lms.dao.GenreDAO;
import com.gcit.lms.dao.LoansDAO;
import com.gcit.lms.dao.PublisherDAO;
import com.gcit.lms.domain.Author;
import com.gcit.lms.domain.Book;
import com.gcit.lms.domain.Borrower;
import com.gcit.lms.domain.Branch;
import com.gcit.lms.domain.Genre;
import com.gcit.lms.domain.Loans;
import com.gcit.lms.domain.Publisher;

public class AdministratorService {
	
	@Autowired
	BasicDataSource ds;
	
	@Autowired
	AuthorDAO adao;
	
	@Autowired
	GenreDAO gdao;
	
	@Autowired
	PublisherDAO pdao;
	
	@Autowired
	BranchDAO bhdao;
	
	@Autowired
	BorrowerDAO udao;
	
	@Autowired
	BCopiesDAO bcdao;
	
	@Autowired
	BookDAO bdao;
	
	@Autowired
	LoansDAO ldao;
	
	/**
	 * Will create Author, Book, Genre, Publisher, Borrower, and Branch;
	 * 
	 * @param obj
	 * @return numeric.
	 * @throws Exception
	 */
	public int createService(Object obj) throws Exception
	{		
		if( obj instanceof Author)
			return createAuthor((Author)obj);
		else if(obj instanceof Book)
			return createBook((Book)obj);
		else if	(obj instanceof Genre)	
			return createGenre((Genre)obj);
		else if(obj instanceof Publisher)
			return createPublisher((Publisher)obj);
		else if(obj instanceof Borrower)
			return createBorrower((Borrower)obj);
		else if(obj instanceof Branch)
			return createBranch((Branch)obj);
		else
			return 0;
	}
	
	/**
	 * Will update the Author, Book, Genre, Publisher, Borrower and Branch.
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public int updateService(Object obj) throws Exception
	{
		if( obj instanceof Author)
			return updateAuthor((Author)obj);
		else if(obj instanceof Book)
			return updateBook((Book)obj);
		else if	(obj instanceof Genre)	
			return updateGenre((Genre)obj);
		else if(obj instanceof Publisher)
			return updatePublisher((Publisher)obj);
		else if(obj instanceof Borrower)
			return updateBorrower((Borrower)obj);
		else if(obj instanceof Branch)
			return updateBranch((Branch)obj);
		else
			return 0;
	}
	
	public List<?> getService(String str)  throws Exception{
		return getService(str, null, 0, 0);
	}

	/**
	 * Will return the Authors, Publishers, Books, Borrowers, Genres and Branches information.
	 * 
	 * @param str
	 * @param searchString
	 * @param startNo
	 * @param endCnt
	 * @return
	 * @throws Exception
	 */
	public List<?> getService(String str, String searchString, int startNo, int endCnt ) throws Exception
	{
		switch(str)
		{
			case "pubs":
				return (List<?>) getPublisher(searchString, startNo, endCnt);
			case "authors":
				return (List<?>) getAuthor(searchString, startNo, endCnt);
			case "books":
				return (List<?>) getBook();
			case "users":
				return (List<?>)getBorrower(startNo, endCnt);
			case "genres":
				return (List<?>)getGenres(startNo, endCnt);
			case "branch":
				return (List<?>)getBranch(startNo, endCnt);
			default:
				break;
		}
		return null;
	}

	public int getCount(String string, String searchString) throws ClassNotFoundException, SQLException {
		switch(string)
		{
		case "pubs":
			return  publisherCount(searchString);
		case "authors":
			return authorCount(searchString);
		//case "books":
		//	return  getBook();
		case "users":
			return borrowerCount(searchString);
		case "genres":
			return genreCount(searchString);
		case "branch":
			return branchCount(searchString);
		default:
			break;
		}
		return 0;
	}

	public Object getServiceById(String str, int id) throws Exception
	{	
		switch(str)
		{
			case "authorId":
				return getAuthor(id);
			case "pubById":
				return getPublisher(id);
			case "cardNo":
				return getBorrower(id);
			case "book":
				return getBook(id);
			case "branch" :
				return getIndviduvalBranch(id);
			default:
				break;
		}
		return null;
	}

	/**
	 * Get Loans.
	 * 
	 * @param cardNo
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public List<Loans> getLoans(int cardNo) throws Exception {
		return ldao.getLoans(cardNo);
	}

	/**
	 * Will delete Publisher, Borrower, Branch, Genre and Author.
	 * 
	 * @param str
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public int deleteService(String str, int id) throws Exception {
		switch(str)
		{
			case "pub":
				return deletePublisher(id);
			case "cardNo":
				return deleteBorrower(id);
			case "branch":
				return deleteBranch(id);
			case "genre":
				return deleteGenre(id);
			case "author":
				return deleteAuthor(id);
			default:
				break;
		}
		return 0;
	}

	/**
	 * New Author creation.
	 * 
	 * @param author
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public int createAuthor(Author author) throws Exception {
		int i=0;
		if(author.getAuthorName()!= null && author.getAuthorName() !=""){
			if(author.getAuthorName().length() > 45){
				throw new Exception("Author Name cannot be more than 45 chars");
			}
			else{
				adao.createAuthor(author);
				i=1;
			}
		}
		return i;
	}
	
	@Transactional
	public int extendDate(int bookId, int branchId, int cardNo) throws Exception {
		int i=0;
		i= ldao.extendLoan(bookId,branchId,cardNo);
		return i;
	}
	
	@Transactional
	private int updateGenre(Genre obj) throws Exception {
		int i=0;
		
			if(obj.getGenreName() !=null && obj.getGenreName() !="" ){
			if(obj.getGenreName().length() > 20){
				throw new Exception("Genre Name cannot be more than 20 chars");
			}else{
				gdao.updateGenre(obj);
				i=1;
			}
		}
		else
			throw new Exception("Genre Name cannot be null");
		return i;
	}
	
	/**
	 * Book update.
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Transactional
	private int updateBook(Book obj) throws Exception {
		int i=0;
		if(obj.getTitle() != null && obj.getTitle() != "" ){
			if(obj.getTitle().length() > 30){
				throw new Exception("Book Name cannot be more than 30 chars");
			}else{
				bdao.updateBook(obj);
				i=1;
			}
		}
		else
			throw new Exception("Book Name cannot be null");
		return i;
	}
		
	
	@Transactional
	private int updatePublisher(Publisher obj)throws Exception {
		int i=0;
		if(obj.getPublisherName() !=null){
			if(obj.getPublisherName().length() > 20){
				throw new Exception("Publisher Name cannot be more than 20 chars");
			}else{
				pdao.updatePublisher(obj);
				i=1;
			}
			
			}
			
		return i;
	}
	
	@Transactional
	private int updateBranch(Branch obj) throws Exception {
		int i=0;
		
			if(obj.getBranchName() !=null && obj.getBranchName() !=""){
			if(obj.getBranchName().length() > 20){
				throw new Exception("Branch Name cannot be more than 20 chars");
			}else{
				bhdao.updateBranch(obj);
				i=1;
			}
			
			}
			else
				throw new Exception("Branch Name and Address cannot be null");
				
				
		return i;
	}

	/**
	 * Will update the Author information.
	 * 
	 * @param author
	 * @return
	 * @throws Exception
	 */
	@Transactional
	private int updateAuthor(Author author) throws Exception {
		int i=0;
		if(author.getAuthorName()!=null && author.getAuthorName() !=""){
			if(author.getAuthorName().length() > 45){
				throw new Exception("Author Name cannot be more than 45 chars");
			}else{
				adao.updateAuthor(author);
				i=1;
			}
		}
		return i;
	}
	
	@Transactional
	private int updateBorrower(Borrower obj) throws Exception{
		int i=0;
			if(obj.getName().length() > 20){
				throw new Exception("Borrower Name cannot be more than 20 chars");
			}else{
				udao.updateBorrower(obj);
				i=1;
			}
			
			
			
		return i;
	}

	@Transactional
	private int branchCount(String searchString) throws ClassNotFoundException, SQLException {
		//	return bhdao.getCount(searchString);
		return 0;
	}

	@Transactional
	private int genreCount(String searchString) throws ClassNotFoundException, SQLException {
		//	return gdao.getCount(searchString);
		return 0;
	}
	
	@Transactional
	private int borrowerCount(String searchString) throws SQLException, ClassNotFoundException {
		//		return udao.getCount(searchString);
		return 0;
	}

	@Transactional
	private int publisherCount(String searchString) throws ClassNotFoundException, SQLException {
		return pdao.getCount(searchString);
	}

	@Transactional
	private int authorCount(String searchString) throws ClassNotFoundException, SQLException {
		return adao.getCount(searchString);
	}

	@Transactional
	private List<?> getBook() throws ClassNotFoundException, SQLException {
		return bdao.getAllBooks();
	}

	/**
	 * Will return the book information.
	 * 
	 * @param bookId
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	@Transactional
	private Object getBook(int bookId) throws ClassNotFoundException, SQLException {
		return bdao.getBook(bookId);
	}
	
	@Transactional
	private Object getIndviduvalBranch(int branchId) throws ClassNotFoundException, SQLException {
		return bhdao.getBranch(branchId);
	}

	@Transactional
	private List<?> getBranch(int startNo, int endCnt) throws Exception {
		return bhdao.getAllBranchs();
	}

	@Transactional
	private List<?> getGenres(int startNo, int endCnt) throws Exception {
		return gdao.getAllGenres();
	}

	@Transactional
	private List<?> getBorrower(int startNo, int endCnt) throws Exception {
		return udao.getAllBorrowers();
	}

	@Transactional
	private Object getBorrower(int cardNo) throws Exception {
		return udao.getBorrower(cardNo);
	}

	/***
	 * Retrieve the publisher information.
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@Transactional
	private Object getPublisher(int id) throws Exception {
		return pdao.getPublisherById(id);
	}
	
	/***
	 * Retrieve the Author information.
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@Transactional
	private Object getAuthor(int id) throws Exception {
		Author author = new Author();
		author.setAuthorId(id);
		return adao.getAuthor(author);
	}

	@Transactional
	private List<?> getPublisher(String searchString, int pageNo, int pageSize) throws Exception {
		return pdao.getAllPublishers(searchString,pageNo, pageSize);
	}

	/**
	 * Will load all the authors from the system.
	 * 
	 * @param searchString
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	@Transactional
	private List<?> getAuthor(String searchString, int pageNo, int pageSize) throws Exception {
		return adao.getAllAuthors(searchString,pageNo, pageSize);
	}

	@Transactional
	private int deleteGenre(int genreId) throws Exception {
		return gdao.deleteGenre(genreId);
	}
	
	/**
	 * Will delete the author information.
	 * 
	 * @param genreId
	 * @return
	 * @throws Exception
	 */
	@Transactional
	private int deleteAuthor(int authorId) throws Exception {
		return adao.deleteAuthor(authorId);
	}

	@Transactional
	private int deleteBranch(int branchId) throws Exception {
		return bhdao.deleteBranch(branchId);
	}

	@Transactional
	private int deleteBorrower(int cardNo) throws Exception {
		return udao.deleteBorrower(cardNo);
	}

	@Transactional
	private int deletePublisher(int publisherId)throws Exception {
		return pdao.deletePublisher(publisherId);
	}

	@Transactional
	private int createBranch(Branch obj) throws Exception {
		int i=0;
		if(obj.getBranchName() !=null && obj.getBranchName() !="" && obj.getBranchAddress() != null && obj.getBranchAddress() != ""){
			if(obj.getBranchName().length() > 20){
				throw new Exception("Branch Name cannot be more than 20 chars");
			}else{
				bhdao.createBranch(obj);
				i=1;
			}
			
		}
		else
			throw new Exception("Branch Name and Address cannot be null");
		return i;
	}

	@Transactional
	private int createBorrower(Borrower obj) throws Exception {
		int i=0;
		if(obj.getName() !=null){
			if(obj.getName().length() > 20){
				throw new Exception("Borrower Name cannot be more than 20 chars");
			}else{
				udao.createBorrower(obj);
				i=1;
			}
		}
		return i;
			
	}

	@Transactional
	private int createPublisher(Publisher obj) throws Exception {
		int i=0;
		if(obj.getPublisherName() !=null && obj.getPublisherName() !=""){
			if(obj.getPublisherName().length() > 20){
				throw new Exception("Publisher Name cannot be more than 20 chars");
			}else{
				pdao.createPublisher(obj);
				i=1;
			}
		}
		else
			throw new Exception("Publisher Name is empty");
			
		return i;
	}
	
	
	@Transactional
	private int createGenre(Genre genre) throws Exception {
		int i=0;
		if(genre.getGenreName() !=null && genre.getGenreName() != ""){
			if(genre.getGenreName().length() > 20){
				throw new Exception("Genre Name cannot be more than 20 chars");
			}else{
				gdao.createGenre(genre);
				i=1;
			}
		}
		else
			throw new Exception("Genre Name cannot be null");
			
		return i;
	}

	@Transactional
	private int createBook(Book obj) {
		int i = 0;
		try {
			bdao.createBook(obj);
			i = 1;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return i;
	}
}
