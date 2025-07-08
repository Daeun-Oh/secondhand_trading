window.addEventListener("DOMContentLoaded", function() {
    const el = document.getElementById("map");
    const searchDataEl = document.getElementById("search-data");

    if (searchDataEl) {
        const searchItems = JSON.parse(searchDataEl.textContent);

        if (searchItems.length > 0) {
            const center = { lat: searchItems[0].lat, lon: searchItems[0].lon };
            commonLib.mapLib.load(el, searchItems, center, "100%", "500px");
            return; // 검색 결과 있으면 여기서 끝
        }
    }

    // 위치 기반으로 검색
    commonLib.mapLib.init(() => {
        navigator.geolocation.getCurrentPosition((pos) => {
            const { latitude: lat, longitude: lon } = pos.coords;
            const center = { lat, lon };

            fetch(`/restaurant/search?lat=${lat}&lon=${lon}&cnt=50`)
                .then((res) => res.json())
                .then(items => {
                    commonLib.mapLib.load(el, items, center, "100%", "500px");
                })
                .catch(err => {
                    console.error("식당 정보 불러오기 실패:", err);
                });
        });
    });
});