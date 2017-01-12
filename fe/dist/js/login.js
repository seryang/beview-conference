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
    var $target = $(e.target);
    if ($target.hasClass('submit')) {
      e.preventDefault();
      e.stopPropagation();
      login();
    }
  }

  function login () {
    if (!ajaxDone) {
      return;
    }
    if (!validation()) {
      return;
    };

    // var data = $form.serialize();
    var data = {
      id: $id.val(),
      password: $password.val()
    };
    UserService.login(data)
      .then(successLogin, failLogin)
      .always(handleAjaxDone);
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

  /**
   * 로그인 실패 처리
   *
   * case 1: id 가 없을 때,
   * case 2: 비밀번호가 틀렸을 때
   * case #: 서버 에러 또는 (클라이언트에서 검증했지만) 요청 정보가 부족한 경우
   */
  function failLogin (error) {
    var message = '로그인 실패, ' + error.responseJSON.message;
    alert(message);
  }

  function handleAjaxDone () {
    ajaxDone = true;
  }

  init();
});
