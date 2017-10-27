package Main;


import DAO.parser.Document;
import DAO.parser.Node;
import Entity.Food;
import Services.Service;
import Services.ServiceException;
import Services.ServiceFactory;

import java.util.List;


public class Main {

    public static void main(String[] args) {

        ServiceFactory factory = ServiceFactory.getInstance();

        Service service = factory.getDocumentService();

        MenuBuilder menuBuilder ;

        try {

            Document document = service.getDocument("task02.xml");

            menuBuilder = new MenuBuilder(document);

            Food food = null;

            List<Node> nodeList = document.getRootChilds();

            for(Node node : nodeList){
                if(node.getNodeName().equalsIgnoreCase("food")){

                    food = menuBuilder.constructFood(node);
                    System.out.println(food);

                }
            }




        } catch (ServiceException e) {
            e.printStackTrace();
        }


    }
}
