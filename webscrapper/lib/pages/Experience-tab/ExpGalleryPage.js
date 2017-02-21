var Page = require('../Page'),
    inherits = require('util').inherits,
    locators = require('../../locators'),
    UGC_CAMPAIGNS = locators.UGC_CAMPAIGNS,
    ADD_CONTENT_BTN = locators.ADD_CONTENT_BTN,
    CONTENT_URL_INPUT = locators.CONTENT_URL_INPUT,
    CONTENT_SUBMIT_BTN = locators.CONTENT_SUBMIT_BTN;

var ExpGalleryPage = module.exports = function(driver, window) {
  Page.call(this, driver, window);
};

inherits(ExpGalleryPage, Page);
