var AdminService = (function () {
  'use strict';

  function convertUrl(type, path) {
    return ['/', type, path].join('');
  }

  return {
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
