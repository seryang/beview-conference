$(document).ready(function () {
  'use strict';

  var $form, $action, $id, $password;
  var ajaxDone = true;
  // 이메일 검증 정규식
  var rEmail = /\w+@\w+(\.\w+){1,2}$/;

  function init () {
    $form = $('form');
    $action = $('.action');
    $id = $('#user-id');
    $password = $('#user-password');

    NavBar.init();

    $action.on('click', 'a', action);
    $form.on('keyup', keyup);
  }

  function keyup (e) {
    switch (e.keyCode) {
      case 13:
        login();
        break;
    }
  }

  function action (e) {
    e.preventDefault();
    e.stopPropagation();

    var $target = $(e.target);
    if ($target.hasClass('submit')) {
      login();
    } else {
      // 뒤로 가기
      window.history.back();
    }
  }

  function login () {
    if (!ajaxDone) {
      return;
    }
    if (!validation()) {
      return;
    };

    return successLogin({
      type: 'dummy',
      cookie: 'dummy',
      status: 200
    });

    var data = $form.serialize();
    RestService.login(data)
      .then(successLogin, failLogin)
      .then(handleAjaxDone);
  }

  /**
   * 클라이언트 폼 검증
   *
   * case 1: id가 이메일인가
   * case 2: password 가 6자리 이상인가
   *
   * @return {boolean} true - 검증 통과, false - 검증 실패
   */
  function validation () {
    var id = $id.val();
    var password = $password.val();

    if (!rEmail.test(id)) {
      alert('아이디가 이메일 형식이 아닙니다.');
      $id.focus();
      return;
    }
    if (password.length < 6) {
      alert('비밀번호는 6자리 이상이어야 합니다.');
      $password.focus();
      return;
    }

    return true;
  }

  function successLogin (res) {
    alert('로그인 성공');
    cookie.set('session', res.cookie);
    window.location.href = 'index.html';
  }

  // TODO: 이미 존재하는 이메일인 경우 메시지 보여줌
  // 그게 아닌 경우 ???
  function failLogin (error) {
    alert('로그인 실패');
  }

  function handleAjaxDone () {
    ajaxDone = true;
  }

  init();

  return {
    init: init
  };
});
