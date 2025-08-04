package org.example
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.Duration
import java.time.format.DateTimeParseException

class EmployeeManager {

    private val employeeList = EmployeeList()
    private val attendanceList = AttendanceList()

    // Add new employee
    fun addEmployee(
        firstName: String,
        lastName: String,
        department: String,
        role: String,
        reportingTo: String
    ) {
        val employee = Employee(firstName, lastName, department, role, reportingTo)
        employeeList.add(employee)
    }

    // Update employee details
    fun updateEmployeeList(
        empId: String,
        newFirstName: String,
        newLastName: String,
        newDepartment: String,
        newRole: String,
        newReportingTo: String
    ) {
        val employee = employeeList.find { it.id == empId }
        if (employee != null) {
            employee.firstName = newFirstName.lowercase()
            employee.lastName = newLastName.lowercase()
            employee.department = newDepartment.lowercase()
            employee.role = newRole.lowercase()
            employee.reportingTo = newReportingTo.lowercase()
            println("Employee $empId updated successfully.")
        } else {
            println("Employee with ID $empId not found.")
        }
    }

    // Delete employee
    fun deleteEmployee(empId: String) {
        val removed = employeeList.removeIf { it.id == empId }
        if (removed) {
            println("Employee $empId deleted.")
        } else {
            println("Employee with ID $empId not found.")
        }
    }

    // Check-in
    fun checkIn(empId: String, inputDateTime: String? = null) {
        if (employeeList.none { it.id == empId }) {
            println("Invalid employee ID.")
            return
        }

        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")

        val checkInTime = try {
            inputDateTime?.takeIf { it.isNotBlank() }?.let {
                LocalDateTime.parse(it, formatter)
            } ?: LocalDateTime.now()
        } catch (e: DateTimeParseException) {
            println("Invalid date/time format. Use yyyy-MM-dd HH:mm")
            return
        }

        val attendance = Attendance(empId, checkInTime)
        attendanceList.add(attendance)
        println("Checked in successfully at: ${checkInTime.format(formatter)}")
    }


    // Check-out
    fun checkOut(empId: String, inputDateTime: String? = null) {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")

        val checkOutTime = try {
            inputDateTime?.takeIf { it.isNotBlank() }?.let {
                LocalDateTime.parse(it, formatter)
            } ?: LocalDateTime.now()
        } catch (e: DateTimeParseException) {
            println("Invalid date/time format. Use yyyy-MM-dd HH:mm")
            return
        }

        // Use the date part of the checkOutTime instead of always using today
        val recordDate = checkOutTime.toLocalDate()

        val record = attendanceList.find {
            it.empId == empId &&
                    it.checkInDateTime.toLocalDate() == recordDate &&
                    it.checkOutDateTime == null
        }

        if (record == null) {
            println("No check-in found for employee $empId on $recordDate or already checked out.")
            return
        }

        if (checkOutTime.isBefore(record.checkInDateTime)) {
            println("Checkout time cannot be before check-in time.")
            return
        }

        val duration = Duration.between(record.checkInDateTime, checkOutTime).toMinutes()
        if (duration < 60) {
            println("Do you want to check out so early? Only $duration minutes worked. Confirm? (yes/no)")
            val confirmation = readln().trim().lowercase()
            if (confirmation != "yes") {
                println("Checkout cancelled.")
                return
            }
        }

        record.checkOutDateTime = checkOutTime
        record.calculateWorkingHours()
        println("Checked out successfully. Total hours worked: ${record.workingHours}")
    }



    // Show employees still working (not checked out)
    fun workingTill() {
        val stillWorking = attendanceList.filter { it.checkOutDateTime == null }
        if (stillWorking.isEmpty()) {
            println("All employees have checked out.")
        } else {
            println("Employees still working:")
            stillWorking.forEach { println(it.empId) }
        }
    }

    // Calculate total working hours for all employees within a date range
    fun calculateTotalWorkingHour(fromDate: String, toDate: String) {
        val from = LocalDateTime.parse("$fromDate 00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
        val to = LocalDateTime.parse("$toDate 23:59", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))

        val filtered = attendanceList.filter {
            it.checkInDateTime >= from && it.checkInDateTime <= to
        }

        if (filtered.isEmpty()) {
            println("No attendance records found in the given date range.")
            return
        }

        val grouped = filtered.groupBy { it.empId }
        for ((empId, records) in grouped) {
            val total = records.sumOf { it.workingHours ?: 0.0 }
            println("Employee ID: $empId | Total Hours Worked: %.2f".format(total))
        }
    }

    // Show all employees
    fun showAllEmployees() {
        println(employeeList.toString())
    }

    // Show all attendance records
    fun showAllAttendance() {
        println(attendanceList.toString())
    }

    // Get valid reporting manager IDs (those with reportingTo == "None")
    fun getReportingManagerIds(): List<String> {
        return employeeList.filter { it.reportingTo == "cto" }.map { it.id }
    }

    //Check whether the employee exist or not
    fun isEmployeeExist(id :String) : Boolean{
        return employeeList.any{it.id==id}
    }
}
