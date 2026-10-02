# Control de estudiantes – Lista simplemente enlazada

Ejercicio de **Estructuras de Datos** (Java). Sistema que gestiona los datos de los estudiantes del departamento de Informática sobre una lista simplemente enlazada propia (sin usar `LinkedList` ni otras colecciones de Java).

**Autor:** [Tu nombre completo y grupo]

## Enunciado resumido

De cada estudiante se conoce **CI, nombre, apellido, sexo, año, si es militante de la UJC y si es becado**. Se piden:

- **a)** Cumpleaños(String mes): listado con los nombres de los estudiantes que cumplen años en un mes dado.
- **b)** CantMilitantes(): listado con la información de los estudiantes militantes de la UJC, ordenados por año de menor a mayor.
- **c)** CantBecados(): cantidad de estudiantes becados.

## Estructura del proyecto

| Archivo | Responsabilidad |
|---|---|
| `Nodo.java` | Nodo genérico: guarda un dato y la referencia al siguiente. |
| `ListaEnlazada.java` | Lista simplemente enlazada genérica (`agregar`, `getCabeza`, `getTamano`, ...). |
| `Estudiante.java` | Datos del estudiante. Implementa `Comparable` por año (menor a mayor). |
| `ControlEstudiante.java` | Contiene la lista de estudiantes y los tres métodos pedidos. |
| `Main.java` | Casos de prueba que verifican cada método. |

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

Los nombres de los métodos no coinciden exactamente con los del enunciado:

| Enunciado | Método implementado | Devuelve | Descripción |
|---|---|---|---|
| Cumpleaños(mes) | `listarPorCumpleanos(String mes)` | `ListaEnlazada<String>` | Nombres de los estudiantes que nacieron en ese mes. |
| CantMilitantes() | `listarMilitantes()` | `ListaEnlazada<Estudiante>` | Militantes de la UJC ordenados por año, de menor a mayor. |
| CantBecados() | `cantBecados()` | `int` | Cantidad de estudiantes becados. |

Ejemplo con los datos de prueba de `Main`:

```
listarPorCumpleanos("03")  ->  [Luis, Marta, Elena]
listarMilitantes()         ->  Ana (1.º), Pedro (1.º), Carlos (2.º), Luis (3.º), Elena (5.º)
cantBecados()              ->  3
```

## Decisiones y supuestos

- **Mes de cumpleaños:** se obtiene del **CI**, que tiene formato `AAMMDDxxxxx`; el mes son los caracteres de las posiciones 2 y 3. El mes se pasa como `"01"` a `"12"`; también se acepta sin cero (`"3"` equivale a `"03"`). Un mes que no existe (por ejemplo `"13"`) simplemente devuelve una lista vacía.
- **Nombres de métodos:** `CantMilitantes()` del enunciado se implementó como `listarMilitantes()`, porque el enunciado pide un **listado** y no una cantidad, y el nombre original resulta engañoso. Los demás nombres siguen la convención de Java (minúscula inicial).
- **Orden de los militantes:** de menor a mayor año, con burbuja sobre una lista nueva, por lo que la lista original de estudiantes no se altera. Los estudiantes del mismo año mantienen su orden de inserción.
- **Tipos de retorno:** los listados se devuelven como `ListaEnlazada` (genérica), no como arreglos.
- **Lista vacía:** los métodos de listado devuelven una lista vacía y `cantBecados()` devuelve 0.
- Se añadió `agregarEstudiante(Estudiante)` en `ControlEstudiante` para poder cargar los datos.
- En `Estudiante`, el método que devuelve el sexo se llama `getSexo()` (el prefijo `is` se reserva para valores booleanos).

## Pruebas

`Main` carga siete estudiantes y ejecuta casos de prueba que imprimen `PASA` o `FALLA` por cada verificación. Cubren:

- **Cumpleaños:** mes con varios estudiantes, mes con uno solo, un estudiante en el último nodo de la lista, un mes sin nadie y el mes escrito sin cero (`"3"`).
- **Militantes:** orden por año, empate de año con orden de inserción conservado y tamaño del listado.
- **Becados:** cantidad esperada.
- **Casos borde:** lista vacía, un solo estudiante, sin militantes ni becados y entrada en orden inverso de año.

Al final se imprime un resumen (`RESULTADO: N pasadas, 0 falladas`). El programa funciona correctamente cuando aparecen **0 falladas**.
