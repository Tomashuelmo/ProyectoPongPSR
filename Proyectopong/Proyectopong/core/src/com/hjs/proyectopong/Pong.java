package com.hjs.proyectopong;

import com.badlogic.gdx.Game;
import com.hjs.proyectopong.net.Cliente;
import com.hjs.proyectopong.net.Redes;
import com.hjs.proyectopong.screen.MainMenuScreen;
import com.hjs.proyectopong.screen.WinnerScreen;

public class Pong extends Game {
    @Override
    public void create() {
        this.setScreen(new MainMenuScreen(this));
    }

    @Override
    public void render() {
        super.render();
        if(Redes.ganador != -1 && Redes.cliente.estado.equals(Cliente.EstadoCliente.JUGANDO)){
            Redes.cliente.estado = Cliente.EstadoCliente.FIN;
            System.out.println("GANADOR :"+ Redes.ganador);
            setScreen(new WinnerScreen(this, Redes.ganador));
        }
    }
}
