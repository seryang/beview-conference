package com.navercorp.techshare.beview.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.navercorp.techshare.beview.exception.AuthorizationException;
import com.navercorp.techshare.beview.exception.Error;
import com.navercorp.techshare.beview.model.Speaker;
import com.navercorp.techshare.beview.model.response.AjaxResponse;
import com.navercorp.techshare.beview.service.SpeakerService;
import com.navercorp.techshare.beview.service.UploadService;

/**
 * Created by seungdols on 2017. 1. 17..
 */
@RestController
@RequestMapping("/api/speakers")
public class SpeakerController {

	@Autowired
	private SpeakerService speakerService;

	@Autowired
	private UploadService uploadService;

	@Value("${upload.img.path}")
	private String uploadUrl;

	@Value("${img.url}")
	private String returnUrl;

	@GetMapping("/{id}")
	public AjaxResponse selectSpeaker(@PathVariable String id) {
		return speakerService.selectSpeaker(id);
	}

	@GetMapping
	public AjaxResponse selectAllSpeaker() {
		return speakerService.selectAllSpeaker();
	}

	@PostMapping
	public AjaxResponse createSpeaker(@RequestBody @Valid Speaker speaker, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			throw new AuthorizationException(Error.INVALID);
		}

		return speakerService.createSpeaker(speaker);
	}

	@PutMapping("/{id}")
	public AjaxResponse updateSpeaker(@PathVariable String id, @RequestBody @Valid Speaker speaker) {
		return speakerService.updateSpeaker(id, speaker);
	}

	@DeleteMapping("/{id}")
	public AjaxResponse deleteSpeaker(@PathVariable String id) {
		return speakerService.deleteSpeaker(id);
	}

	@PostMapping("/uploadImg")
	public AjaxResponse imgUpload(MultipartFile file) {
		return uploadService.uploadFile(file, uploadUrl, returnUrl);
	}
}
