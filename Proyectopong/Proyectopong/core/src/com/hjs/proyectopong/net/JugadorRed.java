package com.hjs.proyectopong.net;

import java.net.InetAddress;

public class JugadorRed {

    public float y;
    public InetAddress ip;
    public int puerto;


    public JugadorRed(InetAddress ip, int puerto) {
        this.ip = ip;
        this.puerto = puerto;
    }
}
