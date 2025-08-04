package org.example

import java.time.LocalDateTime
import java.time.Duration

class Attendance(
    val empId: String,
    val checkInDateTime: LocalDateTime
) {
    var checkOutDateTime: LocalDateTime? = null
    var workingHours: Double? = null

    fun calculateWorkingHours() {
        if (checkOutDateTime != null) {
            val duration = Duration.between(checkInDateTime, checkOutDateTime)
            val hours = duration.toMinutes().toDouble() / 60
            workingHours = String.format("%.2f", hours).toDouble()
        }
    }

    override fun toString(): String {
        val checkInStr = checkInDateTime.toString()
        val checkOutStr = checkOutDateTime?.toString() ?: "Not Checked Out"
        val hoursStr = workingHours?.let { "%.2f".format(it) } ?: "N/A"

        return "Employee ID: $empId | Check-In: $checkInStr | Check-Out: $checkOutStr | Hours Worked: $hoursStr"
    }
}
