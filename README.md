# Control de profesores y operaciones sobre lista enlazada

Ejercicio de **Estructuras de Datos** (Java). Contiene dos partes que comparten la misma lista simplemente enlazada propia (sin usar `LinkedList` ni otras colecciones de Java):

1. Sistema de control de **profesores** del departamento de Informática.
2. **Clase práctica 2**: operaciones sobre una lista simplemente enlazada (eliminar repetidos, rotar y concatenar).

## Enunciados resumidos

### Parte 1: Control de profesores

De cada profesor se conoce **nombre, edad y categoría docente** (Instructor, Asistente, Auxiliar, Titular). Se piden:

- **a)** `ProxCambio()`: nombres de los profesores próximos a pasar a asistente (instructores con más de 26 años).
- **b)** `MostrarLista()`: información de los profesores ordenados por edad.
- **c)** `CantProfesores()`: cantidad de profesores por categoría docente, en un `String`.

### Parte 2: Clase práctica 2

Dada una lista simplemente enlazada:

1. Eliminar los elementos repetidos.
2. Rotar una posición a la derecha: `A-B-C-D` → `D-A-B-C`.
3. Concatenar dos listas: `A-B-C-D` y `E-F-G-H` → `A-B-C-D-E-F-G-H`.

## Estructura del proyecto

| Archivo | Responsabilidad |
|---|---|
| `Nodo.java` | Nodo genérico: guarda un dato y la referencia al siguiente. |
| `ListaEnlazada.java` | Lista genérica. Además de `agregar`, `getCabeza`, `getTamano`, ..., contiene los tres métodos de la **Parte 2**. |
| `Profesor.java` | Datos del profesor. Implementa `Comparable` por edad (menor a mayor). |
| `ControlProfesores.java` | Contiene la lista de profesores y los tres métodos de la **Parte 1**. |
| `Main.java` | Casos de prueba de ambas partes. |

## Cómo ejecutarlo

Requiere un JDK instalado. Probado con **JDK 21**; el código no usa características recientes del lenguaje.

Desde la carpeta del proyecto:

```
javac -encoding UTF-8 *.java
java Main
```

(En IntelliJ: abrir la carpeta como proyecto y ejecutar `Main`.)

Si las tildes se ven mal en la consola, ejecutar con `java -Dfile.encoding=UTF-8 Main`.

## Métodos

### Parte 1 (en `ControlProfesores`)

| Método | Devuelve | Descripción |
|---|---|---|
| `ProxCambio()` | `ListaEnlazada<String>` | Nombres de los instructores con edad **mayor que 26**. |
| `MostrarLista()` | `String` | Una línea por profesor, ordenados por edad **de menor a mayor**. |
| `CantProfesores()` | `String` | Texto con la cantidad de profesores de cada categoría. |

Ejemplo con los datos de prueba de `Main`:

```
ProxCambio()  ->  [Ana, Marta, Raul]

CantProfesores()  ->
Cantidad de profesores por categoría:
- Instructores: 5
- Asistentes: 2
- Auxiliares: 1
- Titulares: 1
```

### Parte 2 (en `ListaEnlazada`)

| Método | Devuelve | Descripción |
|---|---|---|
| `eliminarDuplicados()` | `void` | Elimina los elementos repetidos, conservando la primera aparición. |
| `rotarElementosDerecha()` | `void` | Mueve el último elemento al inicio. |
| `concatenarListaCon(ListaEnlazada<T> o)` | `void` | Agrega al final de esta lista los elementos de `o`. |

Ejemplos:

```
eliminarDuplicados():          A-B-A-C-B        ->  A-B-C
rotarElementosDerecha():       A-B-C-D          ->  D-A-B-C
concatenarListaCon():          A-B-C-D + E-F-G-H ->  A-B-C-D-E-F-G-H
```

## Decisiones y supuestos

**Parte 1**

- **Orden por edad:** ascendente (menor a mayor). Se ordena con burbuja sobre una **copia**, por lo que la lista original conserva su orden de inserción. Los empates de edad mantienen su orden original.
- **Límite de edad:** la condición es estricta (`> 26`); un instructor de exactamente 26 años no entra en `ProxCambio()`.
- **Tipos de retorno:** `ProxCambio()` devuelve nombres (no objetos `Profesor`), como pide el enunciado.
- **Lista vacía:** `ProxCambio()` devuelve una lista vacía, `MostrarLista()` una cadena vacía y `CantProfesores()` todos los conteos en 0.
- Se añadió `agregarProfesor(Profesor)` en `ControlProfesores` para poder cargar los datos.

**Parte 2**

- **Eliminar repetidos:** compara con `equals`, sin memoria extra (dos punteros, O(n²)). Como `Profesor` no sobrescribe `equals`, dos objetos `Profesor` distintos con los mismos datos **no** se consideran repetidos; la operación se probó con listas de `String`.
- **Rotar:** modifica la lista en el sitio. Con lista vacía o de un solo elemento no hace nada.
- **Concatenar:** modifica la lista que llama al método. Los nodos de la otra lista **pasan a formar parte** de esta (no se copian), así que esa otra lista no debe modificarse después. Concatenar una lista consigo misma lanza `IllegalArgumentException`, porque crearía un ciclo infinito.
- **Tamaño:** los tres métodos mantienen `getTamano()` actualizado.

## Pruebas

`Main` ejecuta casos de prueba que imprimen `PASA` o `FALLA` por cada verificación.

**Parte 1**
- `ProxCambio`: edad justo en el límite (26 y 27), asistentes mayores de 26 que no deben entrar y un cumplidor en el último nodo.
- `MostrarLista`: orden completo, empates de edad, que no se altere la lista original y entradas ya ordenadas o en orden inverso.
- `CantProfesores`: texto exacto con el conteo por categoría.
- Casos borde: lista vacía, un solo profesor, sin instructores y categorías con formato distinto.

**Parte 2**
- `eliminarDuplicados`: repetidos en distintas posiciones, todos iguales, sin repetidos y lista vacía.
- `rotarElementosDerecha`: el ejemplo `A-B-C-D`, listas de 0, 1 y 2 elementos, y que tantas rotaciones como elementos devuelvan la lista original.
- `concatenarListaCon`: el ejemplo `A-B-C-D` + `E-F-G-H`, con una de las listas vacía o ambas, tamaño actualizado y concatenación consigo misma.

Al final se imprime un resumen (`RESULTADO: N pasadas, 0 falladas`). El programa funciona correctamente cuando aparecen **0 falladas**.
