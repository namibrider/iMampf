package iEat.vo;

import iEat.model.Dish;

import java.util.List;

public class DishListVO {
    private int pagesCount;
    private long totalDishes;

    private String actionMessage;
    private String searchMessage;

    private List<Dish> dishes;

    public DishListVO() {
    }

    public DishListVO(int pages, long totalDishes, List<Dish> dishes) {
        this.pagesCount = pages;
        this.dishes = dishes;
        this.totalDishes = totalDishes;
    }

    public int getPagesCount() {
        return pagesCount;
    }

    public void setPagesCount(int pagesCount) {
        this.pagesCount = pagesCount;
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
    }

    public long getTotalDishes() {
        return totalDishes;
    }

    public void setTotalDishes(long totalDishes) {
        this.totalDishes = totalDishes;
    }

    public String getActionMessage() {
        return actionMessage;
    }

    public void setActionMessage(String actionMessage) {
        this.actionMessage = actionMessage;
    }

    public String getSearchMessage() {
        return searchMessage;
    }

    public void setSearchMessage(String searchMessage) {
        this.searchMessage = searchMessage;
    }
}