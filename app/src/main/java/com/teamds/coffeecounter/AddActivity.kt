package com.teamds.coffeecounter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.room.Room
import androidx.room.RoomDatabase
import com.teamds.coffeecounter.databinding.ActivityAddBinding

class AddActivity : AppCompatActivity() {

    lateinit var binding: ActivityAddBinding
    lateinit var db : RoomDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setSupportActionBar(binding.addActionbar.root as Toolbar)


        supportActionBar?.apply {
            setDisplayShowTitleEnabled(false)         //기본 타이틀 비활성화
            setDisplayHomeAsUpEnabled(true)           //Back button 활성화
        }

        binding.apply {
            seekbarSize.setIndicatorTextFormat("\${TICK_TEXT}")
            seekbarShot.setIndicatorTextFormat("\${TICK_TEXT}")
            addActionbar.actionbarText.text = "커피 기록"
            addBtnconfirm.visibility = View.GONE
        }

        binding.addRadio1.setOnCheckedChangeListener { group, checkedId ->
            binding.addBtnconfirm.visibility = View.VISIBLE
        }

        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database-name"
        ).build()

        


    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home){
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}