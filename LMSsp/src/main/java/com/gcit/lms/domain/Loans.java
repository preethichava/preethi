/**
 * 
 */
package com.gcit.lms.domain;

import java.util.Date;

/**
 * @author preethi
 *
 */
public class Loans {
	private Book book;
	private Branch branch;
	private Borrower borrower;
	private Date dateOut;
	private Date dueDate;
	private Date dateIn;
	
	/**
	 * @return the dateIn
	 */
	public Date getDateIn() {
		return dateIn;
	}
	/**
	 * @param dateIn the dateIn to set
	 */
	public void setDateIn(Date dateIn) {
		this.dateIn = dateIn;
	}
	/**
	 * @return the book
	 */
	public Book getBook() {
		return book;
	}
	/**
	 * @param book the book to set
	 */
	public void setBook(Book book) {
		this.book = book;
	}
	/**
	 * @return the branch
	 */
	public Branch getBranch() {
		return branch;
	}
	/**
	 * @param branch the branch to set
	 */
	public void setBranch(Branch branch) {
		this.branch = branch;
	}
	/**
	 * @return the borrower
	 */
	public Borrower getBorrower() {
		return borrower;
	}
	/**
	 * @param borrower the borrower to set
	 */
	public void setBorrower(Borrower borrower) {
		this.borrower = borrower;
	}
	/**
	 * @return the dateOut
	 */
	public Date getDateOut() {
		return dateOut;
	}
	/**
	 * @param dateOut the dateOut to set
	 */
	public void setDateOut(Date dateOut) {
		this.dateOut = dateOut;
	}
	/**
	 * @return the dueDate
	 */
	public Date getDueDate() {
		return dueDate;
	}
	/**
	 * @param dueDate the dueDate to set
	 */
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
}
