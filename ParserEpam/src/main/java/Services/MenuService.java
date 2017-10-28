package Services;

import DAO.parser.Document;
import Entity.Food;
import Entity.Menu;

import java.util.List;

public interface MenuService {

    Document getDocument(String xmlName) throws ServiceException;

    Food findFoodById(Menu menu , int id);

    List<Food> getFoods(Menu menu);

    Food findFoodByName(Menu menu , String foodName);




}
