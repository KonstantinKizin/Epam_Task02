package Main;

import DAO.DAOException;
import DAO.parser.FileParser;
import DAO.parser.Node;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        FileParser fileParser = new FileParser("task02.xml");

        try {
            List<Node> nodeList = fileParser.getNodeList();

            for(Node node : nodeList){
                System.out.println(node);
            }
        } catch (DAOException e) {
            e.printStackTrace();
        }

    }
}
