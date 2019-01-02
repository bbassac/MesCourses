package lioncorps.org.mescourses.services;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Random;

import lioncorps.org.mescourses.bean.Collection;
import lioncorps.org.mescourses.bean.Item;
import lioncorps.org.mescourses.bean.Liste;

public class InMemoryServiceProvider implements IServiceProvider {

    private static Collection internalCollection = null ;
    private static InMemoryServiceProvider instance;
    public static InMemoryServiceProvider getInstance() {
        if (instance ==null){
            instance = new InMemoryServiceProvider();
        }
        return instance;
    }

    private InMemoryServiceProvider() {
        String stringJson = "{\n" +
                "    \"id\": 1,\n" +
                "    \"listes\": [\n" +
                "        {\n" +
                "            \"id\": 1,\n" +
                "            \"nom\": \"Liste 1\",\n" +
                "            \"template\": true,\n" +
                "            \"items\": [\n" +
                "                {\n" +
                "                    \"id\": 1,\n" +
                "                    \"nom\": \"Item 1\",\n" +
                "                    \"quantite\": \"2 kg\",\n" +
                "                    \"done\": false\n" +
                "                }\n" +
                "            ]\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": 2,\n" +
                "            \"nom\": \"Liste 2\",\n" +
                "            \"template\": false,\n" +
                "            \"items\": []\n" +
                "        }\n" +
                "    ]\n" +
                "}";


        //Jacksonize to bean

        try {
            final ObjectMapper mapper = new ObjectMapper();
            JsonParser jp = mapper.getJsonFactory().createJsonParser(new ByteArrayInputStream(stringJson.getBytes("UTF-8")));
            internalCollection =  mapper.readValue(jp, Collection.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @Override
    public Collection loadCollection()  {
        return internalCollection;
    }

    @Override
    public Liste loadListe(Long listID) {
        for (Liste l : internalCollection.getListes()){
            if (l.getId().equals(listID)){
                return l;
            }
        }
        return null;

    }

    @Override
    public Liste addItemToListe(Long listId, String nom, String quantite){
        Item item = new Item();
        item.setNom(nom);
        item.setId(new Random().nextLong());
        item.setQuantite(quantite);
        item.setDone(false);
        for (Liste l : internalCollection.getListes()){
            if (l.getId().equals(listId)){
                l.addItem(item);
            }
        }

        return loadListe(listId);
    }

    @Override
    public Collection addListe(String nom) {
        Liste l = new Liste();
        l.setId(new Random().nextLong());
        l.setNom(nom);
        l.setTemplate(false);
        internalCollection.addListe(l);
        return internalCollection;
    }
}
