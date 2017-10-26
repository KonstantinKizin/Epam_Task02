package DAO.parser;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileParser {








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
                root = getTag(s);
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
        String rootTagName = getTagName(tag);
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




    private void buildNodes(Node root , Deque<String> unit){
        if(!unit.isEmpty()) {
            String first = unit.getFirst();
            unit.removeFirst();

            if (cheakForOneLine(first)) {
                Node node = constructNodeFromOneLine(first);
                root.getChildren().add(node);
                buildNodes(root, unit);
            } else if (first.startsWith("value:")) {
                root.setNodeValue(first.substring(6,first.length()));
                buildNodes(root, unit);
            } else if (first.startsWith("<")) {
                Node node = constructNode(first);
                root.getChildren().add(node);
                buildNodes(node , unit);

            }
        }
    }



    private Node  constructNodeFromOneLine(String oneLine){
        Node node = new Node();
        String[] tagAndValue = separateOneLine(oneLine);
        node.setNodeName(tagAndValue[0]);
        node.setNodeValue(tagAndValue[1]);
        if(hasAttr(oneLine)){
            List<String> attrs = getAttrList(oneLine);
            Map<String, String> attrsMap = constructAttrMap(attrs);
            node.getAttributes().putAll(attrsMap);
        }

        return node;
    }




    private Node constructNode(String tagName){

        Node node = new Node();
        node.setNodeName(getTagName(tagName));
        if (hasAttr(tagName)) {
            List<String> attrs = getAttrList(tagName);
            Map<String, String> attrsMap = constructAttrMap(attrs);
            node.getAttributes().putAll(attrsMap);
        }


        return node;
    }

    private List<String> getAttrList(String tag){

        List<String> attrList = new ArrayList<>();
        String attRegExp = " [a-z A-Z0-9]*=\"[a-zA-Z0-9]*\"";
        Pattern p1 = Pattern.compile(attRegExp);
        Matcher m1 = p1.matcher(tag);
        while (m1.find()){
            String attr = m1.group().trim();
            attrList.add(attr);
        }
        return attrList;
    }


    


    private Map<String ,String> constructAttrMap(List<String> attrList){
        Map<String , String> attrMap = new HashMap<>();
        String keyRegExp = "[a-z A-Z0-9]*";
        String valueRegExp = "\"[a-zA-Z0-9]*\"";
        Pattern keyPattern = Pattern.compile(keyRegExp);
        Pattern valuePattern = Pattern.compile(valueRegExp);
        Matcher keyMatcher;
        Matcher valueMatcher;
        String key = null;
        String value = null;
        for(String att : attrList){
            keyMatcher = keyPattern.matcher(att);
            valueMatcher = valuePattern.matcher(att);
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
