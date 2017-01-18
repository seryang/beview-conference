package com.navercorp.techshare.beview.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.navercorp.techshare.beview.exception.Error;
import com.navercorp.techshare.beview.exception.InvalidException;
import com.navercorp.techshare.beview.model.Speaker;
import com.navercorp.techshare.beview.model.response.AjaxResponse;
import com.navercorp.techshare.beview.repository.SpeakerDao;

/**
 * Created by seungdols on 2017. 1. 17..
 */
@Service
public class SpeakerService {

	@Autowired
	private SpeakerDao speakerDao;

	public AjaxResponse selectSpeaker(String id) {
		return new AjaxResponse(speakerDao.selectSpeaker(id));
	}

	public AjaxResponse selectAllSpeaker(Integer page) {
		return new AjaxResponse(speakerDao.selectAllSpeaker(page));
	}

	public AjaxResponse createSpeaker(Speaker speaker) {
		if (speakerDao.isExistSpeaker(speaker) != null) {
			throw new InvalidException(Error.DUPLICATE);
		}
		speakerDao.createSpeaker(speaker);
		return new AjaxResponse();
	}

	public AjaxResponse updateSpeaker(String id, Speaker speaker) {
		if (speakerDao.isExistSpeaker(speaker) != null) {
			throw new InvalidException(Error.DUPLICATE);
		}
		speakerDao.updateSpeaker(id, speaker);
		return new AjaxResponse();
	}

	public AjaxResponse deleteSpeaker(String id) {
		speakerDao.deleteSpeaker(id);
		return new AjaxResponse();
	}
}
