# Material visual de la idea (punto 1.2)

Bosquejos de baja fidelidad de **IPN Tycoon (ruta EduTycoon)** para la primera versión descrita en [`idea.md`](idea.md). La app se juega en **horizontal (landscape)**, como está configurado en `android/AndroidManifest.xml`.

> **Autoría y uso de IA:** todas las figuras las elaboró María Guadalupe Hernández Alvirde con apoyo de **Claude (Anthropic)**. Los bosquejos se generaron como HTML y se exportaron a PNG. El diagrama de recorrido se generó con Mermaid. El contenido se basa en el código real del repositorio.

---

## 1. Pantallas principales

![Bosquejo de las pantallas principales](img/01-pantallas-principales.png)

*Figura 1. Bosquejo de las seis pantallas principales: Bienvenida, Selección de partida, Partidas guardadas, Mapa con HUD, Ventana del plantel (rediseñada) y Menú de pausa. Elaboró: María Guadalupe Hernández Alvirde, con apoyo de IA (Claude, Anthropic).*

| # | Pantalla | Archivo en el repositorio |
|---|---|---|
| P1 | Bienvenida | `core/.../Bienvenida.kt` |
| P2 | Selección de partida | `core/.../SeleccionPartida.kt` |
| P3 | Partidas guardadas | `core/.../PartidasGuardadas.kt` |
| P4 | Mapa del IPN + HUD | `core/.../GameScreen.kt` |
| P5 | Ventana del plantel | `core/.../BuildingInfoWindow.kt` |
| P6 | Menú de pausa | `core/.../PauseMenuWindow.kt` |

## 2. Estados que no son la ruta feliz

![Estados alternos: carga, vacío, error y dato inválido](img/02-estados-alternos.png)

*Figura 2. Estados alternos señalados sobre las pantallas donde ocurren: carga, lista vacía, error y dato inválido (saldo insuficiente). Elaboró: María Guadalupe Hernández Alvirde, con apoyo de IA (Claude, Anthropic).*

| Estado | Dónde ocurre | Qué ve el usuario |
|---|---|---|
| **Carga** | P3, al tocar un slot ocupado | Botones bloqueados e indicador "Cargando partida…" |
| **Lista vacía** | P3 sin partidas guardadas; P5 con el plantel en nivel máximo | Mensaje que explica qué hacer; botón "NIVEL MÁXIMO" deshabilitado |
| **Error** | P3 si `cargarPartida()` falla; P6 si falla el guardado | Mensaje con las opciones Reintentar y Volver |
| **Dato inválido** | P5 cuando el costo supera el saldo | Botón deshabilitado desde que se abre la ventana y el texto "Te faltan $X" |

## 3. Ventana del plantel: actual vs. propuesta

![Comparación de la ventana del plantel](img/03-ventana-plantel-actual-vs-propuesta.png)

*Figura 3. Comparación entre la ventana actual (`BuildingInfoWindow.kt`) y la propuesta de la v1: ingreso correcto, saldo después de la compra y regla de compra visible. Elaboró: María Guadalupe Hernández Alvirde, con apoyo de IA (Claude, Anthropic).*

## 4. Esquema de la pantalla de juego

![Esquema de la pantalla de juego con elementos y controles](img/04-esquema-pantalla-juego.png)

*Figura 4. Esquema de la pantalla de juego: HUD, botón de menú, planteles comprados y sin comprar, mapa, avisos de ciclo y eventos, y diálogo del tutorial, con sus controles táctiles. Elaboró: María Guadalupe Hernández Alvirde, con apoyo de IA (Claude, Anthropic).*

| Control | Acción |
|---|---|
| Tocar un plantel | Abre "Gestión del Plantel" |
| Tocar el mapa vacío | Cierra la ventana abierta |
| Arrastrar (pan) | Mueve la cámara |
| Pellizcar (pinch) | Acerca o aleja el mapa (zoom) |
| Botón ☰ | Abre el menú de pausa |
| Botón Atrás de Android | Retrocede en el diálogo o regresa a Selección de partida |

## 5. Recorrido del usuario

Desde que abre la app hasta que completa la tarea principal: **comprar o mejorar un plantel**. Los nodos en rojo son estados alternos y el verde es la tarea completada.

```mermaid
flowchart TD
    A([Abre la app]) --> B[P1 Bienvenida]
    B -- toca START --> C[P2 Selección de partida]
    C -- NUEVA PARTIDA --> T[P4 Mapa + tutorial<br/>escribe nombre y escuela]
    C -- CARGAR PARTIDA --> D[P3 Partidas guardadas]
    D --> D1{¿Hay partidas?}
    D1 -- No --> V[/Estado VACÍO<br/>toca un slot para empezar/]
    V --> T
    D1 -- Sí, toca un slot --> L[/Estado CARGA<br/>Cargando partida.../]
    L --> L1{¿Cargó bien?}
    L1 -- No --> E[/Estado ERROR<br/>Reintentar o Volver/]
    E -- Reintentar --> L
    E -- Volver --> C
    L1 -- Sí --> M[P4 Mapa con HUD]
    T --> M
    M -- toca un plantel --> W[P5 Ventana del plantel<br/>costo, saldo después, ingreso]
    W --> N1{¿Nivel máximo?}
    N1 -- Sí --> NM[/Estado VACÍO<br/>NIVEL MÁXIMO, botón deshabilitado/]
    NM -- cerrar --> M
    N1 -- No --> S{¿Saldo ≥ costo?}
    S -- No --> I[/Estado DATO INVÁLIDO<br/>botón deshabilitado, te faltan $X/]
    I -- cerrar --> M
    S -- Sí, toca COMPRAR o MEJORAR --> OK([✅ Tarea completada<br/>plantel comprado o mejorado,<br/>HUD actualizado])
    OK -. opcional .-> P[P6 Pausa → Guardar partida]

    classDef alt fill:#fde2e2,stroke:#d62828,color:#000;
    classDef done fill:#d8f3dc,stroke:#2a7a2a,color:#000;
    class V,L,E,NM,I alt;
    class OK done;
```

![Diagrama del recorrido del usuario](img/05-recorrido-usuario.png)

*Figura 5. Diagrama del recorrido del usuario, desde que abre la app hasta que compra o mejora un plantel, incluidos los estados alternos. Elaboró: María Guadalupe Hernández Alvirde, con apoyo de IA (Claude, Anthropic), generado con Mermaid.*
