package com.example.gerador_senhas

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import android.widget.Switch
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.security.SecureRandom

class MainActivity : AppCompatActivity() {

    private lateinit var switchMinusculas: Switch
    private lateinit var switchMaiusculas: Switch
    private lateinit var switchNumeros: Switch
    private lateinit var switchCaracteres: Switch
    private lateinit var gerarSenha: Button
    private lateinit var mostrarSenha: Button
    private lateinit var copiarSenha: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inicialização das views
        switchMinusculas = findViewById(R.id.switch_minusculas)
        switchMaiusculas = findViewById(R.id.switch_maiusculas)
        switchNumeros = findViewById(R.id.switch_numeros)
        switchCaracteres = findViewById(R.id.switch_caracteres)
        gerarSenha = findViewById(R.id.gerar_senha)
        mostrarSenha = findViewById(R.id.mostrar_senha)
        copiarSenha = findViewById(R.id.copiar_senha) // Certifique-se de ter esse botão no layout

        gerarSenha.setOnClickListener {
            if (!switchMinusculas.isChecked && !switchMaiusculas.isChecked &&
                !switchNumeros.isChecked && !switchCaracteres.isChecked) {
                // Exibir mensagem de erro se nenhuma opção estiver marcada
                Toast.makeText(this, "Selecione pelo menos uma opção para gerar a senha.", Toast.LENGTH_SHORT).show()
            } else {
                val senha = gerarSenhaAleatoria()
                mostrarSenha.text = senha // Atualiza o texto do botão com a senha gerada
                mostrarSenha.isEnabled = true // Ativa o botão para mostrar senha
            }
        }

        copiarSenha.setOnClickListener {
            copiarParaClipboard(mostrarSenha.text.toString())
        }
    }

    private fun gerarSenhaAleatoria(): String {
        val caracteresPermitidos = StringBuilder()
        val random = SecureRandom()

        if (switchMinusculas.isChecked) {
            caracteresPermitidos.append("abcdefghijklmnopqrstuvwxyz")
        }
        if (switchMaiusculas.isChecked) {
            caracteresPermitidos.append("ABCDEFGHIJKLMNOPQRSTUVWXYZ")
        }
        if (switchNumeros.isChecked) {
            caracteresPermitidos.append("0123456789")
        }
        if (switchCaracteres.isChecked) {
            caracteresPermitidos.append("!@#$%^&*()_-+=<>?/[]{}|")
        }

        val tamanhoSenha = 12 // Defina o tamanho desejado para a senha
        val senha = StringBuilder()

        for (i in 0 until tamanhoSenha) {
            val index = random.nextInt(caracteresPermitidos.length)
            senha.append(caracteresPermitidos[index])
        }

        return senha.toString()
    }

    private fun copiarParaClipboard(senha: String) {
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Senha Gerada", senha)
        clipboard.setPrimaryClip(clip)
        Toast.makeText(this, "Senha copiada para a área de transferência!", Toast.LENGTH_SHORT).show()
    }
}
