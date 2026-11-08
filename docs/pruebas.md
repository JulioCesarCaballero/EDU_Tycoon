# Matriz de Pruebas de QA - Entrega 1

## Proyecto: EduTycoon - Gestión Estudiantil ESCOM
**Institución:** Instituto Politécnico Nacional (IPN)  
**Unidad Académica:** Escuela Superior de Cómputo (ESCOM)  
**Asignatura:** Desarrollo de aplicaciones móviles nativas (2027-1)  
**Característica:** Regla de compra que impida gastar más saldo del disponible  
**Rama:** `feature/purchase-balance-rule`  

---

## 📋 Resumen Ejecutivo de Pruebas
Esta matriz documenta la validación funcional, de casos límite, ciclo de vida/recreación y accesibilidad para la característica de **validación estricta de saldo y prevención de sobregiro** en la adquisición y mejora de edificios escolares (`ReglaDeCompra`).

* **Dispositivo de prueba 1:** Google Pixel 7 (Emulador Android Studio - API 34).
* **Dispositivo de prueba 2:** Samsung Galaxy S21 / Dispositivo físico (API 34 / Android 14).
* **Dispositivo de prueba 3:** Samsung Galaxy A56 (SM-A566E) / Dispositivo físico (API 36.1 / Android 16).
* **Herramienta de pruebas automatizadas:** JUnit 4 / Gradle (`./gradlew :core:test`).

---

## 🧪 Casos de Prueba Registrados

### Matriz General de QA (Regla de Compra y Lógica de Negocio)

| ID | Caso de Prueba | Tipo / Categoría | Pasos de Ejecución | Resultado Esperado | Resultado Real | Estado |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| **CP-01** | Compra exitosa de edificio con saldo suficiente | Flujo Principal (Ruta Feliz) | 1. Iniciar partida con saldo inicial ($500,000 MXN).<br>2. Abrir la ventana de gestión del edificio "Escuela de Computación" ($300,000 MXN).<br>3. Verificar que el botón muestre "COMPRAR $300.0K".<br>4. Pulsar el botón. | `ReglaDeCompra.comprar()` devuelve `ResultadoCompra.Permitida`. El saldo se debita a $200,000 MXN, el edificio se marca como comprado con nivel 1 y la ventana se cierra actualizando el mapa. | Saldo debitado a $200,000 MXN, nivel 1 asignado y sprite actualizado correctamente. | ✅ APROBADO |
| **CP-02** | Bloqueo preventivo de compra por saldo insuficiente | Caso Límite / Error de Saldo | 1. Iniciar partida con saldo de $200,000 MXN.<br>2. Abrir ventana de "Dirección General" cuyo costo es de $5,000,000 MXN.<br>3. Observar el estado del botón y del indicador de saldo.<br>4. Intentar interactuar con el botón. | `ReglaDeCompra.evaluar()` devuelve `ResultadoCompra.SaldoInsuficiente`. El botón se inicializa deshabilitado (`isDisabled = true`) mostrando en rojo "¡SALDO INSUFICIENTE! (Faltan $4.80M)", y el saldo permanece intacto en $200,000 MXN. | Botón deshabilitado en color rojo, texto con saldo faltante visible y saldo intacto en $200,000 MXN. | ✅ APROBADO |
| **CP-03** | Mejora de nivel de edificio con fondos insuficientes | Caso Límite / Transacción de Mejora | 1. Poseer un edificio en nivel 1 cuyo costo de mejora a nivel 2 sea de $300,000 MXN.<br>2. Ajustar saldo a $100,000 MXN.<br>3. Abrir ventana de gestión del edificio.<br>4. Verificar validación de saldo. | El sistema detecta que el saldo ($100,000) es menor al costo de mejora ($300,000), devolviendo `ResultadoCompra.SaldoInsuficiente` y bloqueando la transacción sin alterar el nivel. | Transacción rechazada, nivel se mantiene en 1 y saldo permanece en $100,000 MXN. | ✅ APROBADO |
| **CP-04** | Preservación de saldo y estado ante recreación o ciclo de vida | Ciclo de Vida / Recreación | 1. Realizar una compra exitosa de edificio.<br>2. Provocar recreación o cambio de foco de la actividad (minimizar o pausar).<br>3. Volver al estado activo del juego. | El saldo debitado y las propiedades compradas persisten sin duplicación de cobro ni reseteo no deseado. | El saldo y nivel de edificios se conservan intactos en memoria (`GameState`). | ✅ APROBADO |
| **CP-05** | Accesibilidad visual y retroalimentación de contraste | Accesibilidad / UI Feedback | 1. Habilitar modo de texto ampliado / accesibilidad en ajustes del sistema.<br>2. Provocar estado de saldo insuficiente en la ventana de gestión.<br>3. Evaluar legibilidad de textos y contraste del botón. | El texto "¡SALDO INSUFICIENTE!" se visualiza con contraste nítido (Color Rojo / Alerta), sin solapamiento de elementos y con área táctil accesible. | Texto legible con contraste adecuado y dimensiones de ventana adaptativas. | ✅ APROBADO |

