# Auditoría IA — Manejo de valores nulos en métodos de búsqueda

## Alcance de la auditoría

Se revisaron los métodos `buscarProducto` de las clases `InventarioNoel.java` y `OfertasNovaventa.java`, con el objetivo de determinar si el recorrido del arreglo de productos/ofertas maneja correctamente las posiciones vacías (`null`) sin generar un `NullPointerException` (NPE) durante la ejecución.

Ambos arreglos se inicializan con espacios reservados en `null` para futuros registros:

```java
String[] productos = {"Ducales", "Festival", "Tosh", null, null};
String[] ofertas   = {"Combo cuidado personal", "Combo Hogar", "Combo Despensa", null, null};
```

Esto significa que cualquier recorrido del arreglo eventualmente va a intentar comparar contra una posición `null`.

## Resultado de la auditoría

**Conclusión general: el método SÍ maneja los `null` correctamente y NO arroja `NullPointerException` al usuario**, gracias a un bloque `try-catch`:

```java
for (int i = 0; i < nombreArray.length; i++) {
    try {
        if (nombreArray[i].equals(nombreProducto)) {
            return true;
        }
    } catch (NullPointerException e) {
        continue;
    }
}
return false;
```

Este patrón se repite de forma idéntica en:
- `InventarioNoel.buscarProducto(String[], String)`
- `OfertasNovaventa.buscarProducto(String[], String)`

### ¿Por qué no explota?
Cuando `nombreArray[i]` es `null`, la llamada `nombreArray[i].equals(...)` lanza un `NullPointerException` en tiempo de ejecución. Sin embargo, ese error queda **contenido dentro del propio ciclo** gracias al `catch`, que simplemente hace `continue` y sigue evaluando la siguiente posición. El programa nunca se cae ni el usuario ve un stacktrace.

## Observaciones adicionales (buenas prácticas)

Aunque el método es funcionalmente correcto, la auditoría identifica un punto de mejora:

- **Usar excepciones para control de flujo no es la práctica recomendada en Java.** Es más claro, eficiente y legible validar el `null` explícitamente antes de comparar, en lugar de esperar a que la excepción ocurra:

```java
for (int i = 0; i < nombreArray.length; i++) {
    if (nombreArray[i] != null && nombreArray[i].equals(nombreProducto)) {
        return true;
    }
}
return false;
```

Ventajas de este enfoque:
- Evita la sobrecarga de lanzar y capturar excepciones en cada iteración sobre una posición vacía.
- Comunica la intención del código de forma explícita (se está previniendo el caso nulo, no reaccionando a un error).
- Es el patrón idiomático esperado en revisiones de código Java.

## Veredicto final

| Clase | ¿Maneja null sin NPE visible? | ¿Es la forma recomendada? |
|---|---|---|
| `InventarioNoel` | ✅ Sí | ⚠️ No (usa try-catch en lugar de validación explícita) |
| `OfertasNovaventa` | ✅ Sí | ⚠️ No (usa try-catch en lugar de validación explícita) |

Ambos programas son seguros frente a los valores `null` del arreglo, pero se recomienda refactorizar el método `buscarProducto` para reemplazar el `try-catch` por una validación explícita de `null`, alineándose con las buenas prácticas de Java.