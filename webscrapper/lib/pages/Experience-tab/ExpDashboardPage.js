var Page = require('../Page'),
    ExpGalleryPage = require('./ExpGalleryPage'),
    ReportPage = require('../ReportDownloadPage/ReportDownload'),
    inherits = require('util').inherits,
    webdriver = require('selenium-webdriver');
    locators = require('../../locators'),
    until = require('selenium-webdriver').until,
    ReportPage = require('../ReportDownloadPage/ReportDownload'),
    EXP_CAMPAIGNS = locators.Exp_CAMPAIGNS;
    EXP_TABLE= locators.EXP_TABLE;
    EXP_REPORT=locators.EXP_REPORT;
  //  getHTMLHelper = require('../helpers/_getHTML'),
  //  ErrorHandler = require('../utils/ErrorHandler')
    /*UGC_CAMPAIGN_TITLE = locators.UGC_CAMPAIGN_TITLE,
    UGC_MANAGE_LINK = locators.UGC_MANAGE_LINK;*/

var ExpDashboardPage = module.exports = function(driver, window) {
  Page.call(this, driver, window);
};

inherits(ExpDashboardPage, Page);

var links={};

var driver = new webdriver.Builder()
   .forBrowser('chrome')
   .build();

var page = new Page(driver);


ExpDashboardPage.prototype.getCampaigns = function() {
  var self = this, driver = this.getDriver(), window = this.getWindow();

 //WebElement cells = driver.getElement(EXP_CAMPAIGNS);
  return driver.findElements(EXP_CAMPAIGNS);
//  console.log(cells);
//  return cells
};

ExpDashboardPage.prototype.findCampaign = function() {
  return this.getCampaigns().then(function(rows) {
    console.log('new length is=='+rows.length);
  //  console.log(rows);

    var filtered = webdriver.promise.filter(rows, function(row) {
        console.log('1');
      //  console.log('value is=='+row.findElement(EXP_TABLE).getText());
   return  row.findElement(EXP_TABLE).then(function(titleEl) {
        return titleEl.getText().then(function(title) {
          console.log(title);
          return title === 'Live';
        });
      });
    });


    return filtered.then(function(rows) {
      return rows;
    });
  });
};

ExpDashboardPage.prototype.manageExp = function() {
  var self = this, driver = this.getDriver(), window = this.getWindow();
  return self.findCampaign()
          .then(function(elements) {
            console.log('lenght is=='+elements.length);
          var i;
          var parentHandle=driver.getWindowHandle();
        for (i=0;i<elements.length;i++){
            console.log("turn is="+i );
            if(i > 0){
              console.log('inside if block iteration=='+i);
            //  driver.switchTo().window(parentHandle);
            var data_rec = function(i){
              console.log('inside method==' +i);
             self.findCampaign().then(function(elements){
               elements[i].findElement(EXP_REPORT).then(function(titleEl) {
                 return titleEl.getAttribute('href').then(function(title) {
                  console.log(title);
                  //var url="window.open('"+title+"')";
                //  driver.executeScript(url);
                var page='page'+i;
                 page = new Page(driver);
                  page.get(title)
                    .then(function() {
                      var reportPage = new ReportPage(page.getDriver(), page.getWindow());
                      return reportPage.manageReport().then(function(){
                      //  driver.switchTo().window(parentHandle);
                      console.log('back');
                      });
                    });

                 });
                });
             });
           }
           data_rec(i);
          }
        //    driver.executeScript("window.open()");
          //   driver.wait(until.elementLocated(EXP_TABLE), 20000).
          if(i==0){
            console.log('inside if block iteration=='+i);
           elements[i].findElement(EXP_REPORT).then(function(titleEl) {
             return titleEl.getAttribute('href').then(function(title) {
              console.log(title);
              var url="window.open('"+title+"')";
            //  driver.executeScript(url);
            var page='page'+i;
             page = new Page(driver);
              page.get(title)
                .then(function() {
                  var reportPage = new ReportPage(page.getDriver(), page.getWindow());
                  return reportPage.manageReport().then(function(){
                  //  driver.switchTo().window(parentHandle);
                  console.log('back');
                  });
                });

             });
            });
          }

       }
          //  console.log("final output is "+links.length);
            return 'cdc';

        });




}
