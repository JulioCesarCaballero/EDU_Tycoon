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

## 📸 Galería Visual de la Actualización (Entrega 2 - Mecánica Tycoon)

A continuación se muestra la evidencia gráfica de la nueva funcionalidad implementada para la actualización de Google Play:

### 1. Comparativa de Comportamiento: Antes vs. Después

| Antes: Sin Burbujas (Generación Pasiva e Incierta) | Después: Burbujas Interactivas (Recaudación Táctil Tycoon) |
| :---: | :---: |
| ![Captura Antes de Burbujas](docs/evidencia/entrega-2/captura_antes_de_burbujas_interactivas.jpg) | ![Burbujas Interactivas Activas](docs/evidencia/entrega-2/burbujas_interactivas.jpg) |
| <sub>**Antes:** Los edificios generaban saldo automáticamente sin retroalimentación visual ni aviso de tiempo.</sub> | <sub>**Después:** Burbujas doradas flotantes oscilan sobre cada plantel al completarse su ciclo de ingresos.</sub> |

---

### 2. Flujo Completo de la Nueva Mecánica en Juego

#### A. Definición y Transparencia de Ciclos de Producción por Edificio
Al pulsar sobre cualquier plantel escolar, la ficha de gestión (`BuildingInfoWindow`) ahora transparenta la duración del ciclo de ingresos y desglosa el tiempo restante en segundos, así como la ganancia que generará cada ciclo.

| Definición de Tiempo por Ciclo | Tiempo de Ciclo de Ingresos Añadido |
| :---: | :---: |
| ![Definición de Tiempo por Ciclo](docs/evidencia/entrega-2/definicion_de_tiempo_por_ciclo_de_ingreso.jpg) | ![Tiempo de Ciclo de Ingresos Añadido](docs/evidencia/entrega-2/tiempo_de_ciclo_ingresos_anadido.jpg) |
| <sub>Visualización de estadísticas base y duración configurada del ciclo (ej. 35s en Escuela de Computación).</sub> | <sub>Desglose en tiempo real del tiempo restante para el cobro o alerta de dinero listo para recolectar.</sub> |

#### B. Generación y Notificación Emergente de Fondos
Al finalizar el ciclo de producción, el edificio deja de estar en espera pasiva y despliega la burbuja interactiva con el indicador de saldo listo para cobro sobre el tejado del inmueble.

<p align="center">
  <img src="docs/evidencia/entrega-2/burbuja_dinero_emergente.jpg" width="600" alt="Burbuja de Dinero Emergente" /><br>
  <sub><b>Burbuja emergente sobre el plantel:</b> Notificación gráfica que señala al jugador que los ingresos están listos para cobro manual.</sub>
</p>

#### C. Animación de Recolección Táctil y Actualización en el HUD
Al pulsar la burbuja con el dedo, se dispara el tintineo de monedas (`coin.wav`), una animación de texto ascendente (`+$X`) que se desvanece suavemente hacia arriba, y el saldo en el HUD superior izquierdo se incrementa al instante.

| Animación de Recolección al Tocar la Burbuja | Saldo del HUD Actualizado de Inmediato |
| :---: | :---: |
| ![Animación de Recolección](docs/evidencia/entrega-2/animacion_recoleccion_dinero.jpg) | ![Saldo Actualizado](docs/evidencia/entrega-2/saldo_actualizado_dinero_recolectado.jpg) |
| <sub>Animación flotante del monto recaudado elevándose sobre el edificio con desvanecimiento alfa.</sub> | <sub>Reflejo inmediato del dinero recolectado en el saldo general del campus escolar.</sub> |

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
