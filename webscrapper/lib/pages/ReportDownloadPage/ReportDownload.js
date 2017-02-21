var Page = require('../Page'),
    inherits = require('util').inherits,
    webdriver = require('selenium-webdriver');
    locators = require('../../locators'),
    until = require('selenium-webdriver').until,
    REPORT_EXPORT=locators.REPORT_EXPORT,
    DOWNLOAD_LINK_REPORT=locators.DOWNLOAD_LINK_REPORT;
      DOWNLOAD_LINK_REPORT1=locators.DOWNLOAD_LINK_REPORT1;
    REPORT_LINK= locators.REPORT_LINK;


  //  getHTMLHelper = require('../helpers/_getHTML'),
  //  ErrorHandler = require('../utils/ErrorHandler')
    /*UGC_CAMPAIGN_TITLE = locators.UGC_CAMPAIGN_TITLE,
    UGC_MANAGE_LINK = locators.UGC_MANAGE_LINK;*/

var ReportDownload = module.exports = function(driver, window) {
  Page.call(this, driver, window);
};

inherits(ReportDownload, Page);

ReportDownload.prototype.getCampaigns = function() {
  var self = this, driver = this.getDriver(), window = this.getWindow();
 //WebElement cells = driver.getElement(EXP_CAMPAIGNS);
  return driver.wait(until.elementLocated(REPORT_LINK), 100000).then(function(){
   return self.clickElement(REPORT_LINK).then(function(){
     console.log('hi 1');
     return driver.wait(until.elementLocated(REPORT_EXPORT), 100000).then(function(){
      self.clickElement(REPORT_EXPORT).then(function(){
      console.log('Hi 2');
     driver.sleep(5000);
   //var promise=driver.wait(until.elementIsVisible(DOWNLOAD_LINK_REPORT), 10000);
    return driver.wait(until.elementLocated(DOWNLOAD_LINK_REPORT), 100000).then(function(){
      return self.getElement(DOWNLOAD_LINK_REPORT).then(function(element){
          return element.getAttribute('href').then(function(title) {
            console.log(title);
          var page2 = new ReportDownload(driver);
             page2.get(title);
              return 0;
           });
       });
      });

  });
});
});
});
//  console.log(cells);
//  return cells
};


ReportDownload.prototype.manageReport = function() {
  console.log('hi inside Report1');
  var self = this, driver = this.getDriver(), window = this.getWindow();
  //driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
//  this.clickLogin = function() {
//    return this.clickElement(LOGIN_BUTTON);
//  };
  console.log('inside report2');

  return self.getCampaigns().then(function(){
    driver.sleep(5000);
    driver.navigate().back();
  });
}
