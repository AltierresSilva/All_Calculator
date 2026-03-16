package com.example.allcalculator.ui.components // Verifique se o "seuprojeto" bate com o seu

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Componente padronizado para todos os botões da All Calculator.
 * * @param texto O número ou símbolo que aparecerá no botão (ex: "7", "+", "=").
 * @param corFundo A cor do botão (ex: Laranja para operações, Cinza para números).
 * @param corTexto A cor da fonte (por padrão será branca).
 * @param aoClicar A ação que será executada no motor da calculadora quando tocado.
 */
@Composable
fun BotaoCalculadora(
    texto: String,
    corFundo: Color,
    corTexto: Color = Color.White, // Cor padrão branca, mas podemos mudar se precisarmos
    aoClicar: () -> Unit
) {
    // Usamos um 'Box' em vez de um 'Button' padrão do Android.
    // O Box nos dá controle total para fazer um círculo perfeito e centralizar o texto.
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(80.dp)           // Define um tamanho fixo e quadrado para o botão
            .clip(CircleShape)     // Corta as bordas transformando o quadrado em círculo
            .background(corFundo)  // Pinta o fundo com a cor que passarmos
            .clickable { aoClicar() } // Aciona a função quando o usuário tocar
    ) {
        Text(
            text = texto,
            fontSize = 32.sp,      // Tamanho da fonte grande para fácil leitura
            fontWeight = FontWeight.Bold, // Fonte em negrito
            color = corTexto
        )
    }
}