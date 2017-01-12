$(document).ready(function () {
  'use strict';

  var $description, $radioGroup, $title, $container;
  var selected = {};

  function init () {
    $description = $('.description');
    $radioGroup = $('.list-type');
    $title = $('.title');

    NavBar.init();
    selectType();

    $radioGroup.on('click', 'input[type="radio"]', selectType);
    $description.on('click', 'a.edit, a.remove', itemAction)
  }

  function selectType () {
    selected.type = $radioGroup.find('input:checked').val();
    $title.text(Utils.toFirstUpper(selected.type) + ' list');
  }

  function itemAction (e) {
    var $target = $(e.target);
    var id = $target.closest('section.item-container').data('id');
    console.log($target, id);
  }

  init();
});
