package com.example.nglauber.archcompsample

import android.arch.lifecycle.ViewModelProviders
import org.junit.Assert.*
import org.junit.Test

import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class ExampleUnitTest {

    private val vm: ScoreViewModel by lazy { getViewModel() }

    @Test
    fun testChangeScore() {
        vm.reset()
        assertEquals(0, vm.score.teamA)
        assertEquals(0, vm.score.teamB)
        vm.updateTeamA()
        assertEquals(1, vm.score.teamA)
        vm.updateTeamB()
        assertEquals(1, vm.score.teamB)
    }

    @Test
    fun testReset() {
        vm.reset()
        assertEquals(0, vm.score.teamA)
        assertEquals(0, vm.score.teamB)
    }

    private fun getViewModel(): ScoreViewModel {
        val activity = Robolectric.setupActivity(MainActivity::class.java)
        return ViewModelProviders.of(activity).get(ScoreViewModel::class.java)
    }
}
