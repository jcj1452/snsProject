package com.example.sns_project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import com.example.sns_project.databinding.ActivitySignUpBinding
import com.example.sns_project.databinding.ActivityStartUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignUpActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bind2 = ActivitySignUpBinding.inflate(layoutInflater)

        auth = Firebase.auth

        setContentView(bind2.root)
        //버튼 누를시 회원가입
        bind2.button.setOnClickListener{

            createAccount(bind2.editTextTextEmailAddress.text.toString(),
            bind2.editTextTextPassword.text.toString(),
            bind2.editTextTextPassword2.text.toString())

        }

    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
    }

//회원가입 함수
    private fun createAccount(email : String, password : String, psc : String) {

    if (email.isNotEmpty() && password.isNotEmpty() && psc.isNotEmpty()) {

        if (password == psc) {

            auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "회원가입 완료", Toast.LENGTH_SHORT).show()
                            val user = auth.currentUser
                            MoveStartActivity()
                        } else {
                            Toast.makeText(this, task.exception.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }
        } else {
            Toast.makeText(this, "비밀번호를 확인해주세요!", Toast.LENGTH_SHORT).show()
        }
    }
    else{
        Toast.makeText(this, "이메일 및 패스워드를 입력해주세요.", Toast.LENGTH_SHORT).show()
    }
}

    private fun MoveStartActivity(){
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

}
