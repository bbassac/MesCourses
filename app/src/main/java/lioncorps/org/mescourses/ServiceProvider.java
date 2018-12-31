package lioncorps.org.mescourses;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import lioncorps.org.mescourses.bean.Collection;
import lioncorps.org.mescourses.bean.Liste;

public class ServiceProvider {
    final ObjectMapper mapper = new ObjectMapper();
    final JsonFactory factory = mapper.getJsonFactory();


    public Collection loadCourses() throws IOException {
        String stringJson =  "{\n" +
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
        JsonParser jp = factory.createJsonParser(new ByteArrayInputStream(stringJson.getBytes("UTF-8")));
        return mapper.readValue(jp, Collection.class);
    }

    public Liste loadItems(Long listID) throws IOException {
        String stringJson = "{\n" +
                "    \"id\": 1,\n" +
                "    \"nom\": \"Liste 1\",\n" +
                "    \"template\": true,\n" +
                "    \"items\": [\n" +
                "        {\n" +
                "            \"id\": 1,\n" +
                "            \"nom\": \"Item 1\",\n" +
                "            \"quantite\": \"2 kg\",\n" +
                "            \"done\": false\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": 2,\n" +
                "            \"nom\": \"Item 3\",\n" +
                "            \"quantite\": \"4 sacs\",\n" +
                "            \"done\": true\n" +
                "        }\n"+
                "    ]\n" +
                "}";

        //Jacksonize to bean
        JsonParser jp = factory.createJsonParser(new ByteArrayInputStream(stringJson.getBytes("UTF-8")));
        return mapper.readValue(jp, Liste.class);

    }
}
