$(document).ready(function () {
  'use strict';

  var $description, $radioGroup, $title, $container;
  var $layer, $register, $forms;
  var type;

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
    $forms.hide()
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
    var message = '삭제 실패, ' + error.responseJSON.message;
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
    // var files = $form.find(':file')[0].files;
    console.log('form data ', data, files);
    // 1. 파일 업로드
    // 2. 아이템 생성
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
    var message = '정보 변경 실패, ' + error.responseJSON.message;
    alert(message);
  }

  function handleAjaxDone () {
    ajaxDone = true;
  }

  function uploadFile (e) {
    var file = this.files;
    if (!file.length || !ajaxDone) {
      return;
    }
    // file upload
    ajaxDone = false;
    return FileService.upload(type, file)
      .then(function (res) {
        return sucessUploadFile(e, res);
      }, failUploadFile, progressUploadFile)
      .always(handleAjaxDone);
  }

  function progressUploadFile (e) {
    console.log(parseInt(e.loaded / e.total) + '%', e);
  }

  function successUploadFile (res) {
    debugger;
  }

  function failUploadFile (error) {
    debugger;
  }

  init();
});
