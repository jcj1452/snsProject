package com.example.sns_project

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.sns_project.databinding.ActivitySnsBinding
import com.example.sns_project.databinding.MainDrawerBinding
import com.example.sns_project.databinding.MainToolbarBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SnsActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private val mainFragment by lazy { MainFragment() }
    private val myFragment by lazy { MyFragment() }
    private val friendFragment by lazy { FriendFragment() }
    private val settingFragment by lazy { SettingFragment() }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val snsmain_b = ActivitySnsBinding.inflate(layoutInflater)
        val tool = MainToolbarBinding.inflate(layoutInflater)

       setContentView(snsmain_b.root)
        snsmain_b.bottomNavi.setOnNavigationItemSelectedListener(this)

        supportFragmentManager.beginTransaction().add(R.id.frame, mainFragment).commit()



        setSupportActionBar(tool.toolbar) // 툴바를 액티비티의 앱바로 지정
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // 드로어를 꺼낼 홈 버튼 활성화
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24) // 홈버튼 이미지 변경
        supportActionBar?.setDisplayShowTitleEnabled(false) // 툴바에 타이틀 안보이게



        //로그인 안되있으면 시작 화면으로 이동
        if (FirebaseAuth.getInstance().currentUser == null){
            MoveStartActivity()
        }

        //로그아웃 버튼
        snsmain_b.button5.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            MoveStartActivity()
        }

    }
    //서랍 열기
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when(item!!.itemId){
            android.R.id.home -> {
                val drawer_b = MainDrawerBinding.inflate(layoutInflater)
                setContentView(drawer_b.root)
                drawer_b.drawerLayout.openDrawer(GravityCompat.START)
                Toast.makeText(this, "적당히해라 ㅅㅂ", Toast.LENGTH_SHORT).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }
    //뒤로가기 누를시 서랍 닫기
    override fun onBackPressed() {
        val drawer_b = MainDrawerBinding.inflate(layoutInflater)
        setContentView(drawer_b.root)
        if (drawer_b.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawer_b.drawerLayout.closeDrawers()
            Toast.makeText(this, "뒤로가기~~~~", Toast.LENGTH_SHORT).show()
        } else {
            super.onBackPressed()
        }
    }

    //네비게이션바
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.navigation_1 ->{
                MoveFragment(mainFragment)
                return true
            }
            R.id.navigation_2 ->{
                MoveFragment(friendFragment)
                return true
            }
            R.id.navigation_3 ->{
                MoveFragment(myFragment)
                return true
            }
            R.id.navigation_4 ->{
                MoveFragment(settingFragment)
                return true
            }
        }
        return false
    }

    //프레그먼트 이동함수
    private fun MoveFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().
        replace(R.id.frame, fragment).commit()

    }



    //시작화면전환 함수
    private fun MoveStartActivity(){
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

}



