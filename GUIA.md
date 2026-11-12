# Guía para Estudiantes - Primeros Pasos con Git y GitHub

Esta guía te ayuda a usar el repositorio de la tarea **Stands - Listas Simplemente Ligadas**.

## Prerrequisitos

- Tener Git instalado en tu computadora ([descargar aquí](https://git-scm.com/))
- Una cuenta en GitHub

## Paso 1: Clonar el repositorio

El profesor te proporcionará un repositorio personalizado. Para copiar el código en tu computadora:

```bash
git clone https://github.com/TU_USUARIO/POO-26-PDs.git
cd POO-26-PDs
```

Reemplaza `TU_USUARIO` con tu nombre de usuario de GitHub.

## Paso 2: Entender la estructura

El repositorio contiene:

```
.
├── src/
│   ├── stand.h         ← Estructura y prototipos de funciones (NO MODIFICAR)
│   └── main.c          ← Pruebas unitarias (NO MODIFICAR)
├── Makefile            ← Para compilar (NO MODIFICAR)
├── README.md           ← Descripción de la tarea
├── GUIA.md             ← Esta guía
└── .gitignore          ← Archivos a ignorar
```

**Tu tarea:** Implementar `src/stand.c` (no existe aún).

## Paso 3: Crear tu rama de trabajo

Trabajarás en una rama separada de `main` para mantener limpio el repo:

```bash
git branch
```

Verás que estás en `main`. Crea tu rama de trabajo:

```bash
git checkout -b mi-solucion
```

O en Git moderno:

```bash
git switch -c mi-solucion
```

## Paso 4: Implementar la solución

Crea el archivo `src/stand.c` con tu implementación de las funciones requeridas:

- `calcularArea()`
- `estadoAString()`
- `crearStand()`
- `insertarOrdenadoPorArea()`
- `buscarStand()`
- `actualizarStand()`
- `borrarStand()`
- `imprimirLista()`
- `liberarLista()`

Puedes consultar los prototipos en `src/stand.h`.

## Paso 5: Compilar y probar

Compila tu código:

```bash
make
make run
```

Si no compila, revisa los errores y corrígelos. Git + GitHub Actions te darán retroalimentación automáticamente.

## Paso 6: Guardar tu progreso en Git

Una vez que tu código funcione, guárdalo:

```bash
git add src/stand.c
git commit -m "Implementar operaciones de lista simplemente ligada para stands"
```

Sube los cambios a GitHub:

```bash
git push origin mi-solucion
```

## Paso 7: Grabar video y crear Pull Request

Una vez que tu solución esté completa:

1. **Graba un video** donde:
   - Expliques el diseño de tu solución
   - Hagas un recorrido por las funciones principales
   - Ejecutes `make run` para mostrar las pruebas en funcionamiento

2. **Crea un Pull Request** (PR):
   - Ve a tu repositorio en GitHub
   - Haz clic en **Pull Requests** → **New Pull Request**
   - Selecciona `main` como base y `mi-solucion` como rama a fusionar
   - En la descripción, agrega el enlace al video y marca el checklist técnico

3. **Avisa al profesor** con el enlace a tu PR

## Detalles técnicos importantes

### Compilación automática (CI/CD)

Cada vez que hagas `git push`, GitHub Actions compila automáticamente tu código:

- ✅ **Pasa:** Tu `stand.c` compila sin errores
- ❌ **Falla:** Hay errores de compilación (revisa los logs)

Ver los resultados en tu PR o en la pestaña **Actions** del repositorio.

### Gestión de memoria

Recuerda:

- Usa `malloc()` para reservar memoria
- Usa `free()` para liberar memoria
- Evita memory leaks: libera todo lo que reserves
- `liberarLista()` debe liberar todos los nodos

### Criterio de ordenamiento

La lista se ordena por **área** (`ancho × largo`), no por número de stand.

Esto es una decisión de diseño, no una regla de las listas ligadas.

## Errores comunes

| Error | Causa | Solución |
|-------|-------|----------|
| `fatal: not a git repository` | No estás en la carpeta del repo | Usa `cd POO-26-PDs` |
| `error: Your branch and 'origin/main' have diverged` | Cambios en conflicto | Sincroniza con `git pull origin main` |
| `Segmentation fault` | Acceso a memoria inválida | Revisa apuntadores y acceso a nodos |
| Compilación falla | Errores de sintaxis o lógica | Lee los mensajes del compilador |

## Recursos útiles

- [Documentación oficial de Git](https://git-scm.com/doc)
- [GitHub Help](https://docs.github.com/es)
- [Tutoriales de listas ligadas en C](https://www.geeksforgeeks.org/data-structures/linked-list/)

## Preguntas frecuentes

**¿Puedo modificar `main.c` o `stand.h`?**
No. El profesor usa esos archivos para compilar. Solo modifica `stand.c`.

**¿Y si borro un archivo por accidente?**
No te preocupes. Puedes recuperarlo con:
```bash
git checkout -- nombre_del_archivo
```

**¿Dónde veo los errores de compilación?**
En la pestaña **Actions** de tu repositorio, haz clic en el último workflow fallido.

---

¡Éxito con la tarea! 🚀
