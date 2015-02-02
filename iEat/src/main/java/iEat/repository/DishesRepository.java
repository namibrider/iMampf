package iEat.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import iEat.model.Dish;

public interface DishesRepository extends PagingAndSortingRepository<Dish, Integer> {
    Page<Dish> findByNameLike(Pageable pageable, String name);
    
    Page<Dish> findByCookLike(Pageable pageable, String cook);
}