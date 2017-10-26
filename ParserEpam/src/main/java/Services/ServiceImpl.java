package Services;

import DAO.DocumentDAO;
import DAO.impl.DocumentDaoImpl;
import Entity.Document;

public class ServiceImpl implements Service {

    private DocumentDAO documentDAO;

    public Document getDocument(String xmlName) {

        documentDAO = new DocumentDaoImpl();

        Document document = documentDAO.getDocument(xmlName);

        return document;
    }
}
