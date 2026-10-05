package io.moviles.IPN_Tycoon

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.kotcrab.vis.ui.widget.VisWindow
import io.moviles.IPN_Tycoon.engine.ReglaDeCompra
import io.moviles.IPN_Tycoon.engine.ResultadoCompra
import ktx.actors.onChange
import ktx.scene2d.*

class BuildingInfoWindow(
    private val data: Propiedad,
    private val onBuildingChanged: () -> Unit
) : VisWindow("Gestión del Plantel") {

    private lateinit var saldoLabel: Label
    private lateinit var estadoLabel: Label
    private lateinit var botonAccion: TextButton

    /** Último saldo mostrado. Si el dinero cambia con la ventana abierta (fin de ciclo o evento), se recalcula. */
    private var saldoMostrado = Long.MIN_VALUE

    init {
        addCloseButton()
        closeOnEscape()
        isModal = false

        add(scene2d.table {

            // ── Nombre ────────────────────────────────────────────────
            label(data.nombre) {
                color = Color.GOLD
            }.cell(padBottom = 6f)
            row()

            // ── Descripción ───────────────────────────────────────────
            label(data.descripcion) {
                setWrap(true)
                color = Color.LIGHT_GRAY
            }.cell(width = 280f, padBottom = 10f)
            row()

            // ── Stats ─────────────────────────────────────────────────
            label("Capacidad: ${data.capacidad} alumnos"); row()

            if (data.comprada) {
                label("Nivel actual: ${data.nivel} / ${data.mejoraMax}") {
                    color = Color.CYAN
                }.cell(padBottom = 2f)
                row()

                val ingresoCiclo = data.baseAlumnos * data.nivel * 10L
                label("Ingreso/ciclo: \$${fmt(ingresoCiclo)}") {
                    color = Color.GREEN
                }
                row()

                if (data.nivel < data.mejoraMax) {
                    val ingresoSiguiente = data.baseAlumnos * (data.nivel + 1) * 10L
                    label("Nivel ${data.nivel + 1}: \$${fmt(ingresoSiguiente)}/ciclo") {
                        color = Color.LIGHT_GRAY
                    }
                    row()
                }
            } else {
                val ingresoNivel1 = data.baseAlumnos * 1 * 10L
                label("Ingreso al comprar: \$${fmt(ingresoNivel1)}/ciclo") {
                    color = Color.GREEN
                }
                row()
            }

            // ── Costo ─────────────────────────────────────────────────
            ReglaDeCompra.costoSiguiente(data)?.let { costo ->
                label("Costo: \$${fmt(costo)}") {
                    color = Color.LIGHT_GRAY
                }.cell(padTop = 6f)
                row()
            }

            // ── Saldo actual ──────────────────────────────────────────
            label("") {
                color = Color.LIGHT_GRAY
                saldoLabel = this
            }.cell(padTop = 2f, padBottom = 2f)
            row()

            // ── Saldo después o cuánto falta ──────────────────────────
            label("") {
                estadoLabel = this
            }.cell(padBottom = 4f)
            row()

            // ── Botón acción ──────────────────────────────────────────
            textButton("") {
                botonAccion = this
                onChange { intentarCompra() }
            }.cell(padTop = 14f, expandX = true, fillX = true)
        }).pad(16f)

        actualizarEstado()
        pack()
        centerWindow()
    }

    override fun act(delta: Float) {
        super.act(delta)
        if (GameState.dinero != saldoMostrado) actualizarEstado()
    }

    fun show(stage: Stage) { stage.addActor(this) }

    /** Aplica la regla de compra a la ventana: textos, colores y si el botón está habilitado. */
    private fun actualizarEstado() {
        saldoMostrado = GameState.dinero
        saldoLabel.setText("Tu saldo: \$${fmt(saldoMostrado)}")

        when (val resultado = ReglaDeCompra.evaluar(saldoMostrado, ReglaDeCompra.costoSiguiente(data))) {
            is ResultadoCompra.Permitida -> {
                estadoLabel.setText("Saldo después: \$${fmt(resultado.saldoDespues)}")
                estadoLabel.color = Color.LIGHT_GRAY
                botonAccion.setText(textoAccion(resultado.costo))
                botonAccion.isDisabled = false
                botonAccion.color = Color.WHITE
            }
            is ResultadoCompra.SaldoInsuficiente -> {
                estadoLabel.setText("Te faltan \$${fmt(resultado.faltante)}")
                estadoLabel.color = Color.SALMON
                botonAccion.setText("SALDO INSUFICIENTE")
                botonAccion.isDisabled = true
                botonAccion.color = Color.GRAY
            }
            ResultadoCompra.NivelMaximo -> {
                estadoLabel.setText("")
                botonAccion.setText("NIVEL MÁXIMO")
                botonAccion.isDisabled = true
                botonAccion.color = Color.WHITE
            }
        }
    }

    private fun intentarCompra() {
        when (ReglaDeCompra.comprar(data)) {
            is ResultadoCompra.Permitida -> {
                onBuildingChanged()
                remove()
            }
            // Si el saldo cambió justo antes del clic, la compra no se hace y la ventana muestra el estado real.
            else -> actualizarEstado()
        }
    }

    private fun textoAccion(costo: Long) =
        if (!data.comprada) "COMPRAR  \$${fmt(costo)}"
        else "MEJORAR LVL ${data.nivel + 1}  \$${fmt(costo)}"

    private fun fmt(v: Long) = when {
        v >= 1_000_000L -> "${"%.2f".format(v / 1_000_000.0)}M"
        v >= 1_000L     -> "${"%.1f".format(v / 1_000.0)}K"
        else            -> v.toString()
    }
}
