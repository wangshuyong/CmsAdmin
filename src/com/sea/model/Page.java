package com.sea.model;

import java.util.List;

public class Page {
	private int totalPages;  
	private int totalRows;
	private int pageSize=20;
	private int currentPage;
	 private int startRow;
    
    public Page(int _totalRows) {
        totalRows = _totalRows;
        totalPages=totalRows/pageSize;
        int mod=totalRows%pageSize;
        if(mod>0){
            totalPages++;
        }
        currentPage = 1;
        setStartRow(0);
    }
    
	public int getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
	public int getTotalRows() {
		return totalRows;
	}
	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public int setCurrentPage(int currentPage) {
		return this.currentPage = currentPage;
	}
	
	
	public boolean isFirstPage() {
		return currentPage==1;
	}
	
	public boolean isLastPage() {
		return currentPage==totalPages;
	}
	
	public boolean isHasPreviousPage() {
		return currentPage != 1; 
	}
	
	public boolean isHasNextPage() {
		return currentPage != totalPages;
	}
	
	public void first() {
	    currentPage = 1;
	    startRow = 0;
	}
	public void previous() {
	    if (currentPage == 1) {
	        return;
	    }
	    currentPage--;
	    startRow = (currentPage - 1) * pageSize;
	}
	public void next() {
	    if (currentPage < totalPages) {
	        currentPage++;
	    }
	    startRow = (currentPage - 1) * pageSize;
	}
	    public void last() {
	        currentPage = totalPages;
	        startRow = (currentPage - 1) * pageSize;
	    }
	    public void refresh(int _currentPage) {
	        currentPage = _currentPage;
	        if (currentPage > totalPages) {
	            last();
	        }
	    }

	public int getStartRow() {
		return startRow;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

}
