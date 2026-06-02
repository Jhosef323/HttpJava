package com.HttpJava;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class Router {
    @FunctionalInterface
    public interface Handler{
        void handle(HttpRequest request, HttpResponse response) throws IOException;
    }

    private final Map<String, Handler> rotas = new LinkedHashMap<>();

    public void get(String caminho, Handler handler){
        rotas.put("GET:" + caminho, handler);
    }
    public void post(String caminho, Handler handler){
        rotas.put("POST:" + caminho, handler);
    }

    public Handler resolver(HttpRequest request){
        String chave = request.getMethod() + ":" + request.getCaminho();
        return rotas.getOrDefault(chave, (r, s) -> {
            s.setStatus(404, "Not Found");
            s.setJson("{\"erro\": \"rota não encontrada\"}");
        });
    }
}
