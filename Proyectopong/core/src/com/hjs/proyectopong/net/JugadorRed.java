package com.hjs.proyectopong.net;

import java.net.InetAddress;

public class JugadorRed {

    public InetAddress ip;
    public int puerto;

    //Inicializa la direccion IP y el puerto del jugador y se los asigna
    public JugadorRed(InetAddress ip, int puerto) {
        this.ip = ip;
        this.puerto = puerto;
    }
}
