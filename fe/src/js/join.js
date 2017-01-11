$(document).ready(function () {
  'use strict';

  var $form, $action, $id, $password, $password2;
  var ajaxDone = true;
  // 이메일 검증 정규식
  var rEmail = /\w+@\w+(\.\w+){1,2}$/;

  function init () {
    $form = $('form');
    $action = $('.action');
    $id = $('#user-id');
    $password = $('#user-password');
    $password2 = $('#user-password-again');

    NavBar.init();

    $action.on('click', 'a', action);
    $form.on('keyup', keyup);
  }

  function keyup (e) {
    switch (e.keyCode) {
      case 13:
        join();
        break;
    }
  }

  function action (e) {
    var $target = $(e.target);
    if ($target.hasClass('submit')) {
      e.preventDefault();
      e.stopPropagation();
      join();
    }
  }

  function join () {
    if (!ajaxDone) {
      return;
    }
    if (!validation()) {
      return;
    };

    return successjoin({
      type: 'dummy',
      status: 200
    });

    var data = $form.serialize();
    RestService.join(data)
      .then(successJoin, failJoin)
      .then(handleAjaxDone);
  }

  /**
   * 클라이언트 폼 검증
   *
   * case 1: id가 이메일인가
   * case 2: password 가 6자리 이상인가
   * case 3: password2 가 6자리 이상인가
   * case 4: password 이 password2 와 같은가
   *
   * @return {boolean} true - 검증 통과, false - 검증 실패
   */
  function validation () {
    var id = $id.val();
    var password = $password.val();
    var password2 = $password2.val();

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
    if (password2.length < 6) {
      alert('비밀번호는 6자리 이상이어야 합니다.');
      $password2.focus();
      return;
    }
    if (password !== password2) {
      alert('비밀번호가 서로 다릅니다. 확인해주세요.');
      $password.focus();
      return;
    }

    return true;
  }

  function successJoin (res) {
    alert('회원가입 성공. 로그인 해주세요.');
    // cookie.set('session', res.cookie);
    window.location.href = 'login.html';
  }

  // TODO: 이미 존재하는 이메일인 경우 메시지 보여줌
  // 그게 아닌 경우 ???
  function failJoin (error) {
    alert('회원가입 실패');
  }

  function handleAjaxDone () {
    ajaxDone = true;
  }

  init();

  return {
    init: init
  };
});
