package com.example.nglauber.archcompsample

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider

class ScoreViewModel(val1: Int, val2: Int) : ViewModel() {

    var score = Score()

    init {
        score.teamA = val1
        score.teamB = val2
    }

    fun updateTeamA() {
        score.teamA++
    }

    fun updateTeamB() {
        score.teamB++
    }

    fun reset() {
        score.teamA = 0
        score.teamB = 0
    }

    class Factory(private val v1: Int,
                                private val v2: Int) : ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return modelClass.getConstructor(Int::class.java, Int::class.java).newInstance(v1, v2)
        }
    }
}