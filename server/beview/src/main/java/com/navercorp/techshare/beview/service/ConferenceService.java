package com.navercorp.techshare.beview.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.navercorp.techshare.beview.exception.AuthorizationException;
import com.navercorp.techshare.beview.model.Conference;
import com.navercorp.techshare.beview.model.response.AjaxResponse;
import com.navercorp.techshare.beview.repository.ConferenceDao;

/**
 * Created by Naver on 2017. 1. 12..
 */
@Service
@Transactional
public class ConferenceService {

	@Autowired
	private ConferenceDao conferenceDao;

	public AjaxResponse createConference(Conference conference) {
		if (conferenceDao.isConference(conference.getId()) != null) {
			throw new AuthorizationException("존재하는 ID의 컨퍼런스입니다.");
		}
		conferenceDao.insertConference(conference);
		return new AjaxResponse();
	}

	public AjaxResponse selectConferenceList() {
		return new AjaxResponse(conferenceDao.selectAllConference());
	}

	public AjaxResponse updateConference(String id, Conference updateData) {
		Conference beforeData = conferenceDao.isConference(id);
		conferenceDao.updateConference(beforeData.convertData(updateData));
		return new AjaxResponse();
	}

	public AjaxResponse deleteConference(String id) {
		conferenceDao.deleteConference(id);
		return new AjaxResponse();
	}
}