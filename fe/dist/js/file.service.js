var FileService = (function () {
  'use strict';

  // 파일 업로드 상태 indicator
  var $loading = $('.loading');

  function upload (type, file) {
    // 유저가 업로드한 파일을 이용해 FormData 생성
    var data = new FormData();
    data.append('file', file);

    var url = '/{type}/' + ((type === 'sessions') ? 'uploadFile' : 'uploadImg');

    return http.post({
      url: url,
      params: {type: type},
      data: data,
      isFileServer: true,
      dataType: false,
      cache: false,
      enctype: 'multipart/form-data',
      contentType: false,
      processData: false,
      beforeSend: showLoadingState
    }).always(hideLoadingState);
  }

  function showLoadingState () {
    $loading.show();
  }

  function hideLoadingState () {
    $loading.hide();
  }

  return {
    upload: upload
  };
})();
