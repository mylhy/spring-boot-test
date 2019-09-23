package com.pro.entity;

public class PageInfo {
	// 当前页
	private int pageNum = 1;
	// 每页的显示
	private int pageSize = 10;
	// 总记录数
	private int totalResult = 0;
	// 总页数
	private int totalPage = 0;

	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getTotalResult() {
		return totalResult;
	}
	public void setTotalResult(int totalResult) {
		this.totalResult = totalResult;
	}
	public int getTotalPage() {
		
		if (totalResult == 0) {
			this.totalPage = 1;
		} else {
			this.totalPage = (totalResult / pageSize); // 总共几页
			if ((totalResult % pageSize) != 0)
				this.totalPage = this.totalPage + 1;
		}
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	
	
	

}
