package com.nttdata.transactionms.business;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

@Component
public class HttpConection {

    public static Map<String, Object> getCuentaByNumeroCuenta() throws Exception {
        Map<String, Object> cuenta = new HashMap<>();
        String url = "http://localhost:8080/cuentas/4";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        System.out.println(response.toString());

        JSONObject myResponse = new JSONObject(response.toString());
        System.out.println(myResponse.toString());
        cuenta = myResponse.toMap();
        System.out.println(cuenta.get("numeroCuenta"));

        return cuenta;
    }


}
