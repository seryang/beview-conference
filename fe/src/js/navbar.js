var NavBar = (function (){
  'use strict';

  var $container, $navbarMenu, $sidebar;

  function init () {
    $container = $('.container');
    $navbarMenu = $('.navbar .menu');
    $sidebar = $('.navbar .sidebar');

    $container.on('click', clickMenu);
    // 햄버거 메뉴 클릭 시, sidebar 열어줌.
    $navbarMenu.on('click', 'a.dropdown', openMenu);
  }

  function successLogout (res) {
    alert('로그아웃 성공');
    cookie.clear();
    window.location.href = 'index.html';
  }

  function failLogout (error) {
    alert('로그아웃 실패');
  }

  function clickMenu (e) {
    var $target = $(e.target);
    // 로그아웃 버튼을 눌렀으면 로그아웃 후, 메인 페이지로 보낸다.
    if ($target.hasClass('logout')) {
      UserService.logout()
        .then(successLogout, failLogout);
    }

    // 현재 sidebar 영역을 눌렀다면 닫지 않는다.
    if ($target.hasClass('sidebar')) {
      return;
    }
    $sidebar.slideUp();
  }

  function openMenu (e) {
    e.preventDefault();
    e.stopPropagation();

    $sidebar.slideDown();
  }

  return {
    init: init
  };
})();
