package com.example.livescorepin

import android.os.Handler
import android.os.Looper

class ScoreSimulator(private val onUpdate: (MatchScore) -> Unit) {

    private val handler = Handler(Looper.getMainLooper())
    private var tickCount = 0

    private val timeSteps = listOf("12'", "38'", "45+2'", "67'", "90+3'", "Full Time")

    private var leftScore = 0
    private var rightScore = 0

    fun start() {
        runStep()
    }

    fun stop() {
        handler.removeCallbacksAndMessages(null)
    }

    private fun runStep() {
        if (tickCount >= timeSteps.size) return

        if (tickCount == 2) leftScore++
        if (tickCount == 4) rightScore++

        val time = timeSteps[tickCount]
        val isOver = tickCount == timeSteps.lastIndex

        onUpdate(
            MatchScore(
                leftFlagRes = R.mipmap.ic_launcher,
                rightFlagRes = R.mipmap.ic_launcher,
                leftScore = leftScore,
                rightScore = rightScore,
                statusLine1 = time,
                statusLine2 = "",
                isMatchOver = isOver
            )
        )

        tickCount++

        if (tickCount < timeSteps.size) {
            handler.postDelayed({ runStep() }, 3000)
        }
    }
}