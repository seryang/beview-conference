package com.navercorp.techshare.beview.Utils;

/**
 * Created by seungdols on 2017. 1. 18..
 */
public class Pagination {
	static private final Integer PAGESIZE = 10;

	static public Integer getStart(Integer page) {
		return (page-1) * PAGESIZE;
	}

	static public Integer getEnd() {
		return PAGESIZE;
	}
}
