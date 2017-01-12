package com.navercorp.techshare.beview.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.navercorp.techshare.beview.model.Session;
import com.navercorp.techshare.beview.model.response.AjaxResponse;
import com.navercorp.techshare.beview.repository.SessionDao;

/**
 * Created by Naver on 2017. 1. 12..
 */
@Service
@Transactional
public class SessionService {

	@Autowired
	private SessionDao sessionDao;

	@Autowired
	private Environment environment;

	public AjaxResponse createSession(Session session) {
		sessionDao.insertSession(session);
		return new AjaxResponse();
	}

	public AjaxResponse uploadFile(MultipartFile uploadFile) {
		// 파일
		String filename = uploadFile.getOriginalFilename();

		// 파일 상대경로
		String filePath = environment.getRequiredProperty("upload.file.path") + File.separator;
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
			e.printStackTrace();
		}

		return new AjaxResponse(environment.getRequiredProperty("file.url") + realFileNm);
	}
}
