var Template = (function () {
  'use strict';

  // register partial template
  var confPartial = $('#conference-partial').html();
  Handlebars.registerPartial('conference', confPartial);

  var trackPartial = $('#track-partial').html();
  Handlebars.registerPartial('track', trackPartial);

  var sessionPartial = $('#session-partial').html();
  Handlebars.registerPartial('session', sessionPartial);

  var speakerPartial = $('#speaker-partial').html();
  Handlebars.registerPartial('speaker', speakerPartial);

  // compile template
  var listTpl = $('#list-template').html();
  var noItemTpl = $('#no-item-template').html();

  return {
    item: Handlebars.compile(listTpl),
    noItem: Handlebars.compile(noItemTpl)
  };
})();
