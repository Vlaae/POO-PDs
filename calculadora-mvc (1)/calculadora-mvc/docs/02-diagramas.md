# 2. Diagramas de clases

Los diagramas están escritos en Mermaid, que GitHub renderiza directamente. Los archivos fuente están en la carpeta [diagramas](diagramas/).

## 2.1 Diagrama actual

```mermaid
classDiagram
    class MainCalculadora {
        +main(String[] args)$ void
    }
    class ModeloCalculadora {
        -String VALOR_INICIAL$
        -BigDecimal resultadoActual
        +reiniciar() void
        +multiplicar(String numero) void
        +fijarValor(String valor) void
        +obtenerValor() String
    }
    class VistaCalculadora {
        -JTextField cajaEntrada
        -JTextField cajaResultado
        -JButton botonMultiplicar
        -JButton botonLimpiar
        +limpiarPantalla() void
        +obtenerTextoEntrada() String
        +actualizarResultado(String) void
        +mostrarError(String) void
        +escucharMultiplicar(ActionListener) void
        +escucharLimpiar(ActionListener) void
    }
    class ControladorCalculadora {
        -ModeloCalculadora modelo
        -VistaCalculadora vista
    }
    class EventoMultiplicar {
        +actionPerformed(ActionEvent) void
    }
    class EventoLimpiar {
        +actionPerformed(ActionEvent) void
    }
    class JFrame
    class ActionListener {
        <<interface>>
    }
    JFrame <|-- VistaCalculadora
    ActionListener <|.. EventoMultiplicar
    ActionListener <|.. EventoLimpiar
    ControladorCalculadora *-- EventoMultiplicar
    ControladorCalculadora *-- EventoLimpiar
    ControladorCalculadora --> ModeloCalculadora : usa
    ControladorCalculadora --> VistaCalculadora : usa
    VistaCalculadora --> ModeloCalculadora : lee valor
    MainCalculadora ..> ModeloCalculadora : crea
    MainCalculadora ..> VistaCalculadora : crea
    MainCalculadora ..> ControladorCalculadora : crea
```

**Lectura del diagrama:** `VistaCalculadora` extiende `JFrame`; el controlador contiene dos clases internas que implementan `ActionListener`; el controlador usa modelo y vista; la vista lee el valor del modelo; `MainCalculadora` crea las tres piezas.

## 2.2 Diagrama propuesto (con la estrategia recomendada)

```mermaid
classDiagram
    class Operacion {
        <<enumeration>>
        SUMAR
        RESTAR
        MULTIPLICAR
        DIVIDIR
        -String etiqueta
        -BinaryOperator~BigDecimal~ funcion
        +aplicar(BigDecimal a, BigDecimal b) BigDecimal
        +etiqueta() String
    }
    class ModeloCalculadora {
        -BigDecimal resultadoActual
        +reiniciar() void
        +aplicar(Operacion op, String numero) void
        +obtenerValor() String
    }
    class VistaCalculadora {
        -JTextField cajaEntrada
        -JTextField cajaResultado
        -Map~Operacion, JButton~ botonesOperacion
        -JButton botonLimpiar
        +obtenerTextoEntrada() String
        +actualizarResultado(String) void
        +mostrarError(String) void
        +escucharOperacion(Operacion, ActionListener) void
        +escucharLimpiar(ActionListener) void
    }
    class ControladorCalculadora {
        -ModeloCalculadora modelo
        -VistaCalculadora vista
    }
    class EventoOperacion {
        -Operacion operacion
        +actionPerformed(ActionEvent) void
    }
    class EventoLimpiar {
        +actionPerformed(ActionEvent) void
    }
    ControladorCalculadora *-- EventoOperacion : una por operación
    ControladorCalculadora *-- EventoLimpiar
    ControladorCalculadora --> ModeloCalculadora
    ControladorCalculadora --> VistaCalculadora
    ModeloCalculadora --> Operacion : delega el cálculo
    VistaCalculadora ..> Operacion : genera un botón por cada valor
    EventoOperacion --> Operacion
```

**Lectura del diagrama:** aparece el `enum Operacion`; el modelo delega el cálculo en él, la vista genera un botón por cada valor y el controlador usa una sola clase de evento (`EventoOperacion`) parametrizada con la operación.
