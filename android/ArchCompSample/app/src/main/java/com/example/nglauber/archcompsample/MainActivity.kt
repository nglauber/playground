package com.example.nglauber.archcompsample

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.nglauber.archcompsample.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        DataBindingUtil.bind<ActivityMainBinding>(viewRoot)
    }

    private val viewModel: ScoreViewModel by lazy {
        val factory = ScoreViewModel.Factory(10, 11)
        ViewModelProviders.of(this, factory).get(ScoreViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding.score = viewModel.score
        buttonTeamA.setOnClickListener { viewModel.updateTeamA() }
        buttonTeamB.setOnClickListener { viewModel.updateTeamB() }
        buttonReset.setOnClickListener { viewModel.reset() }
    }
}
