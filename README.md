# EduTycoon - Simulador de Gestión e Infraestructura IPN

EduTycoon es un videojuego de simulación económica y gestión escolar desarrollado en Kotlin y [libGDX](https://libgdx.com/) para Android, desarrollado para la unidad de aprendizaje **Desarrollo de Aplicaciones Móviles Nativas (2027-1)** en la **Escuela Superior de Cómputo (ESCOM)** del **Instituto Politécnico Nacional (IPN)**.

---

## 🚀 Historial de Versiones y Lanzamientos (Google Play)

* **Versión 1.1.0 (versionCode 2) - Actualización Entrega 2:**
  - **Mecánica Tycoon de Recaudación Interactiva:** Cada edificio adquirido cuenta con un temporizador de ciclo de producción independiente.
  - **Burbujas Flotantes Táctiles:** Al concluir el ciclo de un edificio, aparece una burbuja interactiva oscilatoria sobre el plantel para recolectar las ganancias manualmente.
  - **Retroalimentación Multisensorial:** Integración de efecto sonoro de monedas/caja registradora (`coin.wav`), animación de texto ascendente con desvanecimiento (`+$X`) y actualización inmediata del HUD.
  - **Equilibrio Económico Progresivo:** Edificios económicos iniciales (Mac & Cheese, Cafetería) tienen ciclos más lentos (45s - 50s); escuelas universitarias y Dirección General entregan mayores ganancias en ciclos más veloces (15s - 25s).
  - **Ficha Informativa Enriquecida:** Desglose del tiempo de ciclo por plantel, segundos restantes y alerta visual de fondos disponibles para cobro.

* **Versión 1.0.0 (versionCode 1) - Lanzamiento Inicial Entrega 1:**
  - Publicación inicial en canal de Prueba Cerrada de Google Play Console.
  - Mapa isométrico interactivo del campus politécnico.
  - Sistema de compra y mejora de edificios escolares (`GameState`, `ReglaDeCompra`).
  - Música ambiental de fondo y control reactivo de audio en menú de pausa (`AudioManager`).

---

## 👥 Instrucciones para Personas Probadoras (Testers)

1. **Acceso al Programa de Prueba Cerrada:**
   - Inicia sesión con la cuenta de Google registrada en la lista de evaluadores autorizados.
   - Accede al enlace de invitación de la Prueba Cerrada en Google Play Console y selecciona **"Aceptar invitación / Convertirse en evaluador"**.
2. **Descarga de la Versión Inicial (v1.0.0):**
   - Descarga e instala EduTycoon desde Google Play Store.
   - Ejecuta la aplicación, completa el tutorial con el Ing. Lázaro Cárdenas y adquiere uno de los primeros edificios accesibles (ej. Cafetería o Mac & Cheese).
3. **Validación de la Actualización OTA (v1.1.0):**
   - Abre Google Play Store sin desinstalar la app previa.
   - Presiona el botón **"Actualizar"** para recibir la versión 1.1.0.
   - Comprueba que tus datos y saldo previo se conserven intactos.
   - Observa la aparición de la burbuja interactiva dorada sobre tus edificios al concluir el ciclo y tócala para escuchar el tintineo de monedas y recolectar las ganancias.

---

## 📁 Documentación Oficial de la Entrega

* 📋 **[docs/entrega-2.md](docs/entrega-2.md):** Índice oficial de verificación (tablero Scrum, enlaces a los 3 PRs fusionados, comparativa técnica y justificación psicológica de diseño).
* 🧪 **[docs/pruebas.md](docs/pruebas.md):** Matriz exhaustiva de QA con los 6 casos de prueba reales obligatorios (instalación, actualización OTA, ciclos, accesibilidad y ciclo de vida).
* 🏷️ **Etiqueta Git Final:** `parcial-2`

---

## 🛠️ Comandos de Construcción y Verificación Local

El proyecto cuenta con integración continua (CI) y soporte Gradle:

* **Configuración del JDK (Android Studio JBR):**
  ```powershell
  $env:JAVA_HOME = "C:\Program Files\Android\Android Studio\jbr"
  ```
* **Ejecutar Suite de Pruebas Unitarias:**
  ```powershell
  ./gradlew :core:test
  ```
* **Compilar Artefacto Android Debug:**
  ```powershell
  ./gradlew assembleDebug
  ```
* **Generar Bundle de Publicación (AAB):**
  ```powershell
  ./gradlew :android:bundleRelease
  ```
