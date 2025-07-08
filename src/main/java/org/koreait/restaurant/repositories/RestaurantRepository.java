package org.koreait.restaurant.repositories;

import org.koreait.restaurant.entities.Restaurant;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface RestaurantRepository extends ListCrudRepository<Restaurant, Long> {
    List<Restaurant> findByNameContainingIgnoreCase(String keyword);
}
