# Ficha de idea: IPN Tycoon (ruta EduTycoon)

> **Equipo:** 
> **Integrantes:** Hernandez Alvirde Maria Guadalupe
> **Repositorio:** https://github.com/JulioCesarCaballero/EDU_Tycoon
> **Fecha:** 5 de octubre de 2026
> **Versión de la ficha:** 1.0 (Entrega 1)

---

## 1. Ruta elegida y motivo

**Ruta:** EduTycoon (IPN Tycoon: Kotlin + libGDX, Android).

**Motivo:** 

## 2. Usuario y contexto

**¿Quién es?** Un estudiante del IPN de 18 a 24 años que juega en el celular y conoce los planteles del juego (ESCOM, ESFM, ENCB, etc.).

**¿Dónde y cuándo usaría la app?** En partidas cortas de 5 a 10 minutos: en el metro o el camión de camino a la escuela, o entre clases. Juega con una mano, con interrupciones, y espera poder dejar la partida y retomarla después.

## 3. Problema observable

> **El jugador gasta su presupuesto en planteles sin saber cuánto le quedará ni en cuánto tiempo recuperará la inversión, y termina sin dinero para mejorar o para cubrir imprevistos sin entender por qué.**

## 4. Alternativa actual

Hoy, dentro del juego, el jugador:

- Decide a prueba y error, o calcula de cabeza el saldo que le quedará.
- Solo se entera de que no le alcanza **después** de presionar *Comprar*, cuando el botón cambia a "¡Saldo insuficiente!".
- Ve en la ventana del plantel un ingreso por ciclo que **no coincide** con el que realmente recibe (ver la sección 9).

## 5. Tarea principal

> **Comprar o mejorar un plantel con el presupuesto disponible, sabiendo antes de confirmar cuánto saldo le quedará y cuánto ganará por ciclo.**

## 6. Criterio de éxito

La idea funciona si, en una prueba con al menos 5 compañeros que no han jugado antes:

- Al menos **4 de 5** compran o mejoran un plantel en **menos de 2 minutos**, sin ayuda.
- Al menos **4 de 5** pueden decir, antes de confirmar, **cuánto saldo les quedará** después de la compra.
- En ningún caso el saldo queda negativo ni se completa una compra con saldo insuficiente.

## 7. Alcance de la primera versión

Lo que **entra** en la v1 (se construye sobre lo que ya existe en el repositorio):

- Mapa del IPN con los planteles actuales y el HUD de dinero, alumnos y reputación.
- Ventana del plantel con: costo, **saldo actual**, **saldo después de la compra** e **ingreso por ciclo correcto** (el mismo que calcula `EconomyEngine`).
- **Regla de compra:** si el costo supera el saldo, el botón aparece deshabilitado desde que se abre la ventana e indica **cuánto dinero falta**. No se puede gastar más del saldo disponible.
- Actualización inmediata del HUD y del sprite del plantel después de comprar o mejorar.
- Guardado y carga local de la partida (Room, ya existente).
- Estados alternos:
  - **Carga:** al abrir una partida guardada.
  - **Vacío:** no hay partidas guardadas, o el plantel ya está en su nivel máximo.
  - **Error:** no se pudo guardar o cargar la partida.
  - **Dato inválido:** saldo insuficiente para la compra o mejora.

## 8. Funciones aplazadas (de forma deliberada)

Estas funciones **no** entran en la v1, para mantener el alcance pequeño y verificable:

- Gestión de docentes y presupuesto por área.
- Préstamos o deuda (saldo negativo controlado).
- Eventos con decisiones del jugador (hoy los eventos son automáticos).
- Planteles nuevos que todavía no tienen sprite.
- Ranking o tabla de posiciones en línea, y sincronización en la nube.
- Logros, estadísticas históricas y tutorial extendido.

## 9. Evidencia e hipótesis pendiente de validar

### Evidencia observada en el proyecto

| Fecha | Método | Resultado real |
|---|---|---|
| 4 oct 2026 | Revisión del código del repositorio | `BuildingInfoWindow.kt` calcula el ingreso mostrado con `baseAlumnos * nivel * 10`, pero `EconomyEngine.kt` acredita `baseAlumnos * nivel * 100`. La ventana muestra **10 veces menos** ingreso del real. |
| 4 oct 2026 | Revisión del código del repositorio | El aviso de saldo insuficiente solo aparece **después** de presionar el botón de compra; antes no se muestra cuánto falta. |
| 4 oct 2026 | Revisión del código del repositorio | En `EventEngine.kt`, si un evento de gasto (por ejemplo, "Fuga de agua") cuesta más que el saldo, `gastar()` regresa `false` y el gasto se ignora sin avisar al jugador. |

[AJUSTAR: confirmar cada punto ejecutando el juego y registrar las issues correspondientes.]

### Hipótesis sin validar



**Plan para validarla (antes de la Entrega 2):**

| Qué | Método | Con quién | Resultado esperado |
|---|---|---|---|
| ¿Entienden cuánto les queda? | Prueba de uso con la versión actual y con la v1 | 5 compañeros | Con la v1, al menos 4 de 5 dicen el saldo restante antes de confirmar |
| ¿Se quedan sin dinero? | Partida de 10 minutos en cada versión | Los mismos 5 | Menos jugadores bloqueados sin dinero con la v1 |
| ¿Es claro el bloqueo de compra? | Pregunta posterior a la prueba | Los mismos 5 | Al menos 4 de 5 explican por qué no pudieron comprar |

Los resultados reales (fecha, método y resultado) se registrarán en esta sección cuando se obtengan.

---

## Declaración de uso de IA

Para redactar el primer borrador de esta ficha y revisar el código del repositorio se usó **Claude (Anthropic)**. El equipo revisó y ajustó el contenido. [AJUSTAR si hicieron más cambios o usaron otra herramienta.]
