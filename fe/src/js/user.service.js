var UserService = (function () {
  'use strict';

  return {
    isLogined: function () {
      return document.cookie.length;
    },
    isAdmin: function () {
      return true;
      // return document.cookie.indexOf('id=admin@admin.com') > 0;
    },
    join: function (data) {
      return http.post({
        url: '/user',
        data: data
      });
    },
    idCheck: function (params) {
      return http.get({
        url: '/user/check',
        params: params
      });
    },
    login: function (data) {
      return http.post({
        url: '/user/login',
        data: data
      });
    },
    logout: function () {
      return http.post({
        url: '/user/logout'
      });
    }
  };
})();
