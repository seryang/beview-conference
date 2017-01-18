package com.navercorp.techshare.beview.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.navercorp.techshare.beview.exception.Error;
import com.navercorp.techshare.beview.exception.InvalidException;
import com.navercorp.techshare.beview.model.Session;
import com.navercorp.techshare.beview.model.Speaker;
import com.navercorp.techshare.beview.model.response.AjaxResponse;
import com.navercorp.techshare.beview.repository.SessionDao;
import com.navercorp.techshare.beview.repository.SpeakerDao;

/**
 * Created by Naver on 2017. 1. 12..
 */
@Service
@Transactional
public class SessionService {

	private final Logger logger = LoggerFactory.getLogger(SessionService.class);

	@Autowired
	private SessionDao sessionDao;

	@Autowired
	private SpeakerDao speakerDao;

	public AjaxResponse createSession(Session session) {
		if (sessionDao.isSession(session.getName(), session.getTrackIdx()) != null) {
			throw new InvalidException(Error.EXIST_NAME);
		}
		int sessionIdx = sessionDao.insertSession(session);

		// speaker 테이블에 정보도 수정 (발표자 idx로 검색해서, 해당 session_idx에 업데이트)
		Speaker speaker = speakerDao.selectSpeaker(String.valueOf(session.getSpeakerIdx()));
		speaker.setSessionIdx(sessionIdx);
		speakerDao.updateSpeaker(String.valueOf(speaker.getIdx()), speaker);
		return new AjaxResponse();
	}

	public AjaxResponse updateSession(Integer idx, Session updateData) {
		if (sessionDao.isSession(updateData.getName(), updateData.getTrackIdx(), idx) != null) {
			throw new InvalidException(Error.EXIST_NAME);
		}

		Session beforeData = sessionDao.selectSession(idx);

		// speaker 테이블에 정보도 수정 (발표자 idx로 검색해서, 해당 session_idx에 업데이트
		// 같으면 수정할 필요 없음
		if (! (beforeData.getSpeakerIdx().equals(updateData.getSpeakerIdx()))) {
			Speaker speaker = speakerDao.selectSpeaker(String.valueOf(beforeData.getSpeakerIdx()));
			speaker.setSessionIdx(null);
			speakerDao.updateSpeaker(String.valueOf(speaker.getIdx()), speaker);

			speaker = speakerDao.selectSpeaker(String.valueOf(updateData.getSpeakerIdx()));
			speaker.setSessionIdx(idx);
			speakerDao.updateSpeaker(String.valueOf(speaker.getIdx()), speaker);
		}

		sessionDao.updateSession(beforeData.convertData(updateData));
		return new AjaxResponse();
	}

	public AjaxResponse selectSessionList(Integer page) {
		return new AjaxResponse(sessionDao.selectAllSession(page));
	}

	public AjaxResponse deleteSession(Integer idx) {
		int speakerIdx = sessionDao.selectSession(idx).getSpeakerIdx();
		// speaker 테이블에 정보도 수정 (발표자 idx로 검색해서, 해당 session_idx에 업데이트)
		Speaker speaker = speakerDao.selectSpeaker(String.valueOf(speakerIdx));
		speaker.setSessionIdx(null);
		speakerDao.updateSpeaker(String.valueOf(speaker.getIdx()), speaker);
		sessionDao.deleteSession(idx);
		return new AjaxResponse();
	}

	public AjaxResponse selectSession(Integer idx) {
		return new AjaxResponse(sessionDao.selectSession(idx));
	}
}