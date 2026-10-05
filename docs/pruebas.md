# Matriz de Pruebas de QA - Entrega 1

## Proyecto: EduTycoon - Gestión Estudiantil ESCOM
**Institución:** Instituto Politécnico Nacional (IPN)  
**Unidad Académica:** Escuela Superior de Cómputo (ESCOM)  
**Asignatura:** Desarrollo de aplicaciones móviles nativas (2027-1)  
**Característica:** Regla de compra que impida gastar más saldo del disponible  
**Rama:** `feature/purchase-balance-rule`  

---

## 📋 Resumen Ejecutivo de Pruebas
Esta matriz documenta la validación funcional, de casos límite, recreación y accesibilidad para la característica de **validación estricta de saldo y prevención de sobregiro** en la adquisición y mejora de edificios escolares (`ReglaDeCompra`).

* **Dispositivo de prueba 1:** Google Pixel 7 (Emulador Android Studio - API 34).
* **Dispositivo de prueba 2:** Samsung Galaxy S21 / Dispositivo físico (API 34 / Android 14).
* **Herramienta de pruebas automatizadas:** JUnit 4 / Gradle (`./gradlew :core:test`).

---

## 🧪 Casos de Prueba Registrados

| ID | Caso de Prueba | Tipo / Categoría | Pasos de Ejecución | Resultado Esperado | Resultado Real | Estado |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| **CP-01** | Compra exitosa de edificio con saldo suficiente | Flujo Principal (Ruta Feliz) | 1. Iniciar partida con saldo inicial ($500,000 MXN).<br>2. Abrir la ventana de gestión del edificio "Escuela de Computación" ($300,000 MXN).<br>3. Verificar que el botón muestre "COMPRAR $300.0K".<br>4. Pulsar el botón. | `ReglaDeCompra.comprar()` devuelve `ResultadoCompra.Permitida`. El saldo se debita a $200,000 MXN, el edificio se marca como comprado con nivel 1 y la ventana se cierra actualizando el mapa. | Saldo debitado a $200,000 MXN, nivel 1 asignado y sprite actualizado correctamente. | ✅ APROBADO |
| **CP-02** | Bloqueo preventivo de compra por saldo insuficiente | Caso Límite / Error de Saldo | 1. Iniciar partida con saldo de $200,000 MXN.<br>2. Abrir ventana de "Dirección General" cuyo costo es de $5,000,000 MXN.<br>3. Observar el estado del botón y del indicador de saldo.<br>4. Intentar interactuar con el botón. | `ReglaDeCompra.evaluar()` devuelve `ResultadoCompra.SaldoInsuficiente`. El botón se inicializa deshabilitado (`isDisabled = true`) mostrando en rojo "¡SALDO INSUFICIENTE! (Faltan $4.80M)", y el saldo permanece intacto en $200,000 MXN. | Botón deshabilitado en color rojo, texto con saldo faltante visible y saldo intacto en $200,000 MXN. | ✅ APROBADO |
| **CP-03** | Mejora de nivel de edificio con fondos insuficientes | Caso Límite / Transacción de Mejora | 1. Poseer un edificio en nivel 1 cuyo costo de mejora a nivel 2 sea de $300,000 MXN.<br>2. Ajustar saldo a $100,000 MXN.<br>3. Abrir ventana de gestión del edificio.<br>4. Verificar validación de saldo. | El sistema detecta que el saldo ($100,000) es menor al costo de mejora ($300,000), devolviendo `ResultadoCompra.SaldoInsuficiente` y bloqueando la transacción sin alterar el nivel. | Transacción rechazada, nivel se mantiene en 1 y saldo permanece en $100,000 MXN. | ✅ APROBADO |
| **CP-04** | Preservación de saldo y estado ante recreación o rotación | Ciclo de Vida / Recreación | 1. Realizar una compra exitosa de edificio.<br>2. Provocar recreación de la actividad (rotación de pantalla o cambio de configuración).<br>3. Volver al estado activo del juego. | El saldo debitado y las propiedades compradas persisten sin duplicación de cobro ni reseteo no deseado. | El saldo y nivel de edificios se conservan intactos en memoria (`GameState`). | ✅ APROBADO |
| **CP-05** | Accesibilidad visual y retroalimentación de contraste | Accesibilidad / UI Feedback | 1. Habilitar modo de texto ampliado / accesibilidad en ajustes del sistema.<br>2. Provocar estado de saldo insuficiente en la ventana de gestión.<br>3. Evaluar legibilidad de textos y contraste del botón. | El texto "¡SALDO INSUFICIENTE!" se visualiza con contraste nítido (Color Rojo / Alerta), sin solapamiento de elementos y con área táctil accesible. | Texto legible con contraste adecuado y dimensiones de ventana adaptativas. | ✅ APROBADO |

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
