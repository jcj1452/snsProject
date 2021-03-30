package com.example.sns_project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import com.example.sns_project.databinding.ActivitySignUpBinding
import com.example.sns_project.databinding.ActivityStartUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        //자동으로 완성된 액티비티 스타트업 바인딩 클래스 인스턴스를 가져옴.
        val bind1 = ActivityStartUpBinding.inflate(layoutInflater)

        //뷰 바인딩과 연결
        setContentView(bind1.root)


        //회원가입 페이지 이동
        bind1.button2.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        //로그인 버튼
        bind1.button4.setOnClickListener{
            Login(bind1.editTextTextEmailAddress2.text.toString(), bind1.editTextTextPassword3.text.toString())

        }


    }
   public override fun onBackPressed() {
        super.onBackPressed()
        moveTaskToBack(true)
        android.os.Process.killProcess(android.os.Process.myPid())
        exitProcess(1)
    }
    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
    }

    private fun Login(email : String, password : String) {

        if (email.isNotEmpty() && password.isNotEmpty()) {


                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(this, "로그인 완료", Toast.LENGTH_SHORT).show()
                                val user = auth.currentUser
                                MoveSnsActivity()

                            } else {
                                Toast.makeText(this, task.exception.toString(), Toast.LENGTH_SHORT).show()
                            }
                        }
        }
        else{
            Toast.makeText(this, "이메일 및 패스워드를 입력해주세요.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun MoveSnsActivity(){
        val intent = Intent(this, SnsActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

}