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

  // register partial item template
  Handlebars.registerPartial(items);

  // register partial form template
  Handlebars.registerPartial(forms);

  // compile template
  var listTpl = $('#list-template').html();
  var noItemTpl = $('#no-item-template').html();
  var formTpl = $('#form-template').html();

  return {
    item: Handlebars.compile(listTpl),
    noItem: Handlebars.compile(noItemTpl),
    form: Handlebars.compile(formTpl)
  };
})();
