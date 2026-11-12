package src;
import java.math.BigDecimal;

public class ModeloCalculadora {
    private static final String VALOR_INICIAL = "0";

    // Usamos BigDecimal para no perder decimales en operaciones grandes
    private BigDecimal resultadoActual;

    // true mientras el usuario no haya hecho ninguna operación desde el último reinicio
    private boolean esperandoPrimerOperando;

    ModeloCalculadora() {
        reiniciar();
    }

    // Regresa el acumulado a su estado inicial
    public void reiniciar() {
        resultadoActual = new BigDecimal(VALOR_INICIAL);
        esperandoPrimerOperando = true;
    }

    // Aplica cualquier operación al acumulado usando el número que escribe el usuario.
    // La primera vez después de reiniciar, el número escrito se toma como primer operando.
    public void aplicar(Operacion operacion, String numero) {
        BigDecimal operando = new BigDecimal(numero); // puede lanzar NumberFormatException

        if (esperandoPrimerOperando) {
            resultadoActual = operando;
            esperandoPrimerOperando = false;
        } else {
            // puede lanzar ArithmeticException (ej. división entre cero)
            resultadoActual = operacion.aplicar(resultadoActual, operando);
        }
    }

    // Pasamos a texto plano para evitar la notación científica (ej. 1e+2)
    public String obtenerValor() {
        return resultadoActual.toPlainString();
    }
}
