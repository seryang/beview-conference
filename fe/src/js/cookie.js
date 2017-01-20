var cookie = (function () {
  'use strict';

  var map = {};
  document.cookie = '';

  function set (key, value) {
    // key 가 없을 땐 쿠키 초기화
    if (key === undefined) {
      document.cookie = '';
      map = {};
      return;
    }

    // key 는 있지만 value 가 없을 때, 해당 key 값 초기화
    if (value === undefined) {
      value = '';
    }

    document.cookie = [document.cookie, (key + '=' + value), ';'].join('');
    return map[key] = value;
  }

  /**
   * 쿠키값 읽기
   * key 가 명시되지 않으면 쿠키 전체를 리턴
   * key 가 명시되면 해당 key 의 값 리턴
   *
   * @param {key} 값을 읽어올 쿠키의 키(key)
   * @return {string} 읽어온 쿠키 값
   */
  function get (key) {
    if (key === undefined) {
      return document.cookie;
    }

    return document.cookie[key];
  }

  /**
   * 쿠키 제거
   * 값을 제거할 key 가 명시되면 해당 key=value 를 제거하고,
   * key 가 명시되지 않으면 쿠키 값을 전부 지운다.
   *
   * @param {key} 제거할 쿠키의 키(key)
   * @return {string} 제거한 쿠키 값
   */
  function clear (key) {
    // key 가 없으면 쿠키 초기화
    if (key === undefined) {
      document.cookie = '';
      return map = {};
    }

    var value = map[key];
    var rMatch = new RegExp(key + '=' + value + ';?');

    document.cookie.replace(rMatch, '');
    delete map[key];

    return value;
  }

  return {
    set: set,
    get: get,
    clear: clear
  };
})();
