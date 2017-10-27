package DAO.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Node {
    private String nodeName;

    private String nodeValue;

    private List<Node> children = new ArrayList<>();

    private Node parent;

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    private Map<String , String> attributes = new HashMap<>();

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getNodeValue() {
        return nodeValue;
    }

    public void setNodeValue(String nodeValue) {
        this.nodeValue = nodeValue;
    }


    public List<Node> getChildren() {
        return children;
    }


    @Override
    public String toString() {
        return "Node{" +
                "nodeName='" + nodeName + '\'' +
                ", nodeValue='" + nodeValue + '\'' +
                ", attributes=" + attributes +
                ", children=" + children +
                '}';
    }
}
