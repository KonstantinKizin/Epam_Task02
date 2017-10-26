package DAO;

import DAO.parser.Document;

public interface DocumentDAO {
    Document getDocument(String xmlName);
}
