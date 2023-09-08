package com.tdc.sensorApp.services.socket;

import com.tdc.sensorApp.entities.Dato;
import com.tdc.sensorApp.entities.Device;
import com.tdc.sensorApp.entities.Novedad;
import com.tdc.sensorApp.services.DatoService;
import com.tdc.sensorApp.services.EmailService;
import com.tdc.sensorApp.services.NovedadService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class SocketTcp {

    private final EmailService emailService;

    private final DatoService datoService;

    private final NovedadService novedadService;

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

                    String receivedData = new String(buffer, 0, bytesRead);
                    System.out.println("Datos recibidos: " + receivedData);

                    String[] values = receivedData.split(";");
                    if (values.length > 0) {
                        String action = values[0];
                        switch (action) {
                            case "1":
                                // Salvar datos
                                Dato dato = buildDato(values);
                                if (dato != null) {
                                    datoService.create(dato);
                                }
                                break;
                            default:
                                // Notificar alguna incidencia // Agregar novedad
                                sendNotification(values[1]);
                                System.out.println("not action detected");
                                break;
                        }
                    }
                }
                clientSocket.close();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void controlarDispositivo(Device device, String actionControl) {
        String serverIp = device.getIpaddress();
        int serverPort = device.getPort();
        System.out.println("Conectando a arduino en " + serverIp + ":" + serverPort);
        try (Socket socket = new Socket(serverIp, serverPort)) {
            OutputStream outputStream = socket.getOutputStream();

            String message = "";

            switch (actionControl) {
                case "1":
                    message = buildMessage(device, actionControl);
                    break;
                case "2":
                    message = "2"; // Relay ON
                    break;
                case "3":
                    message = "3"; // Relay OFF
                    break;
                case "4":
                    message = "4"; // Relay OFF
                    break;
                default:
                    System.out.println("Not action detected");
            }

            if (message.isEmpty()) {
                throw new RuntimeException("Does not have any action");
            }

            byte[] messageBytes = message.getBytes();
            outputStream.write(messageBytes);

            System.out.println("Mensaje enviado: " + message);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String buildMessage(Device device, String action) {
        // Protocolo de comunicacion -> action;kd;kp;ki;setpoint;ipaddressserver;portServer;ipaddress;port;idConfig
        StringBuilder chainValue = new StringBuilder();
        if (device != null) {
            chainValue
                    .append(action)
                    .append(";")
                    .append(device.getKp())
                    .append(";")
                    .append(device.getKd())
                    .append(";")
                    .append(device.getKi())
                    .append(";")
                    .append(device.getSetpoint())
                    .append(";")
                    .append(device.getIpaddressserver())
                    .append(";")
                    .append(device.getPortserver())
                    .append(";")
                    .append(device.getIpaddress())
                    .append(";")
                    .append(device.getPort())
                    .append(";")
                    .append(device.getIdConfiguracion());
        }
        return chainValue.toString();
    }

    // Protocolo de comunicacion (Recibo de Arduino) -> action;value;outputPid;Rpm;kp;kd;ki;setPoint;idConfiguration;idDispositivo
    private Dato buildDato(String[] values) {
        Dato dato = new Dato();
        dato.setFecha(LocalDateTime.now());
        dato.setValor(values[1]);
        dato.setOutputPid(values[2]);
        dato.setRpm(values[3]);
        dato.setKp(values[4]);
        dato.setKd(values[5]);
        dato.setKi(values[6]);
        dato.setSetpoint(values[7]);
        dato.setPorcentaje(values[8]);
        dato.setIdConfiguration(values[9]);

        Device device = new Device();
        device.setIdDevice(Long.parseLong(values[10]));

        dato.setDispositivo(device);

        return dato;
    }

    private Novedad buildNovedad(String descripcion) {
        Novedad novedad = new Novedad();
        novedad.setDescripcion(descripcion);
        novedad.setFecha(LocalDateTime.now());

        return novedad;
    }

    private void sendNotification(String notification) {
        // Protocolo de comunicacion (Recibo de Arduino) -> action;notificacion
        Novedad novedad = buildNovedad(notification);
        novedadService.create(novedad);
        System.out.println("Sending email " + notification);
        try {
            emailService.sendSimpleMessage("nicolas.oliva@hab.com.ar", "nicolas.oliva@hab.com.ar", "Notification Arduino", notification);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