---

### Pruebas Funcionales en Dispositivo Físico (Samsung SM-A566E)

#### Caso 1: Flujo principal (Happy Path) - Compra exitosa con saldo suficiente
* **Objetivo:** Verificar que el jugador pueda comprar una instalación cuando cuenta con el dinero necesario en un dispositivo físico.
* **Pasos:**
  1. Iniciar el juego y verificar que el saldo inicial cubra la propiedad "Mac and Cheese" (costo $120,000).
  2. Tocar el edificio "Mac and Cheese" en el mapa.
  3. Pulsar el botón "COMPRAR $120.0K" en la ventana de información.
* **Resultado Esperado:** 
  - El botón ejecuta el cobro y la ventana se cierra.
  - El saldo se reduce en $120,000 de forma correcta.
  - El edificio cambia su estado a comprado (nivel 1) y comienza a generar ingresos en los siguientes ciclos.
* **Resultado Real:** La compra se efectúa correctamente y el saldo disminuye conforme a lo esperado.
* **Estado:** APROBADO (PASS).

#### Caso 2: Datos vacíos o inválidos - Compra con saldo insuficiente (Detección de defecto y validación preventiva)
* **Objetivo:** Comprobar el comportamiento del sistema cuando el saldo disponible es menor al costo de la instalación.
* **Pasos:**
  1. Iniciar la aplicación con un saldo inicial bajo (ej. $10,000).
  2. Tocar el "Auditorio Alejo Peralta" en el mapa (costo base $800,000).
  3. Observar la ventana `BuildingInfoWindow` y pulsar el botón de comprar.
* **Resultado Esperado:** 
  - El sistema debe prevenir la compra desde el inicio.
  - El botón debe permanecer deshabilitado con retroalimentación inmediata de saldo insuficiente.
  - El saldo no debe sufrir modificaciones ni pasar a números negativos.
* **Resultado Real:** En la versión inicial previa a `ReglaDeCompra`, el botón aparecía habilitado visualmente y únicamente tras pulsarlo se actualizaba a "¡Saldo insuficiente!" bloqueándose (sin descontar saldo negativo debido al guard clause en `GameState`). Con la integración de `ReglaDeCompra`, la validación es preventiva: el botón se inicializa deshabilitado (`isDisabled = true`), mostrando el texto en color de alerta y desglosando la cantidad faltante.
* **Estado:** APROBADO (PASS) / DEFECTO PREVIAMENTE DETECTADO Y SUBSANADO.

