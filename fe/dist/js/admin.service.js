var AdminService = (function () {
  'use strict';

  function convertUrl(type, path) {
    return ['/', type, path].join('');
  }

  return {
    get: function (type, params) {
      return http.get({
        url: convertUrl(type, '/{id}'),
        params: params
      });
    },
    create: function (type, data) {
      return http.post({
        url: convertUrl(type),
        data: data
      });
    },
    delete: function (type, params) {
      return http.delete({
        url: convertUrl(type, '/{id}'),
        params: params
      });
    },
    update: function (type, params, data) {
      return http.put({
        url: convertUrl(type, '/{id}'),
        params: params,
        data: data
      });
    }
  };
})();
