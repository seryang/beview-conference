var UserService = (function () {
  'use strict';

  return {
    isLogined: function () {
      return document.cookie.length;
    },
    isAdmin: function () {
      return document.cookie.indexOf('admin@beview.kr') > 0;
    },
    join: function (data) {
      return http.post({
        url: '/users',
        data: data
      });
    },
    idCheck: function (params) {
      return http.get({
        url: '/users/check',
        params: params
      });
    },
    login: function (data) {
      return http.post({
        url: '/users/login',
        data: data
      });
    },
    logout: function () {
      return http.post({
        url: '/users/logout'
      });
    }
  };
})();
