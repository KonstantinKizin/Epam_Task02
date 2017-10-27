package Entity;

import java.util.ArrayList;
import java.util.List;

public class Menu {

    private List<Food> foods = new ArrayList<>();

    public List<Food> getFoods() {
        return foods;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Menu menu = (Menu) o;

        return foods != null ? foods.equals(menu.foods) : menu.foods == null;
    }

    @Override
    public int hashCode() {
        return foods != null ? foods.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "foods=" + foods +
                '}';
    }
}
