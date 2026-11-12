# Calculadora MVC en Java

## Programación Orientada a Objetos
## Tercera prueba de desempeño

## Descripción

Calculadora de escritorio en Java construida con el patrón **Modelo-Vista-Controlador (MVC)**. Antes solo multiplicaba un acumulado por el número que escribe el usuario,pero ahora puede sumar, restar, multiplicar y dividir mediante un enum Operacion (patrón Strategy), usando `BigDecimal`para mejorar la presición de las operaciones

## Video explicativo

**[Ver video](https://youtu.be/NZYigpsjIEA)**


## Resumen de la propuesta implementada

- **Command** aporta deshacer/rehacer e historial, pero para este caso implica una clase por operación más un invocador, por lo que **no se considera la opción más conveniente** mientras solo se necesiten más operaciones.
- **Estrategia elegida:** un `enum Operacion` (patrón Strategy) con la fórmula de cada operación. El modelo expone un único método `aplicar(Operacion, String)`, la vista genera un botón por operación y el controlador usa una sola clase de evento.
- Agregar una operación nueva requiere **una sola línea** en el enum.
- Si más adelante se pide deshacer o historial, Command (con Memento) puede construirse sobre esta base.

## Patrones y conceptos aplicados

- Modelo-Vista-Controlador (MVC)
- Strategy (propuesta de extensión con `enum`)
- Command (analizado y comparado)
- Observer, Memento, Factory (considerados como complemento)
