package com.HttpJava;

import java.util.concurrent.ExecutorService;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;

public class Server {
    private final int porta;
    private final Router router;
    private final ExecutorService pool = Executors.newFixedThreadPool(10);

    public Server(int porta, Router router){
        this.porta = porta;
        this.router = router;
    }

    public void iniciar() throws IOException{
        try(ServerSocket serverSocket = new ServerSocket(porta)){
            System.out.println("Servidor ouvindo na porta " + porta);

            while (true) {
                Socket conexao = serverSocket.accept();
                pool.submit(new ConexaoHandler(conexao, router));
            }
        }
    }
}
