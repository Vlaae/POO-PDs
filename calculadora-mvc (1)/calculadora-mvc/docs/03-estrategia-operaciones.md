# 3. Estrategia para incrementar las operaciones

## 3.1 Objetivo

Agregar suma, resta, división (y las que vengan después) **sin romper MVC** y modificando la menor cantidad de código posible.

## 3.2 Idea central

Cada operación es una **estrategia de cálculo** que recibe dos valores y devuelve un resultado. Se representa con un `enum Operacion` que guarda la etiqueta del botón y la función matemática. El modelo expone un único método `aplicar(Operacion, String)` en lugar de un método por operación (`multiplicar`, `sumar`, `restar`...).

```java
package src;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.function.BinaryOperator;

public enum Operacion {
    SUMAR("Sumar", BigDecimal::add),
    RESTAR("Restar", BigDecimal::subtract),
    MULTIPLICAR("Multiplicar", BigDecimal::multiply),
    DIVIDIR("Dividir", (a, b) -> a.divide(b, MathContext.DECIMAL128));

    private final String etiqueta;
    private final BinaryOperator<BigDecimal> funcion;

    Operacion(String etiqueta, BinaryOperator<BigDecimal> funcion) {
        this.etiqueta = etiqueta;
        this.funcion = funcion;
    }

    public BigDecimal aplicar(BigDecimal a, BigDecimal b) { return funcion.apply(a, b); }
    public String etiqueta() { return etiqueta; }
}
```

## 3.3 Cambios por capa (se mantiene MVC)

| Capa | Cambio | Qué NO cambia |
|---|---|---|
| Modelo | Reemplaza `multiplicar` por `aplicar(Operacion op, String numero)`: `resultadoActual = op.aplicar(resultadoActual, new BigDecimal(numero))`. | Sigue sin conocer Swing ni la vista. |
| Vista | Recorre `Operacion.values()` y crea un botón por operación. Expone `escucharOperacion(op, listener)`. | Sigue sin calcular nada. |
| Controlador | Una sola clase interna `EventoOperacion` parametrizada con la operación, en lugar de una clase por botón. Captura también `ArithmeticException` (división entre cero). | Sigue siendo el único que comunica modelo y vista. |

```java
class EventoOperacion implements ActionListener {
    private final Operacion operacion;
    EventoOperacion(Operacion operacion) { this.operacion = operacion; }

    public void actionPerformed(ActionEvent e) {
        String texto = vista.obtenerTextoEntrada().trim();
        try {
            modelo.aplicar(operacion, texto);
            vista.actualizarResultado(modelo.obtenerValor());
        } catch (NumberFormatException ex) {
            vista.mostrarError("Pon un número válido: '" + texto + "'");
        } catch (ArithmeticException ex) {
            vista.mostrarError("Operación no válida (por ejemplo, división entre cero).");
        }
    }
}
```

Para agregar una operación nueva (por ejemplo potencia o módulo) basta con **añadir una línea al enum**. Los botones y los listeners se generan solos al recorrer `Operacion.values()`.

## 3.4 Decisiones de diseño a tomar

1. **Valor inicial.** Con 1 funciona para multiplicar, pero 1 + 5 daría 6, lo que sería confuso. Opciones: (a) iniciar en 0 y que la primera operación tome el número escrito como primer operando; (b) que cada operación tenga su elemento neutro. Se recomienda (a) por ser lo que espera un usuario de una calculadora.
2. **División.** `BigDecimal.divide` sin `MathContext` lanza `ArithmeticException` con resultados no exactos (1/3). Por eso se usa `MathContext.DECIMAL128`. La división entre cero también lanza `ArithmeticException`, que el controlador ya captura.
3. **Limpieza de MVC.** Aprovechando el cambio, la vista debe dejar de llamar a `fijarValor` y mostrar siempre `modelo.obtenerValor()`.
