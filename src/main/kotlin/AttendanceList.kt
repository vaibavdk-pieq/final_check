package org.example

class AttendanceList : ArrayList<Attendance>() {

    override fun add(attendance: Attendance): Boolean {
        // Prevent duplicate check-in on the same date
        val sameDayCheckIn = this.any {
            it.empId == attendance.empId &&
                    it.checkInDateTime.toLocalDate() == attendance.checkInDateTime.toLocalDate()
        }

        if (sameDayCheckIn) {
            println("Attendance for employee ${attendance.empId} already exists for this date.")
            return false
        }

        println("Attendance marked for employee ${attendance.empId} at ${attendance.checkInDateTime}.")
        return super.add(attendance)
    }

    override fun toString(): String {
        return if (this.isEmpty()) {
            "No attendance records found."
        } else {
            this.joinToString("\n") { it.toString() }
        }
    }
}
