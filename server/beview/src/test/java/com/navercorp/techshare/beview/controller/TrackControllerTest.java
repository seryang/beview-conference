package com.navercorp.techshare.beview.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.navercorp.techshare.beview.model.Track;
import com.navercorp.techshare.beview.model.response.AjaxResponse;
import com.navercorp.techshare.beview.service.TrackService;

/**
 * Created by seungdols on 2017. 1. 17..
 */
@RunWith(SpringRunner.class)
@WebMvcTest(TrackController.class)
public class TrackControllerTest {
	static List<Track> trackList;
	static Track dumyTrack;
	static final String IDX = "10";
	@Autowired
	MockMvc mvc;

	@MockBean
	private TrackService trackService;

	@Before
	public void setUp() {
		trackList = new ArrayList<>();
		Track track = new Track();
		track.setIdx(0);
		track.setConferenceIdx(0);
		track.setLocation("test");
		track.setName("testName");
		trackList.add(track);

		dumyTrack = new Track(10, "track1", "301", 7);
	}


	@Test
	public void selectTrackList() throws Exception {
		when(trackService.selectTrackAllList()).thenReturn(new AjaxResponse(trackList));

		mvc.perform(
			MockMvcRequestBuilders.get("/api/tracks")
				.contentType(MediaType.APPLICATION_JSON)
		)
			.andExpect(status().isOk())
			.andExpect(jsonPath("$..idx").exists())
			.andExpect(jsonPath("$..name").value("testName"))
			.andExpect(jsonPath("$..location").value("test"))
			.andExpect(jsonPath("$..conferenceIdx").value(0));
	}

	@Test
	public void selectTrack() throws Exception {
		when(trackService.selectTrack(IDX)).thenReturn(new AjaxResponse(dumyTrack));

		mvc.perform(
			MockMvcRequestBuilders.get("/api/tracks/" + IDX)
				.contentType(MediaType.APPLICATION_JSON)
		)
			.andExpect(status().isOk())
			.andExpect(jsonPath("$..idx").exists())
			.andExpect(jsonPath("$..name").value("track1"))
			.andExpect(jsonPath("$..location").value("301"))
			.andExpect(jsonPath("$..conferenceIdx").value(7));
	}

}