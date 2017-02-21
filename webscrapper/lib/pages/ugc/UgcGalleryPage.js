var Page = require('../Page'),
    inherits = require('util').inherits,
    locators = require('../../locators'),
    UGC_CAMPAIGNS = locators.UGC_CAMPAIGNS,
    ADD_CONTENT_BTN = locators.ADD_CONTENT_BTN,
    CONTENT_URL_INPUT = locators.CONTENT_URL_INPUT,
    CONTENT_SUBMIT_BTN = locators.CONTENT_SUBMIT_BTN;

var UgcGalleryPage = module.exports = function(driver, window) {
  Page.call(this, driver, window);
};

inherits(UgcGalleryPage, Page);

UgcGalleryPage.prototype.clickAddToContentBtn = function() {
  var self = this;
  console.log(0);
  return self.getElement(ADD_CONTENT_BTN)
    .then(function(el) {
      return self.moveToAndClickElement(el);
    });
};

UgcGalleryPage.prototype.fillInForm = function(url) {
  console.log(1);
  return this.getElement(CONTENT_URL_INPUT).then(function(el) {
    el.sendKeys(url);
  });
};

UgcGalleryPage.prototype.submitForm = function() {
  console.log(2);
  return this.clickElement(CONTENT_SUBMIT_BTN);
};

UgcGalleryPage.prototype.addContent = function(url) {
  var self = this;
  return self.clickAddToContentBtn()
    .then(function() {
      return self.fillInForm(url);
    })
    .then(function() {
      return self.submitForm();
    });
};