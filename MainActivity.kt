package br.edu.up.atividade2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import br.edu.up.atividade2.ui.theme.Atividade2Theme
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Atividade2Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GameScreen()
                }
            }
        }
    }
}

@Composable
fun GameScreen() {
    var clickCount by remember { mutableStateOf(0) }
    var targetClicks by remember { mutableStateOf(Random.nextInt(1, 51)) }
    var gameFinished by remember { mutableStateOf(false) }
    var gaveUp by remember { mutableStateOf(false) }

    // Determina a imagem atual com base na contagem de cliques
    val imageResource: Painter = when {
        gaveUp -> painterResource(R.drawable.desistencia)
        clickCount >= targetClicks -> painterResource(R.drawable.conquista)
        clickCount > (targetClicks * 0.66) -> painterResource(R.drawable.afinal)
        clickCount > (targetClicks * 0.33) -> painterResource(R.drawable.mediana)
        else -> painterResource(R.drawable.inicial)
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Imagem de fundo que cobre toda a tela
        Image(
            painter = imageResource,
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center)
        )

        // Conteúdo sobreposto à imagem
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            if (gameFinished) {
                Text("Parabéns! Você completou a jornada.")
                Button(onClick = {
                    // Redefine o estado
                    clickCount = 0
                    targetClicks = Random.nextInt(1, 51)
                    gameFinished = false
                    gaveUp = false
                }) {
                    Text("Jogar Novamente")
                }
            } else if (gaveUp) {
                Text("Você desistiu da jornada.")
                Row {
                    Button(onClick = {
                        // Redefine o estado
                        clickCount = 0
                        targetClicks = Random.nextInt(1, 51)
                        gameFinished = false
                        gaveUp = false
                    }) {
                        Text("Sim")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(onClick = { /* Encerrar o aplicativo */ }) {
                        Text("Não")
                    }
                }
            } else {
                Button(
                    onClick = {
                        clickCount++
                        if (clickCount >= targetClicks) {
                            gameFinished = true
                        }
                    },
                    modifier = Modifier
                        .size(120.dp)
                        .background(
                            Color.Red,
                            shape = CircleShape
                        )
                        .align(Alignment.CenterHorizontally)

                ) {
                    Text("Clique para avançar", color = Color.White)
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { gaveUp = true },
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)

                ) {
                    Text("Desistir")
                }
            }
        }
    }
}
