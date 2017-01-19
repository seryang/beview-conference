package com.navercorp.techshare.beview.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.navercorp.techshare.beview.model.Conference;
import com.navercorp.techshare.beview.model.Session;
import com.navercorp.techshare.beview.model.Speaker;
import com.navercorp.techshare.beview.model.Track;
import com.navercorp.techshare.beview.model.response.AjaxResponse;
import com.navercorp.techshare.beview.repository.ConferenceDao;
import com.navercorp.techshare.beview.repository.FavoriteDao;
import com.navercorp.techshare.beview.repository.SessionDao;
import com.navercorp.techshare.beview.repository.SpeakerDao;
import com.navercorp.techshare.beview.repository.TrackDao;

/**
 * Created by Naver on 2017. 1. 18..
 */
@Service
@Transactional
public class ScheduleService {

	@Autowired
	private ConferenceDao conferenceDao;

	@Autowired
	private TrackDao trackDao;

	@Autowired
	private SessionDao sessionDao;

	@Autowired
	private SpeakerDao speakerDao;

	@Autowired
	private FavoriteDao favoriteDao;

	public AjaxResponse getSchedule(Integer idx, String userId) {

		// 컨퍼런스 정보
		Conference conference = conferenceDao.isConferenceByIdx(idx);

		// 컨퍼런스 - 트랙 리스트
		List<Track> trackList = trackDao.selectTrackAllListByConferenceId(conference.getIdx());
		conference.setTrackList(trackList);

		// 컨터런스 - 트랙 리스트별 - 세션 리스트
		for (int i = 0; i < trackList.size(); i++) {
			Integer trackIdx = conference.getTrackList().get(i).getIdx();
			List<Session> sessionList = sessionDao.selectSessionAllListByTrackId(trackIdx);
			conference.getTrackList().get(i).setSessionList(sessionList);
			conference.getTrackList().get(i).setConferenceName(conference.getName());

			// 컨퍼런스 - 트랙 리스트별 - 세션 리스트별 - 발표자
			for (int j = 0; j < sessionList.size(); j++) {
				Integer speakerIdx = sessionList.get(j).getSpeakerIdx();
				Speaker speaker = speakerDao.selectSpeaker(String.valueOf(speakerIdx));
				speaker.setSessionIdx(conference.getTrackList().get(i).getSessionList().get(j).getIdx());
				speaker.setSessionName(conference.getTrackList().get(i).getSessionList().get(j).getName());
				conference.getTrackList().get(i).getSessionList().get(j).setSpeaker(speaker);
				conference.getTrackList().get(i).getSessionList().get(j).setSpeakerName(speaker.getName());
				conference.getTrackList().get(i).getSessionList().get(j).setTrackName(
					conference.getTrackList().get(i).getName());
				conference.getTrackList().get(i).getSessionList().get(j).setSpeaker(speaker);

				// 현재 사용자가 찜한 세션인지 체크
				Integer sessionIdx = sessionList.get(j).getIdx();
				Boolean isFavorite = favoriteDao.selectFavoriteById(userId, sessionIdx) != null;
				conference.getTrackList().get(i).getSessionList().get(j).setFavorite(isFavorite);
			}
		}
		return new AjaxResponse(conference);
	}
}
