package com.hjs.proyectopong.net;


import com.hjs.proyectopong.Pong;
import com.hjs.proyectopong.screen.PongScreen;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Cliente extends Thread {

    private DatagramSocket socket;
    public boolean fin = false;
    private InetAddress ipServer;
    private boolean conectado = false;
    private Pong game;
    public int ganador;

    public enum EstadoCliente {
        ESPERANDO,
        JUGANDO,
        FIN
    }

    public EstadoCliente estado = EstadoCliente.ESPERANDO;
    
    //Metodos para prender el cliente
    public Cliente() {
        try {
            socket = new DatagramSocket();
            ipServer = InetAddress.getByName("255.255.255.255");
            System.out.println("Inicia cliente");
        } catch (SocketException | UnknownHostException e) {
            fin = true;
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        do {
            byte[] datos = new byte[1024];
            DatagramPacket dp = new DatagramPacket(datos, datos.length);
            try {
                socket.receive(dp);
                procesarMensaje(dp);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } while (!fin);
        socket.close();
    }

    private void procesarMensaje(DatagramPacket dp) {
        String msg = new String(dp.getData()).trim();
        System.out.println("Mensaje " + msg);
        String[] mensajeCompuesto = msg.split("#");


        // el mensajeCompuesto[0] es el nombre del evento
        if (mensajeCompuesto[0].equals("conexion")) {
            System.out.println("Conectado al servidor");
            conectado = true;
            ipServer = dp.getAddress();
            // Guarda el ID del cliente
            Redes.idCliente = Integer.valueOf(mensajeCompuesto[1]);
            System.out.println("ID " + Redes.idCliente);
        }
        // Si no esta conectado, no comprobar eventos del juego
        if (!conectado) return;

        if (mensajeCompuesto[0].equals("empezar")) { // Empezar juego
            estado = EstadoCliente.JUGANDO;
        }
        // fin#0 => ganador jugador 0
        if (mensajeCompuesto[0].equals("fin")) { // Termina el juego
            this.ganador = Integer.valueOf(mensajeCompuesto[1]);
            estado = EstadoCliente.FIN;
            Redes.ganador = ganador;
        }

        if (estado.equals(EstadoCliente.JUGANDO)) {
            // jugador#0#45 => El jugador 0 está en la posicion Y = 45
            if (mensajeCompuesto[0].equals("jugador")) {
                Integer id = Integer.valueOf(mensajeCompuesto[1]);
                float y = Float.valueOf(mensajeCompuesto[2]);
                if (id == 0 && PongScreen.player1 != null)
                    PongScreen.player1.getBounds().setPosition(PongScreen.player1.getBounds().x, y);
                else if (id == 1 && PongScreen.player2 != null)
                    PongScreen.player2.getBounds().setPosition(PongScreen.player2.getBounds().x, y);
            } else if (mensajeCompuesto[0].equals("pelota")) {
                // pelota#x#y => pelota#10#50 -> pelota en la posicion X=10 Y=50
                float x = Float.valueOf(mensajeCompuesto[1]);
                float y = Float.valueOf(mensajeCompuesto[2]);
                if(PongScreen.ball != null){
                    PongScreen.ball.getBounds().setPosition(x, y);
                }
            }else if (mensajeCompuesto[0].equals("score")) {
                // score#0#5 => Jugador 0 tiene puntaje 5
                Integer id = Integer.valueOf(mensajeCompuesto[1]);
                int points = Integer.valueOf(mensajeCompuesto[2]);
                if(id == 0)PongScreen.scorePlayer1 = points;
                else PongScreen.scorePlayer2 = points;
            }
        }
    }

    //h
    public void enviarMensaje(String msg) {
        byte[] mensaje = msg.getBytes();

        try {
            DatagramPacket dp = new DatagramPacket(mensaje, mensaje.length, ipServer, Redes.puerto);
            socket.send(dp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // Devuelve si el cliente está conectado al servidor
    public boolean isConectado() {
        return conectado;
    }
    // Finaliza la conexión del cliente y resetea el estado del cliente a esperando
    public void fin() {
        estado = EstadoCliente.ESPERANDO;
    }

}