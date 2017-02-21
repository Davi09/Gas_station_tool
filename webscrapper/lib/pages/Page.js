var until = require('selenium-webdriver').until;

function Page(driver, window) {
  this._driver = driver;
  this._originalWindow = window;
}

Page.prototype.get = function(url) {
  var self = this;
  return self.getDriver().get(url).then(function() {
    return self.getDriver().getWindowHandle().then(function(handle) {
      self._originalWindow = handle;
      return true;
    });
  });
}

Page.prototype.getDriver = function() {
  return this._driver;
};

Page.prototype.getWindow = function() {
  return this._originalWindow;
};

Page.prototype.getElement = function(locator, isVisible) {
  var driver = this.getDriver(),
      //el = driver.findElement(locator),
      ms = 100000;

  return driver.wait(until.elementLocated(locator), ms).then(function(el) {
    var promise = driver.wait(until.elementIsVisible(el), ms).then(function(visibility) {
      return el;
    });

    console.log('isVisible', isVisible);
    return el;
    //return isVisible === undefined ? el : promise;
  });
};

Page.prototype.getElements = function(locator, isVisible) {
  var driver = this.getDriver(),
      //el = driver.findElement(locator),
      ms = 100000;

  return driver.wait(until.elementsLocated(locator), ms);
};

Page.prototype.clickElement = function(locator, isVisible) {
  return this.getElement(locator, isVisible).then(function(el) {
    return el.click();
  });
};

Page.prototype.moveToElement = function(el) {
  var self = this;
  return el.getLocation().then(function(position) {
    var x = position.x, y = position.y;
    var script = ['window.scrollTo(', x, ',', y, ');', 'arguments[arguments.length-1]();'].join('');
    return self.getDriver().executeAsyncScript(script);
  })
};

Page.prototype.moveToAndClickElement = function(el) {
  return this.moveToElement(el).then(function() {
    return el.click();
  });
};


// Page.prototype.waitUntilElementVisible = function(el, ms) {
//   ms = ms || 5000;
//   return this.getDriver().wait(until.elementIsVisible(el), ms).then(function() {
//     return el;
//   });
// };

Page.prototype.switchTo = function() {

};


Page.prototype.scrollToBottom = function() {
  var self = this,
      script = 'window.scrollTo(0, document.body.scrollHeight);';

  return self.getDriver().executeAsyncScript(script).then(function() {
    return self;
  });
};

module.exports = Page;
