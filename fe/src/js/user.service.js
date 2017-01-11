var UserService = (function () {
  'use strict';

  return {
    join: function (data) {
      return http.post({
        url: '/user/join',
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
      // logout 은 서버로 요청을 날리지 않는다.
      // Deffered Promise 를 리턴한다.
      var deferred = $.Deferred();
      deferred.resolve({});
      return deferred.promise();
    }
  };
})();
