public class Main {

    private static int pasadas = 0;
    private static int falladas = 0;

    public static void main(String[] args) {
        ControlProfesores control = new ControlProfesores();

        control.agregarProfesor(new Profesor("Ana",    30, "Instructor"));
        control.agregarProfesor(new Profesor("Luis",   25, "Instructor"));  // 25: no supera 26
        control.agregarProfesor(new Profesor("Marta",  27, "Instructor"));  // 27: justo por encima del límite
        control.agregarProfesor(new Profesor("Carlos", 26, "Instructor"));  // 26: borde exacto, NO cumple (>26)
        control.agregarProfesor(new Profesor("Elena",  45, "Titular"));
        control.agregarProfesor(new Profesor("Pedro",  38, "Auxiliar"));
        control.agregarProfesor(new Profesor("Sofia",  31, "Asistente"));   // asistente mayor de 26: no cuenta
        control.agregarProfesor(new Profesor("Raul",   40, "Instructor"));
        control.agregarProfesor(new Profesor("Jorge",  30, "Asistente"));   // empata en edad con Ana (30)
        // Raul no es el último nodo; el último es Jorge. Más abajo se prueba el último nodo aparte.

        System.out.println("=== a) ProxCambio ===");
        ListaEnlazada<String> prox = control.ProxCambio();
        mostrar(prox);
        verificarNombres("Próximos a cambio: Ana, Marta, Raul", prox, "Ana", "Marta", "Raul");

        System.out.println("\n=== b) MostrarLista (edad de menor a mayor) ===");
        String lista = control.MostrarLista();
        System.out.print(indentar(lista));
        String[] lineas = lista.split("\n");
        verificar("Hay 9 líneas (una por profesor)", lineas.length == 9);
        verificar("Primero el menor (Luis, 25)", lineas[0].contains("Luis"));
        verificar("Último el mayor (Elena, 45)", lineas[8].contains("Elena"));
        verificar("Orden completo ascendente", ordenEdades(lineas, 25, 26, 27, 30, 30, 31, 38, 40, 45));
        verificar("Empate de edad estable: Ana antes que Jorge",
                lista.indexOf("Ana") < lista.indexOf("Jorge"));
        verificar("No se imprimen nodos (sin 'Nodo@')", !lista.contains("Nodo@"));
        verificar("La lista original NO se alteró (sigue empezando por Ana)",
                control.getListadoProfesores().getCabeza().getDato().getNombre().equals("Ana"));

        System.out.println("\n=== c) CantProfesores ===");
        String cant = control.CantProfesores();
        System.out.println(indentar(cant));
        verificar("Conteo exacto por categoría",
                cant.equals("Cantidad de profesores por categoría:\n" +
                        "- Instructores: 5\n" +
                        "- Asistentes: 2\n" +
                        "- Auxiliares: 1\n" +
                        "- Titulares: 1"));

        System.out.println("\n=== Casos borde ===");
        ControlProfesores vacio = new ControlProfesores();
        verificar("Lista vacía: ProxCambio vacío", vacio.ProxCambio().isEmpty());
        verificar("Lista vacía: MostrarLista devuelve cadena vacía", vacio.MostrarLista().isEmpty());
        verificar("Lista vacía: todos los conteos en 0",
                vacio.CantProfesores().contains("Instructores: 0") && vacio.CantProfesores().contains("Titulares: 0"));

        ControlProfesores uno = new ControlProfesores();
        uno.agregarProfesor(new Profesor("Solo", 50, "Instructor"));
        verificarNombres("Un solo profesor: ProxCambio", uno.ProxCambio(), "Solo");
        verificar("Un solo profesor: MostrarLista tiene 1 línea", uno.MostrarLista().split("\n").length == 1);
        verificar("Un solo profesor: 1 instructor", uno.CantProfesores().contains("Instructores: 1"));

        ControlProfesores ultimo = new ControlProfesores();   // el cumplidor está en el ÚLTIMO nodo
        ultimo.agregarProfesor(new Profesor("Joven", 20, "Instructor"));
        ultimo.agregarProfesor(new Profesor("Viejo", 60, "Titular"));
        ultimo.agregarProfesor(new Profesor("Ultimo", 33, "Instructor"));
        verificarNombres("Cumplidor en el último nodo", ultimo.ProxCambio(), "Ultimo");

        ControlProfesores sinInstructores = new ControlProfesores();
        sinInstructores.agregarProfesor(new Profesor("A", 40, "Titular"));
        sinInstructores.agregarProfesor(new Profesor("B", 35, "Asistente"));
        verificar("Sin instructores: ProxCambio vacío", sinInstructores.ProxCambio().isEmpty());

        ControlProfesores mayusculas = new ControlProfesores();   // categorías escritas distinto
        mayusculas.agregarProfesor(new Profesor("M1", 30, "INSTRUCTOR"));
        mayusculas.agregarProfesor(new Profesor("M2", 30, "instructor"));
        mayusculas.agregarProfesor(new Profesor("M3", 30, " Instructor "));
        verificarNombres("Categorías con distinto formato", mayusculas.ProxCambio(), "M1", "M2", "M3");
        verificar("Conteo ignora mayúsculas/espacios", mayusculas.CantProfesores().contains("Instructores: 3"));

        ControlProfesores descendente = new ControlProfesores();   // entrada de mayor a menor (peor caso de la burbuja)
        for (int e = 24; e >= 20; e--) descendente.agregarProfesor(new Profesor("P" + e, e, "Auxiliar"));
        verificar("Entrada descendente (peor caso burbuja) queda ascendente",
                ordenEdades(descendente.MostrarLista().split("\n"), 20, 21, 22, 23, 24));

        ControlProfesores yaOrdenada = new ControlProfesores();    // entrada ya ordenada: no debe cambiar
        for (int e = 20; e <= 24; e++) yaOrdenada.agregarProfesor(new Profesor("Q" + e, e, "Auxiliar"));
        verificar("Entrada ya ascendente se mantiene igual",
                ordenEdades(yaOrdenada.MostrarLista().split("\n"), 20, 21, 22, 23, 24));

        System.out.println("\n=== Clase práctica 2: operaciones de ListaEnlazada ===");

        System.out.println("-- 1. eliminarDuplicados --");
        ListaEnlazada<String> d1 = de("A", "B", "A", "C", "B");
        d1.eliminarDuplicados();
        verificarLista("A-B-A-C-B -> A-B-C", d1, "A-B-C");
        verificar("Tamaño actualizado (3)", d1.getTamano() == 3);
        ListaEnlazada<String> d2 = de("A", "A", "A", "A");
        d2.eliminarDuplicados();
        verificarLista("A-A-A-A -> A", d2, "A");
        ListaEnlazada<String> d3 = de("A", "B", "C");
        d3.eliminarDuplicados();
        verificarLista("Sin repetidos queda igual", d3, "A-B-C");
        ListaEnlazada<String> d4 = de("A", "B", "B");
        d4.eliminarDuplicados();
        verificarLista("Repetido en el último nodo: A-B-B -> A-B", d4, "A-B");
        ListaEnlazada<String> d5 = de();
        d5.eliminarDuplicados();
        verificarLista("Lista vacía no falla", d5, "");

        System.out.println("-- 2. rotarElementosDerecha --");
        ListaEnlazada<String> r1 = de("A", "B", "C", "D");
        r1.rotarElementosDerecha();
        verificarLista("A-B-C-D -> D-A-B-C", r1, "D-A-B-C");
        verificar("Tamaño se mantiene (4)", r1.getTamano() == 4);
        verificar("La nueva cabeza es D", r1.getCabeza().getDato().equals("D"));
        ListaEnlazada<String> r2 = de("A", "B");
        r2.rotarElementosDerecha();
        verificarLista("Dos elementos: A-B -> B-A", r2, "B-A");
        ListaEnlazada<String> r3 = de("A");
        r3.rotarElementosDerecha();
        verificarLista("Un elemento queda igual", r3, "A");
        ListaEnlazada<String> r4 = de();
        r4.rotarElementosDerecha();
        verificarLista("Lista vacía no falla", r4, "");
        ListaEnlazada<String> r5 = de("A", "B", "C", "D");
        for (int i = 0; i < 4; i++) r5.rotarElementosDerecha();
        verificarLista("4 rotaciones en lista de 4 vuelven al original", r5, "A-B-C-D");

        System.out.println("-- 3. concatenarListaCon --");
        ListaEnlazada<String> c1 = de("A", "B", "C", "D");
        c1.concatenarListaCon(de("E", "F", "G", "H"));
        verificarLista("A-B-C-D + E-F-G-H -> A-B-C-D-E-F-G-H", c1, "A-B-C-D-E-F-G-H");
        verificar("Tamaño actualizado (8)", c1.getTamano() == 8);
        ListaEnlazada<String> c2 = de("A", "B");
        c2.concatenarListaCon(de());
        verificarLista("Concatenar con una lista vacía no cambia nada", c2, "A-B");
        ListaEnlazada<String> c3 = de();
        c3.concatenarListaCon(de("X", "Y"));
        verificarLista("Concatenar sobre una lista vacía", c3, "X-Y");
        verificar("Tamaño sobre lista vacía (2)", c3.getTamano() == 2);
        ListaEnlazada<String> c4 = de();
        c4.concatenarListaCon(de());
        verificarLista("Dos listas vacías", c4, "");
        ListaEnlazada<String> c5 = de("A", "B");
        boolean lanzo = false;
        try { c5.concatenarListaCon(c5); } catch (IllegalArgumentException e) { lanzo = true; }
        verificar("Concatenar una lista consigo misma lanza IllegalArgumentException", lanzo);
        verificarLista("...y la lista queda intacta", c5, "A-B");

        System.out.println("\nRESULTADO: " + pasadas + " pasadas, " + falladas + " falladas");
    }

