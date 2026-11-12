# 4. Análisis de patrones de diseño

## 4.1 ¿Conviene el patrón Command?

### Cómo se aplicaría

Se crearía una interfaz `Comando` con `ejecutar()` (y opcionalmente `deshacer()`). Cada operación sería una clase concreta (`ComandoSumar`, `ComandoRestar`...) que tiene como **receptor** al modelo y guarda el operando. El controlador crearía el comando y lo pasaría a un **invocador** (por ejemplo, un `Historial`) que lo ejecuta.

### Ventajas específicas

- **Deshacer/rehacer.** Es el beneficio más fuerte: cada comando sabe revertirse y el historial guarda una pila.
- **Historial de operaciones y macros.** Se puede listar, repetir o guardar la secuencia de cálculos.
- **Desacople.** El invocador no sabe qué operación ejecuta; solo llama a `ejecutar()`.
- **Cola o registro.** Los comandos se pueden encolar, guardar en archivo o ejecutar después.

### Desventajas específicas en esta calculadora

- **Explosión de clases.** Cuatro operaciones implican cuatro clases más la interfaz y el invocador. Cada operación nueva es otra clase, cuando en realidad solo cambia una fórmula.
- **Sobreingeniería.** Hoy el controlador ya funciona como invocador, y el modelo ya es el receptor. Command añadiría una capa que no resuelve ningún problema actual.
- **Deshacer no es trivial.** Para multiplicar por 0 no existe operación inversa; para dividir con redondeo tampoco. Habría que guardar el valor anterior en cada comando (Memento), con lo que crece la complejidad.
- **Estado en los comandos.** Cada comando guarda su operando y el valor previo, lo que sube el consumo de memoria y exige cuidado con la inmutabilidad.
- **Más indirección** para algo que es una sola línea de cálculo, lo que dificulta explicarlo y depurarlo.

### Conclusión

Con los requisitos actuales (solo agregar operaciones), **Command no es la estrategia más conveniente**: aporta mucho código y su ventaja principal (deshacer/historial) no se pide. Si más adelante se solicitan **deshacer, rehacer o historial**, Command pasa a ser justificado y se integra sin problema sobre la estrategia propuesta, porque cada `ComandoOperacion` solo tendría que guardar una `Operacion` y el valor previo.

## 4.2 Ventajas de la estrategia propuesta frente a Command

| Criterio | Enum de operaciones (Strategy) | Command |
|---|---|---|
| Costo de agregar una operación | 1 línea en el enum | 1 clase nueva + registro en la vista |
| Número de clases | 1 enum | 1 interfaz + N clases + invocador |
| Cambios en el Modelo | Un solo método genérico | Mínimos, pero el receptor sigue necesitando un método por operación |
| Cumple Abierto/Cerrado | Sí (modificar el enum o extenderlo) | Sí |
| Deshacer/rehacer | No de forma nativa | Sí (su gran ventaja) |
| Facilidad para explicar y mantener | Alta | Media |

## 4.3 ¿Se podría considerar algún otro patrón?

| Patrón | Para qué sirve aquí | Valoración |
|---|---|---|
| **Strategy** (recomendado) | Cada operación es un algoritmo intercambiable. El enum con lambdas es su versión más compacta en Java. | Ideal para el requisito actual. |
| **Factory Method / Registro (Map)** | Crear operaciones a partir de un símbolo (`"+"` → `SUMAR`). | Útil si se agregan operaciones por configuración o teclado. |
| **Observer** | Que el modelo notifique a la vista cuando cambia el valor, sin que el controlador la actualice a mano. | Mejora MVC clásico y complementa la propuesta. |
| **Memento** | Guardar el estado anterior del acumulado. | Solo si se necesita deshacer sin Command. |
| **Composite / Interpreter** | Expresiones completas como `2+3*4` con precedencia. | Excesivo ahora; se justificaría para una calculadora científica. |

**Recomendación final:** Strategy (enum) como base, Observer como mejora opcional de MVC y Command (con Memento) únicamente si aparece el requisito de deshacer o historial.
