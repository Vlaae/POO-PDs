package src;

public class MainCalculadora {
    public static void main(String[] args) {
        // Inicializamos las piezas del patrón MVC
        ModeloCalculadora modelo = new ModeloCalculadora();
        VistaCalculadora vista = new VistaCalculadora();
        new ControladorCalculadora(modelo, vista);

        vista.setVisible(true); // Ventana visible
    }
}
