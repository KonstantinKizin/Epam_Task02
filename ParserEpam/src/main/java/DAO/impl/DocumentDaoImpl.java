package DAO.impl;

import DAO.DAOException;
import DAO.DocumentDAO;
import DAO.parser.Document;
import DAO.parser.FileParser;


public class DocumentDaoImpl implements DocumentDAO {

    private FileParser fileParser;

    private Document document;

    public Document getDocument(String xmlName) throws DAOException {

        fileParser = new FileParser(xmlName);

        document = fileParser.getDocument();

        return document;
    }
}
