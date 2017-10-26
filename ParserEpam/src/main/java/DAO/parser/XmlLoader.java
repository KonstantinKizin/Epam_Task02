package DAO.parser;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class XmlLoader {

    private final StringBuilder fileText = new StringBuilder();
    private final String fileName;

    public XmlLoader(String fileName){
        this.fileName = fileName;
    }


    private String getAbsoluteFilePath(){
        ClassLoader classLoader = getClass().getClassLoader();
        String path = new String(classLoader.getResource(fileName).getPath());
        return path;
    }


    public  StringBuilder getLoadedText() throws IOException {

        String absolutePath = this.getAbsoluteFilePath();

        try(Scanner scanner = new Scanner(new File(absolutePath))){
            while (scanner.hasNextLine()){
                fileText.append(scanner.nextLine()+"\n");
            }
        }catch(IOException e){

        }
        return fileText;
    }
}
