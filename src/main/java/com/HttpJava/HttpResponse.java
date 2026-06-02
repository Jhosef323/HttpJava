package com.HttpJava;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

public class HttpResponse {
    private  int status = 200;
    private String statusText = "OK";
    private Map<String, String> headers = new LinkedHashMap<>();
    private byte[] body = new byte[0];

    public void setJson(String json){
        byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
        this.body = bytes;
        headers.put("Content-Type", "application/json; charset=utf-8");
        headers.put("Content-Length", String.valueOf(bytes.length));
    }

    public void enviar(OutputStream out) throws IOException{
        StringBuilder sb = new StringBuilder();
        sb.append("HTTP/1.1 ").append(status).append(" ").append(statusText).append("\r\n");
        for(var entry: headers.entrySet()){
            sb.append(entry.getKey()).append(": ").append(entry.getValue()).append("\r\n");
        }
        sb.append("\r\n");
        out.write(sb.toString().getBytes(StandardCharsets.UTF_8));
        out.write(body);
        out.flush();
    }
}
