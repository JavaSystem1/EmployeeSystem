package tech.getarrays.employeemanager.model;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;

import java.io.IOException;

public class Singleton {
    private static Singleton instance = null;

    private Singleton()
    {
        System.out.println("Singleton created");
    }

    public static synchronized Singleton getInstance()
    {
        if(instance == null)
            instance = new Singleton();

        return instance;
    }

    public String getCurrenciesJson() throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder().build();

        Request request = new Request.Builder()
                .url("https://api.apilayer.com/fixer/latest?symbols=EUR%2CGBP%2CPLN&base=USD")
                .addHeader("apikey", "J08ttYyr5n21Z1NbxEi9OVmxZornVgJw")
                .get()
                .build();
        ResponseBody response = client.newCall(request).execute().body();
        return response.string();
    }
}
