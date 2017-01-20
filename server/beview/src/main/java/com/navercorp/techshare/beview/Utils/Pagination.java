package com.navercorp.techshare.beview.utils;

/**
 * Created by seungdols on 2017. 1. 18..
 */
public class Pagination {
	static private final Integer PAGE_SIZE = 10;

	static public Integer getStart(Integer page) {
		return (page-1) * PAGE_SIZE;
	}

	static public Integer getEnd() {
		return PAGE_SIZE;
	}
}
