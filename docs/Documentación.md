# 1. Documentación de lo implementado

## 1.1 Descripción general

La calculadora aplica suma, resta, multiplicación y división sobre un acumulado BigDecimal. El acumulado inicia en 0 y, después de iniciar o limpiar, la primera operación toma el número escrito como primer operando. A partir de ahí, cada operación se aplica al acumulado con el número escrito. La vista genera un botón por cada valor del enum Operacion, así que agregar una operación nueva solo requiere una línea en el enum.

El proyecto sigue el patrón **MVC**: el Modelo guarda y calcula el dato, la Vista lo muestra y captura la entrada, y el Controlador conecta ambos.

## 1.2 Estructura del proyecto

| Archivo | Clase | Rol MVC | Responsabilidad |
|---|---|---|---|
| `MainCalculadora.java` | `MainCalculadora` | Arranque | Crea modelo, vista y controlador, y muestra la ventana. |
| `Operacion.java` | `Operacion` (enum) | Modelo (estrategias) | Define cada operación: etiqueta y fórmula. |
| `ModeloCalculadora.java` | `ModeloCalculadora` | Modelo | Guarda el acumulado y aplica la operación elegida. |
| `VistaCalculadora.java` | `VistaCalculadora` | Vista | Ventana Swing con un botón por operación. No calcula. |
| `ControladorCalculadora.java` | `ControladorCalculadora` | Controlador | Conecta botones, modelo y vista. |

## 1.3 Descripción de cada clase

#### Operacion

Enum con un campo `etiqueta` (texto del botón) y un campo `funcion` (`BinaryOperator<BigDecimal>`).

| Valor | Etiqueta | Fórmula |
|---|---|---|
| `SUMAR` | Sumar | `BigDecimal::add` |
| `RESTAR` | Restar | `BigDecimal::subtract` |
| `MULTIPLICAR` | Multiplicar | `BigDecimal::multiply` |
| `DIVIDIR` | Dividir | `a.divide(b, MathContext.DECIMAL128)` |

| Método | Descripción |
|---|---|
| `aplicar(BigDecimal a, BigDecimal b)` | Ejecuta la fórmula de la operación con los dos valores. |
| `etiqueta()` | Devuelve el texto que se muestra en el botón. |

`MathContext.DECIMAL128` evita que divisiones no exactas (como 1/3) lancen excepción.

#### ModeloCalculadora

Atributos: `VALOR_INICIAL = "0"`, `resultadoActual` (`BigDecimal`) y `esperandoPrimerOperando` (`boolean`).

| Método | Descripción |
|---|---|
| `reiniciar()` | Regresa el acumulado a 0 y marca que se espera el primer operando. |
| `aplicar(Operacion operacion, String numero)` | Convierte el texto a `BigDecimal` y, si es la primera vez tras reiniciar, lo toma como acumulado; si no, aplica la operación. Lanza `NumberFormatException` (texto inválido) o `ArithmeticException` (por ejemplo, división entre cero). El acumulado no cambia si hay error. |
| `obtenerValor()` | Devuelve el acumulado como texto plano. |

#### VistaCalculadora

Extiende `JFrame` y ya **no recibe el modelo**. Contiene `cajaEntrada`, `cajaResultado` (solo lectura), `botonLimpiar` y un `Map<Operacion, JButton>` (`botonesOperacion`) que se llena recorriendo `Operacion.values()`.

| Método | Descripción |
|---|---|
| `obtenerTextoEntrada()` | Devuelve lo que el usuario escribió. |
| `actualizarResultado(String)` | Muestra el nuevo total. |
| `mostrarError(String)` | Muestra un cuadro de diálogo con el mensaje. |
| `escucharOperacion(Operacion, ActionListener)` | Registra el listener del botón de esa operación. |
| `escucharLimpiar(ActionListener)` | Registra el listener del botón *Limpiar*. |

#### ControladorCalculadora

Al construirse muestra en la vista el valor inicial que indica el modelo, registra un `EventoOperacion` por cada valor de `Operacion` y un `EventoLimpiar`.

- `EventoOperacion`: lee el texto, llama a `modelo.aplicar(operacion, texto)` y actualiza la vista. Captura `NumberFormatException` (número inválido) y `ArithmeticException` (operación no válida) y muestra el error.
- `EventoLimpiar`: reinicia el modelo y muestra el valor que éste indica.

#### MainCalculadora

Crea `ModeloCalculadora`, `VistaCalculadora` (sin parámetros) y `ControladorCalculadora`, y hace visible la ventana.

## 1.4 Flujo de ejecución

**Operación (sumar, restar, multiplicar o dividir):** el usuario escribe un número y pulsa el botón de la operación, `EventoOperacion` lee el texto desde la vista, el modelo aplica la operación al acumulado y la vista muestra el nuevo valor. Si hay error, la vista muestra un mensaje y el acumulado se conserva.

**Limpiar:** el modelo vuelve a 0 y la vista muestra ese valor.

### 1.5 Cambios respecto a la versión original

| Aspecto | Versión original | Versión final |
|---|---|---|
| Operaciones | Solo multiplicar | Sumar, restar, multiplicar y dividir |
| Método del modelo | `multiplicar(String)` y `fijarValor(String)` | `aplicar(Operacion, String)` |
| Valor inicial | 1 | 0 (la primera operación toma el número escrito) |
| Botones de la vista | Uno fijo para multiplicar | Uno por cada valor de `Operacion` |
| Vista y modelo | La vista recibía el modelo y lo modificaba | La vista no conoce el modelo |
| Clases de evento | `EventoMultiplicar` | `EventoOperacion` (una clase para todas las operaciones) |
| Errores controlados | Número inválido | Número inválido y operación no válida (división entre cero) |
| Agregar una operación | Método + botón + listener | Una línea en el enum |
