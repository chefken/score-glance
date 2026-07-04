package com.example.livescorepin

data class MatchScore(
    val leftFlagRes: Int,
    val rightFlagRes: Int,
    val leftScore: Int,
    val rightScore: Int,
    val statusLine1: String,
    val statusLine2: String,
    val isMatchOver: Boolean
)