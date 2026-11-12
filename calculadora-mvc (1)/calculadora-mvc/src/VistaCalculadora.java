package src;
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.swing.*;

class VistaCalculadora extends JFrame {
    // Componentes de la interfaz
    private JTextField cajaEntrada = new JTextField(8);
    private JTextField cajaResultado = new JTextField(20);
    private JButton botonLimpiar = new JButton("Limpiar");

    // Un botón por cada operación definida en el enum
    private Map<Operacion, JButton> botonesOperacion = new LinkedHashMap<>();

    VistaCalculadora() {
        cajaResultado.setEditable(false); // Para que el usuario no escriba directo aquí

        // Panel principal y acomodo
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.add(new JLabel("Número"));
        panel.add(cajaEntrada);

        // Se crea un botón por cada operación: si el enum crece, la vista no cambia
        for (Operacion op : Operacion.values()) {
            JButton boton = new JButton(op.etiqueta());
            botonesOperacion.put(op, boton);
            panel.add(boton);
        }

        panel.add(new JLabel("Resultado"));
        panel.add(cajaResultado);
        panel.add(botonLimpiar);

        this.setContentPane(panel);
        this.pack();

        this.setTitle("Calculadora MVC - Práctica");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // Métodos que llama el controlador
    String obtenerTextoEntrada() {
        return cajaEntrada.getText();
    }

    void actualizarResultado(String nuevoTotal) {
        cajaResultado.setText(nuevoTotal);
    }

    void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    // Listeners para los botones
    void escucharOperacion(Operacion operacion, ActionListener listener) {
        botonesOperacion.get(operacion).addActionListener(listener);
    }

    void escucharLimpiar(ActionListener listenLimpiar) {
        botonLimpiar.addActionListener(listenLimpiar);
    }
}
