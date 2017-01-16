package com.navercorp.techshare.beview.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.navercorp.techshare.beview.exception.AuthorizationException;
import com.navercorp.techshare.beview.model.Track;
import com.navercorp.techshare.beview.model.response.AjaxResponse;
import com.navercorp.techshare.beview.repository.TrackDao;

/**
 * Created by seungdols on 2017. 1. 12..
 */
@Service
public class TrackService {

	private final Logger logger = LoggerFactory.getLogger(TrackService.class);

	@Autowired
	private TrackDao trackDao;

	public AjaxResponse createTrack(Track track) {
		Track dumy = trackDao.isExistTrackInConference(track.getConferenceIdx(), track.getName());
		if (dumy != null) {
			throw new AuthorizationException("기존에 저장 되어있습니다.");
		}
		trackDao.insertTrack(track);
		return new AjaxResponse();
	}

	public AjaxResponse selectTrackAllList() {
		return new AjaxResponse(trackDao.selectTrackAllList());
	}

	public AjaxResponse selectTrack(String id) {
		return new AjaxResponse(trackDao.selectTrack(id));
	}

	public AjaxResponse updateTrack(String id, Track track) {
		Track dumy = trackDao.isExistTrackInConference(track.getConferenceIdx(), track.getName());
		if (dumy != null) {
			throw new AuthorizationException("기존에 저장 되어있습니다.");
		}

		trackDao.updateTrack(id, track);
		return new AjaxResponse();
	}

	public AjaxResponse deleteTrack(String id) {
		trackDao.deleteTrack(id);
		return new AjaxResponse();
	}
}
