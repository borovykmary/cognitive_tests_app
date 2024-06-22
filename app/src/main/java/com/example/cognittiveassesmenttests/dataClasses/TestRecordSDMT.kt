package com.example.cognittiveassesmenttests.dataClasses

/**
 * A data class that represents a record of an SDMT test.
 * It holds the test's properties, such as date and score.
 */
data class TestRecordSDMT(
    val testDate: String,
    val testScore: String,
    val testAll: String,
    val testCorrect: String
)
