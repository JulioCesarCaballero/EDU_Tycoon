# Ficha de idea: IPN Tycoon (ruta EduTycoon)

> **Institución:** Instituto Politécnico Nacional (IPN)  
> **Unidad Académica:** Escuela Superior de Cómputo (ESCOM)  
> **Programa Académico:** Ingeniería en Sistemas Computacionales (Plan 2020)  
> **Unidad de Aprendizaje:** Desarrollo de aplicaciones móviles nativas  
> **Semestre / Periodo:** 2027-1  
> **Profesor Titular:** M. en C. Gabriel Hurtado Avila  
> **Equipo:** Equipo EduTycoon ESCOM  
> **Integrantes:**  
> - Aragón Martínez Manuel  
> - Caballero Pérez Julio César  
> - Hernandez Alvirde Maria Guadalupe  
> **Repositorio:** https://github.com/JulioCesarCaballero/EDU_Tycoon  
> **Fecha:** 5 de octubre de 2026  
> **Versión de la ficha:** 1.0 (Entrega 1 - Puntos 1.1 y 1.3)  

---

## 1.1 Ficha de idea

### 1. Ruta elegida y motivo

**Ruta elegida:** EduTycoon (IPN Tycoon: Kotlin + libGDX en Android Nativo).

**Motivo:** 
1. **Identidad comunitaria y pertinencia contextual:** Refleja la cotidianidad y los desafíos reales de los estudiantes del IPN (especialmente de la ESCOM), quienes deben administrar su tiempo, rendimiento académico, estrés, horas de sueño y presupuesto económico para sobrevivir al semestre escolar.
2. **Arquitectura modular nativa y probada:** El proyecto base EduTycoon ya cuenta con un desacoplamiento limpio entre la lógica multiplataforma (`core` en Kotlin puro con motores de economía y eventos) y el lanzador nativo de Android (`android`), facilitando la implementación de patrones de gestión de estado unidireccionales y pruebas unitarias aisladas sin dependencias del framework gráfico.
3. **Mecánica de gestión ligera (Tycoon Casual / Micro-decisiones):** Permite diseñar sesiones ágiles y adictivas de 3 a 5 minutos, ideales para jugar con una sola mano en trayectos de transporte público (Metro Politécnico/Lindavista o Metrobús) o tiempos muertos entre bloques de clase.

---

### 2. Usuario y contexto

* **¿Quién es el usuario?:** Estudiante universitario activo del IPN (18 a 24 años), principalmente de Ingeniería en Sistemas Computacionales y carreras afines con alta carga de trabajo práctico, que juega en dispositivos móviles Android y conoce los planteles del Instituto (ESCOM, ESFM, ENCB, etc.).
* **¿Dónde y cuándo usaría la aplicación?:** En partidas cortas de 3 a 8 minutos durante sus trayectos cotidianos en transporte público (Metro o camión hacia Zacatenco), en descansos de pasillo o cafetería entre clases, o en periodos de desconexión breve al finalizar su jornada académica. Juega habitualmente con interrupciones y requiere que el juego preserve su estado sin pérdidas.

---

### 3. Problema observable en una sola frase

> *"El estudiante gasta su presupuesto escolar y administra sus recursos sin conocer previamente el saldo restante ni el impacto que sus decisiones tendrán en sus niveles de estrés, descanso y finanzas, terminando en bancarrota o colapso académico sin entender la causa."*

---

### 4. Alternativa actual

Hoy, dentro del juego y en su vida diaria, el usuario:
- Decide a prueba y error, o intenta calcular mentalmente el dinero que le sobrará.
- Solo se entera de que no le alcanza **después** de pulsar el botón de compra, cuando este cambia tardíamente a *"¡Saldo insuficiente!"*.
- Visualiza en la ventana del plantel un ingreso por ciclo que **no coincide** con el que realmente acredita el motor económico (`BuildingInfoWindow.kt` calcula `baseAlumnos * nivel * 10`, pero `EconomyEngine.kt` acredita `baseAlumnos * nivel * 100`).
- Recurre a simuladores genéricos extranjeros (*BitLife*, *Reigns*) que carecen de contexto universitario local, o a apps de productividad pesadas (*Notion*, *Google Calendar*) que generan aburrimiento en lugar de una experiencia lúdica formativa.

---

### 5. Tarea principal

> **Evaluar y ejecutar compras de planteles o toma de decisiones académicas con el presupuesto disponible, conociendo de forma anticipada y visible cuánto saldo le quedará y el impacto directo en sus recursos antes de confirmar.**

---

### 6. Criterio de éxito

