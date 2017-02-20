$(document).ready(function () {

    //var userList = new List('users', options);
    $.ajaxSetup({
        async: false
    });

    var sexOffenderNode = new Array();
    var allSexOffenders = new Array();
    var tmpAllSexOffenders = new Array();

    var zillowNode = new Array();
    var allZillows = new Array();
    var tmpAllZillows = new Array();

    var yelpNode = new Array();
    var allYelps = new Array();
    var tmpAllYelps = new Array();

    var fbNode = new Array();
    var allFBs = new Array();

    var mapGeo = new Array();

    $(document).on('click', '#search', function () {
        var address = $("#address").val();
        var state = $("#state").val();
        var zip = $("#zip").val();
        var accessToken = $("#facebookAccessToken").val();
        var uid = $("#facebookUid").val();
        if (state != "NY") {
            $("#on").attr("disabled", true);
        }
        //alert($("#facebookAccessToken").val());
        var yelpSearchUrl = "/Home/YelpJson?address=" + address + "&zip=" + zip + "&state=" +state;
        var zillowSearchUrl = "/Home/ZillowJson?zip=" + zip + "&address=" + address;
        var fbSearchUrl = "/Home/FacebookJson?access=" + accessToken + "&uid=" + uid;
        var soSearchUrl = "/Home/SexOffenderJson?zip=" + zip + "&state=" + state;

        var mainAddress = "";

        //facebook
        $.getJSON(fbSearchUrl, function (json) {
            $.each(json['fbData'], function (key, value) {
                fbNode = {
                    friendname: value['name'],
                    placename: value['placename'],
                    address: value['address'],
                    long: value['lng'],
                    lat: value['lat']
                }

            });
            allFBs.push(fbNode);
        });
        
        //zillow
        $.getJSON(zillowSearchUrl, function (json) {
            $.each(json['zillowData'], function (key, value) {
                if (key == "Longitude" || key == "Latitude" || key == "RentEstimate" || key == "RentEstimateLow" || key == "RentEstimateHigh") {
                    zillowNode.push(value);
                }
                
            });
            zillowNode.push(address);
        });
        //sex offender
        $.getJSON(soSearchUrl, function (json) {

            $.each(json['soData'], function (key, value) {
                sexOffenderNode = {
                    name: value['name'],
                    link: value['link'],
                    address: value['address'],
                    long: value['lng'],
                    lat: value['lat']
                }
                allSexOffenders.push(sexOffenderNode);
            });
        });

        //Yelp
        $.getJSON(yelpSearchUrl, function (json) {
            $.each(json['yelpData'], function (key, value) {
                var category = "";
                switch (key) {
                    case 0:
                        category = "restaurant";
                        break;
                    case 1:
                        category = "nightlife";
                        break;
                    case 2:
                        category = "shopping";
                        break;
                    case 3:
                        category = "active";
                        break;
                }
                $.each(value['businesses'], function (key, value) {
                    yelpNode = {
                        name: value['name'],
                        category: category,
                        ratinglink: value['rating_img_url_small'],
                        address: value['location']['coordinate']['address'],
                        rating: value['rating'],
                        long: value['location']['coordinate']['longitude'],
                        lat: value['location']['coordinate']['latitude']
                 }
                 allYelps.push(yelpNode);
                });
            });
        });

        getMap(zillowNode, allSexOffenders, allYelps);
       



    });

    $(document).on('click', '#on', function () {
        if ($(this).is(":checked"))
        {
            getMap(zillowNode, allSexOffenders, allYelps);
        }
        else {
            tmpAllSexOffenders = new Array();
            getMap(zillowNode, tmpAllSexOffenders, allYelps);
        }
    });
    $(document).on('click', '#activelife', function () {
        if ($(this).is(":checked")) {
            getMap(zillowNode, allSexOffenders, allYelps);
        }
        else {
            getMap(zillowNode, allSexOffenders, allYelps);
        }
    });
    $(document).on('click', '#shopping', function () {
        if ($(this).is(":checked")) {
            getMap(zillowNode, allSexOffenders, allYelps);
        }
        else {
            getMap(zillowNode, allSexOffenders, allYelps);
        }
    });
    $(document).on('click', '#nightlife', function () {
        if ($(this).is(":checked")) {
            getMap(zillowNode, allSexOffenders, allYelps);
        }
        else {
            getMap(zillowNode, allSexOffenders, allYelps);
        }
    });
    $(document).on('click', '#restaurant', function () {
        if ($(this).is(":checked")) {
            getMap(zillowNode, allSexOffenders, allYelps);
        }
        else {
            getMap(zillowNode, allSexOffenders, allYelps);
        }
    });
});

