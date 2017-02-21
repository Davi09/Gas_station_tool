var Page = require('../Page'),
    //FbDashboardPage = require('../experiences/FbDashboardPage'),
    inherits = require('util').inherits,
    locators = require('../../locators'),
    FB_INT_SEARCH_QUERY = locators.FB_INT_SEARCH_QUERY,
    FB_INT_SEARCH_SUBMIT = locators.FB_INT_SEARCH_SUBMIT,
    FB_INT_SEARCH_RESULTS = locators.FB_INT_SEARCH_RESULTS,
    IMPERSONATE_LINK =  locators.IMPERSONATE_LINK;

var FbInternalPage = module.exports = function(driver, window) {
  Page.call(this, driver, window);
};

inherits(FbInternalPage, Page);

FbInternalPage.prototype.enterQuery = function(query) {
  return this.getElement(FB_INT_SEARCH_QUERY).then(function(el) {
    return el.sendKeys(query);
  });
};

FbInternalPage.prototype.clickSubmit = function() {
  return this.clickElement(FB_INT_SEARCH_SUBMIT);
};

FbInternalPage.prototype.search = function(query) {
  var self = this;
  return self.enterQuery(query).then(function() {
    return self.clickSubmit();
  })
};

FbInternalPage.prototype.impersonate = function() {
  var self = this;
  return this.getElement(IMPERSONATE_LINK).then(function(el) {
    var url = el.getAttribute('href');
    return self.get(url);
  });
};

FbInternalPage.prototype.searchAndImpersonate = function(query) {
  var self = this;
  return self.search(query).then(function() {
    return self.impersonate().then(function() {
      return self;
    });
  });
};
