package Services;

import DAO.DAOException;
import DAO.DocumentDAO;
import DAO.impl.DocumentDaoImpl;
import DAO.parser.Document;

public class ServiceImpl implements Service {

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
}
