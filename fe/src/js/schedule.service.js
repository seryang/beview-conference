var ScheduleService = (function () {
  'use strict';

  return {
    favorite: function (params, cancel) {
      var fn = (cancel) ? 'delete' : 'update';
      return this[fn](params);
    },
    timeTable: function (params) {
      return http.get({
        url: '/conferences/{id}',
        params: params
      });
    },
    detail: function (params) {
      return http.get({
        url: '/sessions/{id}',
        params: params
      });
    },
    track: function (params) {
      return http.get({
        url: '/tracks/{id}',
        params: params
      });
    },
    speaker: function (params) {
      return http.get({
        url: '/speakers/{id}',
        params: params
      });
    },
    delete: function (params) {
      return http.delete({
        url: '/favorites/{id}',
        params: params
      });
    },
    update: function (params) {
      return http.post({
        url: '/favorites/{id}',
        params: params
      });
    }
  };
})();
