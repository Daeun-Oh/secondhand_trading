window.addEventListener("DOMContentLoaded", function() {
    const el = document.getElementById("map");

    // 현재 위치의 식당 정보 조회
    commonLib.mapLib.init(() => {
        navigator.geolocation.getCurrentPosition((pos) => {
            const { latitude: lat, longitude: lon } = pos.coords;
            const center = { lat, lon };

            fetch(`/restaurant/search?lat=${lat}&lon=${lon}&cnt=50`)
                .then((res) => res.json())
                .then(items => {
                    commonLib.mapLib.load(el, items, center, "100%", "350px");
                })
                .catch(err => {
                    console.error("식당 정보 불러오기 실패:", err);
                });
        });
    });
});