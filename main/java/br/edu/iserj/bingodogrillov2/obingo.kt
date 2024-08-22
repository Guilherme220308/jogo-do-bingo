package br.edu.iserj.bingodogrillov2

import android.content.Intent
import android.graphics.Color
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import br.edu.iserj.bingodogrillov2.databinding.ActivityObingoBinding

class obingo : AppCompatActivity() {
    private lateinit var binding: ActivityObingoBinding
    private var placar = 0
    private var numeroatual = -1
    private val handler = Handler(Looper.getMainLooper())
    private val botoes = mutableListOf<Button>()
    private var jogoativo = true
    private val marcado = MutableList(25) { false } // Novo indicador de botões acertados
    private lateinit var musicafundo: MediaPlayer
    override fun onCreate(savedInstanceState: Bundle?) {

        musicafundo = MediaPlayer.create(this, R.raw.musicafundo)
        musicafundo.isLooping = true
        musicafundo.start()


        binding = ActivityObingoBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        definirbotao()

        // Iniciar o jogo automaticamente
        comecajogo()
    }


    private fun definirbotao() {
        // Adiciona todos os botões à lista
        botoes.apply {
            add(binding.buttoncartela)
            add(binding.buttoncartela2)
            add(binding.buttoncartela3)
            add(binding.buttoncartela4)
            add(binding.buttoncartela5)
            add(binding.buttoncartela6)
            add(binding.buttoncartela7)
            add(binding.buttoncartela8)
            add(binding.buttoncartela9)
            add(binding.buttoncartela10)
            add(binding.buttoncartela11)
            add(binding.buttoncartela12)
            add(binding.buttoncartela13)
            add(binding.buttoncartela14)
            add(binding.buttoncartela15)
            add(binding.buttoncartela16)
            add(binding.buttoncartela17)
            add(binding.buttoncartela18)
            add(binding.buttoncartela19)
            add(binding.buttoncartela20)
            add(binding.buttoncartela21)
            add(binding.buttoncartela22)
            add(binding.buttoncartela23)
            add(binding.buttoncartela24)
            add(binding.buttoncartela25)
        }

        // Configurar o comportamento dos botões
        botoes.forEach { button ->
            button.setOnClickListener {
                val number = button.text.toString().toIntOrNull()
                if (number != null) {
                    onButtonClick(button, number)
                }
            }
        }
    }

    private fun comecajogo() {
        placar = 0
        updateScore()
        val numbers = generateRandomBingoNumbers()
        distributeNumbersToButtons(numbers)
        startNumberDraw()
    }

    private fun resetGame() {
        handler.removeCallbacksAndMessages(null) // Cancela todos os sorteios
        botoes.forEach { button ->
            button.setBackgroundColor(ContextCompat.getColor(this, android.R.color.darker_gray))
            button.setTextColor(Color.WHITE)
            button.text = ""
        }
        placar = 0
        updateScore()
        marcado.fill(false) // Reseta os botões marcados
    }

    private fun generateRandomBingoNumbers(): List<Int> {
        return (1..25).shuffled()
    }

    private fun distributeNumbersToButtons(numbers: List<Int>) {
        botoes.forEachIndexed { index, button ->
            button.text = numbers[index].toString()
            button.setBackgroundColor(ContextCompat.getColor(this, android.R.color.darker_gray))
            button.setTextColor(Color.WHITE)
        }
    }

    private fun startNumberDraw() {
        handler.postDelayed(object : Runnable {
            override fun run() {
                drawNumber()
                handler.postDelayed(this, 7000) // Executa novamente após 7 segundos
            }
        }, 0) // Começa imediatamente
    }

    private fun drawNumber() {
        numeroatual = (1..25).random()
        // Aqui você pode exibir o número sorteado em um TextView ou outra forma de indicação
        binding.textnumerovez.text = "Número da vez: $numeroatual"
    }

    private fun onButtonClick(button: Button, number: Int) {
        val index = botoes.indexOf(button)

        if (number == numeroatual && !marcado[index]) { // Verifica se o número é o atual e não foi marcado ainda
            // Acertou
            placar += 10
            button.setBackgroundColor(Color.GREEN)
            button.setTextColor(Color.BLACK)
            marcado[index] = true // Marca o botão como acertado
            checkForWin() // Verifica vitória após marcar o botão
        } else if (!marcado[index]) { // Apenas subtrai pontos se o botão ainda não tiver sido marcado
            // Errou
            placar -= 5
            button.setBackgroundColor(Color.RED)
            button.setTextColor(Color.WHITE)
        }

        updateScore()
    }

    private fun updateScore() {
        binding.textpontos.text = "Pontuação: $placar"
    }

    private fun checkForWin() {
        val combinacoes = listOf(
            // Horizontal
            listOf(0, 1, 2, 3, 4),
            listOf(5, 6, 7, 8, 9),
            listOf(10, 11, 12, 13, 14),
            listOf(15, 16, 17, 18, 19),
            listOf(20, 21, 22, 23, 24),
            // Vertical
            listOf(0, 5, 10, 15, 20),
            listOf(1, 6, 11, 16, 21),
            listOf(2, 7, 12, 17, 22),
            listOf(3, 8, 13, 18, 23),
            listOf(4, 9, 14, 19, 24),
            // Diagonal
            listOf(0, 6, 12, 18, 24),
            listOf(4, 8, 12, 16, 20)
        )

        for (combination in combinacoes) {
            if (combination.all { index ->
                    marcado[index] // Verifica se todos os índices da combinação estão marcados
                }) {
                jogoativo = false
                handler.removeCallbacksAndMessages(null) // Parar sorteios
                Toast.makeText(this, "Você ganhou!", Toast.LENGTH_LONG).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
                break
            }
        }
    }
}
