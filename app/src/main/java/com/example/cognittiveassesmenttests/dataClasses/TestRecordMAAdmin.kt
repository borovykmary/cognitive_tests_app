package com.example.cognittiveassesmenttests.dataClasses

/**
 * A data class that represents a record of a MiniAce test for admin.
 * It holds the test's properties, such as id, userID, various addresses, names, date, time, and drawing URL.
 */
data class TestRecordMAAdmin(
    val id: String,
    val userID: String,
    val address: String,
    val address1: String,
    val address1Repeat: String,
    val addressRepeat: String,
    val animals: String,
    val day: String,
    val month: String,
    val name: String,
    val name1: String,
    val name1Repeat: String,
    val nameRepeat: String,
    val year: String,
    val testDate: String,
    val testTime: String,
    val drawingUrl: String
)
