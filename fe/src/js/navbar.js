var NavBar = (function (){
  'use strict';

  var $container, $navbarMenu, $sidebar;

  function init () {
    $container = $('.container');
    $navbarMenu = $('.navbar .menu');
    $sidebar = $('.navbar .sidebar');

    $container.on('click', function (e) {
      // 현재 sidebar 영역을 눌렀다면 닫지 않는다.
      if ($(e.target).hasClass('sidebar')) {
        return;
      }
      $sidebar.slideUp();
    });

    // 햄버거 메뉴 클릭 시, sidebar 열어줌.
    $navbarMenu.on('click', 'a.dropdown', function (e) {
      e.preventDefault();
      e.stopPropagation();

      $sidebar.slideDown();
    });
  }

  return {
    init: init
  };
})();
