var FileService = (function () {
  'use strict';

  // 파일 업로드 상태 indicator
  var $uploading = $('.uploading');

  function upload (type, file) {
    // 유저가 업로드한 파일을 이용해 FormData 생성
    var data = new FormData();
    data.append('file', file);

    return http.post({
      url: '/{type}/uploadFile',
      params: {type: type},
      data: data,
      isFileServer: true,
      dataType: false,
      cache: false,
      enctype: 'multipart/form-data',
      contentType: false,
      processData: false,
      beforeSend: showUploadingState
    }).always(hideUploadingState);
  }

  function showUploadingState () {
    $uploading.show();
  }

  function hideUploadingState () {
    $uploading.hide();
  }

  return {
    upload: upload
  };
})();
