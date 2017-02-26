<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>JSP Page</title>
</head>
<body ng-app="app" ng-controller="Ctrl as ctrl">
<h1>API Up and Running!!!</h1>
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.6/angular.min.js"></script>
<script
        src="https://code.jquery.com/jquery-3.1.1.min.js"
        integrity="sha256-hVVnYaiADRTO2PzUGmuLJr8BLUSjGIZsDYGmIJLv2b8="
        crossorigin="anonymous"></script>
<audio controls>
    <source id="audiosrc" src="" type="audio/mp3">
</audio>

<script type="text/javascript">
    angular.module("app", []);

    angular.module("app").controller("Ctrl", Ctrl);

    Ctrl.$inject = ['$http'];

    function Ctrl($http) {
        var vm = this;
        vm.data = '';

        $http.get("http://localhost:8080/music-api/v1/audionew", {responseType: 'arraybuffer'})
            .then(
                function (data) {
                    var binary = convertDataURIToBinary(data);
                    var file = new Blob([binary], {type: 'audio/ogg'});
                    var fileURL = URL.createObjectURL(file);

                    $("#audiosrc").attr("src", fileURL);
                    $("#audiosrc")[0].pause();
                    $("#audiosrc")[0].load();//suspends and restores all audio element
                    $("#audiosrc")[0].oncanplaythrough =  $("#audiosrc")[0].play();
//
//                    console.log("FILE LENGTH :: " + data.length);
//                    var audioSrc = document.getElementById("audiosrc");
//                    audioSrc.src = fileURL;
                },
                function (err) {
                    console.log("ERROR");
                }
            );

        function convertDataURIToBinary(dataURI) {
            var BASE64_MARKER = ';base64,';
            var base64Index = dataURI.indexOf(BASE64_MARKER) + BASE64_MARKER.length;
            var base64 = dataURI.substring(base64Index);
            var raw = window.atob(base64);
            var rawLength = raw.length;
            var array = new Uint8Array(new ArrayBuffer(rawLength));

            for (i = 0; i < rawLength; i++) {
                array[i] = raw.charCodeAt(i);
            }
            return array;
        }
    }

</script>
</body>
</html>
