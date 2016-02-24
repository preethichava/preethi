package com.gcit.lms.service;


import java.sql.SQLException;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.gcit.lms.dao.BCopiesDAO;
import com.gcit.lms.dao.BookDAO;
import com.gcit.lms.dao.BorrowerDAO;
import com.gcit.lms.dao.BranchDAO;
import com.gcit.lms.dao.LoansDAO;
import com.gcit.lms.domain.BCopies;
import com.gcit.lms.domain.Branch;

public class LibrarianService {
	
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

	@Transactional
	public List<BCopies> getAvailableBooks(int branchId) throws ClassNotFoundException, SQLException
	{
		return bcdao.getBorrowableBooks(branchId);
	}

	@Transactional
	public int updateCopies(BCopies bcopies) throws Exception {
		int i=0;
		i = bcdao.updateBCopies(bcopies);
		return i;
	}
	
	@Transactional
	public List<BCopies> getCopies(int branchId) throws ClassNotFoundException, SQLException
	{
		return bcdao.getBranchCopies(branchId);
	}
	
	@Transactional
	public Branch getBranch(int branchId) throws Exception
	{	
		return bhdao.getBranch(branchId);
	}
}
