$(document).ready(function () {
  'use strict';

  var $timeTable, $detail;
  var sessions;

  var ajaxDone = true;

  function init () {
    $timeTable = $('.time-table');
    $detail = $('.detail.layer');

    NavBar.init();
    getScheduleDate();

    $timeTable.on('click', 'button.favorite', favoriteItem)
              .on('click', '.time-item', showDetailItem);
    $detail.on('click', 'button.favorite', favoriteItemInDetail)
           .on('click', 'a.cancel', closeLayer)
  }

  // TODO:
  //  현재 query string 에 id 정보로 confenrence Id 정보를 바꿔서 조회할 수 있다.
  //  그런데 이건 토의한 내용이 아닌, 테스트를 위해 임시로 추가한 기능이다.
  function getScheduleDate () {
    var ids = /id=(\d+)/.exec(window.location.search);

    return ScheduleService.timeTable({
      id: (!ids) ? 1 : ids[1]
    }).then(successGetTimeTable, failGetTimeTable);
  }

  function successGetTimeTable (res) {
    var data = res.data[0];
    data.times = changeColumn(data.trackList);
    sessions = data.times.reduce(function (flatten, time) {
      return flatten.concat(time.sessions);
    }, []);
    var schedule = getScheduleFromTemplate(data);
    $timeTable.html(schedule);
  }

  /**
   *  현재 서버에서 내려온 데이터는 시간을 기준으로 내려오지 않는다.
   *  트랙 기준으로 되어 있는 데이터를, 시간을 기준으로 바꿔야 한다.
   */
  function changeColumn (trackList) {
    if (!trackList) {
      return [];
    }

    var times = ['T1', 'T2', 'T3', 'T4'].map(function (time) {
      var sessions = trackList.reduce(function (flatten, track) {
        return flatten.concat(track.sessionList);
      }, []).filter(function (session) {
        session.timeInfo = Utils.getTimeInfoFromKey(session.time);
        return session.time === time;
      });

      return {
        time: time,
        timeInfo: Utils.getTimeInfoFromKey(time),
        sessions: sessions
      };
    });

    return times;
  }

  function failGetTimeTable (error) {
    var message = '타임 테이블 정보 로딩 실패, ' + error.responseJSON.message;
    alert(message);
  }

  function favoriteItemInDetail (e) {
    return favoriteItem(e, true);
  }

  function favoriteItem (e, isDetail) {
    e.preventDefault();
    e.stopPropagation();
    if (!ajaxDone) {
      return;
    }

    var $target = $(e.target);
    var selector = (isDetail) ? '.session-detail' : '.time-item';
    var id = $target.closest(selector).data('id');
    var cancel = $target.hasClass('on');
    ajaxDone = false;

    return ScheduleService.favorite({
      id: id
    }, cancel).then(function () {
      return successFavoriteItem(e, id, isDetail);
    }, failFavoriteItem)
      .always(handleAjaxDone);
  }

  function successFavoriteItem (e, id, isDetail) {
    var $target = $(e.target);
    $target.toggleClass('on');

    sessions.forEach(function (session) {
      if (session.idx === id) {
        session.favorite = !session.favorite;
      }
    });

    if (isDetail) {
      var selector = '.time-item[data-id="'+id+'"] .heart';
      $timeTable.find(selector).toggleClass('on');
    }
  }

  function failFavoriteItem (error) {
    var message = '세션 찜하기 실패, ' + error.responseJSON.message;
    alert(message);
  }

  function showDetailItem (e) {
    e.preventDefault();
    e.stopPropagation();

    var $timeItem = $(e.target).closest('.time-item');
    var id = $timeItem.data('id');

    var filtered = sessions.filter(function (session) {
    	return session.idx === id;
    });

    openLayer(filtered[0]);
  }

  function openLayer (data) {
    var detail = getDetailFromTemplate(data);
    $detail.html(detail);
    $detail.slideDown();
  }

  function getScheduleFromTemplate (data) {
    return getDOMFromTemplate('schedule', data || {});
  }

  function getDetailFromTemplate (data) {
    return getDOMFromTemplate('detail', data || {});
  }

  function getDOMFromTemplate (templateType, data) {
    return Template[templateType](data);
  }

  function closeLayer (e) {
    e.preventDefault();
    $detail.slideUp();
  }

  function handleAjaxDone () {
    ajaxDone = true;
  }

  init();
});
