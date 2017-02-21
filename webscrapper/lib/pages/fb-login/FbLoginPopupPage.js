var Page = require('../Page'),
    FbDashboardPage = require('../experiences/FbDashboardPage'),
    inherits = require('util').inherits,
    locators = require('../../locators'),
    EMAIL_FIELD = locators.FB_EMAIL_FIELD,
    PASSWORD_FIELD = locators.FB_PASSWORD_FIELD,
    LOGIN_BUTTON = locators.FB_LOGIN_BUTTON,
    KEEP_ME_LOGGED_IN_CHECKBOX = locators.FB_KEEP_ME_LOGGED_IN_CHECKBOX;

var FbLoginPopupPage = module.exports = function(driver, window) {
  Page.call(this, driver, window);

  // this.email = this.getElement(EMAIL_FIELD);
  // this.password = this.getElement(PASSWORD_FIELD);
  // this.loginButton = this.getElement(LOGIN_BUTTON);
  // console.log(this);
};

inherits(FbLoginPopupPage, Page);

FbLoginPopupPage.prototype.enterEmail = function(email) {
  return this.getElement(EMAIL_FIELD).then(function(el) {
    return el.sendKeys(email);
  });
};

FbLoginPopupPage.prototype.enterPassword = function(pswd) {
  return this.getElement(PASSWORD_FIELD).then(function(el) {
    return el.sendKeys(pswd);
  });
};

FbLoginPopupPage.prototype.clickLogin = function() {
  return this.clickElement(LOGIN_BUTTON);
};

FbLoginPopupPage.prototype.login = function(email, pswd) {
  var self = this, driver = this.getDriver(), window = this.getWindow();
  console.log('window', window);
  return this.enterEmail(email)
          .then(function() {
            return self.enterPassword(pswd);
          })
          .then(function() {
            return self.clickLogin();
          })
          .then(function() {
            return driver.switchTo().window(window);
          })
          .then(function() {
            return new FbDashboardPage(driver, window);
          });
};
