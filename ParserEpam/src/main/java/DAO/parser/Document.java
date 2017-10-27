package DAO.parser;

import java.util.List;
import java.util.Map;

public class Document {

    private Node root;

    public Node getRoot() {
        return root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }

    public String getRootElementName(){
        if(this.root != null){
            return root.getNodeName();
        }else {
            return null;
        }
    }

    public List<Node> getRootChilds(){
        if(root != null){
            return root.getChildren();
        }else {
            return null;
        }
    }

    public Map<String , String> getRootAttributes(){
        if(root != null){
            return root.getAttributes();
        }else {
            return null;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Document document = (Document) o;

        return root != null ? root.equals(document.root) : document.root == null;
    }

    @Override
    public int hashCode() {
        return root != null ? root.hashCode() : 0;
    }


    @Override
    public String toString() {
        return "Document{" +
                "root=" + root +
                '}';
    }
}
