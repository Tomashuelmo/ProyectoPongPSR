package com.hjs.proyectopong.net;


import com.hjs.proyectopong.screen.ServerPongScreen;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;


public class Servidor extends Thread {
    private DatagramSocket socket;
    private boolean fin = false;
    private int conectados = 0;
    private int maximo = 2;
    private JugadorRed[] jugadores;
    public boolean iniciaJuego;

    // Constructor de la clase Servidor
    public Servidor() {
        jugadores = new JugadorRed[maximo];
        try {
            // Inicializa el socket en el puerto especificado
            socket = new DatagramSocket(Redes.puerto);
            System.out.println("Iniciado server");
        } catch (SocketException e) {
            fin = true;
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (!fin) {
            byte[] datos = new byte[1024];
            DatagramPacket dp = new DatagramPacket(datos, datos.length);
            try {
                // Recibe paquetes de datos y los procesa
                socket.receive(dp);
                procesarMensaje(dp);
            } catch (IOException e) {
                // Manejo de excepción vacía, podría ser mejorada
            }
        }
    }


    private void procesarMensaje(DatagramPacket dp) {
        String msg = new String(dp.getData()).trim();
        System.out.println("Mensaje " + msg);
        String[] mensajeEvento = msg.split("#");

        // Comprueba si el jugador está conectado, si es jugador 1 o 2 
        int nroJugador = -1;
        for (int i = 0; i < jugadores.length; i++) {
            if (jugadores[i] == null) continue;
            if (dp.getPort() == jugadores[i].puerto && dp.getAddress().equals(jugadores[i].ip)) {
                nroJugador = i; // 0 o 1
                break;
            }
        }

        // Procesa los diferentes tipos de mensajes recibidos
        switch (mensajeEvento[0]) {
            case "conectar":
                if (conectados <= 2) {
                    jugadores[conectados] = new JugadorRed(dp.getAddress(), dp.getPort());

                    // Envia el id del jugador al cliente
                    enviarMensaje("conexion#" + conectados, jugadores[conectados].ip, jugadores[conectados].puerto);
                    conectados++;
                    if (conectados == maximo) {
                        // Inicia el juego cuando los jugadores están completos
                        enviarMensaje("empezar");
                        iniciaJuego = true;
                    }
                }
                System.out.println("Jugador: " + dp.getAddress() + " " + dp.getPort());
                break;
            case "arriba":
                if (nroJugador == -1) return;
                // Mueve la paleta del jugador hacia arriba
                if (nroJugador == 0) ServerPongScreen.player1.moveUp();
                else if (nroJugador == 1) ServerPongScreen.player2.moveUp();
                break;
            case "abajo":
                if (nroJugador == -1) return;
                // Mueve la paleta del jugador hacia abajo
                if (nroJugador == 0) ServerPongScreen.player1.moveDown();
                else if (nroJugador == 1) ServerPongScreen.player2.moveDown();
                break;
            default:
                break;
        }
    }


    public void enviarMensaje(String msg, InetAddress ipDestino, int puerto) {
        byte[] mensaje = msg.getBytes();
        try {
            // Envia un mensaje a un destino específico
            DatagramPacket dp = new DatagramPacket(mensaje, mensaje.length, ipDestino, puerto);
            socket.send(dp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void enviarMensaje(String msg) {
        byte[] mensaje = msg.getBytes();
        try {
            // Envia un mensaje a todos los jugadores conectados
            for (int i = 0; i < jugadores.length; i++) {
                DatagramPacket dp = new DatagramPacket(mensaje, mensaje.length, jugadores[i].ip, jugadores[i].puerto);
                socket.send(dp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void terminarJuego(int jugadir) {
        // Envia un mensaje de fin de juego y el id del jugador ganador
        enviarMensaje("fin#" + jugadir);
        iniciaJuego = false;
        conectados = 0;
        jugadores = new JugadorRed[maximo];
    }
}
