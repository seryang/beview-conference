var AdminService = (function () {
  'use strict';

  // ajax 데이터 로딩 상태 indicator
  var $loading = $('.loading');

  function showLoadingState () {
    $loading.show();
  }

  function hideLoadingState () {
    $loading.hide();
  }

  function convertUrl(type, path) {
    return ['/', type, path].join('');
  }

  return {
    getListOf: function (type, params) {
      return http.get({
        url: convertUrl(type, '?page={page}'),
        params: params,
        beforeSend: showLoadingState
      }).always(hideLoadingState);
    },
    get: function (type, params) {
      return http.get({
        url: convertUrl(type, '/{id}'),
        params: params,
        beforeSend: showLoadingState
      }).always(hideLoadingState);
    },
    create: function (type, data) {
      return http.post({
        url: convertUrl(type),
        data: data,
        beforeSend: showLoadingState
      }).always(hideLoadingState);
    },
    delete: function (type, params) {
      return http.delete({
        url: convertUrl(type, '/{id}'),
        params: params,
        beforeSend: showLoadingState
      }).always(hideLoadingState);
    },
    update: function (type, params, data) {
      return http.put({
        url: convertUrl(type, '/{id}'),
        params: params,
        data: data
      }).always(hideLoadingState);
    }
  };
})();
