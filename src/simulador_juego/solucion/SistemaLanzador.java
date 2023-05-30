package simulador_juego.solucion;

public class SistemaLanzador {
    public static void main(String[] args) {
        int numeroTiposEnemigos = 4;
        int numeroEnemigosPorTipo = 5;
        int numeroMaximoEnemigosTotales = 10;

        IJuego juego = new Juego(numeroTiposEnemigos, numeroEnemigosPorTipo, numeroMaximoEnemigosTotales);

        for (int tipoEnemigo = 0; tipoEnemigo < numeroTiposEnemigos; tipoEnemigo++) {
            // Creación de hilos de enemigos
            ActividadEnemiga enemigos = new ActividadEnemiga(tipoEnemigo, juego);
            new Thread(enemigos).start();

            // Creación de hilos de aliados
            ActividadAliada aliados = new ActividadAliada(tipoEnemigo, juego);
            new Thread(aliados).start();
        }
    }
}
