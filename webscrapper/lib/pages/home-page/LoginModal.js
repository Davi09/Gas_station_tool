var Page = require('../Page'),
    FbLoginPopupPage = require('../fb-login/FbLoginPopupPage'),
    inherits = require('util').inherits,
    OP_FB_LOGIN = require('../../locators').OP_FB_LOGIN;


var LoginModal = module.exports = function(driver, window) {
  Page.call(this, driver, window);
};

inherits(LoginModal, Page);

LoginModal.prototype.loginWithFacebook = function() {
  var self = this,
      driver = this.getDriver(),
      window = this.getWindow();

  return self.clickElement(OP_FB_LOGIN, false)
    .then(function() {
      //Wait until the Facebook login window is present
      return driver.wait(function(d) {
        return d.getAllWindowHandles().then(function(handles) {
          return handles.length === 2 ? handles:false;
        });
      }, 10000)
    })
    .then(function(handles) {
      console.log(2, handles);
      return driver.switchTo().window(handles[1]);
    })
    .then(function() {
      return new FbLoginPopupPage(driver, window);
    });
};