La propuesta se considera exitosa si, en una prueba de usabilidad con al menos 5 estudiantes de ESCOM que no hayan jugado previamente:
1. Al menos **4 de 5** logran comprar o mejorar un plantel en **menos de 2 minutos** sin requerir instrucciones externas.
2. Al menos **4 de 5** identifican y expresan verbalmente, **antes de presionar el botón de compra**, cuánto saldo les quedará después de la transacción.
3. En el **100% de los casos**, el sistema bloquea cualquier intento de compra con fondos insuficientes, garantizando que el saldo jamás resulte negativo ni se generen sobregiros.
4. El jugador comprende visualmente el balance de recursos esenciales para sobrevivir a los ciclos del semestre.

---

### 7. Delimitación del alcance: Primera versión (MVP - Entrega 1)

Lo que **entra** en la primera versión (construido y verificado sobre el repositorio):
* **Mapa interactivo del IPN:** Visualización de planteles escolares (ESCOM, Dirección General, Cafetería, etc.) y barra superior (HUD) con saldo de dinero, alumnos y ciclos jugados.
* **Ventana de gestión de plantel con información transparente:** Indicación clara del costo, saldo actual, **saldo restante proyectado tras la compra** e ingreso real por ciclo sincronizado con `EconomyEngine`.
* **Regla estricta de validación de compra:** Si el costo supera el saldo, el botón se inicializa deshabilitado desde que se abre la ventana, se resalta en color rojo e indica **cuánto dinero falta** para realizar la operación.
* **Actualización atómica de estado:** Actualización inmediata de saldo en `GameState`, nivel del edificio y cambio visual del sprite en el mapa sin inconsistencias de concurrencia.
* **Manejo de estados alternos:**
  * **Carga:** Al inicializar la partida o cargar slots locales.
  * **Vacío:** Cuando el plantel ya alcanzó su nivel máximo o no existen registros previos.
  * **Dato inválido / Error:** Detección y bloqueo de compras con saldo insuficiente.
  * **Persistencia local:** Guardado y recuperación confiable del estado de la partida.

---

### 8. Funciones aplazadas (Deliberadamente fuera del MVP)

Para mantener un alcance verificable y técnicamente riguroso en este primer avance, se aplazan para entregas posteriores:
* **Gestión de estrés y sueño avanzada con minijuegos de código:** Se aplaza para la Entrega 2 para priorizar la estabilidad de las reglas económicas y de compra en la Entrega 1.
* **Préstamos estudiantiles y sistema de deuda bancaria controlada:** Requiere diseño de tasas de interés y penalizaciones semestrales.
* **Eventos con ramificaciones complejas de diálogo:** Actualmente los eventos se disparan por ciclo de forma automática.
* **Sincronización en la nube y tabla de clasificación global (Leaderboard):** Evita dependencias de red y servicios externos no solicitados en la Entrega 1.
* **Árbol de materias optativas y habilidades del estudiante:** Se aplaza para la Entrega final.

---

### 9. Evidencia e hipótesis pendiente de validar

#### Evidencia técnica observada en el repositorio base

| Fecha | Método | Resultado real |
| :--- | :--- | :--- |
| **04/10/2026** | Inspección estática de código | `BuildingInfoWindow.kt` calcula el ingreso mostrado con `baseAlumnos * nivel * 10`, mientras que `EconomyEngine.kt` acredita `baseAlumnos * nivel * 100`. La interfaz subestimaba por un factor de 10 el beneficio real del edificio. |
| **04/10/2026** | Prueba de interacción en UI | El aviso de fondos insuficientes solo se desplegaba de forma reactiva al hacer clic en el botón de compra, omitiendo indicar preventivamente el monto faltante. |
| **04/10/2026** | Análisis de transacciones | En `EventEngine.kt`, si un evento de gasto superaba el saldo disponible, la función `gastar()` retornaba `false` sin alertar explícitamente al jugador del bloqueo. |

#### Declaración explícita de hipótesis sin validar

> **Hipótesis pendiente de validar:**
> *"Se declara formalmente que esta propuesta constituye una **hipótesis pendiente de validar**. Se plantea que proporcionar al estudiante una interfaz con desglose preventivo del saldo restante proyectado y bloqueo explícito con monto faltante incrementa la comprensión económica y reduce en al menos un 80% las partidas perdidas por insolvencia no planificada en comparación con la interfaz reactiva original.*
> 
> *La validación experimental se realizará antes de la Entrega 2 mediante pruebas presenciales con 5 compañeros de ESCOM evaluando tiempo de decisión, tasa de error y retroalimentación cualitativa."*

---

## 1.3 Historias de usuario y criterios de aceptación

### Historia de Usuario Principal (Core Gameplay Loop)

