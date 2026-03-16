package com.example.allcalculator.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.allcalculator.domain.MotorCalculadora

class CalculadoraViewModel : ViewModel() {
    private val motor = MotorCalculadora()

    // O que aparece no visor principal
    var estadoVisor by mutableStateOf("0")
        private set

    // O pré-resultado (menor, aparece enquanto digitamos)
    var estadoPreResultado by mutableStateOf("")
        private set

    // Expondo as variáveis para a tela poder montar a expressão (ex: "5 + ")
    var numeroAnterior by mutableStateOf("")
        private set
    var operacaoAtual by mutableStateOf("")
        private set

    // Lista reativa que guarda o histórico das operações
    val historico = mutableStateListOf<String>()

    private var esperandoNovoNumero = false

    fun aoDigitarNumero(numero: String) {
        if (esperandoNovoNumero) {
            estadoVisor = numero
            esperandoNovoNumero = false
        } else {
            if (estadoVisor == "0" && numero != ".") {
                estadoVisor = numero
            } else if (numero == "." && estadoVisor.contains(".")) {
                return
            } else {
                estadoVisor += numero
            }
        }
        atualizarPreResultado() // Calcula dinamicamente ao digitar
    }

    fun aoEscolherOperacao(operacao: String) {
        if (numeroAnterior.isNotEmpty() && operacaoAtual.isNotEmpty() && !esperandoNovoNumero) {
            calcularResultado()
        }
        numeroAnterior = estadoVisor
        operacaoAtual = operacao
        esperandoNovoNumero = true
        estadoPreResultado = "" // Limpa o pré-resultado ao trocar a operação
    }

    fun calcularResultado() {
        if (numeroAnterior.isNotEmpty() && operacaoAtual.isNotEmpty() && !esperandoNovoNumero) {
            val resultado = motor.calcular(numeroAnterior, estadoVisor, operacaoAtual)
            if (resultado.isNotEmpty() && resultado != "Erro") {
                // Adiciona a conta completa ao histórico
                historico.add("$numeroAnterior $operacaoAtual $estadoVisor = $resultado")

                estadoVisor = resultado
                numeroAnterior = ""
                operacaoAtual = ""
                estadoPreResultado = ""
                esperandoNovoNumero = true
            }
        }
    }

    fun limpar() {
        estadoVisor = "0"
        numeroAnterior = ""
        operacaoAtual = ""
        estadoPreResultado = ""
        esperandoNovoNumero = false
        // Nota: A função limpar ("C") não apaga o histórico, como nas calculadoras reais.
    }

    fun apagarUltimo() {
        if (esperandoNovoNumero || estadoVisor == "Erro") return
        estadoVisor = if (estadoVisor.length > 1) {
            estadoVisor.dropLast(1)
        } else {
            "0"
        }
        atualizarPreResultado() // Atualiza o pré-resultado se apagar um número
    }

    // Função interna que calcula a operação antes de apertar o "="
    private fun atualizarPreResultado() {
        if (numeroAnterior.isNotEmpty() && operacaoAtual.isNotEmpty() && estadoVisor != "Erro" && estadoVisor.isNotEmpty() && estadoVisor != ".") {
            val parcial = motor.calcular(numeroAnterior, estadoVisor, operacaoAtual)
            estadoPreResultado = if (parcial != "Erro") "= $parcial" else ""
        } else {
            estadoPreResultado = ""
        }
    }
    // NOVA FUNÇÃO: Aplica a regra matemática da porcentagem
    fun aplicarPorcentagem() {
        if (estadoVisor == "Erro" || estadoVisor.isEmpty()) return

        try {
            val valorAtual = java.math.BigDecimal(estadoVisor)

            if (numeroAnterior.isEmpty()) {
                // Cenário 1: Apenas divide por 100 (ex: "50" -> "0.5")
                estadoVisor = valorAtual.divide(java.math.BigDecimal("100"), 8, java.math.RoundingMode.HALF_UP)
                    .stripTrailingZeros().toPlainString()
            } else {
                // Cenário 2: Calcula a porcentagem baseada no número anterior (ex: 200 + 10% = 20)
                val valorBase = java.math.BigDecimal(numeroAnterior)
                val valorPorcentagem = valorBase.multiply(valorAtual)
                    .divide(java.math.BigDecimal("100"), 8, java.math.RoundingMode.HALF_UP)
                estadoVisor = valorPorcentagem.stripTrailingZeros().toPlainString()
            }
            atualizarPreResultado() // Atualiza o mini-visor automaticamente
        } catch (e: Exception) {
            estadoVisor = "Erro"
        }
    }
    // NOVA FUNÇÃO: Inverte o sinal do número atual (positivo para negativo e vice-versa)
    fun inverterSinal() {
        if (estadoVisor == "0" || estadoVisor == "Erro" || estadoVisor.isEmpty()) return

        estadoVisor = if (estadoVisor.startsWith("-")) {
            estadoVisor.drop(1) // Remove o sinal de menos se já for negativo
        } else {
            "-$estadoVisor" // Adiciona o sinal de menos se for positivo
        }
        atualizarPreResultado() // Atualiza o mini-visor para refletir a mudança
    }
}