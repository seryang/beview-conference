package com.navercorp.techshare.beview.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.navercorp.techshare.beview.exception.Error;
import com.navercorp.techshare.beview.exception.InvalidException;
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
		if (conferenceDao.isConferenceById(conference.getId()) != null) {
			throw new InvalidException(Error.EXIST_ID);
		}
		conferenceDao.insertConference(conference);
		return new AjaxResponse();
	}

	public AjaxResponse selectConferenceList(Integer page) {
		return new AjaxResponse(conferenceDao.selectAllConference(page));
	}

	public AjaxResponse updateConference(Integer idx, Conference updateData) {

		if (conferenceDao.isConferenceById(updateData.getId(), idx) != null) {
			throw new InvalidException(Error.EXIST_ID);
		}

		Conference beforeData = conferenceDao.isConferenceByIdx(idx);
		conferenceDao.updateConference(beforeData.convertData(updateData));
		return new AjaxResponse();
	}

	public AjaxResponse deleteConference(Integer idx) {
		conferenceDao.deleteConference(idx);
		return new AjaxResponse();
	}
}