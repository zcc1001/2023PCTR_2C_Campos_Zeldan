package simulador_juego.solucion;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ActividadEnemiga implements Runnable {

    private int tipoEnemigo;
    private IJuego juego;

    public ActividadEnemiga(int tipoEnemigo, IJuego juego) {
        this.tipoEnemigo = tipoEnemigo;
        this.juego = juego;
    }

    public void run() {
        for (int i = 0; i < 10; i++) {
            try {
                juego.generarEnemigo(tipoEnemigo);
                TimeUnit.MILLISECONDS.sleep(new Random().nextInt(5) * 1000);
            } catch (InterruptedException e) {
                Logger.getGlobal().log(Level.INFO, "Enemigo NO generado");
                Logger.getGlobal().log(Level.INFO, e.toString());
                return;
            }
        }
    }
}
