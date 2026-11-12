#include <stdio.h>
#include <stdlib.h>

#include "stand.h"

static void verificarCreacion(Stand *stand, const char *etiqueta) {
    if (stand == NULL) {
        fprintf(stderr, "Error al crear %s. Memoria insuficiente.\n", etiqueta);
        exit(EXIT_FAILURE);
    }
}

int main(void) {
    Stand *lista = NULL;
    Stand *resultado;

    /* Simulación de pruebas unitarias básicas */
    Stand *s1 = crearStand(101, 4.0f, 3.0f, DISPONIBLE);
    Stand *s2 = crearStand(102, 2.0f, 2.5f, RESERVADO);
    Stand *s3 = crearStand(103, 3.0f, 5.0f, DISPONIBLE);
    Stand *s4 = crearStand(104, 5.0f, 3.0f, DISPONIBLE);
    Stand *s5 = crearStand(105, 4.0f, 2.5f, RESERVADO);
    Stand *s6 = crearStand(106, 6.0f, 5.0f, DISPONIBLE);

    verificarCreacion(s1, "s1");
    verificarCreacion(s2, "s2");
    verificarCreacion(s3, "s3");
    verificarCreacion(s4, "s4");
    verificarCreacion(s5, "s5");
    verificarCreacion(s6, "s6");

    printf("\n[Prueba] Insertar stands ordenados por área:\n");
    insertarOrdenadoPorArea(&lista, s1);
    insertarOrdenadoPorArea(&lista, s2);
    insertarOrdenadoPorArea(&lista, s3);
    insertarOrdenadoPorArea(&lista, s4);
    insertarOrdenadoPorArea(&lista, s5);
    insertarOrdenadoPorArea(&lista, s6);
    imprimirLista(lista);

    printf("\n[Prueba] Buscar stand #102:\n");
    resultado = buscarStand(lista, 102);
    if (resultado != NULL) {
        printf("Encontrado -> Stand #%d, Área: %.2fm^2, Estado: %s\n",
               resultado->numero,
               calcularArea(resultado),
               estadoAString(resultado->estado));
    } else {
        printf("No encontrado\n");
    }

    printf("\n[Prueba] Actualizar stand #103 (cambio de área y estado a Vendido):\n");
    if (actualizarStand(&lista, 103, 1.0f, 1.0f, VENDIDO)) {
        printf("Actualización exitosa\n");
    } else {
        printf("No se pudo actualizar\n");
    }
    imprimirLista(lista);

    printf("\n[Prueba] Borrar stand #101:\n");
    if (borrarStand(&lista, 101)) {
        printf("Eliminación exitosa\n");
    } else {
        printf("No se pudo eliminar\n");
    }
    imprimirLista(lista);

    liberarLista(&lista);
    return 0;
}
