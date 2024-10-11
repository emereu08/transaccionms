package com.nttdata.transactionms.business;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class HttpConection {

     public StringBuffer ejecutarSolicitudHttp(String uri, String tipoConexion) {
        StringBuffer response = new StringBuffer();
        try {
            URL url = new URL(uri);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod(tipoConexion);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            while ((inputLine = bufferedReader.readLine()) != null) {
                response.append(inputLine);
            }
            bufferedReader.close();
        } catch (Exception e){
            System.out.println("Error: "+e.getMessage());
        }
        return response;
    }

    public StringBuffer ejecutarSolicitudHttp(String uri, String tipoConexion, String body) {
        StringBuffer response = new StringBuffer();
        try {
            URL url = new URL(uri);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod(tipoConexion);
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);

            try(OutputStream outputStream = con.getOutputStream()) {
                byte[] input = body.getBytes("utf-8");
                outputStream.write(input, 0, input.length);
            }

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            while ((inputLine = bufferedReader.readLine()) != null) {
                response.append(inputLine);
            }
            bufferedReader.close();
        } catch (Exception e){
            System.out.println("Error: "+e.getMessage());
        }
        return response;
    }

    public Map<String, Object> stringToMap(String response){
        JSONObject jsonObject = new JSONObject(response);
        return jsonObject.toMap();
    }

    public List<Map<String, Object>> stringToArray(String response){
        //JSONObject jsonObject = new JSONObject(response);

        Gson gson = new Gson();
        Type resultType = new TypeToken<List<Map<String, Object>>>(){}.getType();
        List<Map<String, Object>> result = gson.fromJson(response, resultType);

        return result;
    }



}
