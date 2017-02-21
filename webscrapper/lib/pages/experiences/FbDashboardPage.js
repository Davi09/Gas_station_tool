var Page = require('../Page'),
    FbInternalPage = require('../internal/FbInternalPage'),
    UgcDashboardPage = require('../ugc/UgcDashboardPage'),
    ExpDashboardPage = require('../Experience-tab/ExpDashboardPage'),
    inherits = require('util').inherits,
    locators = require('../../locators'),
    USER_DROPDOWN = locators.USER_DROPDOWN,
    INTERNAL_BUTTON = locators.INTERNAL_BUTTON,
    DASHBOARD_UGC_LINK = locators.DASHBOARD_UGC_LINK,
    DASHBOARD_EXP_DROPDOWN =locators.DASHBOARD_EXP_Dropdown,
    DASHBOARD_EXP_LINK=locators.DASHBOARD_EXP_LINK;

var FbDashboardPage = module.exports = function(driver, window) {
  Page.call(this, driver, window);
};

inherits(FbDashboardPage, Page);

FbDashboardPage.prototype.clickDropdown = function() {
  return this.clickElement(USER_DROPDOWN);
};

FbDashboardPage.prototype.selectInternal = function() {
  return this.clickElement(INTERNAL_BUTTON);
};

FbDashboardPage.prototype.goToInternal = function() {
  var self = this, driver = this.getDriver(), window = this.getWindow();
  return self.clickDropdown().then(function() {
    return self.selectInternal().then(function() {
      return new FbInternalPage(driver, window);
    });
  })
};

FbDashboardPage.prototype.goToUGC = function() {
  var self = this, driver = this.getDriver(), window = this.getWindow();
    return self.clickElement(DASHBOARD_EXP_DROPDOWN).then(function(){
  return self.clickElement(DASHBOARD_UGC_LINK).then(function() {
    return new UgcDashboardPage(driver, window);
  });
});
}
FbDashboardPage.prototype.goToExperience = function() {
  var self = this, driver = this.getDriver(), window = this.getWindow();
  return self.clickElement(DASHBOARD_EXP_DROPDOWN).then(function(){
    return self.clickElement(DASHBOARD_EXP_LINK).then(function() {
    return new ExpDashboardPage(driver, window);
  });
  });
}
