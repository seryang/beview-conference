var http = (function () {
  'use strict';

  var publicApi = {};
  var BASE_URL = 'http://localhost:3000';
  var methods = ['get', 'post', 'put', 'delete'];

  //  url path 내 {dynamic string} 을 params 내에서 key 참조를 통해 찾아서 바꿔준다.
  function convertUrl (url, params) {
    var converted = url;
    var re = /(\{(\w+)\})/g;
    var matches;

    while((matches = re.exec(url)) !== null) {
      var matched = matches[1];
      var key = matches[2];
      converted = url.replace(matched, params[key]);
    }

    return converted;
  }

  methods.forEach(function (method) {
    publicApi[method] = function (options) {
      return new Promise(function (resolve, reject) {

        if (options.params) {
          options.url = convertUrl(options.url, options.params);
        }

        Object.assign(options, {
          method: method,
          url: BASE_URL + options.url,
          dataType: 'json'
        });

        $.ajax(options).then(resolve, reject);
      });
    };
  });

  return publicApi;
})();
