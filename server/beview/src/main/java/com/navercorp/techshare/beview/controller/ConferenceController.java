package com.navercorp.techshare.beview.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CookieValue;
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
import com.navercorp.techshare.beview.exception.Error;
import com.navercorp.techshare.beview.exception.InvalidException;
import com.navercorp.techshare.beview.model.Conference;
import com.navercorp.techshare.beview.model.response.AjaxResponse;
import com.navercorp.techshare.beview.service.ConferenceService;
import com.navercorp.techshare.beview.service.ScheduleService;

/**
 * Created by Naver on 2017. 1. 12..
 */
@RestController
@RequestMapping("/api/conferences")
@Api(value = "Conference CURD", description = "컨퍼런스 정보 API")
public class ConferenceController {

	@Autowired
	private ConferenceService conferenceService;

	@Autowired
	private ScheduleService scheduleService;

	/**
	 * [컨퍼런스 조회 - 해당 컨퍼런스 전체 스케줄 조회]
	 *
	 * @param idx
	 * @param userId
	 * @return AjaxResponse
	 */
	@GetMapping("/{idx}")
	@ApiOperation("컨퍼런스 전체 스케쥴 조회")
	public AjaxResponse conferenceInfo(@PathVariable Integer idx,
		@CookieValue(value = "id", required = false) String userId) {
		return scheduleService.getSchedule(idx, userId);
	}

	/**
	 *  [컨퍼런스 전체 리스트 조회]
	 *
	 * @return AjaxResponse
	 */
	@Auth
	@GetMapping
	@ApiOperation("컨퍼런스 전체 리스트 조회 ")
	public AjaxResponse selectConferenceList(@RequestParam(value = "page", required = false) Integer page) {
		return conferenceService.selectConferenceList(page);
	}

	/**
	 * [컨퍼런스 등록]
	 *
	 * @param conference
	 * @return AjaxResponse
	 */
	@Auth
	@PostMapping
	@ApiOperation("컨퍼런스 등록")
	public AjaxResponse createConference(@RequestBody @Valid Conference conference, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			throw new InvalidException(Error.INVALID);
		}
		return conferenceService.createConference(conference);
	}

	/**
	 *  [컨퍼런스 정보 수정]
	 *
	 * @param idx
	 * @param conference
	 * @return AjaxResponse
	 */
	@Auth
	@PutMapping("/{idx}")
	@ApiOperation("컨퍼런스 정보 수정")
	public AjaxResponse updateConference(@PathVariable Integer idx, @RequestBody @Valid Conference conference,
		BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			throw new InvalidException(Error.INVALID);
		}
		return conferenceService.updateConference(idx, conference);

	}

	/**
	 *  [컨퍼런스 삭제]
	 *
	 * @param idx
	 * @return AjaxResponse
	 */
	@Auth
	@DeleteMapping("/{idx}")
	@ApiOperation("컨퍼런스 정보 삭제")
	public AjaxResponse deleteConference(@PathVariable Integer idx) {
		return conferenceService.deleteConference(idx);
	}
}