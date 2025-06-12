package org.koreait.restaurant.services;

import org.junit.jupiter.api.Test;
import org.koreait.restaurant.entities.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class RestaurantInfoServiceTest {

    @Autowired
    private RestaurantInfoService infoService;

    @Test
    void test() {
        List<Restaurant> items = infoService.getNearest(37.524703211998, 126.6268233148004, 20);
        items.forEach(System.out::println);
    }
}
