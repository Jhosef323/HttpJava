package com.HttpJava;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ConexaoHandler implements Runnable{
    private final Socket socket;
    private final Router router;

    public ConexaoHandler(Socket socket, Router router){
        this.socket = socket;
        this.router = router;
    }

   @Override
    public void run(){
        try(socket){
            HttpRequest request = HttpRequest.parse(socket.getInputStream());
            HttpResponse response = new HttpResponse();
            router.resolver(request).handle(request, response);
            response.enviar(socket.getOutputStream());
        } catch (IOException e){
            System.err.println("Erro no servidor " + e.getMessage());
        }
    }
}
