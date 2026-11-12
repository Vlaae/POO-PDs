# 5. Guion del video

**Duración objetivo:** unos 3 minutos (el límite es 5).

*Tono relajado y directo. Cada escena indica qué mostrar en pantalla.*

## Escena 1: Intro (0:00 a 0:15)

**En pantalla:** la calculadora funcionando.

**Locución:** «Hola, soy Vale. Les voy a explicar cómo agregarle más operaciones a nuestra calculadora MVC, que ahorita solo multiplica, sin romper el patrón.»

## Escena 2: Cómo está hoy (0:15 a 0:45)

**En pantalla:** diagrama actual ([2.1](02-diagramas.md)).

**Locución:** «Ahorita el modelo guarda un acumulado y solo sabe multiplicar. La vista es la ventana y no calcula nada, y el controlador conecta los botones con el modelo. Si copiamos este esquema para cada operación, necesitaríamos un método, un botón y un listener nuevos por cada una. Funciona, pero se vuelve repetitivo.»

## Escena 3: ¿Y Command? (0:45 a 1:30)

**En pantalla:** esquema rápido de Command con dos columnas: ventajas y desventajas.

**Locución:** «La primera idea es el patrón Command: cada operación es una clase con su método ejecutar. Lo bueno es que permite deshacer, rehacer y llevar historial. Lo malo es que necesitaríamos una clase por operación, más una interfaz y un invocador, cuando lo único que cambia es la fórmula. Además, deshacer no es tan simple, porque multiplicar por cero no se puede revertir. Como nadie nos pide deshacer, sería sobreingeniería, así que no lo usamos.»

## Escena 4: Nuestra estrategia (1:30 a 2:30)

**En pantalla:** diagrama propuesto ([2.2](02-diagramas.md)) y el código del enum `Operacion`.

**Locución:** «Lo que proponemos es el patrón Strategy con un enum llamado Operacion. Cada valor tiene su nombre y su fórmula: sumar, restar, multiplicar, dividir. El modelo tiene un solo método, aplicar. La vista crea un botón por cada valor del enum. El controlador usa una sola clase de evento y atrapa errores, como dividir entre cero. MVC se queda igual: el modelo calcula, la vista muestra y el controlador conecta. Y si quiero potencia, agrego una línea al enum y listo.»

## Escena 5: Cierre (2:30 a 3:00)

**En pantalla:** resumen en tres puntos.

**Locución:** «Dos detalles: el valor inicial pasa de uno a cero para que sumar tenga sentido, y la división usa MathContext para que uno entre tres no truene. Si algún día piden deshacer, Command con Memento se puede montar encima de esto, y Observer también sirve para que el modelo avise a la vista. Eso es todo, gracias.»

## Tips de grabación

- Graba la calculadora antes y después del cambio (suma, resta y división entre cero).
- Exporta los diagramas como imagen y muestra el código solo unos 10 segundos por bloque.
- Si te pasas de tiempo, acorta la Escena 3.
