package com.sea.service;

import com.sea.model.Page;

public interface PageService {

	    public Page getPager(String currentPage,String pagerMethod,int totalRows) ;

}
