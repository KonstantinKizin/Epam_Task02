package Entity;

import DAO.parser.Document;
import DAO.parser.Node;

import java.util.List;

public class MenuBuilder {

    private final Document document;

    public MenuBuilder(Document document){
        this.document = document;
    }

    private Food constructFood(Node node){

        Food food = new Food();
        int foodId = Integer.parseInt(node.getAttributes().get("id"));
        food.setId(foodId);
        List<Node> nodeList = node.getChildren();
        for(Node tmp : nodeList){
            if(tmp.getNodeName().equalsIgnoreCase("name")){
                food.setName(tmp.getNodeValue());
            }else if(tmp.getNodeName().equalsIgnoreCase("price")){
                food.setPrice(tmp.getNodeValue());
            }else if(tmp.getNodeName().equalsIgnoreCase("description")){
                food.setDescription(tmp.getNodeValue());
            }else if(tmp.getNodeName().equalsIgnoreCase("calories")){
                food.setCalories(tmp.getNodeValue());
            }
        }
        return food;
    }


    public Menu getMenu(){

        Menu menu = new Menu();

        List<Node> nodeList = this.document.getRootChilds();

        for (Node tmp : nodeList){
            if(tmp.getNodeName().equalsIgnoreCase("food")){
                Food food = this.constructFood(tmp);
                menu.getFoods().add(food);
            }
        }
        return menu;
    }



}
