package com.navercorp.techshare.beview.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
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
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import com.navercorp.techshare.beview.annotation.Auth;
import com.navercorp.techshare.beview.exception.Error;
import com.navercorp.techshare.beview.exception.InvalidException;
import com.navercorp.techshare.beview.model.Session;
import com.navercorp.techshare.beview.model.response.AjaxResponse;
import com.navercorp.techshare.beview.service.SessionService;
import com.navercorp.techshare.beview.service.UploadService;

/**
 * Created by Naver on 2017. 1. 12..
 */
@RestController
@RequestMapping("/api/sessions")
@Api(value = "Session CRUD", description = "세션 정보 API")
public class SessionController {

	@Autowired
	private SessionService sessionService;

	@Autowired
	private UploadService uploadService;

	@Value("${upload.file.path}")
	private String uploadUrl;

	@Value("${file.url}")
	private String returnUrl;

	/**
	 *  [세션 전체 리스트 조회]
	 *
	 * @return AjaxResponse
	 */
	@Auth
	@GetMapping
	@ApiOperation("세션 전체 리스트 조회")
	public AjaxResponse selectSessionList(@RequestParam(value = "page", required = false) Integer page) {
		return sessionService.selectSessionList(page);
	}

	/**
	 *  [세션 조회]
	 *
	 * @return AjaxResponse
	 */
	@Auth
	@GetMapping("/{idx}")
	@ApiOperation("세션 단건 조회")
	public AjaxResponse selectSession(@PathVariable Integer idx,
		@CookieValue(value = "id", required = false) String id) {
		return sessionService.selectSession(idx, id);
	}

	/**
	 * [세션 등록]
	 * @param session
	 * @param bindingResult
	 * @return AjaxResponse
	 */
	@Auth
	@PostMapping
	@ApiOperation("세션 정보 등록")
	public AjaxResponse createSession(@RequestBody @Valid Session session, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			throw new InvalidException(Error.INVALID);
		}
		return sessionService.createSession(session);
	}

	/**
	 * [세션 업데이트]
	 *
	 * @param idx
	 * @param session
	 * @return AjaxResponse
	 */
	@Auth
	@PutMapping("/{idx}")
	@ApiOperation("세션 단건 조회")
	public AjaxResponse updateSession(@PathVariable Integer idx, @RequestBody @Valid Session session,
		BindingResult bindingResult) {
				if (bindingResult.hasErrors()) {
			throw new InvalidException(Error.INVALID);
		}
		return sessionService.updateSession(idx, session);
	}

	/**
	 *  [세션 삭제]
	 *
	 * @param idx
	 * @return AjaxResponse
	 */
	@Auth
	@DeleteMapping("/{idx}")
	@ApiOperation("세션 정보 삭제")
	public AjaxResponse deleteSession(@PathVariable Integer idx) {
		return sessionService.deleteSession(idx);
	}

	/**
	 * [발표자료 업로드]
	 *
	 * @param file
	 * @return AjaxResponse
	 */
	@Auth
	@PostMapping("/uploadFile")
	@ApiOperation("세션 파일 업로드")
	public AjaxResponse fileUpload(MultipartFile file) {
		return uploadService.uploadFile(file, uploadUrl, returnUrl);
	}
}
