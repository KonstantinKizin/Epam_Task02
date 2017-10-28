package Services.Impl;

import DAO.DAOException;
import DAO.DocumentDAO;
import DAO.impl.DocumentDaoImpl;
import DAO.parser.Document;
import Entity.Food;
import Entity.Menu;
import Services.ServiceException;
import Services.MenuService;

import java.util.List;

public class MenuServiceImpl implements MenuService {

    private DocumentDAO documentDAO;

    public Document getDocument(String xmlName) throws ServiceException {

        documentDAO = new DocumentDaoImpl();

        Document document = null;
        try {
            document = documentDAO.getDocument(xmlName);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return document;
    }

    @Override
    public Food findFoodById(Menu menu, int id) {

        Food food = null;
        for(Food tmp : menu.getFoods()){
            if(tmp.getId() == id){
                food = tmp;
                break;
            }
        }
        return food;
    }

    @Override
    public List<Food> getFoods(Menu menu) {
        return menu.getFoods();
    }

    @Override
    public Food findFoodByName(Menu menu, String foodName) {

        Food food = null;
        for(Food tmp : menu.getFoods()){
            if(tmp.getName().equalsIgnoreCase(foodName)){
                food = tmp;
                break;
            }
        }

        return food;
    }




}
