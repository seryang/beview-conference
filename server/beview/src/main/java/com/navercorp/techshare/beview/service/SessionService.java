package com.navercorp.techshare.beview.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.navercorp.techshare.beview.exception.Error;
import com.navercorp.techshare.beview.exception.InvalidException;
import com.navercorp.techshare.beview.model.Session;
import com.navercorp.techshare.beview.model.response.AjaxResponse;
import com.navercorp.techshare.beview.repository.SessionDao;

/**
 * Created by Naver on 2017. 1. 12..
 */
@Service
@Transactional
public class SessionService {

	@Autowired
	private SessionDao sessionDao;

	public AjaxResponse createSession(Session session) {
		if (sessionDao.isSession(session.getName(), session.getTrackIdx()) != null) {
			throw new InvalidException(Error.EXIST_NAME);
		}
		sessionDao.insertSession(session);

		//? speaker 테이블에 정보도 수정 (발표자 idx로 검색해서, 해당 session_idx에 업데이트
		return new AjaxResponse();
	}

	public AjaxResponse updateSession(Integer idx, Session updateData) {
		if (sessionDao.isSession(updateData.getName(), updateData.getTrackIdx()) != null) {
			throw new InvalidException(Error.EXIST_NAME);
		}

		Session beforeData = sessionDao.selectSession(idx);
		sessionDao.updateSession(beforeData.convertData(updateData));
		//? speaker 테이블에 정보도 수정 (발표자 idx로 검색해서, 해당 session_idx에 업데이트
		return new AjaxResponse();
	}

	public AjaxResponse selectSessionList() {
		return new AjaxResponse(sessionDao.selectAllSession());
	}

	public AjaxResponse deleteSession(Integer idx) {
		sessionDao.deleteSession(idx);
		//? speaker 테이블에 정보도 수정 (발표자 idx로 검색해서, 해당 session_idx에 업데이트
		return new AjaxResponse();
	}

	public AjaxResponse selectSession(Integer idx) {
		return new AjaxResponse(sessionDao.selectSession(idx));
	}
}