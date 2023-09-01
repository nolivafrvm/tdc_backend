package com.tdc.sensorApp.services.socket;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

//@Service
public class SocketTcp {

    @PostConstruct
    public void startServer() {
        try {
            ServerSocket serverSocket = new ServerSocket(12345); // Puerto que escuchará el servidor

            while (true) {
                System.out.println("Esperando conexiones...");
                Socket clientSocket = serverSocket.accept(); // Esperar a que un cliente se conecte
                System.out.println("Cliente conectado desde " + clientSocket.getInetAddress());
                enviarPaquete();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void enviarPaquete() {
        String serverIp = "192.168.1.103";
        int serverPort = 3000;

        try (Socket socket = new Socket(serverIp, serverPort)) {
            System.out.println("Conectado al servidor en " + serverIp + ":" + serverPort);

            // Obtener el flujo de salida del socket para enviar datos
            OutputStream outputStream = socket.getOutputStream();

            // Mensaje que deseas enviar
            String message = "Hola desde el cliente TCP";
            byte[] messageBytes = message.getBytes();

            // Enviar el mensaje
            outputStream.write(messageBytes);

            System.out.println("Mensaje enviado: " + message);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
