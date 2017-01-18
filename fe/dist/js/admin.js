$(document).ready(function () {
  'use strict';

  var $description, $radioGroup, $title, $container;
  var $layer, $register, $forms;
  var type, typeLabel;

  var ajaxDone = true;

  function init () {
    $container = $('.container');
    $description = $('.description');
    $radioGroup = $('.list-type');
    $title = $description.find('.title');
    $layer = $container.find('.layer');
    $register = $container.find('.layer.register');
    $forms = $register.find('.form');

    NavBar.init();
    selectType();

    $radioGroup.on('click', 'input[type="radio"]', selectType);
    $description.on('click', 'a.register, a.edit, a.remove', itemAction);
    $layer.on('click', 'a.cancel, a.done', layerAction)
          .on('change', 'input[type="file"]', uploadFile);
  }

  function selectType () {
    type = $radioGroup.find('input:checked').val();
    typeLabel = Utils.toFirstUpper(type).slice(0, type.length-1);
    $title.text(typeLabel + ' list');

    getListOfType();
  }

  function getListOfType () {
    if (!ajaxDone) {
      return;
    }

    ajaxDone = false;
    return AdminService.getListOf(type)
      .then(successGetListOfType, failGetListOfType)
      .always(handleAjaxDone);
  }

  function successGetListOfType (res) {
    console.log(type, res);
  }

  function failGetListOfType (error) {
    var message = typeLabel + ' 조회 실패, ' + error.responseJSON.message;
    console.log(message, error);
    alert(message);
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
    $forms.hide();
    // 1. 현재 type 에 해당하는 layer 보여주고
    // 2. 해당 layer 기존 form data clear
    $forms.filter('.' + type).show()
          .find('form')[0].reset();
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
    var message = typeLabel + ' 삭제 실패, ' + error.responseJSON.message;
    alert(message);
  }

  function layerAction (e) {
    e.preventDefault();
    e.stopPropagation();

    var $target = $(e.target);

    if ($target.hasClass('cancel')) {
      closeItem();
    } else if ($target.hasClass('create')) {
      createItem(e);
    } else if ($target.hasClass('done')) {
      doneEditItem(e);
    }
  }

  function createItem (e) {
    var $form = $(e.target).closest('form');
    var data = Utils.getFormDataToJSON($form.serializeArray());

    return AdminService.create(type, data)
      .then(successCreateItem, failCreatItem)
      .always(handleAjaxDone);
  }

  function successCreateItem (res) {
    var message = typeLabel + ' 생성 성공!';
    alert(message);
    closeItem();
  }

  function failCreatItem (error) {
    var message = typeLabel + ' 생성 실패, ' + error.responseJSON.message;
    alert(message);
  }

  function closeItem () {
    $layer.data('id', '').slideUp();
  }

  function doneEditItem (e) {
    if (!ajaxDone) {
      return;
    }
    var id = $(e.target).closest('.layer').data('id')
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
    var message = typeLabel + ' 정보 변경 실패, ' + error.responseJSON.message;
    alert(message);
  }

  function handleAjaxDone () {
    ajaxDone = true;
  }

  function uploadFile (e) {
    var file = this.files[0];
    if (!file || !ajaxDone) {
      return;
    }

    ajaxDone = false;
    return FileService.upload(type, file)
      .then(function (res) {
        return successUploadFile(e, res);
      }, failUploadFile)
      .always(handleAjaxDone);
  }

  function progressUploadFile (e) {
    debugger;
    $percentage.show();
    var pencentage = parseInt(e.loaded / e.total);
    console.log(percentage+'%', e);
    $percentage.find('.percentage').css('width', percentage+'%');
  }

  function successUploadFile (e, res) {
    var $hidden = $(e.target).siblings('[type="hidden"]');
    $hidden.val(res.message);
  }

  function failUploadFile (error) {
    var message = '파일 업로드 실패, ' + error.responseJSON.message;
    alert(message);
  }

  init();
});
