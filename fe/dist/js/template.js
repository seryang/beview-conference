var Template = (function () {
  'use strict';

  // register partial template
  var confPartial = $('#conference-partial').html();
  var noConfPartial = $('#not-found-conference-partial').html();
  Handlebars.registerPartial('conference', confPartial);
  Handlebars.registerPartial('no-conference', noConfPartial);

  var trackPartial = $('#track-partial').html();
  var noTrackPartial = $('#not-found-track-partial').html();
  Handlebars.registerPartial('track', trackPartial);
  Handlebars.registerPartial('no-track', noTrackPartial);

  var sessionPartial = $('#session-partial').html();
  var noSessionPartial = $('#not-found-session-partial').html();
  Handlebars.registerPartial('session', sessionPartial);
  Handlebars.registerPartial('no-session', noSessionPartial);

  var speakerPartial = $('#speaker-partial').html();
  var noSpeakerPartial = $('#not-found-speaker-partial').html();
  Handlebars.registerPartial('speaker', speakerPartial);
  Handlebars.registerPartial('no-speaker', noConfPanoSpeakerPartialrtial);

  // compile template
  var confTpl = $('#conference-template').html();
  var noConfTpl = $('#not-found-conference-template').html();
  var trackTpl = $('#track-template').html();
  var noTrackTpl = $('#not-found-track-template').html();
  var sessionTpl = $('#session-template').html();
  var noSessionTpl = $('#not-found-session-template').html();
  var speakerTpl = $('#speaker-template').html();
  var noSpeakerTpl = $('#not-found-speaker-template').html();

  return {
    conference: Handlebars.compile(confTpl),
    noConference: Handlebars.compile(noConfTpl),
    track: Handlebars.compile(trackTpl),
    noTrack: Handlebars.compile(noTrackTpl),
    session: Handlebars.compile(sessionTpl),
    noSession: Handlebars.compile(noSessionTpl),
    speaker: Handlebars.compile(speakerTpl),
    noSpeaker: Handlebars.compile(noSpeakerTpl)
  };
})();
