package iEat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import iEat.repository.DishesRepository;
import iEat.model.Dish;
import iEat.vo.DishListVO;

@Service
@Transactional
public class DishesService {

    @Autowired
    private DishesRepository dishesRepository;

    @Transactional(readOnly = true)
    public DishListVO findAll(int page, int maxResults) {
        Page<Dish> result = executeQueryFindAll(page, maxResults);

        if(shouldExecuteSameQueryInLastPage(page, result)){
            int lastPage = result.getTotalPages() - 1;
            result = executeQueryFindAll(lastPage, maxResults);
        }

        return buildResult(result);
    }

    public void save(Dish dish) {
        dishesRepository.save(dish);
    }

    @Secured("ROLE_ADMIN")
    public void delete(int dishId) {
        dishesRepository.delete(dishId);
    }

    @Transactional(readOnly = true)
    public DishListVO findByNameLike(int page, int maxResults, String name) {
        Page<Dish> result = executeQueryFindByName(page, maxResults, name);

        if(shouldExecuteSameQueryInLastPage(page, result)){
            int lastPage = result.getTotalPages() - 1;
            result = executeQueryFindByName(lastPage, maxResults, name);
        }

        return buildResult(result);
    }

    private boolean shouldExecuteSameQueryInLastPage(int page, Page<Dish> result) {
        return isUserAfterOrOnLastPage(page, result) && hasDataInDataBase(result);
    }

    private Page<Dish> executeQueryFindAll(int page, int maxResults) {
        final PageRequest pageRequest = new PageRequest(page, maxResults, sortByNameASC());

        return dishesRepository.findAll(pageRequest);
    }

    private Sort sortByNameASC() {
        return new Sort(Sort.Direction.ASC, "name");
    }

    private DishListVO buildResult(Page<Dish> result) {
        return new DishListVO(result.getTotalPages(), result.getTotalElements(), result.getContent());
    }

    private Page<Dish> executeQueryFindByName(int page, int maxResults, String name) {
        final PageRequest pageRequest = new PageRequest(page, maxResults, sortByNameASC());

        return dishesRepository.findByNameLike(pageRequest, "%" + name + "%");
    }

    private boolean isUserAfterOrOnLastPage(int page, Page<Dish> result) {
        return page >= result.getTotalPages() - 1;
    }

    private boolean hasDataInDataBase(Page<Dish> result) {
        return result.getTotalElements() > 0;
    }
}