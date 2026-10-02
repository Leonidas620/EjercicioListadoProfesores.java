import java.util.Objects;

public class ListaEnlazada<T> {
    private Nodo<T> cabeza;
    private int tamano;

    public ListaEnlazada() {
        cabeza = null;
        tamano = 0;
    }

    public void agregar(T dato) {
        Nodo<T> nuevo = new Nodo<>(dato);
        if (cabeza == null) {
            cabeza = nuevo;
        } else {
            Nodo<T> aux = cabeza;
            while (aux.getSiguiente() != null) {
                aux = aux.getSiguiente();
            }
            aux.setSiguiente(nuevo);
        }
        tamano++;
    }

    public Nodo<T> getCabeza() { return cabeza; }
    public void setCabeza(Nodo<T> cabeza) { this.cabeza = cabeza; }

    public int getTamano() { return tamano; }
    public boolean isEmpty() { return tamano == 0; }

    public Nodo<T> obtenerNodo(int indice) {
        Nodo<T> actual = cabeza;
        for (int i = 0; i < indice; i++) {
            actual = actual.getSiguiente();
        }
        return actual;
    }

    public void eliminarDuplicados() {
        Nodo<T> fijo = cabeza;
        while (fijo != null) {
            Nodo<T> anterior = fijo;
            Nodo<T> corredor = fijo.getSiguiente();
            while (corredor != null) {
                if (Objects.equals(fijo.getDato(), corredor.getDato())) {
                    anterior.setSiguiente(corredor.getSiguiente());
                    tamano--;
                } else {
                    anterior = corredor;
                }
                corredor = corredor.getSiguiente();
            }
            fijo = fijo.getSiguiente();
        }
    }

    // metodo para rotar a la derecha los elementos de la lista: A-B-C-D -> D-A-B-C
    public void rotarElementosDerecha() {
        if (cabeza == null || cabeza.getSiguiente() == null) {
            return;
        }
        Nodo<T> penultimo = cabeza;
        while (penultimo.getSiguiente().getSiguiente() != null) {
            penultimo = penultimo.getSiguiente();
        }
        Nodo<T> ultimo = penultimo.getSiguiente();
        penultimo.setSiguiente(null);
        ultimo.setSiguiente(cabeza);
        cabeza = ultimo;
    }

    public void concatenarListaCon(ListaEnlazada<T> o) {
        if (o == this) {
            throw new IllegalArgumentException("No se puede concatenar una lista consigo misma");
        }
        if (o.getCabeza() == null) {
            return;
        }
        if (cabeza == null) {
            cabeza = o.getCabeza();
        } else {
            Nodo<T> aux = cabeza;
            while (aux.getSiguiente() != null) {
                aux = aux.getSiguiente();
            }
            aux.setSiguiente(o.getCabeza());
        }
        tamano += o.getTamano();
    }
}
