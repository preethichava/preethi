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

import com.gcit.lms.domain.Book;
import com.gcit.lms.domain.Branch;
import com.gcit.lms.domain.Publisher;

import com.gcit.lms.domain.Author;


@SuppressWarnings({ "unchecked", "rawtypes" })
public class PublisherDAO extends BaseDAO implements ResultSetExtractor<List<Publisher>> {

	@Autowired
	BookDAO bdao;

	public int createPublisher(Publisher publisher) throws ClassNotFoundException,
	SQLException {
return template.update("insert into tbl_publisher (publisherName,publisherAddress,publisherPhone) values(?,?,?)",
		new Object[] { publisher.getPublisherName(),publisher.getPublisherAddress(),publisher.getPublisherPhone() });
}

	public List<Publisher> readAll() throws ClassNotFoundException,
			SQLException {
		return template.query("select * from tbl_publisher", this);
	}
	

	
	public Publisher getPublisherById(int publisherId) throws ClassNotFoundException,
			SQLException {
		List<Publisher> publishers = new ArrayList<Publisher>();
		publishers = template.query("select * from tbl_publisher where publisherId = ?",
				new Object[] { publisherId },this);

		if (publishers != null && publishers.size() > 0) {
			return publishers.get(0);
		}
		return null;
	}
	
	public Publisher getPublisher(int publisherId) throws ClassNotFoundException,
	SQLException {
List<Publisher> publishers = new ArrayList<Publisher>();
publishers = template.query("select * from tbl_publisher where publisherId = ?",
		new Object[] { publisherId },this);

if (publishers != null && publishers.size() > 0) {
	return publishers.get(0);
}
return null;
}
	

	@Override
	public List<Publisher> extractData(ResultSet rs) {
		List<Publisher> publishers = new ArrayList<Publisher>();
		
		try {
			while (rs.next()) {
				Publisher p = new Publisher();
				p.setPublisherId(rs.getInt("publisherId"));
				p.setPublisherAddress(rs.getString("publisherAddress"));
				p.setPublisherName(rs.getString("publisherName"));
				p.setPublisherPhone(rs.getString("publisherPhone"));
				List<Book> books;
				
					books = (List<Book>)bdao.template.query(
							"select * from tbl_book where pubId =?",
							new Object[] { p.getPublisherId() },bdao);
					p.setBooks(books);
				
				
				publishers.add(p);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return publishers;
	}


//	@Override
//	public List extractDataFirstLevel(ResultSet rs) {
//		List<Publisher> publishers = new ArrayList<Publisher>();
//
//		try {
//			while (rs.next()) {
//				Publisher p = new Publisher();
//				p.setPublisherId(rs.getInt("publisherId"));
//				p.setPublisherAddress(rs.getString("publisherAddress"));
//				p.setPublisherName(rs.getString("publisherName"));
//				p.setPublisherPhone(rs.getString("publisherPhone"));
//				
//				publishers.add(p);
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//
//		return publishers;
//	}
//
	public int updatePublisher(Publisher publisher) throws ClassNotFoundException,
			SQLException {
int i=0;
		return template.update("update tbl_publisher set publisherName = ?,publisherAddress=?,publisherPhone=? where publisherId = ?",
				new Object[] { publisher.getPublisherName(), publisher.getPublisherAddress(), publisher.getPublisherPhone(), publisher.getPublisherId() });
	}

	public int deletePublisher(int publisherId) throws ClassNotFoundException,
			SQLException {
		if(checkPublisher(publisherId)==0)
		return template.update("delete from tbl_publisher where publisherId=?",
				new Object[] { publisherId });
		return 0;
	}
	
	

	private int checkPublisher(int publisherId) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		return template.queryForObject("select Count(*) from tbl_book where pubId=?",
				new Object[] { publisherId },Integer.class);
	}

	public List<Publisher> getAllPublishers(String searchString, int pageNo, int pageSize) throws ClassNotFoundException,
			SQLException {
		setPageNo(pageNo);
		setPageSize(pageSize);
		searchString = "%"+searchString+"%";
		
		return template.query("select * from tbl_publisher where publisherName like ? or publisherAddress like ? or publisherPhone like ?",
				new Object[] { searchString,searchString,searchString },this);
	}

	public int getCount(String searchString) throws ClassNotFoundException, SQLException {
			searchString = "%"+searchString+"%";
			return template.queryForObject("select count(*) as count from tbl_publisher where publisherName like ? or publisherAddress like ? or publisherPhone like ?", 
					new Object[] { searchString,searchString,searchString },Integer.class);
		
	}

	

	
}