#### Caso 3: Ciclo de vida y Recreación de actividad (Pausa, minimización y reanudación)
* **Objetivo:** Confirmar que al minimizar la app o pausar la actividad no se reinicie la partida en curso ni se pierda el progreso.
* **Pasos:**
  1. Comprar un edificio y acumular al menos 3 ciclos de ingresos.
  2. Minimizar la aplicación presionando el botón Home o cambiando a la vista de aplicaciones recientes.
  3. Reanudar EduTycoon desde la multitarea.  
  *(Nota técnica: `AndroidManifest.xml` fija la orientación en `android:screenOrientation="landscape"`, por lo que la rotación física a vertical está deshabilitada a nivel de sistema; la prueba valida la preservación del estado ante pausa y reanudación).*
* **Resultado Esperado:** El motor gráfico (LibGDX) y el estado de la actividad se reanudan correctamente, manteniendo intactos los edificios comprados, su nivel y el saldo actual sin pérdida de progreso.
* **Resultado Real:** Los datos se conservan en memoria dentro de `GameState` y la superficie de renderizado se reanuda sin reiniciar el ciclo de juego.
* **Estado:** APROBADO (PASS).

#### Caso 4: Red no disponible - Modo avión y juego offline
* **Objetivo:** Verificar el funcionamiento de la aplicación sin conexión activa a Internet.
* **Pasos:**
  1. Activar el Modo Avión en el dispositivo.
  2. Abrir la aplicación EduTycoon desde un arranque limpio (Cold start).
  3. Interactuar con las mecánicas (recolectar ingresos, abrir ventanas de edificios).
* **Resultado Esperado:** La aplicación debe abrir sin cerrarse inesperadamente, el motor económico debe seguir calculando ciclos y no deben aparecer pantallas bloqueantes de error de red.
* **Resultado Real:** La aplicación es completamente local. Las operaciones y el renderizado corren con normalidad sin requerir servidores externos.
* **Estado:** APROBADO (PASS).

#### Caso 5: Accesibilidad - Adaptación de Interfaz de Usuario
* **Objetivo:** Evaluar si las ventanas emergentes (`VisWindow`) y textos del juego se mantienen legibles y dentro de la pantalla en distintas densidades de píxeles o resoluciones.
* **Pasos:**
  1. Entrar a los Ajustes de pantalla del dispositivo y subir el tamaño de visualización / fuente al máximo.
  2. Regresar a EduTycoon y abrir la ventana de "Dirección General".
  3. Observar los textos descriptivos y el botón de compra.
* **Resultado Esperado:** Los componentes de la interfaz (`Scene2D`) deben organizarse sin solapar texto ni esconder botones críticos fuera del área táctil.
* **Resultado Real:** La ventana de gestión muestra toda la información (capacidad, ingreso, saldo). Dependiendo de la relación de aspecto del teléfono, el texto descriptivo hace un salto de línea correcto sin desbordar.
* **Estado:** APROBADO (PASS).

---

## 🤖 Cobertura de Pruebas Unitarias Automatizadas (CI)
Se implementaron y ejecutaron con éxito las pruebas automatizadas en `io.moviles.IPN_Tycoon.engine.ReglaDeCompraTest` y `io.moviles.IPN_Tycoon.GameStateTest`:
* `costoSiguiente para propiedad no comprada devuelve el precio base`: Valida el cálculo de precio inicial.
* `costoSiguiente para propiedad comprada devuelve el costo de mejora`: Valida el cálculo escalado de mejora.
* `costoSiguiente para propiedad en nivel máximo devuelve null`: Previene operaciones fuera de rango.
* `evaluar con saldo suficiente devuelve Permitida con saldoDespues correcto`: Garantiza la precisión aritmética de la compra.
* `evaluar con saldo insuficiente devuelve SaldoInsuficiente con faltante correcto`: Verifica el cálculo del monto que falta.
* `comprar con saldo suficiente descuenta el dinero y sube de nivel`: Valida la mutación atómica de estado.
* `comprar con saldo insuficiente no modifica el dinero ni la propiedad`: Garantiza que el saldo nunca caiga en números rojos.

**Resultado de ejecución:**
```text
Gradle Test Run :core:test
Tests completed: 100% successful, 0 failures, 0 skipped.
```
