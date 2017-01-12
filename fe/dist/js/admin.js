$(document).ready(function () {
  'use strict';

  var $description, $radioGroup, $title, $container;
  var $detail;
  var type;

  var ajaxDone = true;

  function init () {
    $container = $('.container');
    $description = $('.description');
    $radioGroup = $('.list-type');
    $title = $description.find('.title');
    $detail = $container.find('.detail');

    NavBar.init();
    selectType();

    $radioGroup.on('click', 'input[type="radio"]', selectType);
    $description.on('click', 'a.edit, a.remove', itemAction);
    $detail.on('click', 'a.cancel, a.done', detailAction);
  }

  function selectType () {
    type = $radioGroup.find('input:checked').val();
    $title.text(Utils.toFirstUpper(type) + ' list');
  }

  function itemAction (e) {
    e.preventDefault();
    e.stopPropagation();

    var $target = $(e.target);
    var id = $target.closest('section.item-container').data('id');

    if ($target.hasClass('edit')) {
      editItem(id);
    } else {
      deleteItem(id);
    }
  }

  function editItem (id) {
    $detail
      .data('id', id)
      .slideDown();
  }

  function deleteItem (id) {
    if (!ajaxDone) {
      return;
    }

    ajaxDone = false;
    return AdminService.delete(type, {
      id: id
    }).then(successDeleteItem, failDeleteItem)
      .always(handleAjaxDone);
  }

  function successDeleteItem (res) {
    alert('삭제 성공!');
  }

  function failDeleteItem (error) {
    var message = '삭제 실패, ' + error.responseJSON.message;
    alert(message);
  }

  function detailAction (e) {
    e.preventDefault();
    e.stopPropagation();

    var $target = $(e.target);
    var id = $detail.data('id');

    if ($target.hasClass('cancel')) {
      closeItem();
    } else {
      doneEditItem(id);
    }
  }

  function closeItem () {
    $detail
      .data('id', '')
      .slideUp();
  }

  function doneEditItem (id) {
    if (!ajaxDone) {
      return;
    }

    ajaxDone = false;
    return AdminService.update(type, {
      id: id
    }, {
      // title, description, track, location, time, speaker
      dummy: true
    }).then(successDoneEditItem, faileDoneEditItem)
      .always(handleAjaxDone);
  }

  function successDoneEditItem (res) {
    alert('정보 변경 성공!');
  }

  function faileDoneEditItem (error) {
    var message = '정보 변경 실패, ' + error.responseJSON.message;
    alert(message);
  }

  function handleAjaxDone () {
    ajaxDone = true;
  }

  init();
});
