package com.HttpJava;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
    private String caminho;
    private String method;
    private String queryString;
    private String body;
    private final Map<String, String> headers = new HashMap<>();

    public static HttpRequest parse(InputStream input) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        HttpRequest req = new HttpRequest();

        String linhaInicial = reader.readLine();
        String[] partes = linhaInicial.split(" ");
        req.method = partes[0];

        String[] caminhoQuery = partes[1].split("\\?", 2);
        req.caminho = caminhoQuery[0];
        req.queryString = caminhoQuery.length > 1 ? caminhoQuery[1] : "";

        String linha;
        while (!(linha = reader.readLine()).isEmpty()){
            String[] kv = linha.split(": ", 2);
            req.headers.put(kv[0].toLowerCase(), kv[1]);
        }

        if(req.headers.containsKey("content-length")){
            int tamanho = Integer.parseInt(req.headers.get("content-length"));
            char[] buf = new char[tamanho];
            reader.read(buf, 0, tamanho);
            req.body = new String(buf);
        }

        return req;
    }

    public String getMethod(){
        return method;
    }
    public String getCaminho(){
        return caminho;
    }
    public String getQueryString(){
        return queryString;
    }
    public String getBody(){
        return body;
    }
    public String getHeader(String name){
        return headers.get(name);
    }
    public Map<String, String> getHeaders(){
        return headers;
    }

  }
