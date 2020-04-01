package restaurant.data.services;

import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public abstract class BaseService<T> implements Service<T> {

    protected File file;

    public BaseService(){
        file = new File("data.xml");
    }

    protected Document connect() {

        try {

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);
            return doc;

        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
