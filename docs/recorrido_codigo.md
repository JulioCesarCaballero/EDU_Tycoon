### 2.3 Recorrido del código: Compra y Mejora de una Propiedad

Elegimos la funcionalidad de "Comprar / Mejorar un edificio". El recorrido desde que el usuario interactúa hasta que el estado del juego cambia es el siguiente:

**1. Acción del usuario y Capa de Interfaz:**
El flujo inicia en el archivo `BuildingInfoWindow.kt`[cite: 12]. Aquí se dibuja la ventana emergente y se define el botón de acción. Cuando el jugador lo presiona, se captura el evento en el bloque `onChange`, se valida el saldo y se aplica el cobro[cite: 12]:

```kotlin
textButton(btnTexto) {
    isDisabled = !puedeMejorar
    onChange {
        if (!puedeMejorar) return@onChange
        
        // Se intenta validar el saldo (Aquí ocurre el defecto actual)
        if (!GameState.puedeComprar(costo)) {
            setText("¡Saldo insuficiente!")
            color = Color.RED
            isDisabled = true
            return@onChange
        }
        
        // Cobro y actualización visual
        GameState.gastar(costo)
        if (!data.comprada) {
            data.comprada = true
            data.nivel    = 1
        } else {
            data.nivel++
        }
        onBuildingChanged()
        this@BuildingInfoWindow.remove()
    }
}
```

**2. Modificación del Modelo de Datos:**
Los atributos de la instalación residen en el archivo `Propiedad.kt`[cite: 10]. Al procesar la compra en la interfaz, se modifica directamente el nivel y el estado de la instancia en memoria[cite: 10, 12]:

```kotlin
data class Propiedad(
    val id: String,
    val nombre: String,
    val precio: Long,
    // ...
    var nivel: Int = 0,
    var comprada: Boolean = false
)
```

**3. Efecto en la Lógica de Negocio (Economía):**
Una vez que `Propiedad.comprada` es `true` y sube de nivel, el motor económico en `EconomyEngine.kt` lo detecta durante el siguiente ciclo de renderizado y comienza a generar ingresos pasivos automáticamente usando la fórmula basada en alumnos y nivel[cite: 11].

**4. Archivos a modificar para cambiar el comportamiento:**
Para corregir el defecto que permite gastar más saldo del disponible y evitar números negativos, se debe modificar la validación dentro del evento del botón en `BuildingInfoWindow.kt` o ajustar la lógica interna de la resta global en el objeto `GameState`[cite: 12].