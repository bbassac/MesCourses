package lioncorps.org.mescourses;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import lioncorps.org.mescourses.bean.Collection;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            Collection collection = loadCourses();
            displayCourses(collection);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void displayCourses(Collection collection) throws JSONException {
        final TextView mTextView = (TextView) findViewById(R.id.text);


    }

    private Collection loadCourses() throws JSONException, IOException {
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
