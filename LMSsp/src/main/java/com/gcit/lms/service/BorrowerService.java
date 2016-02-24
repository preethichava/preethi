package com.gcit.lms.service;


import java.sql.SQLException;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.gcit.lms.dao.BCopiesDAO;
import com.gcit.lms.dao.BorrowerDAO;
import com.gcit.lms.dao.LoansDAO;
import com.gcit.lms.domain.BCopies;
import com.gcit.lms.domain.Borrower;
import com.gcit.lms.domain.Loans;

public class BorrowerService {

	@Autowired
	BasicDataSource ds;
	
	@Autowired
	BorrowerDAO udao;
	
	@Autowired
	BCopiesDAO bcdao;

	@Autowired
	LoansDAO ldao;

	@Transactional
	public Borrower verifyCard(int cardNo) throws ClassNotFoundException, SQLException  {
		return udao.getBorrower(cardNo);
	}

	@Transactional
	public int returnBook(Loans loan) throws Exception{
		if(ldao.returnBook(loan)>0)
		{
			BCopies bc =new BCopies();
			bc.setBook(loan.getBook());
			bc.setBranch(loan.getBranch());
			bc.setAddCopies(1);
			
			if( bcdao.updateBCopies(bc)>0){
				return 1;
			}
		}
		return 0;
	}

	@Transactional
	public int checkOut(Loans loan) throws Exception {
		if(ldao.loanBook(loan)>0)
		{
			BCopies bc = new BCopies();
			bc.setBook(loan.getBook());
			bc.setBranch(loan.getBranch());
			bc.setAddCopies(-1);
			if( bcdao.updateBCopies(bc)>0){
				return 1;
			}
		}
		return 0;
	}
}
