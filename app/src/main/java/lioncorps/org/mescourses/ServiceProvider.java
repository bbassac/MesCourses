package lioncorps.org.mescourses;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import lioncorps.org.mescourses.bean.Collection;

public class ServiceProvider {

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

        final ObjectMapper mapper = new ObjectMapper();
        final JsonFactory factory = mapper.getJsonFactory();
        JsonParser jp = factory.createJsonParser(new ByteArrayInputStream(stringJson.getBytes("UTF-8")));
        //Jacksonize to bean
        return mapper.readValue(jp, Collection.class);
    }
}
