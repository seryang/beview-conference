var FileService = (function () {
  'use strict';
  // 일반적으로 파일 서버는 웹 서버와 분리되어 있기 때문에
  // 기존 http 서비스의 URL과 다른 Path를 사용한다.

  function upload (type, file) {
    return http.post({
      url: '/{type}/uploadFile',
      params: {type: type},
      data: file,
      isFileServer: true,
      enctype: 'multipart/form-data',
      contentType: false,
      dataType: false,
      cache: false,
      processData: false
    });
  }

  return {
    upload: upload
  };
})();
