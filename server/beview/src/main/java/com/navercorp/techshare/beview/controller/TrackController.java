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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import com.navercorp.techshare.beview.annotation.Auth;
import com.navercorp.techshare.beview.exception.AuthorizationException;
import com.navercorp.techshare.beview.exception.Error;
import com.navercorp.techshare.beview.model.Track;
import com.navercorp.techshare.beview.model.response.AjaxResponse;
import com.navercorp.techshare.beview.service.TrackService;

/**
 * Created by seungdols on 2017. 1. 12..
 */

@RestController
@RequestMapping("/api/tracks")
@Api(value = "Track CURD API", description = "트랙 정보 API")
public class TrackController {

	@Autowired
	private TrackService trackService;

	@Auth
	@PostMapping
	@ApiOperation("발표자 정보 생성")
	public AjaxResponse createTrack(@RequestBody @Valid Track track, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			throw new AuthorizationException(Error.INVALID);
		}
		return trackService.createTrack(track);
	}

	@Auth
	@GetMapping
	@ApiOperation("발표자 정보 전체 리스트 조회")
	public AjaxResponse selectTrackList(@RequestParam(value = "page", required = false) Integer page) {
		return trackService.selectTrackAllList(page);
	}

	@Auth
	@GetMapping("/{id}")
	@ApiOperation("발표자 정보 단건 조회")
	public AjaxResponse selectTrack(@PathVariable String id) {
		return trackService.selectTrack(id);
	}

	@Auth
	@PutMapping("/{id}")
	@ApiOperation("발표자 정보 수정")
	public AjaxResponse updateTrack(@PathVariable String id, @RequestBody @Valid Track track) {
		return trackService.updateTrack(id, track);
	}

	@Auth
	@DeleteMapping("/{id}")
	@ApiOperation("발표자 정보 삭제")
	public AjaxResponse deleteTrack(@PathVariable String id) {
		return trackService.deleteTrack(id);
	}

}
