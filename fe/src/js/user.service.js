var UserService = (function () {
  'use strict';

  return {
    join: function (data) {
      return http.post({
        url: '/user/join',
        data: data
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
      var deferred = $.Deferred();
      deferred.resolve({});
      return deferred.promise();
    }
  };
})();
