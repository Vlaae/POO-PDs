# 1. Documentación de lo implementado

## 1.1 Descripción general

La aplicación es una calculadora de escritorio hecha en Java (Swing) que mantiene un **acumulado** y lo multiplica por el número que el usuario escribe. El acumulado inicia en 1 y puede reiniciarse con el botón *Limpiar*. Se usa `BigDecimal` para no perder precisión y `toPlainString()` para evitar notación científica (por ejemplo `1E+2`).

El proyecto sigue el patrón **MVC**: el Modelo guarda y calcula el dato, la Vista lo muestra y captura la entrada, y el Controlador conecta ambos.

## 1.2 Estructura del proyecto

| Archivo | Clase | Rol MVC | Responsabilidad |
|---|---|---|---|
| `MainCalculadora.java` | `MainCalculadora` | Arranque | Crea modelo, vista y controlador, y muestra la ventana. |
| `ModeloCalculadora.java` | `ModeloCalculadora` | Modelo | Guarda el acumulado y realiza el cálculo. |
| `VistaCalculadora.java` | `VistaCalculadora` | Vista | Ventana Swing: cajas de texto y botones. No calcula. |
| `ControladorCalculadora.java` | `ControladorCalculadora` | Controlador | Escucha los botones, llama al modelo y actualiza la vista. |

## 1.3 Descripción de cada clase

### MainCalculadora

Punto de entrada (`main`). Instancia `ModeloCalculadora`, le pasa el modelo a `VistaCalculadora`, crea el `ControladorCalculadora` con ambos y hace visible la ventana.

### ModeloCalculadora

Contiene el atributo `resultadoActual` (`BigDecimal`) y la constante `VALOR_INICIAL = "1"`.

| Método | Descripción |
|---|---|
| `reiniciar()` | Regresa el acumulado a 1. |
| `multiplicar(String numero)` | Multiplica el acumulado por el número recibido. Lanza `NumberFormatException` si el texto no es válido. |
| `fijarValor(String valor)` | Asigna un valor directamente al acumulado. |
| `obtenerValor()` | Devuelve el acumulado como texto plano. |

### VistaCalculadora

Extiende `JFrame`. Contiene `cajaEntrada` (donde se escribe el número), `cajaResultado` (solo lectura), `botonMultiplicar` y `botonLimpiar`, organizados con un `FlowLayout`.

| Método | Descripción |
|---|---|
| `obtenerTextoEntrada()` | Devuelve lo que el usuario escribió. |
| `actualizarResultado(String)` | Muestra el nuevo total. |
| `limpiarPantalla()` | Deja el resultado en el valor inicial. |
| `mostrarError(String)` | Muestra un cuadro de diálogo con el mensaje. |
| `escucharMultiplicar(ActionListener)` / `escucharLimpiar(ActionListener)` | Permiten que el controlador registre sus listeners. |

### ControladorCalculadora

Recibe modelo y vista y registra dos clases internas como listeners:

- `EventoMultiplicar`: lee el texto, llama a `modelo.multiplicar(...)`, actualiza la vista y, si hay `NumberFormatException`, muestra un error.
- `EventoLimpiar`: llama a `modelo.reiniciar()` y limpia la pantalla.

## 1.4 Flujo de ejecución

**Multiplicar:** el usuario escribe un número y pulsa *Multiplicar*, `EventoMultiplicar` lee el texto desde la vista, el modelo actualiza el acumulado, el controlador pide el valor al modelo y la vista lo muestra.

**Limpiar:** el usuario pulsa *Limpiar*, `EventoLimpiar` reinicia el modelo y la vista muestra de nuevo el valor inicial.

## 1.5 Observaciones sobre el código actual

- La vista llama a `miModelo.fijarValor(VALOR_INICIAL)` en su constructor. Lo ideal es que la vista solo lea del modelo y que el controlador (o el `main`) decida el estado inicial.
- `VALOR_INICIAL` está duplicado en Modelo y Vista; `limpiarPantalla()` usa la constante de la vista en lugar de pedir el valor al modelo. Es mejor que la vista siempre muestre lo que dice el modelo.
- Iniciar en 1 tiene sentido para multiplicar, pero **no para sumar o restar**. Este punto hay que resolverlo al agregar operaciones (ver [estrategia de operaciones](03-estrategia-operaciones.md)).
- La vista no está desacoplada del modelo por completo: recibe el modelo en el constructor aunque solo necesita datos de él.
