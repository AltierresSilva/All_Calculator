package com.example.allcalculator.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.allcalculator.ui.components.AnuncioBanner // Importação do nosso novo banner
import com.example.allcalculator.ui.components.BotaoCalculadora
import com.example.allcalculator.viewmodel.CalculadoraViewModel

@Composable
fun TelaCalculadoraPadrao(
    viewModel: CalculadoraViewModel = viewModel()
) {
    // Paleta de cores da nossa interface
    val corFundoApp = Color(0xFF1C1C1C)
    val corBotaoNumero = Color(0xFF333333)
    val corBotaoAcao = Color(0xFFA5A5A5)
    val corBotaoOperacao = Color(0xFFFF9800)

    // Observando o estado do nosso ViewModel (O Cérebro)
    val textoVisor = viewModel.estadoVisor
    val preResultado = viewModel.estadoPreResultado
    val expressao = if (viewModel.numeroAnterior.isNotEmpty()) {
        "${viewModel.numeroAnterior} ${viewModel.operacaoAtual}"
    } else ""

    // Controle de rolagem automática para o histórico
    val listState = rememberLazyListState()
    LaunchedEffect(viewModel.historico.size) {
        if (viewModel.historico.isNotEmpty()) {
            listState.animateScrollToItem(viewModel.historico.size - 1)
        }
    }

    // Estrutura principal da tela
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(corFundoApp)
            .padding(16.dp)
    ) {
        // --- 1. ANÚNCIO NO TOPO ---
        AnuncioBanner()

        // --- 2. CABEÇALHO ---
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Padrão",
                color = corBotaoAcao,
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium
            )
            TextButton(onClick = {
                // Futuramente: Lógica para mostrar o anúncio intersticial e navegar para a Tela PRO
            }) {
                Text(
                    text = "⭐ MODO PRO",
                    color = corBotaoOperacao,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        // --- 3. HISTÓRICO DAS CONTAS ---
        LazyColumn(
            state = listState,
            modifier = Modifier
                .weight(1f) // Este weight empurra todo o teclado para baixo de forma responsiva
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.End
        ) {
            items(viewModel.historico) { item ->
                Text(
                    text = item,
                    color = Color.Gray.copy(alpha = 0.7f),
                    fontSize = 24.sp,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
        }

        // --- 4. PAINEL DO VISOR PRINCIPAL ---
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp, top = 16.dp),
            horizontalAlignment = Alignment.End
        ) {
            // Conta em andamento (ex: "10 +")
            Text(text = expressao, fontSize = 28.sp, color = corBotaoAcao)

            // Número atual sendo digitado
            Text(
                text = textoVisor,
                fontSize = 64.sp,
                fontWeight = FontWeight.Light,
                color = Color.White,
                textAlign = TextAlign.End
            )

            // Resultado parcial em tempo real
            Text(
                text = preResultado,
                fontSize = 28.sp,
                color = corBotaoAcao,
                modifier = Modifier.height(32.dp)
            )
        }

        // --- 5. TECLADO ---

        // Linha 1: Limpar, Inverter Sinal, Porcentagem, Dividir
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            BotaoCalculadora("C", corBotaoAcao, Color.Black) { viewModel.limpar() }
            BotaoCalculadora("±", corBotaoAcao, Color.Black) { viewModel.inverterSinal() }
            BotaoCalculadora("%", corBotaoAcao, Color.Black) { viewModel.aplicarPorcentagem() }
            BotaoCalculadora("÷", corBotaoOperacao) { viewModel.aoEscolherOperacao("÷") }
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Linha 2: 7, 8, 9, Multiplicar
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            BotaoCalculadora("7", corBotaoNumero) { viewModel.aoDigitarNumero("7") }
            BotaoCalculadora("8", corBotaoNumero) { viewModel.aoDigitarNumero("8") }
            BotaoCalculadora("9", corBotaoNumero) { viewModel.aoDigitarNumero("9") }
            BotaoCalculadora("×", corBotaoOperacao) { viewModel.aoEscolherOperacao("×") }
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Linha 3: 4, 5, 6, Subtrair
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            BotaoCalculadora("4", corBotaoNumero) { viewModel.aoDigitarNumero("4") }
            BotaoCalculadora("5", corBotaoNumero) { viewModel.aoDigitarNumero("5") }
            BotaoCalculadora("6", corBotaoNumero) { viewModel.aoDigitarNumero("6") }
            BotaoCalculadora("-", corBotaoOperacao) { viewModel.aoEscolherOperacao("-") }
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Linha 4: 1, 2, 3, Somar
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            BotaoCalculadora("1", corBotaoNumero) { viewModel.aoDigitarNumero("1") }
            BotaoCalculadora("2", corBotaoNumero) { viewModel.aoDigitarNumero("2") }
            BotaoCalculadora("3", corBotaoNumero) { viewModel.aoDigitarNumero("3") }
            BotaoCalculadora("+", corBotaoOperacao) { viewModel.aoEscolherOperacao("+") }
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Linha 5: 0, Ponto, Apagar, Igual
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            BotaoCalculadora("0", corBotaoNumero) { viewModel.aoDigitarNumero("0") }
            BotaoCalculadora(".", corBotaoNumero) { viewModel.aoDigitarNumero(".") }
            BotaoCalculadora("⌫", corBotaoNumero) { viewModel.apagarUltimo() }
            BotaoCalculadora("=", corBotaoOperacao) { viewModel.calcularResultado() }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // --- 6. ANÚNCIO NO RODAPÉ ---
        AnuncioBanner()
    }
}