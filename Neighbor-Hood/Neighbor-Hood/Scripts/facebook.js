

function getData() {
    FB.getLoginStatus(function (response) {
        var accessToken = response.authResponse.accessToken;
        $.post('@Url.Action("UserInfo", "Home")',
        { 'accessToken': accessToken },
        function (data, statusText) {
            var name = data.name;
            var id = data.id;
        });
    });
}

<h4>GoogleMap</h4>

<script type="text/javascript">
    var beaches = [['house','Bondi Beach', -33.890542, 151.274856, 4],
  ['resturant','Coogee Beach', -33.923036, 151.259052, 5],
  ['shop','Cronulla Beach', -34.028249, 151.157507, 3],
  ['bar','Manly Beach', -33.80010128657071, 151.28747820854187, 2],
  ['club','Maroubra Beach', -33.950198, 151.259302, 1]
    ];
function getMap() {

    var myLatlng = new google.maps.LatLng(-33.890542, 151.274856);
    var mapOptions = {
        center: myLatlng,
        zoom: 12,
        scrollwheel: false,
        mapTypeId: google.maps.MapTypeId.ROADMAP
    };
    var map = new google.maps.Map(document.getElementById("map_canvas"), mapOptions);

    setMarkers(map, beaches);
}

function setMarkers(map, locations) {
        
    for (var i = 0; i < locations.length; i++) {
        var beach = locations[i];
        var type = beach[0];
        // var info = beach[1] +"  "+ 'http://www.w3schools.com/googleAPI/google_maps_basic.asp';
        var bb="http://www.w3schools.com/googleAPI/google_maps_basic.asp";
        var info = '<!DOCTYPE html>'+
            '<div  style="overflow:hidden;line-height:3.35;min-width:200px;">' + beach[1] +
      
  '<div id="bodyContent"  >' +
  '<details>'+
'<summary>Click Here</summary>'+
'<p> - by Refsnes Data. All Rights Reserved.</p>'+
'<p>' + bb + '</p>' +
'<p>Attribution:Place <a href=' + bb + '>' +
  ' For More info</a> ' +
'</p>' +//insert more info here by passing the variable
'</details>'+
  '</div>' +
  '</div>'+
 '</html>';
        var myLatLng = new google.maps.LatLng(beach[2], beach[3]);
        var iconBase = 'https://maps.google.com/mapfiles/kml/shapes/';
        var infowindow = new google.maps.InfoWindow();

        //  var service = new google.maps.places.PlacesService(map);
        if(type=='house'){
            //  var myLatLng = new google.maps.LatLng(beach[2], beach[3]);
            
            var marker = new google.maps.Marker({
                position: myLatLng,
                map: map,
                icon: iconBase + 'homegardenbusiness_maps.png',
                title: beach[1],
                zIndex: beach[4],
                animation:google.maps.Animation.BOUNCE

            });
            //var infowindow = new google.maps.InfoWindow();
            google.maps.event.addListener(marker, 'click', function () {
                infowindow.setContent(place.name);
                infowindow.open(map,this);
            });
        }
        else if (type == 'resturant') {
            var icon = {  //code to rsize google map icon
                url: iconBase + 'dining_maps.png', // url
                scaledSize: new google.maps.Size(25, 25), //scaled size
                origin: new google.maps.Point(0, 0), //origin
                anchor: new google.maps.Point(0, 0) //anchor
            };
              

            var marker = new google.maps.Marker({
                position: myLatLng,
                map: map,
                icon: icon, //iconBase + 'dining_maps.png',
                title: beach[1],
                zIndex: beach[4]
            });
            var infowindowopts = {
                minWidth: 5000,
                minHeight: 3000,
                content: info,
            };
            infowindow.setOptions(infowindowopts);
            //  infowindow.setContent(info);
            infowindow.open(map, marker);
            google.maps.event.addListener(marker, 'click', function () {
                infowindow.setContent(info);
                infowindow.open(map,this);
            });
        }
        else if (type == 'bar') {
            var marker = new google.maps.Marker({
                position: myLatLng,
                map: map,
                icon: iconBase + 'bars_maps.png',
                title: beach[1],
                zIndex: beach[4]
            });
            google.maps.event.addListener(marker, 'click', function () {
                infowindow.setContent(info);
                infowindow.open(map, this);
            });
        }
        else if (type == 'club') {
            var marker = new google.maps.Marker({
                position: myLatLng,
                map: map,
                icon: iconBase + 'capital_small_highlight_maps.png',
                title: beach[1],
                zIndex: beach[4]
            });
            google.maps.event.addListener(marker, 'click', function () {
                infowindow.setContent(info);
                infowindow.open(map, this);
            });
        }
        else if (type == 'shop') {
            var marker = new google.maps.Marker({
                position: myLatLng,
                map: map,
                icon: iconBase + 'grocery_maps.png',
                title: beach[1],
                zIndex: beach[4]
            });
            google.maps.event.addListener(marker, 'click', function () {
                infowindow.setContent(info);
                infowindow.open(map, this);
            });
        }
        else if (type == 'bar') {
            var marker = new google.maps.Marker({
                position: myLatLng,
                map: map,
                // icon:'pinball.jpeg',
                title: beach[1],
                zIndex: beach[4]
            });
            google.maps.event.addListener(marker, 'click', function () {
                infowindow.setContent(info);
                infowindow.open(map, this);
            });
        }
    }

}
//google.maps.event.addDomListener(window, 'load', initialize);
</script>
<input type="submit" name="Go" value="Get Map" onclick="getMap()" />
<div id="map_canvas" style="width:900px; height:500px"></div>
<div class="glyphicon-map-marker" style="width: 900px;"><div class="MarkerContext">Text</div></div>