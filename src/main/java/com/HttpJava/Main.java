package com.HttpJava;

import java.io.IOException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main(String[] args) throws IOException {
        Router router = new Router();

        router.get("/ping", (request, response) -> {
            response.setJson("{\"status\": \"ok\"}");
        });

        router.get("/eco", (request, response) -> {
            String resposta = String.format("{\"caminho\": \"%s\", \"query\": \"%s\"}", request.getCaminho(), request.getQueryString());
            response.setJson(resposta);
        });

        router.post("/dados", (request, response) -> {
            System.out.println("Body recebido: " + request.getBody());
            response.setJson("{\"recebido\": true }");
        });

        new Server(8080, router).iniciar();
    }
}
