$(document).ready(function () {
  'use strict';

  var $window = $(window), $document = $(document);
  var $description, $radioGroup, $title, $container;
  var $layer, $register, $forms, $list;
  var type, typeLabel, page, tick = 0;

  // ajaxDone 상태는 ajax 요청이 끝났는지 상태를 제어하는 변수가
  // POST, PUT, DELETE 같은 경우, 요청이 끝나기 전에 다시 호출되면 문제가 발생하기 때문에
  // 해당 요청이 발생헀을 때, ajaxDone 이 false 라면 새로 요청을 날리지 않는다.
  var ajaxDone = true;

  function init () {
    $container = $('.container');
    $description = $('.description');
    $radioGroup = $('.list-type');
    $title = $description.find('.title');
    $layer = $container.find('.layer');
    $register = $container.find('.layer.register');
    $forms = $register.find('.form');
    $list = $description.find('.list-item-area');

    NavBar.init();
    selectType();

    $radioGroup.on('click', 'input[type="radio"]', selectType);
    $description.on('click', 'a.register, a.edit, a.remove', itemAction);
    $layer.on('click', 'a.cancel, a.done', layerAction)
          .on('change', 'input[type="file"]', uploadFile);

    $window.on('scroll', scrolledToBottom);
  }

  function scrolledToBottom (e) {
    // 현재 스크롤 위치를 파악 후,
    // container 와 비교해서 bottom 으로부터 # px 이내 범위 안에 있을 때
    // 다음 페이지를 불러옴
    var bottom = parseInt($list.height());
    var isNeedMoreLoading = (bottom - 300 < ($window.scrollTop() + 120));

    if (isNeedMoreLoading && ajaxDone) {
      page++;
      ajaxDone = false;
      getListOfType(true);
    }
  }

  function selectType () {
    type = $radioGroup.find('input:checked').val();
    typeLabel = Utils.toFirstUpper(type).slice(0, -1);
    $title.text(typeLabel + ' list');
    page = 1;

    getListOfType();
  }

  function getListOfType (moreLoading) {
    // 여기서는 ajax request done handling 을 할 필요가 없어서 제거함
    // GET 요청은 safe, idempotent 하기 때문이다.
    return AdminService.getListOf(type, {
      page: page
    }).then(function (res) {
      return successGetListOfType(res, moreLoading);
    }, failGetListOfType)
      .always(handleAjaxDone);
  }

  // 현재 item 이 아니것도 없을 때, noItem 템플릿 보여줌
  function successGetListOfType (res, moreLoading) {
    console.log(type, res, page);
    var isEmpty = !res.data.length;

    if (isEmpty) {
      return $list.html(Template.noItem());
    }
    appendData(res.data.slice(0, 3), moreLoading);
    // appendData(res.data, moreLoading);
  }

  function appendData (data, moreLoading) {
    // startTime, endTime 경우, server 에서 second 정보가 넘어오기 때문에 해당 정보 slice
    if (type === "sessions") {
      data.forEach(function (item) {
        item.startTime = (item.startTime || {}).slice(0, -3);
        item.endTime = (item.endTime || {}).slice(0, -3);
      });
    }
    // step 1: data 를 item template 에 넣어서 DOM 생성
    // step 2: 생성한 DOM 을 $list 에 넣기 (DOM Tree 삽입)
    //   - step 2.1: moreLoading 이 false 라면 기존 $list 는 초기화
    var doms = getItemFromTemplate(type, data);

    if (!moreLoading) {
      $list.html('');
    }
    $list.append(doms);
  }

  function getItemFromTemplate (type, data) {
    var obj = {};
    obj[type] = data;
    return Template.item(obj);
  }

  function failGetListOfType (error) {
    var message = typeLabel + ' 조회 실패, ' + error.responseJSON.message;
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
      } else if ($target.hasClass('remove')) {
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
    if (!ajaxDone) {
      return;
    }

    ajaxDone = false;
    $layer.data('id', id).slideDown();
    return AdminService.get(type, {
      id: id
    }).then(successGetDetailItem, failGetDetailItem)
      .always(handleAjaxDone);
  }

  function successGetDetailItem (res) {
    // TODO: 서버에서 받아온 상세 정보, html 에 삽입
    console.log('detail ', type, res);
  }

  function failGetDetailItem (error) {
    var message = typeLabel + ' 상세 정보 로딩 실패, ' + error.responseJSON.message;
    alert(message);
  }

  function deleteItem (id) {
    if (!ajaxDone) {
      return;
    }

    var message = '관련된 데이터가 전부 삭제될 수 있습니다. 계속 진행하시겠습니까?';
    if (!window.confirm(message)) {
      return;
    }
    ajaxDone = false;
    return AdminService.delete(type, {
      id: id
    }).then(function (res) {
      return successDeleteItem(id);
    }, failDeleteItem)
      .always(handleAjaxDone);
  }

  function successDeleteItem (id) {
    alert('삭제 성공!');
    // 삭제 성공하면, 리스트에서 해당 아이템을 지워줌
    // ex) section[data-id="12"]
    var selector = ['section[data-id="', id, '"]'].join('');
    $list.find(selector).remove();
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
    // 새로 생성한 아이템을 목록 보여주기 위해 목록 reloading
    closeItem();
    getListOfType();
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
    var id = $(e.target).closest('.layer').data('id');
    ajaxDone = false;

    // TODO: update data object 생성
    debugger;
    return AdminService.update(type, {
      id: id
    }, {
      // title, description, track, location, time, speaker
      dummy: true
    }).then(function (res) {
      return successDoneEditItem(id, data);
    }, faileDoneEditItem)
      .always(handleAjaxDone);
  }

  function successDoneEditItem (id, data) {
    alert('정보 변경 성공!');
    // TODO:
    //  step 1: 생성을 요청한 data 를 기준으로 Template 을 이용해 새로운 DOM 을 만든다.
    //  step 2: 새로운 DOM 기존 위치에 삽입 후, 기존 DOM 을 제거한다.
    var selector = ['section[data-id="', id, '"]'].join('');
    var item = getItemFromTemplate(type, data);
    var $position = $list.find(selector);
    debugger;
    $(item).insertBefore($position);
    $position.remove();
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
