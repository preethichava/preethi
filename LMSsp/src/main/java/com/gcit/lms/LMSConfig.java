package com.gcit.lms;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.gcit.lms.dao.AuthorDAO;
import com.gcit.lms.dao.BCopiesDAO;
import com.gcit.lms.dao.BookDAO;
import com.gcit.lms.dao.BorrowerDAO;
import com.gcit.lms.dao.BranchDAO;
import com.gcit.lms.dao.GenreDAO;
import com.gcit.lms.dao.LoansDAO;
import com.gcit.lms.dao.PublisherDAO;
import com.gcit.lms.service.AdministratorService;
import com.gcit.lms.service.BorrowerService;
import com.gcit.lms.service.LibrarianService;

@EnableWebMvc
@EnableTransactionManagement
@Configuration
public class LMSConfig extends WebMvcConfigurerAdapter {
	private static String driver = "com.mysql.jdbc.Driver";
	private static String dbURL = "jdbc:mysql://localhost:3306/library";
	private static String userName = "root";
	private static String pwd = "5209";
	
	@Bean
	public AdministratorService service(){
		return new AdministratorService();
	}
	
	@Bean
	public BorrowerService borrowservice(){
		return new BorrowerService();
	}
	
	@Bean
	public LibrarianService libservice(){
		return new LibrarianService();
	}
	
	@Bean
	public AuthorDAO authorDAO(){
		return new AuthorDAO();
	}
	
	@Bean
	public BranchDAO branchDAO(){
		return new BranchDAO();
	}
	@Bean
	public BorrowerDAO borrowerDAO(){
		return new BorrowerDAO();
	}
	
	@Bean
	public LoansDAO loanDAO(){
		return new LoansDAO();
	}
	
	@Bean
	public BCopiesDAO bcopiesDAO(){
		return new BCopiesDAO();
	}
	
	@Bean
	public BookDAO bookDAO(){
		return new BookDAO();
	}
	
	@Bean
	public PublisherDAO publisherDAO(){
		return new PublisherDAO();
	}
	
	@Bean
	public GenreDAO genreDAO(){
		return new GenreDAO();
	}
	
	@Bean
	public BasicDataSource dataSource()
	{
		BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName(driver);
		ds.setUrl(dbURL);
		ds.setUsername(userName);
		ds.setPassword(pwd);
		
		return ds;
		
	}
	
	@Bean
	public PlatformTransactionManager txManager(){
		DataSourceTransactionManager tx = new DataSourceTransactionManager();
		
		tx.setDataSource(dataSource());
		return tx;
	}
	
	@Bean
	public JdbcTemplate template(){
		JdbcTemplate template =new JdbcTemplate();
		template.setDataSource(dataSource());
		return template;
	}
	
	
}