    // ---------- utilidades de la clase práctica 2 ----------

    private static ListaEnlazada<String> de(String... elementos) {
        ListaEnlazada<String> lista = new ListaEnlazada<>();
        for (String e : elementos) lista.agregar(e);
        return lista;
    }

    private static void verificarLista(String descripcion, ListaEnlazada<String> lista, String esperado) {
        StringBuilder sb = new StringBuilder();
        Nodo<String> n = lista.getCabeza();
        int limite = 100;                              // evita bucles infinitos si hubiera un ciclo
        while (n != null && limite-- > 0) {
            if (sb.length() > 0) sb.append("-");
            sb.append(n.getDato());
            n = n.getSiguiente();
        }
        verificar(descripcion, sb.toString().equals(esperado));
    }

    // ---------- utilidades de prueba ----------

    private static void mostrar(ListaEnlazada<String> lista) {
        StringBuilder sb = new StringBuilder();
        Nodo<String> n = lista.getCabeza();
        while (n != null) {
            sb.append(n.getDato());
            if (n.getSiguiente() != null) sb.append(", ");
            n = n.getSiguiente();
        }
        System.out.println("  [" + sb + "]");
    }

    private static String indentar(String texto) {
        return "  " + texto.replace("\n", "\n  ").replaceAll("\\s+$", "") + "\n";
    }

    private static void verificar(String descripcion, boolean condicion) {
        if (condicion) pasadas++; else falladas++;
        System.out.println("  [" + (condicion ? "PASA " : "FALLA") + "] " + descripcion);
    }

    private static void verificarNombres(String descripcion, ListaEnlazada<String> obtenida, String... esperados) {
        boolean ok = obtenida.getTamano() == esperados.length;
        Nodo<String> n = obtenida.getCabeza();
        for (int i = 0; ok && i < esperados.length; i++) {
            ok = n != null && n.getDato().equals(esperados[i]);
            if (n != null) n = n.getSiguiente();
        }
        verificar(descripcion, ok);
    }

    // Comprueba que cada línea contenga "edad=<esperada>" en el orden dado.
    private static boolean ordenEdades(String[] lineas, int... edades) {
        if (lineas.length != edades.length) return false;
        for (int i = 0; i < edades.length; i++) {
            if (!lineas[i].contains("edad=" + edades[i] + ",")) return false;
        }
        return true;
    }
}
