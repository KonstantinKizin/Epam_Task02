package DAO.parser;

import DAO.DAOException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileParser {

    private final static String ATTRIBUTE = " [a-z A-Z0-9]*=\"[a-zA-Z0-9]*\"";
    private final static Pattern ATTRIBUTE_PATTERN = Pattern.compile(ATTRIBUTE);
    private final static String MAP_KEY = "[a-z A-Z0-9]*";
    private final static String MAP_VALUE = "\"[a-zA-Z0-9]*\"";
    private final static Pattern MAP_KEY_PATTERN = Pattern.compile(MAP_KEY);
    private final static Pattern MAP_VALUE_PATTENR = Pattern.compile(MAP_VALUE);
    private String xmlName;
    private final XmlLoader loader ;
    private Node root;

    public FileParser(String xmlName){
        this.xmlName = xmlName;
        loader = new XmlLoader(xmlName);
    }


    public Document getDocument() throws DAOException {

        Document document = new Document();
        List<Node> list = null;
        Deque<String> deque = this.getElementsStack();
        String firstTag = deque.getFirst();
        this.root = this.constructNode(firstTag);
        deque.removeFirst();
        list = this.createChildesNodes(deque);
        this.root.getChildren().addAll(list);
        document.setRoot(this.root);
        return document;
    }

    private List<Node> createChildesNodes(Deque<String> deque){

        List<Node> nodes = new ArrayList<>();
        while (deque.size() != 1){
            List<String> units = this.makeHierarhyUnit(deque);
            Deque<String > unitsStack = new ArrayDeque<>(units);
            Node root = new Node();
            String tag  = unitsStack.getFirst();
            unitsStack.removeFirst();
            if(hasAttr(tag)){
                List<String> attrs = this.getAttrList(tag);
                Map<String , String > attrsMap = this.constructAttrMap(attrs);
                root.getAttributes().putAll(attrsMap);
            }
            String rootTag = this.getTagName(tag);
            root.setNodeName(rootTag);
            buildNodesTree(unitsStack , root);
            nodes.add(root);

        }

        return nodes;
    }





    private Deque<String> getElementsStack() throws DAOException {

        Deque<String> deque = new ArrayDeque<>();
        try {
            StringBuilder sb = this.loader.getLoadedText();
            Scanner sc = new Scanner(new String(sb));
            while (sc.hasNextLine()) {
                String element = sc.nextLine();
                if (element.startsWith("<?")) {
                    continue;
                } else {
                    deque.add(element.trim());
                }
            }

        }catch (FileNotFoundException e) {
            throw new DAOException(e);
        }catch (IOException e){
            throw new DAOException(e);
        }
        return deque;
    }






    private boolean cheakForOneLine(String oneLine){
        if(oneLine.contains("</") && (oneLine.charAt(1) != '/')){
            return true;
        }else {
            return false;
        }
    }



    private String[] separateOneLine(String oneLine){
        String[] separated = new String[2];
        int charPoint = oneLine.indexOf('>');
        int secondCharPoint = oneLine.indexOf("</");
        separated[0] = oneLine.substring(1,charPoint);
        separated[1] = oneLine.substring(charPoint + 1 , secondCharPoint );
        return separated;
    }




    private String getTag(String line){
        String tagName = null;
        if(line.contains(" ")){
            int indexOfSpace = line.indexOf(" ");
            tagName = line.substring(1 , indexOfSpace)+">";
        }else {
            tagName = line.substring(1, line.length());
        }
        return tagName;
    }




    private List<String> makeHierarhyUnit(Deque<String> xml){
        ArrayList<String> hierarhy = new ArrayList<>();
        String root = null;
        for(String s : xml) {
            if (hierarhy.isEmpty()) {
                root = this.getTag(s);
            }
            if(root != null){
                if(cheakForOneLine(s)){
                    hierarhy.add(s.trim());
                    xml.removeFirst();
                }else if(getTag(s).equalsIgnoreCase("/"+root)){
                    xml.removeFirst();
                    break;
                }else if(getTag(s).startsWith("/")){
                    xml.removeFirst();
                    continue;
                }else if(s.startsWith("<") && !s.startsWith("</")){
                    hierarhy.add(s.trim());
                    xml.removeFirst();
                }else if(!s.startsWith("/")){
                    hierarhy.add("value:"+s);
                    xml.removeFirst();
                }
            }
        }
        return hierarhy;
    }







    private Node makeNodesFromUnit(List<String> unit){
        Node root = new Node();
        String tag  = unit.get(0);
        String rootTagName = this.getTagName(tag);
        root.setNodeName(rootTagName);
        if(hasAttr(tag)){
            List<String> attrs = getAttrList(tag);
            Map<String , String > attrsMap = constructAttrMap(attrs);
            root.getAttributes().putAll(attrsMap);
        }
        return root;
    }

    private boolean hasAttr(String tag){
        if(tag.contains(" ") && tag.contains("=")){
            return true;
        }else {
            return false;
        }
    }





    private String getTagName(String tag){

        String tagValue = null;
        int firstIndex = tag.indexOf("<");
        if(tag.trim().contains(" ")){
            int spaceIndex = tag.indexOf(" ");
            tagValue = tag.substring(firstIndex + 1,spaceIndex);
        }else {
            int lastIndex = tag.indexOf(">");
            tagValue = tag.substring(firstIndex + 1, lastIndex);
        }
        return tagValue;
    }




    private void buildNodesTree(Deque<String> unit , Node parent){
        if(!unit.isEmpty()) {
            String first = unit.getFirst();
            unit.removeFirst();
            if (first.startsWith("<") && !cheakForOneLine(first)) {
                Node node = constructNode(first);
                parent.getChildren().add(node);
                node.setParent(parent);
                buildNodesTree(unit, node);
            } else if (cheakForOneLine(first)) {
                Node node = constructNodeFromOneLine(first);
                parent.getChildren().add(node);
                node.setParent(parent);
                buildNodesTree(unit, parent);
            } else if (first.startsWith("value:")) {
                parent.setNodeValue(first.substring(6, first.length()));
                if (parent.getParent() != null) {
                    buildNodesTree(unit, parent.getParent());
                } else {
                    buildNodesTree(unit, parent);
                }

            }
        }
    }



    private Node  constructNodeFromOneLine(String oneLine){
        Node node = new Node();
        String[] tagAndValue = this.separateOneLine(oneLine);
        node.setNodeName(tagAndValue[0]);
        node.setNodeValue(tagAndValue[1]);
        if(hasAttr(oneLine)){
            List<String> attrs = this.getAttrList(oneLine);
            Map<String, String> attrsMap = this.constructAttrMap(attrs);
            node.getAttributes().putAll(attrsMap);
        }

        return node;
    }




    private Node constructNode(String tagName){

        Node node = new Node();
        node.setNodeName(getTagName(tagName));
        if (hasAttr(tagName)) {
            List<String> attrs = this.getAttrList(tagName);
            Map<String, String> attrsMap = this.constructAttrMap(attrs);
            node.getAttributes().putAll(attrsMap);
        }


        return node;
    }

    private List<String> getAttrList(String tag){

        List<String> attrList = new ArrayList<>();
        Matcher m1 = ATTRIBUTE_PATTERN.matcher(tag);
        while (m1.find()){
            String attr = m1.group().trim();
            attrList.add(attr);
        }
        return attrList;
    }





    private Map<String ,String> constructAttrMap(List<String> attrList){
        Map<String , String> attrMap = new HashMap<>();
        Matcher keyMatcher;
        Matcher valueMatcher;
        String key = null;
        String value = null;
        for(String att : attrList){
            keyMatcher = MAP_KEY_PATTERN.matcher(att);
            valueMatcher = MAP_VALUE_PATTENR.matcher(att);
            if(keyMatcher.find()){
                key = keyMatcher.group();
            }
            if(valueMatcher.find()){
                String rowValue = valueMatcher.group();
                value = rowValue.substring(1 , rowValue.length() - 1);
            }
            attrMap.put(key , value);
        }
        return attrMap;
    }






}
