public class Profesor implements Comparable<Profesor> {
    private String nombre;
    private int edad;
    private String categoria;

    public Profesor(String nombre, int edad, String categoria) {
        this.nombre = nombre;
        this.edad = edad;
        this.categoria = categoria;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public int getEdad() {
        return edad;
    }

    @Override
    public String toString() {
        return "Profesor{" +
                "nombre='" + nombre + '\'' +
                ", edad=" + edad +
                ", categoria='" + categoria + '\'' +
                '}';
    }

    // Orden natural: por edad ascendente (de menor a mayor).
    @Override
    public int compareTo(Profesor o) {
        return Integer.compare(this.edad, o.getEdad());
    }
}
