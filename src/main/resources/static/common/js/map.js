// 네임 스페이스 만들기 - commonLib.xxx 이렇게 흔한 변수 이름들을 사용하기 위해
// var 사용: 덮어 씌울 수 있기 때문에
var commonLib = commonLib ?? {}
commonLib.mapLib = {
    // 카카오 맵 SDK를 동적 로딩
    init() {
        const headEl = document.head;
        // kakao-map-sdk라는 id가 head에 존재하지 않다면
        // -> 의미: 카카오 맵 SDK 자바스크립트가 추가된 상태가 아니라면
        if (!document.getElementById("kakao-map-sdk")) {
            const scriptEl = document.createElement("script");
            scriptEl.src = "//dapi.kakao.com/v2/maps/sdk.js?appkey=c353cbff16c035545fd3661e0c9019ed";
            scriptEl.id = "kakao-map-sdk";

            headEl.prepend(scriptEl);

            scriptEl.onload = () => {
                if (typeof callback === 'function') {
                    callback();
                }
            };
        }
    },

    /**
    * 지도 출력
    *
    * @param el 지도를 출력할 Canvas document 객체
    * @param items 지도 데이터
    * @param center 지도 출력 시 처음에 위치할 가운데 좌표
    * @param width 지도의 너비
    * @param height 지도의 높이
    */
    load(el, items, center, width, height) {
        // 너비 또는 높이가 설정되어 있는 경우
        if (width) {
            el.style.width = width;
        }
        if (height) {
            el.style.height = height;
        }


        if (center) {
            this.showMap(el, items, center);
        }
        else {  // 가운데 좌표가 없으면, 현재 위치 좌표를 기준으로 가져 옴
            navigator.geolocation.getCurrentPosition((pos) => {
                // pos.coords 객체 안에서 latitude, longitude 변수를 꺼내 lat, lon에 할당
                const { latitude: lat, longitude: lon } = pos.coords;
                this.showMap(el, items, {lat, lon});
            });
        }
    },
    showMap(el, items, center) {
        const mapOptions = {
            center: new kakao.maps.LatLng(center.lat, center.lon),
            level: 3,
        };

        // 지도 객체
        const map = new kakao.maps.Map(el, mapOptions);

        // 마커 표시
        if (!items || items.length === 0) console.log("데이터가 없습니다.");
        items.forEach(item => {
            const marker = new kakao.maps.Marker({
                position: new kakao.maps.LatLng(lat, lon),
            });

            marker.setMap(map);

            // 인포 윈도우 표시
            const iwContent = `<div class='restaurant-name'>${name}</div>
                <div class='restaurant-address'>${roadAddress}</div>
            `;

            const iwPosition = new kakao.maps.LatLng(lat, lon);

            const infoWindow = new kakao.maps.InfoWindow({
                position: iwPosition,
                content: iwContent,
                removable: true,
            });

            infoWindow.open(map, marker);
        });
    }
};

window.addEventListener("COMContentLoaded", function() {
    commonLib.mapLib.init();  // commonLib 아래에 mapLib 추가
});