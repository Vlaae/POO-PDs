package src;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.function.BinaryOperator;

/**
 * Cada valor del enum es una estrategia de cálculo: guarda la etiqueta que
 * se muestra en el botón y la función que recibe dos números y devuelve el resultado.
 * Para agregar una operación nueva basta con añadir una línea aquí.
 */
public enum Operacion {
    SUMAR("Sumar", BigDecimal::add),
    RESTAR("Restar", BigDecimal::subtract),
    MULTIPLICAR("Multiplicar", BigDecimal::multiply),
    // MathContext evita la excepción en divisiones no exactas (ej. 1/3)
    DIVIDIR("Dividir", (a, b) -> a.divide(b, MathContext.DECIMAL128));

    private final String etiqueta;
    private final BinaryOperator<BigDecimal> funcion;

    Operacion(String etiqueta, BinaryOperator<BigDecimal> funcion) {
        this.etiqueta = etiqueta;
        this.funcion = funcion;
    }

    public BigDecimal aplicar(BigDecimal a, BigDecimal b) {
        return funcion.apply(a, b);
    }

    public String etiqueta() {
        return etiqueta;
    }
}
