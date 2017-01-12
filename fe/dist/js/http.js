var http = (function () {
  'use strict';

  var publicApi = {};
  var BASE_URL = 'http://10.83.24.172:8080' + '/api';
  // var BASE_URL = '/api';
  var methods = ['get', 'post', 'put', 'delete'];

  // url convert 에서 {dynamic string} 을 판별할 정규식
  var reParam = /(\{(\w+)\})/g;

  // url path 내 {dynamic string} 을 params 내에서 key 참조를 통해 찾아서 바꿔준다.
  function convertUrl (url, params) {
    var converted = url;
    var matches;

    while((matches = reParam.exec(url)) !== null) {
      var matched = matches[1];
      var key = matches[2];
      converted = url.replace(matched, params[key]);
    }

    return converted;
  }

  methods.forEach(function (method) {
    publicApi[method] = function (options) {
      if (options.params) {
        options.url = convertUrl(options.url, options.params);
      }

      Object.assign(options, {
        method: method,
        url: BASE_URL + options.url,
        // contentType: 'application/json',
        dataType: 'json',
        timeout: 5000
      });

      return $.ajax(options);
    };
  });

  return publicApi;
})();
