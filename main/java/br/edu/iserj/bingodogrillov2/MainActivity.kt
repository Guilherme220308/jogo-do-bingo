package br.edu.iserj.bingodogrillov2

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.edu.iserj.bingodogrillov2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val jogar = binding.buttonjogar

        jogar.setOnClickListener {
            val intent = Intent(this, obingo ::class.java)
            startActivity(intent)
            finish()
        }
    }
}