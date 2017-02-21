var By = require('selenium-webdriver').By;

module.exports = {
  OP_LOGIN_BUTTON: By.css('a[href="#modal-login"]'),
  OP_FB_LOGIN: By.xpath('(//a[@onclick="fblogin();"])[2]'),
  FB_EMAIL_FIELD: By.id('email'),
  FB_PASSWORD_FIELD: By.id('pass'),
  FB_LOGIN_BUTTON: By.id('u_0_2'),
  FB_KEEP_ME_LOGGED_IN_CHECKBOX: By.id('persist_box'),
  USER_DROPDOWN: By.xpath('(//a[@id="user-name-dropdown"])'),
  INTERNAL_BUTTON: By.css('a[href="/FacebookInternal.psp"]'),
  FB_INT_SEARCH_QUERY: By.id('fbIntSearchQuery'),
  FB_INT_SEARCH_SUBMIT: By.id('btnSearch'),
  FB_INT_SEARCH_RESULTS: By.id('fbIntSearchResults'),
  IMPERSONATE_LINK: By.xpath('(//div[@id="fbIntSearchResults"]/table/tbody/tr[3]/td[1]/a)[1]'),
  DASHBOARD_UGC_LINK: By.xpath('//a[@href="/hashtag/dashboard/"]'),
  DASHBOARD_EXP_Dropdown:By.xpath('(//a[@class="dropdown-toggle"])'),
  DASHBOARD_COM_LINK:By.xpath('//a[@href="/commerce/dashboard/"]'),
  DASHBOARD_EXP_LINK:By.xpath('//a[@href="/FacebookDashboard.psp"]'),
  UGC_CAMPAIGNS: By.css('.campaign.row'),
  UGC_CAMPAIGN_TITLE: By.css('.campaign-state'),
  UGC_MANAGE_LINK: By.css('.campaign-manage-entries > a'),
  ADD_CONTENT_BTN: By.css('a[href="#add_missing_content_modal"]'),
  ADD_CONTENT_MODAL: By.id('add_missing_content_modal'),
  ALERT_BOX: By.id('alert_box'),
  CONTENT_URL_INPUT: By.id('content_url'),
  CONTENT_SUBMIT_BTN: By.id('submit_missing_content_btn'),
  Exp_CAMPAIGNS: By.css('.CampaignTable > tbody >tr'),
  EXP_TABLE: By.css('.CampaignStatusTd > .GenStatus'),
  EXP_REPORT:By.css('.CampaignAppMenu > .menu > a:nth-of-type(2)'),
//  REPORT_LINK:By.css('.SimpleStatus > div > a'),
  //NEW CONTENT
    UGC_REPORT:By.css('.campaign-reports > a '),
    REPORT_LINK:By.xpath('(//a[@id="exports-tab"])'),
    REPORT_EXPORT:By.xpath('//button[@id="export-content"]'),
    DOWNLOAD_LINK_REPORT1:By.css('#export-status > #export-success'),
  DOWNLOAD_LINK_REPORT:By.css('#export-status > #export-success > a')

};
