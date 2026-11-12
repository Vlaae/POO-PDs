# 3. Estrategia para incrementar las operaciones

## 3.1 Objetivo

Agregar suma, resta, división (y las que vengan después) **sin romper MVC** y modificando la menor cantidad de código posible.

## 3.2 Idea central

Cada operación es una **estrategia de cálculo** que recibe dos valores y devuelve un resultado. Se representa con un `enum Operacion` que guarda la etiqueta del botón y la función matemática. El modelo expone un único método `aplicar(Operacion, String)` en lugar de un método por operación (`multiplicar`, `sumar`, `restar`...).

## 3.3 Cambios por capa (se mantiene MVC)

| Capa | Cambio | Qué NO cambia |
|---|---|---|
| Modelo | Reemplaza `multiplicar` por `aplicar(Operacion op, String numero)`, que usa `op.aplicar(resultadoActual, new BigDecimal(numero))`. Se elimina `fijarValor`. | Sigue sin conocer Swing ni la vista. |
| Vista | Recorre `Operacion.values()` y crea un botón por operación. Expone `escucharOperacion(op, listener)`. Ya no recibe ni modifica el modelo. | Sigue sin calcular nada. |
| Controlador | Una sola clase interna `EventoOperacion` parametrizada con la operación, en lugar de una clase por botón. Captura también `ArithmeticException` (división entre cero). | Sigue siendo el único que comunica modelo y vista. |

## 3.4 Decisiones de diseño tomadas

1. **Valor inicial.** Con 1 funcionaba para multiplicar, pero 1 + 5 daría 6. Se eligió iniciar en **0** y que la primera operación después de reiniciar tome el número escrito como primer operando. En el modelo se implementa con la bandera `esperandoPrimerOperando`.
2. **División.** `BigDecimal.divide` sin `MathContext` lanza `ArithmeticException` con resultados no exactos (1/3). Por eso se usa `MathContext.DECIMAL128`. La división entre cero también lanza `ArithmeticException`, que el controlador captura y muestra como error sin modificar el acumulado.
3. **Limpieza de MVC.** La vista dejó de llamar a `fijarValor` y de recibir el modelo. El controlador, al construirse, muestra el valor inicial que indica el modelo, y la vista siempre muestra lo que el controlador le pide.
4. **Orden de las validaciones.** El modelo convierte el texto a `BigDecimal` antes de modificar el acumulado, así que un texto inválido no altera el resultado.
