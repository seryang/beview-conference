package com.navercorp.techshare.beview.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.navercorp.techshare.beview.exception.Error;
import com.navercorp.techshare.beview.exception.InvalidException;
import com.navercorp.techshare.beview.model.response.AjaxResponse;

/**
 * Created by Naver on 2017. 1. 13..
 */
@Service
public class UploadService {

	public AjaxResponse uploadFile(MultipartFile uploadFile, String uploadUrl, String returnUrl) {
		// 파일
		String filename = uploadFile.getOriginalFilename();

		// 파일 상대경로
		String filePath = uploadUrl + File.separator;
		File file = new File(filePath);

		// 디렉토리 존재하지 않을경우 디렉토리 생성
		if (!file.exists()) {
			file.mkdirs();
		}

		// 파일명 중복 방지 ( 임의값 생성 )
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		String today = formatter.format(new Date());

		String realFileNm = today + UUID.randomUUID().toString() + filename.substring(filename.lastIndexOf("."));
		String rlFileNm = filePath + realFileNm;

		// 서버에 파일쓰기  ( Spring - FileCopyUtils )
		MultipartFile upload = uploadFile;

		try {
			FileCopyUtils.copy(upload.getInputStream(), new FileOutputStream(rlFileNm));
		} catch (IOException e) {
			throw new InvalidException(Error.UPLOAD_FAIL);
		}

		return new AjaxResponse(returnUrl + File.separator + realFileNm);
	}
}
