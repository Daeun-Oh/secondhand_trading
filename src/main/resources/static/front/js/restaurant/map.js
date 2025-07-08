var commonLib = commonLib ?? {}
commonLib.mapLib = {
    init(callback) {
        console.log("init");

        const headEl = document.head;
        if (!document.getElementById("kakao-map-sdk")) {
            console.log("kakao-map-sdk");
            const scriptEl = document.createElement("script");
            scriptEl.src = "https://dapi.kakao.com/v2/maps/sdk.js?appkey=45d23dddc5e4fe4d2e1383b90afb2d18&libraries=services&autoload=false"; // 라이브러리 필요 시 추가
            scriptEl.id = "kakao-map-sdk";
            scriptEl.onload = () => {
                const waitUntilReady = () => {
                    try {
                        // kakao가 아예 없으면 에러 발생
                        if (kakao && kakao.maps && kakao.maps.load) {
                            kakao.maps.load(() => {
                                console.log("kakao.maps.load 완료");
                                if (typeof callback === "function") callback();
                            });
                        } else {
                            throw new Error("kakao.maps.load 아직 없음");
                        }
                    } catch (e) {
                        console.log("kakao 객체 대기 중...", e.message);
                        setTimeout(waitUntilReady, 200);
                    }
                };

                waitUntilReady();
            };

            console.log(scriptEl);

            headEl.prepend(scriptEl);

        } else {

            if (kakao.maps) {
                console.log("kakao.maps 있음")
            }
            if (kakao.maps.load) {
                console.log("kakao.maps.load 있음")
            }
            if (kakao === "undefined") {
                console.log("kakao 있음")
            }

            if (typeof kakao !== "undefined" && kakao.maps && kakao.maps.load) {
                kakao.maps.load(() => {
                    console.log("kakao.maps.load (이미 로드된 경우)");
                    if (typeof callback === 'function') callback();
                });
            } else {
                console.warn("kakao 객체는 있지만 아직 maps.load를 지원하지 않음");
            }
        }
    },

    load(el, items, center, width, height) {
        console.log("load");
        if (width) el.style.width = width;
        if (height) el.style.height = height;

        console.log("width", el.style.width, "height", el.style.height);

        if (center) {
            this.showMap(el, items, center);
        } else {
            navigator.geolocation.getCurrentPosition(pos => {
                const { latitude: lat, longitude: lon } = pos.coords;
                this.showMap(el, items, {lat, lon});
            });
        }
    },

    showMap(el, items, center, retryCount = 0) {
        const MAX_RETRIES = 20;  // 최대 시도 횟수

//        console("kakao", kakao, "kakao.maps", kakao.maps)
        if (typeof kakao !== "undefined" && kakao.maps) {
            const mapOptions = {
                center: new kakao.maps.LatLng(center.lat, center.lon),
                level: 2,
            };
            const map = new kakao.maps.Map(el, mapOptions);

            if (!items || items.length === 0) {
                console.log("데이터가 없습니다.");
                return;
            }

            items.forEach(({lat, lon, name, roadAddress}) => {
                const marker = new kakao.maps.Marker({
                    position: new kakao.maps.LatLng(lat, lon),
                });
                marker.setMap(map);

                const iwContent = `<div class='restaurant-name'>${name}</div><div class='restaurant-address'>${roadAddress}</div>`;
                const infoWindow = new kakao.maps.InfoWindow({
                    content: iwContent,
                    removable: true,
                });
                infoWindow.open(map, marker);
            });
        } else {
            console.warn("kakao maps 객체가 아직 준비되지 않았습니다. 재시도:", retryCount + 1);
            if (retryCount < MAX_RETRIES) {
                setTimeout(() => {
                    this.showMap(el, items, center, retryCount + 1);
                }, 1000); // 1초 후 재시도
            } else {
                console.error("kakao maps 로딩 실패: 재시도 초과");
            }
        }
    }
};

window.addEventListener("DOMContentLoaded", function() {
    const el = document.getElementById("map");

    commonLib.mapLib.init(() => {
        // SDK 로드 후 위치 조회 및 지도 출력 예시
        navigator.geolocation.getCurrentPosition(pos => {
            const { latitude: lat, longitude: lon } = pos.coords;

            // 예: 빈 배열로 표시 (나중에 실제 데이터 fetch로 대체)
            const items = [];

            commonLib.mapLib.load(el, items, {lat, lon}, "100%", "350px");
        });
    });
});