```gherkin
Como estudiante de ingeniería en sistemas y jugador de EduTycoon,
quiero conocer con anticipación el costo, el saldo que me quedará y el ingreso que generará un plantel antes de comprarlo,
para tomar decisiones financieras inteligentes y evitar quedarme sin saldo para operar durante el semestre escolar.
```

---

### Criterio de Aceptación Principal (Ruta Feliz: Compra Válida con Fondos Suficientes)

```gherkin
Escenario: Compra exitosa de un plantel educativo con saldo suficiente
  Dado que el jugador tiene un saldo disponible de $500,000 MXN en GameState,
    y selecciona el plantel "Escuela de Computación" (precio: $300,000 MXN, nivel actual: 0, no comprada),
  Cuando abre la ventana de gestión del plantel ("BuildingInfoWindow"),
  Entonces el botón de acción aparece habilitado con el texto "COMPRAR $300.0K",
    y se muestra la etiqueta con el saldo proyectado posterior a la compra ("Saldo restante: $200.0K"),
    y al pulsar el botón, el saldo disminuye a $200,000 MXN,
    y el plantel se marca como comprado con nivel 1,
    y la ventana se cierra actualizando el sprite del edificio en el mapa del IPN.
```

*Verificabilidad:* **Sí / No**. Un evaluador externo puede abrir la ventana, corroborar que el saldo proyectado coincida con la resta aritmética ($500K - $300K = $200K), pulsar el botón y constatar que el saldo y el nivel se actualicen en pantalla sin margen de interpretación.

---

### Criterios de Aceptación para Estados Alternos y Casos Límite

#### Escenario Alterno 1: Bloqueo de Compra por Saldo Insuficiente (Dato Inválido / Regla de Compra)
```gherkin
Escenario: Intento de compra cuando el costo supera el saldo disponible
  Dado que el jugador dispone de un saldo de $150,000 MXN,
    y abre la ventana del plantel "Dirección General" cuyo costo es de $5,000,000 MXN,
  Cuando se renderiza la ventana de gestión,
  Entonces el botón de acción se inicializa automáticamente deshabilitado (isDisabled = true),
    y el texto del botón se muestra en color rojo con el mensaje de advertencia y el saldo faltante: "¡SALDO INSUFICIENTE! (Faltan $4.85M)",
    y el saldo del jugador permanece intacto en $150,000 MXN sin permitir cobros parciales ni sobregiros.
```

#### Escenario Alterno 2: Mejora de Nivel con Fondos Insuficientes
```gherkin
Escenario: El jugador intenta mejorar un plantel comprado sin tener el saldo requerido
  Dado que el jugador posee el plantel "Cafetería" en nivel 1 (costo de mejora a nivel 2: $150,000 MXN),
    y su saldo actual en GameState es de $40,000 MXN,
  Cuando abre la ventana del plantel,
  Entonces el sistema evalúa que el saldo ($40,000) es menor al costo de mejora ($150,000),
    y el botón de mejora permanece deshabilitado indicando el faltante de $110,000 MXN,
    y no se incrementa el nivel del plantel.
```

#### Escenario Alterno 3: Manejo de Estado Límite / Vacío (Plantel en Nivel Máximo)
```gherkin
Escenario: El plantel ha alcanzado su límite de mejora
  Dado que el plantel "Auditorio Alejo Peralta" se encuentra en su nivel máximo de mejora (nivel 2 de 2),
  Cuando el usuario consulta la ventana de información del plantel,
  Entonces el botón de acción aparece deshabilitado con el texto "NIVEL MÁXIMO",
    y no se muestra costo de mejora ni opciones de compra,
    y el sistema indica que la capacidad operativa del inmueble está al 100%.
```

#### Escenario Alterno 4: Restablecimiento y Consistencia de Partida (*Reset State*)
```gherkin
Escenario: Inicio de una nueva partida o reseteo de progreso
  Dado que el jugador decide reiniciar su partida desde el menú principal,
  Cuando se invoca el reinicio del juego,
  Entonces el saldo se restablece al valor base inicial ($500,000 MXN),
    y todos los planteles vuelven al estado de no comprados (nivel 0),
    y el contador de ciclos jugados se reinicia a 0 sin que queden datos residuales en memoria.
```

---

## 10. Declaración de uso de herramientas de Inteligencia Artificial

* En apego a los lineamientos de la entrega, se declara de forma transparente que para la redacción inicial y estructuración de la ficha de idea se consultó **Claude (Anthropic)**, y para la profundización técnica, integración del modelo de micro-gestión estudiantil y formulación formal de las historias de usuario y criterios de aceptación Gherkin verificables se utilizó **Gemini (Google DeepMind)**. Todo el contenido fue revisado, adaptado y validado técnicamente por los integrantes del equipo.
