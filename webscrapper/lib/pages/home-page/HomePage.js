var Page = require('../Page'),
    inherits = require('util').inherits,
    OP_LOGIN_BUTTON = require('../../locators').OP_LOGIN_BUTTON,
    LoginModal = require('./LoginModal');

var HomePage = module.exports = function(driver, window) {
  Page.call(this, driver, window);
};

inherits(HomePage, Page);

HomePage.prototype.clickLoginModal = function() {
  var self = this;
  return self.clickElement(OP_LOGIN_BUTTON)
    .then(function() {
      return new LoginModal(self.getDriver(), self.getWindow());
    });
};

module.exports = HomePage;
