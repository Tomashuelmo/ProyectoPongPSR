package com.hjs.proyectopong.net;

public abstract class Redes {
    public static Cliente cliente;
    public static Servidor server;

    public static int ganador = -1;

    public static final int puerto = 45455;

    public static int idCliente;


    public static void empezarServidor() {
        // Verifica si el servidor es nulo osea si no esta iniciado
        if (server == null) {
            // Crea una nueva instancia del servidor
            server = new Servidor();
            server.start();
        }
    }
    //Metodo que verifica si existe el cliente, en caso que no lo crea y manda el mensaje conectar, pero si existe manda unicamente "conectar"
    public static void empezarCliente(){
        idCliente = -1;
        ganador = -1;
        if(cliente != null && !cliente.fin){
            cliente.fin();
            cliente.enviarMensaje("conectar");
            return;
        }
        cliente = new Cliente();
        cliente.start();
        cliente.enviarMensaje("conectar");
    }




}
