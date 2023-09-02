package com.tdc.sensorApp.services.socket;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

@Component
public class SocketTcp {

    @Async("threadPoolTaskExecutor")
    public void startServer() {
        try {
            ServerSocket serverSocket = new ServerSocket(12345); // Puerto que escuchará el servidor

            while (true) {
                System.out.println("Esperando conexiones...");

                Socket clientSocket = serverSocket.accept(); // Esperar a que un cliente se conecte
                System.out.println("Cliente conectado desde " + clientSocket.getInetAddress());

                // Obtener el flujo de entrada del socket para recibir datos
                InputStream inputStream = clientSocket.getInputStream();
                byte[] buffer = new byte[1024]; // Tamaño del búfer para leer los datos

                // Leer los datos del flujo de entrada
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    // Convertir los datos en una cadena (asumiendo que son datos de texto)
                    String receivedData = new String(buffer, 0, bytesRead);
                    System.out.println("Datos recibidos: " + receivedData);

                    // Aquí puedes procesar los datos recibidos según tus necesidades

                }

                // Cierra el socket del cliente cuando termines de recibir datos
                clientSocket.close();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void enviarPaquete() {
        String serverIp = "localhost"; // Arduino Dir
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

            // Acciones
            // 1. Actualizar/Guardar los datos del dispositivo ( Kp, Ki, Kd, SetPoint)
            // 2. Reiniciar.
            // 3. Mover el relay
            String action = getAction() ;

            System.out.println("Mensaje enviado: " + message);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getAction() {
        return null;
    }
}
