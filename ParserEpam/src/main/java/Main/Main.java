package Main;


import DAO.parser.Document;
import DAO.parser.Node;
import Services.Service;
import Services.ServiceException;
import Services.ServiceFactory;

import java.util.List;


public class Main {

    public static void main(String[] args) {

        ServiceFactory factory = ServiceFactory.getInstance();

        Service service = factory.getDocumentService();

        try {

            Document document = service.getDocument("task02.xml");

            System.out.println(document);

        } catch (ServiceException e) {
            e.printStackTrace();
        }


    }
}
