$(document).ready(function () {
  'use strict';

  var $window = $(window), $document = $(document);
  var $description, $radioGroup, $title, $container;
  var $layer, $list;
  var type, typeLabel, page;

  // TODO: 발표를 위한 임시 select option 데이터
  var selectOptions = {
    conferences: {},
    tracks: {},
    speakers: {}
  };

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
    $list = $description.find('.list-item-area');

    NavBar.init();
    selectType();

    $radioGroup.on('click', 'input[type="radio"]', selectType);
    $description.on('click', 'a.register, a.edit, a.remove', itemAction);
    $layer.on('click', 'a.cancel, a.done', layerAction)
          .on('change', 'input[type="file"]', uploadFile);

    $window.on('scroll', scrolledToBottom);
  }

  function getSelectOptionData () {
    // 1. 트랙 리스트 받아오기
    // 2. 세션 리스트 받아오기
    return $.when(
      AdminService.getMetaInfo('conferences'),
      AdminService.getMetaInfo('tracks'),
      AdminService.getMetaInfo('speakers')
    ).then(function (conferences, tracks, speakers) {
      selectOptions.conferences = conferences[0].data.map(function (conference) {
        return {
          text: conference.name,
          value: conference.idx
        };
      });
      selectOptions.tracks = tracks[0].data.map(function (track) {
        return {
          text: track.name,
          value: track.idx
        };
      });
      selectOptions.speakers = speakers[0].data.map(function (speaker) {
        return {
          text: speaker.name,
          value: speaker.idx
        };
      });
      console.log(selectOptions);
    });
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
    getSelectOptionData();
  }

  function getListOfType (moreLoading) {
    if (!moreLoading) {
      page = 1;
    }
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
    var isEmpty = !moreLoading && !res.data.length;

    if (isEmpty) {
      return $list.html(Template.noItem());
    }

    appendData(res.data, moreLoading);
  }

  function appendData (data, moreLoading) {
    if (type === "sessions") {
      data.forEach(function (item) {
        item.timeInfo = Utils.getTimeInfoFromKey(item.time);
      });
    }
    // step 1: data 를 item template 에 넣어서 DOM 생성
    // step 2: 생성한 DOM 을 $list 에 넣기 (DOM Tree 삽입)
    //   - step 2.1: moreLoading 이 false 라면 기존 $list 는 초기화
    var doms = getItemFromTemplate(data);

    if (!moreLoading) {
      $list.html('');
    }
    $list.append(doms);
  }

  function getFormFromTemplate (data) {
    return getDOMFromTemplate('form', data || {});
  }

  function getItemFromTemplate (data) {
    return getDOMFromTemplate('item', data);
  }

  function getDOMFromTemplate (templateType, data) {
    var obj = {};
    obj[type] = data;
    return Template[templateType](obj);
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
    openLayer({
      isRegister: true
    });
  }

  function openLayer (data) {
    var form = getFormFromTemplate(data);
    // type 에 따라 동적으로 로딩해야 하는 select option 정보 넣어주기
    if (type === 'tracks' || type === 'sessions') {
      var options = getOptionDOM(type);
      $layer.html(form);
      insertOptionDOM(options);
      resolveSelectItem();
    } else {
      $layer.html(form);
    }

    $layer.slideDown();
  }

  function resolveSelectItem () {
    $layer.find('select')
      .each(function (idx, select) {
        var elem = $(select);
        var selected = elem.data('value');
        elem.val(selected);
      });
  }

  function insertOptionDOM (options) {
    if (!options) {
      return;
    }

    var $form = $layer.find('form');
    if (options.conference) {
      $form.find('#item-id').html(options.conference);
    }
    if (options.track) {
      $form.find('#item-track-name').html(options.track);
    }
    if (options.speaker) {
      $form.find('#item-speaker-name').html(options.speaker)
    }
  }

  function optionDOM (obj) {
    return ['<option value="', obj.value, '">', obj.text, '</options>'].join('');
  }

  function getOptionDOM (type) {
    var doms = {};

    if (type === 'tracks') {
      doms.conference = selectOptions.conferences.map(optionDOM).join('');
    } else {
      doms.track = selectOptions.tracks.map(optionDOM).join('');
      doms.speaker = selectOptions.speakers.map(optionDOM).join('');
    }
    return doms;
  }

  function editItem (id) {
    if (!ajaxDone) {
      return;
    }

    ajaxDone = false;
    return AdminService.get(type, {
      id: id
    }).then(successGetDetailItem, failGetDetailItem)
      .always(handleAjaxDone);
  }

  function successGetDetailItem (res) {
    // 서버에서 받아온 상세 정보, html 에 삽입
    console.log('detail ', type, res);
    openLayer(res.data[0]);
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
      closeLayer();
    } else if ($target.hasClass('create')) {
      createItem(e);
    } else if ($target.hasClass('edit')) {
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
    // 새로 생성한 아이템 목록 보여주기 위해 목록 reloading
    getListOfType();
    closeLayer();
  }

  function failCreatItem (error) {
    var message = typeLabel + ' 생성 실패, ' + error.responseJSON.message;
    alert(message);
  }

  function closeLayer () {
    $layer.slideUp();
  }

  function doneEditItem (e) {
    if (!ajaxDone) {
      return;
    }
    var $form = $layer.find('form');
    var data = Utils.getFormDataToJSON($form.serializeArray());
    var id = $layer.find('.form').data('id');
    ajaxDone = false;

    return AdminService.update(type, {
      id: id
    }, data).then(function (res) {
      return successDoneEditItem(id, data);
    }, faileDoneEditItem)
      .always(handleAjaxDone);
  }

  function successDoneEditItem (id, data) {
    alert('정보 변경 성공!');
    // 업데이트 한 아이템을 보여주기 위해 목록 reloading
    getListOfType();
    closeLayer();
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
    var $target = $(e.target);
    var $hidden = $target.siblings('[type="hidden"]');
    var $text = $target.siblings('.text-muted');
    $hidden.val(res.message);
    $text.text('파일: ' + res.message);
  }

  function failUploadFile (error) {
    var message = '파일 업로드 실패, ' + error.responseJSON.message;
    alert(message);
  }

  init();
});
