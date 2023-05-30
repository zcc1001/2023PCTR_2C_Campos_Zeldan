package simulador_juego.solucion;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ActividadAliada implements Runnable {
    private int tipoEnemigo;
    private IJuego juego;

    public ActividadAliada(int tipoEnemigo, IJuego juego) {
        this.tipoEnemigo = tipoEnemigo;
        this.juego = juego;
    }

    @Override
    public void run() {
        for (int i = 0; i < juego.numeroMaximoEnemigos(); i++) {
            try {
                juego.eliminarEnemigo(tipoEnemigo);
                TimeUnit.MILLISECONDS.sleep(new Random().nextInt(5) * 1000);
            } catch (InterruptedException e) {
                Logger.getGlobal().log(Level.INFO, "Enemigo NO eliminado");
                Logger.getGlobal().log(Level.INFO, e.toString());
                return;
            }
        }
    }
}
