var Template = (function () {
  'use strict';

  var items = {
    conference: $('#conference-partial').html(),
    track: $('#track-partial').html(),
    session: $('#session-partial').html(),
    speaker: $('#speaker-partial').html()
  };

  var forms = {
    'conference-form': $('#form-conference-partial').html(),
    'track-form': $('#form-track-partial').html(),
    'session-form': $('#form-session-partial').html(),
    'speaker-form': $('#form-speaker-partial').html()
  };

  var timeTable = {
    'time-section': $('#time-section-partial').html(),
    'session-item': $('#session-item-partial').html()
  };
  var sessionDetail = {
    'session-detail': $('#detail-partial').html()
  };

  // register partial item template
  Handlebars.registerPartial(items);
  // register partial form template
  Handlebars.registerPartial(forms);
  // register schedule time table form template
  Handlebars.registerPartial(timeTable);
  // register session detail form template
  Handlebars.registerPartial(sessionDetail);

  // compile template
  var tpls = {
    item: $('#list-template').html(),
    noItem: $('#no-item-template').html(),
    form: $('#form-template').html(),
    schedule: $('#schedule-template').html(),
    detail: $('#detail-template').html()
  };

  var result = {};
  for (var key in tpls) {
    if (tpls[key]) {
      result[key] = Handlebars.compile(tpls[key]);
    }
  }

  return result;
})();
