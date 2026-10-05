# Matriz Inicial de Pruebas - EduTycoon

## Información del Entorno de Pruebas
- **Dispositivo de prueba:** samsung SM-A566E
- **Versión de Android:** Android 16
- **Nivel de API:** API 36.1

---

### Caso 1: Flujo principal (Happy Path) - Compra exitosa con saldo suficiente
* **Objetivo:** Verificar que el jugador pueda comprar una instalación cuando cuenta con el dinero necesario.
* **Pasos:**
  1. Iniciar el juego y verificar que el saldo inicial cubra la propiedad "Mac and Cheese" (costo $120,000).
  2. Tocar el edificio "Mac and Cheese" en el mapa.
  3. Pulsar el botón "COMPRAR $120.0K" en la ventana de información.
* **Resultado Esperado:** 
  - El botón ejecuta el cobro y la ventana se cierra.
  - El saldo se reduce en $120,000 de forma correcta.
  - El edificio cambia su estado a comprado (nivel 1) y comienza a generar ingresos en los siguientes ciclos.
* **Resultado Real:** La compra se efectúa correctamente y el saldo disminuye.
* **Estado:** APROBADO (PASS).

---

### Caso 2: Datos vacíos o inválidos - Compra con saldo insuficiente (Detección de defecto)
* **Objetivo:** Comprobar el comportamiento del sistema cuando el saldo disponible es menor al costo de la instalación.
* **Pasos:**
  1. Iniciar la aplicación con un saldo inicial bajo (ej. $10,000).
  2. Tocar el "Auditorio Alejo Peralta" en el mapa (costo base $800,000).
  3. Pulsar el botón de comprar en la ventana `BuildingInfoWindow`.
* **Resultado Esperado:** 
  - El sistema debe bloquear la acción.
  - El botón debe mostrar el texto "¡Saldo insuficiente!" en color rojo.
  - El saldo del jugador no debe sufrir modificaciones ni pasar a números negativos.
* **Resultado Real:** Aunque la ventana intenta validar el saldo, un defecto en la lógica de `GameState` permite que la función prosiga, otorgando el edificio y dejando el marcador de dinero en números negativos.
* **Estado:** FALLIDO (FAIL).
* **Acción tomada:** Registrado como defecto para ser corregido en la Parte 3 (Regla de compra que impida gastar más del saldo).

---

### Caso 3: Rotación y Recreación de pantalla - Persistencia de estado
* **Objetivo:** Confirmar que al rotar el dispositivo móvil no se reinicie la partida en curso.
* **Pasos:**
  1. Comprar un edificio y acumular al menos 3 ciclos de ingresos.
  2. Rotar el dispositivo de orientación vertical a horizontal.
  3. Volver a rotar a orientación vertical.
* **Resultado Esperado:** El motor gráfico (LibGDX) adapta la resolución o las franjas negras de la cámara, manteniendo intactos los edificios comprados, su nivel y el saldo actual. No hay pérdida de progreso.
* **Resultado Real:** Los datos se conservan en memoria dentro de `GameState` y la vista se reajusta sin reiniciar el ciclo de juego.
* **Estado:** APROBADO (PASS).

---

### Caso 4: Red no disponible - Modo avión y juego offline
* **Objetivo:** Verificar el funcionamiento de la aplicación sin conexión activa a Internet.
* **Pasos:**
  1. Activar el Modo Avión en el dispositivo.
  2. Abrir la aplicación EduTycoon desde un arranque limpio (Cold start).
  3. Interactuar con las mecánicas (recolectar ingresos, abrir ventanas de edificios).
* **Resultado Esperado:** La aplicación debe abrir sin cerrarse inesperadamente, el motor económico debe seguir calculando ciclos y no deben aparecer pantallas bloqueantes de error de red.
* **Resultado Real:** La aplicación es completamente local. Las operaciones y el renderizado corren con normalidad sin requerir servidores externos.
* **Estado:** APROBADO (PASS).

---

### Caso 5: Accesibilidad - Adaptación de Interfaz de Usuario
* **Objetivo:** Evaluar si las ventanas emergentes (`VisWindow`) y textos del juego se mantienen legibles y dentro de la pantalla en distintas densidades de píxeles o resoluciones.
* **Pasos:**
  1. Entrar a los Ajustes de pantalla del dispositivo y subir el tamaño de visualización / fuente al máximo.
  2. Regresar a EduTycoon y abrir la ventana de "Dirección General".
  3. Observar los textos descriptivos y el botón de compra.
* **Resultado Esperado:** Los componentes de la interfaz (`Scene2D`) deben organizarse sin solapar texto ni esconder botones críticos fuera del área táctil.
* **Resultado Real:** La ventana de gestión muestra toda la información (capacidad, ingreso, saldo). Dependiendo de la relación de aspecto del teléfono, el texto descriptivo hace un salto de línea correcto sin desbordar.
* **Estado:** APROBADO (PASS).