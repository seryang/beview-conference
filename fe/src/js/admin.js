$(document).ready(function () {
  'use strict';

  var $description, $radioGroup, $title, $container;
  var $layer, $register;
  var type;

  var ajaxDone = true;

  function init () {
    $container = $('.container');
    $description = $('.description');
    $radioGroup = $('.list-type');
    $title = $description.find('.title');
    $layer = $container.find('.layer');
    $register = $container.find('.layer.register');

    NavBar.init();
    selectType();

    $radioGroup.on('click', 'input[type="radio"]', selectType);
    $description.on('click', 'a.register, a.edit, a.remove', itemAction);
    $layer.on('click', 'a.cancel, a.done', layerAction);
  }

  function selectType () {
    type = $radioGroup.find('input:checked').val();
    $title.text(Utils.toFirstUpper(type) + ' list');
  }

  function itemAction (e) {
    e.preventDefault();
    e.stopPropagation();
    var $target = $(e.target);

    if ($target.hasClass('register')) {
      registerItem();
    } else {
      var id = $target.closest('section.item-container').data('id');
      if ($target.hasClass('edit')) {
        editItem(id);
      } else if ($target.hasClass('delete')) {
        deleteItem(id);
      }
    }
  }

  function registerItem () {
    $register.slideDown();
  }

  function editItem (id) {
    $layer.data('id', id).slideDown();
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

  function layerAction (e) {
    e.preventDefault();
    e.stopPropagation();

    var $target = $(e.target);
    var id = $layer.data('id');

    if ($target.hasClass('cancel')) {
      closeItem();
    } else if ($target.hasClass('create')) {
      createItem();
    } else if ($target.hasClass('done')) {
      doneEditItem(id);
    }
  }

  function createItem () {
    var $form = $register.find('form');
    var data = Utils.getFormDataToJSON($form.serializeArray());

    return AdminService.create(type, data)
      .then(successCreateItem, failCreatItem)
      .always(handleAjaxDone);
  }

  function successCreateItem (res) {
    alert('success create item');
  }
  function failCreatItem (error) {
    var message = Utils.toFirstUpper(type) +  ' 생성 실패, ' + error.responseJSON.message;
    alert(message);
  }

  function closeItem () {
    $layer.data('id', '').slideUp();
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
