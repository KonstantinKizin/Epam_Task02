package Main;


import DAO.parser.Document;
import Entity.Food;
import Entity.Menu;
import Entity.MenuBuilder;
import Services.MenuService;
import Services.ServiceException;
import Services.ServiceFactory;


public class Main {

    public static void main(String[] args) {

        ServiceFactory factory = ServiceFactory.getInstance();

        MenuService service = factory.getMenuService();

        MenuBuilder menuBuilder ;

        try {

            Document document = service.getDocument("task02.xml");

            menuBuilder = new MenuBuilder(document);

            Menu menu = menuBuilder.getMenu();

            Food food = service.findFoodById(menu , 5);

            PrintFood.print(food);


        } catch (ServiceException e) {
            e.printStackTrace();
        }


    }
}
