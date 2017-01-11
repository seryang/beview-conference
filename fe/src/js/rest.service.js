var RestService = (function () {
  'use strict';

  return {
    main: function (params) {
      return http.get({
        url: '/{conferenceId}/main',
        params: params
      });
    },
    get: function (params) {
      return http.get({
        url: '/{conferenceId}',
        params: params
      });
    },
    create: function (params, data) {
      return http.post({
        url: '/{conferenceId}',
        params: params,
        data: data
      });
    },
    update: function (params, data) {
      return http.put({
        url: '/{conferenceId}/{userId}',
        params: params,
        data: data
      });
    },
    delete: function (params) {
      return http.delete({
        url: '/{conferenceId}/{userId}',
        params: params
      });
    }
  };
})();
