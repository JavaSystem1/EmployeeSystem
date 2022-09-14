package tech.getarrays.employeemanager.model;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class SingletonTest {

    private static Singleton instance = null;

    @Test
    void getInstance() {
        if(instance == null)
            instance = new Singleton();

        assertNotEquals(null, instance);
    }

    @Test
    void getCurrenciesJson() throws Exception {
        OkHttpClient client = new OkHttpClient().newBuilder().build();

        Request request = new Request.Builder()
                .url("https://api.apilayer.com/fixer/latest?symbols=EUR%2CGBP%2CPLN&base=USD")
                .addHeader("apikey", "BIgqMQ5mX6tUnWxg8CeupBQbHY08J2W1")
                .get()
                .build();

        ResponseBody response = client.newCall(request).execute().body();

        JSONObject json = new JSONObject(response.string());

        JSONAssert.assertEquals("{base:\"USD\"}", json, false);
        JSONAssert.assertEquals("{date:\"2022-09-14\"}", json, false);
    }
}