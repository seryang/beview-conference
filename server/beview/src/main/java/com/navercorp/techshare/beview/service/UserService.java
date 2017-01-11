package com.navercorp.techshare.beview.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.navercorp.techshare.beview.controller.UserVo;
import com.navercorp.techshare.beview.repository.UserDao;

/**
 * Created by Naver on 2017. 1. 10..
 */
@Service
public class UserService {


	@Autowired
	private UserDao userDao;

	@Transactional
	public Integer insertUser(UserVo uvo) {
		return userDao.insertUser(uvo);
	}
}
