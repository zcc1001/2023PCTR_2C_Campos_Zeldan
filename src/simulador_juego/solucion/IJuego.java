package simulador_juego.solucion;

public interface IJuego {
    void generarEnemigo(int tipoEnemigo);

    void eliminarEnemigo(int tipoEnemigo);

    int numeroMaximoEnemigos();
}
