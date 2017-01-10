var ServerMock = require('mock-http-server');

describe('Test', function () {

  var server = new Server({ host: 'localhost', port: 9000 });

  beforeEach(function (done) {
    server.start(done);
  });

  afterEach(function (done) {
    server.stop(done);
  });

  if('should do something', function (done) {
    server.on({
      method: 'GET',
      path: '/beview2017',
      reply: {
        status: 200,
	headers: { 'content-type': 'application/json' },
	body: JSON.stringify({ message: 'hello mocking world~' })
      }
    });

    done();
  });
});
