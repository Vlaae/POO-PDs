## Tarea de Programación 1

**Título:** Stands - Listas Simplemente Ligadas  
**Deadline:** TBD  
**Modalidad:** Individual
**Entrega:** Repo Individual

### Objetivos

- Verificar conocimientos previos de programación estructurada en C.
- Promover el uso de apuntadores y memoria dinámica.
- Introducir el uso de repositorios en GitHub para envío de tareas.
- Promover comunicación técnica al explicar el código desarrollado.

### Contexto

Una compañía necesita un sistema para vender stands en una Feria de Libros (FILEY App).

Se debe construir una lista simplemente ligada ordenada para administrar los stands.

### Datos de la estructura `Stand`

- Número de stand
- Ancho del stand (metros)
- Largo del stand (metros)
- Estado del stand (`Disponible`, `Reservado`, `Vendido`)

> El criterio de ordenamiento de la lista será por **área** del stand (`ancho x largo`).

### Operaciones mínimas requeridas

- Insertar stand
- Borrar stand
- Actualizar stand
- Buscar stand
- Imprimir lista completa

### Requisitos de implementación

- Lenguaje C.
- Usar apuntadores y memoria dinámica.
- Mantener modularidad y responsabilidad única por función.
- Incluir comentarios para mejorar legibilidad.
- Probar el comportamiento en `main` con un bloque de simulación tipo pruebas unitarias.

### Código base incluido en este repositorio

- `src/stand.h`: estructura `Stand`, enum de estado y prototipos de funciones.
- `src/main.c`: bloque de pruebas para insertar, buscar, actualizar, borrar e imprimir.
- `Makefile`: compilación y ejecución rápida.
- `GUIA.md`: primer contacto con Git y GitHub.
- `src/stand.c`: implementación de las funciones declaradas en `stand.h`.

## Antes de empezar

**Lee primero la [`GUIA.md`](GUIA.md)** si es tu primer contacto con Git o GitHub.

## Compilación y ejecución

```bash
make
make run
```

## Entregables para estudiantes

Cada estudiante debe trabajar en su repositorio personal (clon o fork, según acuerdo con el docente) e implementar `src/stand.c` con las operaciones solicitadas.

### 1. Código funcional

Tu implementación debe:

- Compilar sin errores ni warnings
- Pasar todas las pruebas en `main.c` sin segmentation faults
- Cumplir con todos los requisitos de memoria dinámica
- Mantener la lista ordenada por área tras cada inserción y actualización

### 2. Documentación

Actualiza el README de tu repositorio con:

- Breve descripción de tu diseño (2-3 párrafos)
- Decisiones importantes (ej: cómo manejaste la reordenación)
- Cualquier reto que hayas enfrentado y cómo lo resolviste

### 3. Video de explicación técnica

Graba un video (3-8 minutos) donde demuestres:

**Checklist técnico para el video:**

| Elemento | Cumple |
|----------|--------|
| Estructura de datos explicada claramente | ☐ |
| Funciones clave documentadas (al menos 3) | ☐ |
| Manejo de memoria dinámico explicado | ☐ |
| Criterio de ordenamiento por área justificado | ☐ |
| Casos límite mencionados (lista vacía, actualización no encontrada) | ☐ |
| Ejecución de pruebas en vivo | ☐ |
| Audio claro y diapositivas legibles | ☐ |

```
Enlace del video: https://...
```

## Retroalimentación automática con GitHub Actions

Cada vez que hagas `git push`, el repositorio:

1. Compila automáticamente tu código
2. Ejecuta las pruebas
3. Verifica memory leaks con valgrind
4. Muestra errores de compilación en tu Pull Request

Revisa la pestaña **Actions** para ver los resultados.

## Opciones de mejora (avanzado)

Si terminas antes de la entrega, considera:

1. **Ordenamiento alternativo:** Implementa una segunda versión que ordene por número de stand en lugar de área. Compara ambas decisiones de diseño en un comentario.

2. **Validación de entrada:** Agrega verificaciones para no permitir dimensiones negativas o cero.

3. **Estadísticas:** Implementa funciones auxiliares como:
   - `float areaTotal(Stand *cabeza)` — suma de áreas
   - `int contarStandsPorEstado(Stand *cabeza, StandEstado estado)` — cuenta por estado
   - `Stand *encontrarMasGrande(Stand *cabeza)` — stand con mayor área

4. **Persistencia:** Guarda y carga la lista desde un archivo `.txt` o `.csv`.

## Errores comunes a evitar

- No liberar memoria reservada → memory leaks
- Modificar punteros sin actualizar la cabeza → perder acceso a la lista
- No verificar si `malloc()` retorna `NULL`
- Acceder a campos de un nodo ya liberado
- Asumir que la lista está ordenada sin verificarlo

## Recursos complementarios

- [Data Structures: Linked Lists in C](https://www.geeksforgeeks.org/data-structures/linked-list/)
- [Memory Management in C](https://www.learn-c.org/en/Memory_Management)
- [GitHub Docs: Pull Requests](https://docs.github.com/en/pull-requests)

---

**Creado para POO 2026 | Ingeniería en Software**
