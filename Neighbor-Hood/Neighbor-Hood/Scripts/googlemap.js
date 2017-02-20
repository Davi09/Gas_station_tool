function getMap(zillow, sexOffenders, Yelp) {
    var zillowLong = "";
    var zillowLat = "";
    $.each(zillow, function (key, value) {
        if (key == 3)
            zillowLong = value;
        if (key == 4)
            zillowLat = value;
    });

    var myLatlng = new google.maps.LatLng(zillowLong, zillowLat);
    var mapOptions = {
        center: myLatlng,
        zoom: 13,
        scrollwheel: false,
        mapTypeId: google.maps.MapTypeId.ROADMAP
    };
    var map = new google.maps.Map(document.getElementById("map_canvas"), mapOptions);

    setMarkers(map, zillowLong, zillowLat, sexOffenders, Yelp);
}
function differentContentMarkers(map, location, i) {
    var iconBase = 'https://maps.google.com/mapfiles/kml/shapes/';
    var SOinfo = '<!DOCTYPE html>' +
           '<div  style="overflow:hidden;line-height:3.35;min-width:200px;">'
           + location['name'] +
            '<div id="bodyContent"  >' +
            'address: ' + location['address'] + '</br>' +
            'link: ' + location['link'] +
        '</div>' +
     '</div>' +
        '</html>';
    var myLatLng = new google.maps.LatLng(location['lat'], location['long']);
    var infowindow = new google.maps.InfoWindow();
    var marker = new google.maps.Marker({
        position: myLatLng,
        map: map,
        icon: iconBase + 'man.png',
        title: locationc[1],
        zIndex: i + 1
    });

    google.maps.event.addListener(marker, 'click', function () {
        infowindow.setContent(SOinfo);
        infowindow.open(map, this);
    });
}
function setMarkers(map, zillowLong, zillowLat, sexOffenders, Yelp) {
    var selected = [];
    $("#yelpCheck :checkbox").each(function () {
        if ($(this).is(":checked")) {
            selected.push($(this).attr('name'));
        }
    });

    var info = '<!DOCTYPE html>' +
           '<div  style="overflow:hidden;line-height:3.35;min-width:200px;">'
           + "Home" +
            '<div id="bodyContent"  >' +
    '</div>' +
    '</div>' +
    '</html>';

    var iconBase = 'https://maps.google.com/mapfiles/kml/shapes/';
    var myLatLng = new google.maps.LatLng(zillowLong, zillowLat);
    var infowindow = new google.maps.InfoWindow();
    var marker = new google.maps.Marker({
        position: myLatLng,
        map: map,
        icon: iconBase + 'homegardenbusiness_maps.png',
        title: "",
        zIndex: 1,
        animation: google.maps.Animation.BOUNCE

    });
    infowindow.setContent(info);
    infowindow.open(map, marker);
    google.maps.event.addListener(marker, 'click', function () {
        infowindow.setContent(info);
        infowindow.open(map, this);
    });
    var SOInfoArray = new Array();
    for (var i = 0; i < sexOffenders.length; i++) {
        var loc = sexOffenders[i];

        var name = loc['name'];
        var SOinfo = '<!DOCTYPE html>' +
           '<div  style="overflow:hidden;line-height:3.35;min-width:200px;">'
           + name +
            '<div id="bodyContent"  >' +
            'address: ' + loc['address'] + '</br>' +
            'link: ' + loc['link'] +
        '</div>' +
     '</div>' +
        '</html>';
        SOInfoArray.push(SOinfo);
        var myLatLng = new google.maps.LatLng(loc['lat'], loc['long']);
        var infowindow = new google.maps.InfoWindow();

        var marker = new google.maps.Marker({
            position: myLatLng,
            map: map,
            icon: iconBase + 'man.png',
            title: loc[1],
            zIndex: loc[4]
        });
        
        //infowindow.setMarkers(marker);
        //infowindow.setContent(SOinfo);
        google.maps.event.addListener(marker, 'click', function () {
            infowindow.setContent(this.title);
            infowindow.open(map, this);
        });
        //differentContentMarkers(map,sexOffenders[i], i);
    }
    for (var i = 0; i < Yelp.length; i++) {

        var location = Yelp[i];
        var type = location['category'];
        var name = location['name'];
        var info = location['ratinglink'];
        var address = location['address'];

        var myLatLng = new google.maps.LatLng(location['lat'], location['long']);
        var infowindow = new google.maps.InfoWindow();
        //  var service = new google.maps.places.PlacesService(map);

        if (type == 'nightlife' && $.inArray('nightlife', selected) != -1) {
            //  var myLatLng = new google.maps.LatLng(beach[2], beach[3]);

            var marker = new google.maps.Marker({
                position: myLatLng,
                map: map,
                icon: iconBase + 'bars.png',
                title: location[1],
                zIndex: i + 1,
            });
            google.maps.event.addListener(marker, 'click', function () {
                infowindow.setContent(info);
                infowindow.open(map, this);
            });
        }
        else if (type == 'restaurant' && $.inArray('restaurant', selected) != -1) {
            var marker = new google.maps.Marker({
                position: myLatLng,
                map: map,
                icon: iconBase + 'dining_maps.png',
                title: location[1],
                zIndex: i + 1
            });
            google.maps.event.addListener(marker, 'click', function () {
                infowindow.setContent(info);
                infowindow.open(map);
            });
        }
        else if (type == 'shopping' && $.inArray('shopping', selected) != -1) {
            var marker = new google.maps.Marker({
                position: myLatLng,
                map: map,
                icon: iconBase + 'grocery_maps.png',
                title: location[1],
                zIndex: i + 1
            });
            google.maps.event.addListener(marker, 'click', function () {
                infowindow.setContent(info);
                infowindow.open(map, this);
            });
        }
        else if (type == 'active' && $.inArray('activelife', selected) != -1) {
            var marker = new google.maps.Marker({
                position: myLatLng,
                map: map,
                icon: iconBase + 'play.png',
                title: location[1],
                zIndex: i + 1
            });
            google.maps.event.addListener(marker, 'click', function () {
                infowindow.setContent(info);
                infowindow.open(map, this);
            });
        }
    }

}

//});