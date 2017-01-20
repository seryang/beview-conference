var http = (function () {
  'use strict';

  var publicApi = {};
  var BASE_URL = '/api';
  var methods = ['get', 'post', 'put', 'delete'];

  // url convert 에서 {dynamic string} 을 판별할 정규식
  var reParam = /(\{(\w+)\})/g;

  /**
   * url path 내 {dynamic string} 을 params 내에서 key 참조를 통해 찾아서 바꿔준다.
   *
   * example:
   *  url: '/conferences/{id}', params: {id: 1}
   *    -> converted: '/conferences/1'
   *
   *  url: '/{type}/uploadFile', params: {type: 'sessions'}
   *    -> converted: '/sessions/uploadFile'
   *
   * @return converted {string} 사용자가 넘겨준 parameter mapping 을 완료한 url
   */
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
      // TODO: options 객체에 search 정보가 들어오면, url query string 으로 변경
      // if (options.search) {
      //   options.url = options.url + $.params(options.search);
      // }
      if (options.data && !options.isFileServer) {
        options.data = JSON.stringify(options.data)
      }

      options = Object.assign({
        method: method,
        contentType: 'application/json',
        dataType: 'json',
        timeout: 5000
      }, options, {
        url: BASE_URL + options.url
      });

      return $.ajax(options);
    };
  });

  return publicApi;
})();
