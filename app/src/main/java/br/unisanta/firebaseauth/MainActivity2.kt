package br.unisanta.firebaseauth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.unisanta.firebaseauth.databinding.ActivityMain2Binding
import br.unisanta.firebaseauth.databinding.ActivityMainBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class MainActivity2 : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityMain2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth
        binding.fbBack.setOnClickListener {
            finish()
        }
        binding.btnLogin.setOnClickListener {
            if(binding.edtEmail.text.length == 0 || binding.edtSenha.text.length == 0){
                Toast.makeText(this, "Preencha os campos obrigatóriamente!", Toast.LENGTH_SHORT).show()
            }
            else{
                Login(binding.edtEmail.text.toString(), binding.edtSenha.text.toString())
            }
        }
        binding.btnCadastro.setOnClickListener {
            if(binding.edtEmail.text.length == 0 || binding.edtSenha.text.length == 0){
                Toast.makeText(this, "Preencha os campos obrigatóriamente!", Toast.LENGTH_SHORT).show()
            }
            else{
                auth.createUserWithEmailAndPassword(binding.edtEmail.text.toString(),binding.edtSenha.text.toString())
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "Cadastrado com sucesso!", Toast.LENGTH_SHORT).show()
                            Login(binding.edtEmail.text.toString(), binding.edtSenha.text.toString())
                        } else {
                            Toast.makeText(this, "Não Criou", Toast.LENGTH_SHORT).show()
                        }
                    }
                binding.edtEmail.text.clear()
                binding.edtSenha.text.clear()
            }
        }

        binding.btnReset.setOnClickListener {
            if(binding.edtEmail.text.length == 0){
                Toast.makeText(this, "Preencha o campo email obrigatóriamente!", Toast.LENGTH_SHORT).show()
            }
            else{
                Firebase.auth.sendPasswordResetEmail(binding.edtEmail.text.toString())
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "Enviado", Toast.LENGTH_SHORT).show()
                        }
                    }
                binding.edtEmail.text.clear()
            }
        }
    }

    fun Login(email :String, senha :String){
        auth.signInWithEmailAndPassword(email,senha)
            .addOnCompleteListener(this){task ->
                if(task.isSuccessful){
                    val intent = Intent(this, MainActivity3::class.java)
                    startActivity(intent)
                }else{
                    Toast.makeText(this, "Não Logou",Toast.LENGTH_SHORT).show()
                }
            }
        binding.edtEmail.text.clear()
        binding.edtSenha.text.clear()
    }
}