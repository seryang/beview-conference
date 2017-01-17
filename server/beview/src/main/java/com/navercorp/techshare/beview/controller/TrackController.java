package com.navercorp.techshare.beview.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.navercorp.techshare.beview.exception.AuthorizationException;
import com.navercorp.techshare.beview.exception.Error;
import com.navercorp.techshare.beview.model.Track;
import com.navercorp.techshare.beview.model.response.AjaxResponse;
import com.navercorp.techshare.beview.service.TrackService;

/**
 * Created by seungdols on 2017. 1. 12..
 */

@RestController
@RequestMapping("/api/track")
public class TrackController {

	@Autowired
	private TrackService trackService;

	@PostMapping
	public AjaxResponse createTrack(@RequestBody @Valid Track track, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			throw new AuthorizationException(Error.INVALID);
		}
		return trackService.createTrack(track);
	}

	@GetMapping
	public AjaxResponse selectTrackList() {
		return trackService.selectTrackAllList();
	}

	@GetMapping("/{id}")
	public AjaxResponse selectTrack(@PathVariable String id) {
		return trackService.selectTrack(id);
	}

	@PutMapping("/{id}")
	public AjaxResponse updateTrack(@PathVariable String id, @RequestBody @Valid Track track) {
		return trackService.updateTrack(id, track);
	}

	@DeleteMapping("/{id}")
	public AjaxResponse deleteTrack(@PathVariable String id) {
		return trackService.deleteTrack(id);
	}

}
