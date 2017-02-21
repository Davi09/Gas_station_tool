var Page = require('./lib/pages/Page');
var HomePage = require('./lib/pages/home-page/HomePage');
var FbDashboardPage = require('./lib/pages/experiences/FbDashboardPage');

var Promise = require('bluebird');

var webdriver = require('selenium-webdriver'),
    until = webdriver.until;

var driver = new webdriver.Builder()
   .forBrowser('chrome')
   .build();

driver.manage().window().maximize();

var OPOP_URL = '';//homepage link

var page = new Page(driver);

var email = process.env.EMAIL;
var pass = process.env.PSWD;
var query = 'katie Schneekloth';
var campaignTitle = 'Vincenzo\'s Hashtag';
var urls = [
            'https://twitter.com/tomgreenlive/status/606571335175839744',
            'https://twitter.com/VeganTweeter/status/606564979471360001',
            'https://twitter.com/VeganTweeter/status/606564979471360001',
            'https://twitter.com/FoxNews/status/606554797353730049',
            'https://twitter.com/MercyForAnimals/status/606527254584262656',
            'https://twitter.com/CNTraveler/status/606528468516511744',
            'https://twitter.com/annieshomegrown/status/606509642236215296',
            'https://twitter.com/Cheesecake/status/606506880635011072',
            'https://twitter.com/wykefarms/status/606506591236571136'
          ];

page.get(OPOP_URL)
  .then(function() {
    var homePage = new HomePage(page.getDriver(), page.getWindow());
    return homePage.clickLoginModal();
  })
  .then(function(loginModal) {
    return loginModal.loginWithFacebook();
  })
  .then(function(fbLoginPopupPage) {
    return fbLoginPopupPage.login(email, pass);
  })
  .then(function(fbDashboardPage) {
    return fbDashboardPage.goToInternal();
  })
  .then(function(fbInternalPage) {
    return fbInternalPage.searchAndImpersonate(query);
  })
  .then(function(fbInternalPage) {
    var driver = fbInternalPage.getDriver(), window = fbInternalPage.getWindow();
    return (new FbDashboardPage(driver, window)).goToUGC();
  })
  .then(function(UgcDashboardPage) {
    return UgcDashboardPage.manageUgc();
  })
  //.then(function(ugcGalleryPage) {
    // var promises = urls.map(function(url) {
    //   return ugcGalleryPage.addContent(url);
    // });

  //  return ugcGalleryPage.addContent(urls[0])
    //return Promise.all(promises);
//  })
  .then(function() {
    console.log('Submitted');
  })

  .then(null,function(err) {
console.log(err);
    driver.close();
  });
