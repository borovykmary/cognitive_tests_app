package com.example.cognittiveassesmenttests.dataClasses

/**
 * A data class that represents a record of a MiniAce test.
 * It holds the test's properties, such as date, time, and various scores.
 */
data class TestRecordMA(
    val testDate: String,
    val testTime: String,
    val attentionScore: String,
    val clockDrawingScore: String,
    val memoryScore: String,
    val fluencyScore: String,
    val memoryRecallScore: String,
)
