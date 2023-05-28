package simulador_juego.solucion;

import java.util.Hashtable;

public class Juego implements IJuego {

    private int contadorEnemigosTotales;
    private Hashtable<Integer, Integer> contadoresEnemigoTipo;
    private Hashtable<Integer, Integer> contadoresEliminadosTipo;
    private int MAXENEMIGOS;
    private int MINENEMIGOS;


    public Juego(int maxEnemigos, int minEnemigos) {
        this.MAXENEMIGOS = maxEnemigos;
        this.MINENEMIGOS = minEnemigos;
    }

    @Override
    public void generarEnemigo(int tipoEnemigo) {

    }

    @Override
    public void eliminarEnemigo(int tipoEnemigo) {

    }

    private void imprimirInfo(){
        //code
    }

    public int sumarContadores(){
        return 0;
    }

    protected void checkInvariante(){
        //code
    }

    protected void comprobarAntesDeGenerar(){
        //code
    }

    protected void comprobarAntesDeEliminar(){
        //code
    }
}
