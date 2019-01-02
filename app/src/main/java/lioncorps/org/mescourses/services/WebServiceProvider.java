package lioncorps.org.mescourses.services;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import com.github.kevinsawicki.http.HttpRequest;
import lioncorps.org.mescourses.bean.Collection;
import lioncorps.org.mescourses.bean.Item;
import lioncorps.org.mescourses.bean.Liste;

public class WebServiceProvider implements IServiceProvider {

    private String url = "http://blueangel82.ddns.net/courses";
    private final ObjectMapper mapper = new ObjectMapper();

    private static WebServiceProvider instance;
    public static WebServiceProvider getInstance() {
        if (instance ==null){
            instance = new WebServiceProvider();
        }
        return instance;
    }

    private WebServiceProvider() {

    }


    @Override
    public Collection loadCollection()  {
        // defaultHttpClient
        HttpRequest httpRequest = HttpRequest.get(url+"/collection");
        // return JSON String
        String stringJson =  httpRequest.body();

        try {
            Collection internalCollection =  mapper.readValue(mapper.getJsonFactory().createJsonParser(new ByteArrayInputStream(stringJson.getBytes("UTF-8"))), Collection.class);
            return internalCollection;
        } catch (IOException e) {
            e.printStackTrace();
        }return null;
    }

    @Override
    public Liste loadListe(Long listID) {
        // defaultHttpClient
        HttpRequest httpRequest = HttpRequest.get(url+"/liste/"+listID);
        // return JSON String
        String stringJson =  httpRequest.body();


        try {
            JsonParser jp = mapper.getJsonFactory().createJsonParser(new ByteArrayInputStream(stringJson.getBytes("UTF-8")));
            Liste internalList  = mapper.readValue(jp, Liste.class);
            return internalList;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Liste addItemToListe(Long listId, String nom, String quantite) {
        Item item = new Item();
        item.setNom(nom);
        item.setQuantite(quantite);
        item.setDone(false);
        try {
            String jsonObject  = mapper.writer().writeValueAsString(item);
            HttpRequest httpRequest = new HttpRequest(url+"/item/"+listId,"POST");
            httpRequest.contentType(HttpRequest.CONTENT_TYPE_JSON,HttpRequest.CHARSET_UTF8);
            httpRequest.send(jsonObject);

            String response = httpRequest.body();

            JsonParser jp = mapper.getJsonFactory().createJsonParser(new ByteArrayInputStream(response.getBytes("UTF-8")));
            Liste collection =  mapper.readValue(jp, Liste.class);
            return collection;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Collection addListe(String nom) {
      Liste liste = new Liste();
      liste.setNom(nom);
      liste.setTemplate(false);

        try {
            String jsonObject  = mapper.writer().writeValueAsString(liste);
            HttpRequest httpRequest = new HttpRequest(url+"/liste","POST");
            httpRequest.contentType(HttpRequest.CONTENT_TYPE_JSON,HttpRequest.CHARSET_UTF8);
            httpRequest.send(jsonObject);

            String response = httpRequest.body();

            JsonParser jp = mapper.getJsonFactory().createJsonParser(new ByteArrayInputStream(response.getBytes("UTF-8")));
            Collection collection =  mapper.readValue(jp, Collection.class);
            return collection;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
