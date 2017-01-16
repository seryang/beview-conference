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

import com.navercorp.techshare.beview.annotation.Auth;
import com.navercorp.techshare.beview.exception.Error;
import com.navercorp.techshare.beview.exception.InvalidException;
import com.navercorp.techshare.beview.model.Conference;
import com.navercorp.techshare.beview.model.response.AjaxResponse;
import com.navercorp.techshare.beview.service.ConferenceService;

/**
 * Created by Naver on 2017. 1. 12..
 */
@RestController
@RequestMapping("/api/conference")
public class ConferenceController {

	@Autowired
	private ConferenceService conferenceService;

	//? 컨퍼런스 조회 - 해당 컨퍼런스 전체 스케줄 조회
//	@GetMapping("/{id}")
//	public AjaxResponse conferenceInfo(@PathVariable String id){
//
//	}

	/**
	 *  [컨퍼런스 전체 리스트 조회]
	 *
	 * @return AjaxResponse
	 */
	@Auth
	@GetMapping
	public AjaxResponse selectConferenceList() {
		return conferenceService.selectConferenceList();
	}

	/**
	 * [컨퍼런스 등록]
	 *
	 * @param conference
	 * @return AjaxResponse
	 */
	@Auth
	@PostMapping
	public AjaxResponse createConference(@RequestBody @Valid Conference conference, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			throw new InvalidException(Error.INVALID);
		}
		return conferenceService.createConference(conference);
	}

	/**
	 *  [컨퍼런스 정보 수정]
	 *
	 * @param id
	 * @param conference
	 * @return AjaxResponse
	 */
	@Auth
	@PutMapping("/{id}")
	public AjaxResponse updateConference(@PathVariable String id, @RequestBody @Valid Conference conference, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			throw new InvalidException(Error.INVALID);
		}
		return conferenceService.updateConference(id, conference);

	}

	/**
	 *  [컨퍼런스 삭제]
	 *
	 * @param id
	 * @return AjaxResponse
	 */
	@Auth
	@DeleteMapping("/{id}")
	public AjaxResponse deleteConference(@PathVariable String id) {
		return conferenceService.deleteConference(id);
	}
}