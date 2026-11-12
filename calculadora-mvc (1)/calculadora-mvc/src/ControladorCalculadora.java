package src;
import java.awt.event.*;

public class ControladorCalculadora {
    private ModeloCalculadora modelo;
    private VistaCalculadora vista;

    ControladorCalculadora(ModeloCalculadora modelo, VistaCalculadora vista) {
        this.modelo = modelo;
        this.vista = vista;

        // La vista muestra lo que dice el modelo desde el inicio
        this.vista.actualizarResultado(modelo.obtenerValor());

        // Un listener por cada operación, usando la misma clase de evento
        for (Operacion op : Operacion.values()) {
            this.vista.escucharOperacion(op, new EventoOperacion(op));
        }
        this.vista.escucharLimpiar(new EventoLimpiar());
    }

    // Acción al dar clic en cualquier botón de operación
    class EventoOperacion implements ActionListener {
        private final Operacion operacion;

        EventoOperacion(Operacion operacion) {
            this.operacion = operacion;
        }

        public void actionPerformed(ActionEvent e) {
            String textoLeido = vista.obtenerTextoEntrada().trim();
            try {
                modelo.aplicar(operacion, textoLeido);
                vista.actualizarResultado(modelo.obtenerValor());
            } catch (NumberFormatException ex) {
                vista.mostrarError("Ey, pon un número válido: '" + textoLeido + "'");
            } catch (ArithmeticException ex) {
                vista.mostrarError("Operación no válida (por ejemplo, división entre cero).");
            }
        }
    }

    // Acción al dar clic en limpiar
    class EventoLimpiar implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            modelo.reiniciar();
            vista.actualizarResultado(modelo.obtenerValor());
        }
    }
}
