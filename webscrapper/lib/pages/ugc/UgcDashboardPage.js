var Page = require('../Page'),
    UgcGalleryPage = require('./UgcGalleryPage'),
    inherits = require('util').inherits,
    webdriver = require('selenium-webdriver');
    locators = require('../../locators'),
      ReportPage = require('../ReportDownloadPage/ReportDownload'),
    UGC_CAMPAIGNS = locators.UGC_CAMPAIGNS,
    UGC_CAMPAIGN_TITLE = locators.UGC_CAMPAIGN_TITLE,
    UGC_MANAGE_LINK = locators.UGC_MANAGE_LINK;
      UGC_REPORT=locators.UGC_REPORT;
var UgcDashboardPage = module.exports = function(driver, window) {
  Page.call(this, driver, window);
};

inherits(UgcDashboardPage, Page);

UgcDashboardPage.prototype.getCampaigns = function() {
  return this.getElements(UGC_CAMPAIGNS);
};

UgcDashboardPage.prototype.findCampaign = function() {
  return this.getCampaigns().then(function(rows) {
    console.log(rows.length);
    var filtered = webdriver.promise.filter(rows, function(row) {
      return row.findElement(UGC_CAMPAIGN_TITLE).then(function(titleEl) {
        return titleEl.getText().then(function(title) {
          return title === 'Live';
        });
      });
    });

    return filtered.then(function(rows) {
      return rows;
    });

  });
};

UgcDashboardPage.prototype.manageUgc = function(campaignTitle) {
  var self = this, driver = this.getDriver(), window = this.getWindow();
  return driver.wait(until.elementLocated(UGC_CAMPAIGNS), 100000).then(function(){
  return self.findCampaign()
  .then(function(elements) {
    console.log('lenght is=='+elements.length);
  var i;

for (i=0;i<elements.length;i++){
    console.log("turn is="+i );
    if(i > 0){
      console.log('inside if block iteration=='+i);
    //  driver.switchTo().window(parentHandle);
    var data_rec = function(i){
      console.log('inside method==' +i);
      return driver.wait(until.elementLocated(UGC_CAMPAIGNS), 10000).then(function(){
     self.findCampaign().then(function(elements){
       elements[i].findElement(UGC_REPORT).then(function(titleEl) {
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
   });
   }
   data_rec(i);
  }
//    driver.executeScript("window.open()");
  //   driver.wait(until.elementLocated(EXP_TABLE), 20000).
  if(i==0){
    console.log('inside if block iteration=='+i);
   elements[i].findElement(UGC_REPORT).then(function(titleEl) {
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
  driver.close();
    return 'cdc';

});
});

}
