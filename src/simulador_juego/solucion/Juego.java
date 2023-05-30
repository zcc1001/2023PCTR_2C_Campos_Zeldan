package simulador_juego.solucion;

import java.util.Enumeration;
import java.util.Hashtable;

public class Juego implements IJuego {

    private int numeroTiposEnemigos; // número T de tipos de enemigos
    private int numeroEnemigosPorTipo;// número N de enemigos de cada tipo T
    private int contadorEnemigosTotales;
    private Hashtable<Integer, Integer> contadoresEnemigoTipo;
    private Hashtable<Integer, Integer> contadoresEliminadosTipo;
    private int MAX_ENEMIGOS;   //número máximo M de enemigos al mismo tiempo de todos los tipos
    private int MIN_ENEMIGOS = 0;

    public Juego(int numeroTiposEnemigos, int numeroEnemigosPorTipo, int numeroMaximoEnemigosTotales) {
        this.contadorEnemigosTotales = 0;
        this.numeroEnemigosPorTipo = numeroEnemigosPorTipo;
        this.MAX_ENEMIGOS = numeroMaximoEnemigosTotales;

        this.contadoresEliminadosTipo = new Hashtable<>();
        this.contadoresEnemigoTipo = new Hashtable<>();
        //inicializamos los contadores para todos los tipos a 0
        for (int i = 0; i < numeroTiposEnemigos; i++) {
            contadoresEnemigoTipo.put(i, 0);
            contadoresEliminadosTipo.put(i, 0);
        }
    }

    @Override
    public synchronized void generarEnemigo(int tipoEnemigo) {

        // Precondicion: Un enemigo de un tipo identificado con un número entero mayor
        // no podrá generarse hasta que se haya generado al menos un enemigo de un tipo con
        // un entero inmediatamente inferior. Es decir, no podrá generarse un enemigo de
        // un tipo 2 hasta que se hayan comenzado a generar los enemigos de tipo 1
        if (tipoEnemigo > 0) {
            int tipoEnemigoInferior = tipoEnemigo - 1;
            int cantidadEnemigosInferior = contadoresEnemigoTipo.get(tipoEnemigoInferior);
            int cantidadEnemigosInferiorEliminados = contadoresEliminadosTipo.get(tipoEnemigoInferior);
            while (cantidadEnemigosInferior <= 0 && cantidadEnemigosInferiorEliminados <= 0) {
                try {
                    wait();
                    cantidadEnemigosInferior = contadoresEnemigoTipo.get(tipoEnemigoInferior);
                    cantidadEnemigosInferiorEliminados = contadoresEliminadosTipo.get(tipoEnemigoInferior);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        int cantidadEnemigos = contadoresEnemigoTipo.get(tipoEnemigo);

        comprobarAntesDeGenerar();

        //obtenemos el valor de enemigos actual y lo incrementamos en 1
        cantidadEnemigos++;
        contadoresEnemigoTipo.put(tipoEnemigo, cantidadEnemigos);
        contadorEnemigosTotales++;

        String mensaje = String.format("Generado enemigo tipo %d", tipoEnemigo);
        imprimirInfo(mensaje);

        checkInvariante(); // Comprobamos que cumple invariante
        notifyAll(); // Se notifica el cambio de estado
    }

    @Override
    public synchronized void eliminarEnemigo(int tipoEnemigo) {

        int cantidadEliminados = contadoresEliminadosTipo.get(tipoEnemigo);
        int cantidadEnemigos = contadoresEnemigoTipo.get(tipoEnemigo);

        //comprobasmos que que para este tipo concreto de enemigo no se eliminen
        // si la cantidada actual es igual o menor a cero "0"
        while (cantidadEnemigos <= MIN_ENEMIGOS) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        comprobarAntesDeEliminar();

        //obtenemos el valor de enemigos actual y lo decrementamos en 1
        cantidadEliminados++;
        contadoresEliminadosTipo.put(tipoEnemigo, cantidadEliminados);
        cantidadEnemigos--;
        contadoresEnemigoTipo.put(tipoEnemigo, cantidadEnemigos);
        contadorEnemigosTotales--;

        String mensaje = String.format("Elimninado enemigo tipo %d", tipoEnemigo);
        imprimirInfo(mensaje);

        checkInvariante(); // Comprobamos que cumple invariante
        notifyAll(); // Se notifica el cambio de estado
    }

    @Override
    public int numeroMaximoEnemigos() {
        return MAX_ENEMIGOS;
    }

    private void imprimirInfo(String mensaje) {
        System.out.println(mensaje);
        System.out.println("--> Enemigos totales: " + contadorEnemigosTotales);

        // Iteramos por todos los enemigos y mostramos cantidades actuales
        for (Integer tipoEnemigo : contadoresEnemigoTipo.keySet()) {
            System.out.printf("----> Enemigos tipo %s: %s ------ [Eliminados:%s] %n",
                    tipoEnemigo,
                    contadoresEnemigoTipo.get(tipoEnemigo),
                    contadoresEliminadosTipo.get(tipoEnemigo));
        }
        System.out.println(" ");
    }

    public int sumarContadores() {
        int sumaContadoresPuerta = 0;
        Enumeration<Integer> iterEnemigos = contadoresEnemigoTipo.elements();
        while (iterEnemigos.hasMoreElements()) {
            sumaContadoresPuerta += iterEnemigos.nextElement();
        }
        return sumaContadoresPuerta;
    }

    protected void checkInvariante() {
        assert sumarContadores() == contadorEnemigosTotales
                : "INV: La suma de contadores de tipos debe ser igual al valor del contador total";
        assert contadorEnemigosTotales <= MAX_ENEMIGOS : "numero maximo de enemigos sobrepasado";
    }

    protected void comprobarAntesDeGenerar() {
        while (contadorEnemigosTotales >= MAX_ENEMIGOS) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    protected void comprobarAntesDeEliminar() {
        while (contadorEnemigosTotales <= MIN_ENEMIGOS) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
