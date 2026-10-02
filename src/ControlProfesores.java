public class ControlProfesores {
    private ListaEnlazada<Profesor> listadoProfesores = new ListaEnlazada<>();

    public void agregarProfesor(Profesor p) {
        listadoProfesores.agregar(p);
    }

    public ListaEnlazada<Profesor> getListadoProfesores() {
        return listadoProfesores;
    }

    // a) nombres de los instructores con más de 26 años (próximos a pasar a asistente)
    public ListaEnlazada<String> ProxCambio() {
        ListaEnlazada<String> listado = new ListaEnlazada<>();
        Nodo<Profesor> actual = listadoProfesores.getCabeza();

        while (actual != null) {
            Profesor p = actual.getDato();
            if (esCategoria(p, "instructor") && p.getEdad() > 26) {
                listado.agregar(p.getNombre());
            }
            actual = actual.getSiguiente();
        }
        return listado;
    }

    // b) información de los profesores ordenados por edad de menor a mayor
    public String MostrarLista() {
        ListaEnlazada<Profesor> copia = new ListaEnlazada<>();
        Nodo<Profesor> actual = listadoProfesores.getCabeza();
        while (actual != null) {
            copia.agregar(actual.getDato());
            actual = actual.getSiguiente();
        }

        if (copia.getCabeza() != null) {
            boolean huboIntercambio;
            do {
                huboIntercambio = false;
                Nodo<Profesor> n = copia.getCabeza();
                while (n.getSiguiente() != null) {
                    Nodo<Profesor> siguiente = n.getSiguiente();

                    if (n.getDato().compareTo(siguiente.getDato()) > 0) {
                        Profesor temp = n.getDato();
                        n.setDato(siguiente.getDato());
                        siguiente.setDato(temp);
                        huboIntercambio = true;
                    }
                    n = n.getSiguiente();
                }
            } while (huboIntercambio);
        }

        StringBuilder informacion = new StringBuilder();
        Nodo<Profesor> aux = copia.getCabeza();
        while (aux != null) {
            informacion.append(aux.getDato()).append("\n");
            aux = aux.getSiguiente();
        }
        return informacion.toString();
    }

    // c) cuenta la cantidad de profesores por categoria docente
    public String CantProfesores() {
        int instructores = 0;
        int asistentes = 0;
        int auxiliares = 0;
        int titulares = 0;

        Nodo<Profesor> aux = listadoProfesores.getCabeza();
        while (aux != null) {
            Profesor p = aux.getDato();
            if (p.getCategoria() != null) {
                switch (p.getCategoria().trim().toLowerCase()) {
                    case "instructor": instructores++; break;
                    case "asistente":  asistentes++;   break;
                    case "auxiliar":   auxiliares++;   break;
                    case "titular":    titulares++;    break;
                }
            }
            aux = aux.getSiguiente();
        }

        return "Cantidad de profesores por categoría:\n" +
                "- Instructores: " + instructores + "\n" +
                "- Asistentes: " + asistentes + "\n" +
                "- Auxiliares: " + auxiliares + "\n" +
                "- Titulares: " + titulares;
    }

    private boolean esCategoria(Profesor p, String categoria) {
        return p.getCategoria() != null && p.getCategoria().trim().equalsIgnoreCase(categoria);
    }
}
