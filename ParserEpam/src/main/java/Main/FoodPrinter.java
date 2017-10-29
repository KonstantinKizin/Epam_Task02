package Main;

import Entity.Food;

public class FoodPrinter {

    public static void print(Food food){

        if(food != null){
        System.out.println("Name : "+food.getName()+"\n"
        +"price : "+food.getPrice()+"\n"
        +"calories : "+food.getCalories()+"\n"
        +"description : "+"'"+food.getDescription()+"'");
    }else{
            System.out.println(food);
        }
    }
}
