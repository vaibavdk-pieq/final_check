import org.example.EmployeeManager

fun addEmployee(employeeManager: EmployeeManager) {
    println("Enter First Name:")
    val firstName = readln().trim()
    println("Enter Last Name:")
    val lastName = readln().trim()

    println("Enter Department:\n 1.HR \n 2.IT \n 3.Sales \n 4.Marketing \n 5.Finance")
    val departmentNumber: Int? = readln().trim().toIntOrNull()
    if (departmentNumber == null || departmentNumber !in 1..5) {
        println("Invalid Department selection. Please enter a number from 1 to 5.")
        return
    }
    val department = when (departmentNumber) {
        1 -> Departments.HR
        2 -> Departments.IT
        3 -> Departments.SALES
        4 -> Departments.MARKETING
        5 -> Departments.FINANCE
        else -> return
    }

    println("Enter Role:\n1. Intern \n2. Frontend Developer \n3. Backend Developer \n4. Database Engineer \n5. Team Lead")
    val roleNumber: Int? = readln().trim().toIntOrNull()
    if (roleNumber == null || roleNumber !in 1..5) {
        println("Invalid Role selection. Please enter a number from 1 to 5.")
        return
    }
    val role = when (roleNumber) {
        1 -> Roles.INTERN
        2 -> Roles.FRONTEND_DEVELOPER
        3 -> Roles.BACKEND_DEVELOPER
        4 -> Roles.DATABASE_ENGINEER
        5 -> Roles.TEAM_LEAD
        else -> return
    }

    val validManagers = employeeManager.getReportingManagerIds()
    if (validManagers.isEmpty()) {
        println("No predefined managers available to assign as reporting manager.")
        return
    }

    println("Valid Reporting Manager IDs: ${validManagers.joinToString(", ")}")
    println("Enter Reporting Manager ID:")
    val reportingTo = readln().trim().lowercase()

    if (reportingTo !in validManagers) {
        println("Invalid Reporting Manager ID!")
    } else {
        employeeManager.addEmployee(firstName, lastName, department.toString(), role.toString(), reportingTo)
    }
}

fun updateEmployee(employeeManager: EmployeeManager) {
    print("Enter Employee ID to update: ")
    val empId = readln().trim().lowercase()
    if (!employeeManager.isEmployeeExist(empId)) {
        println("Invalid Employee ID.")
        return
    }

    println("Enter Updated First Name:")
    val newFirstName = readln().trim().lowercase()
    println("Enter Updated Last Name:")
    val newLastName = readln().trim().lowercase()

    println("Enter Department:\n 1.HR \n 2.IT \n 3.Sales \n 4.Marketing \n 5.Finance")
    val departmentNumber: Int? = readln().trim().toIntOrNull()
    if (departmentNumber == null || departmentNumber !in 1..5) {
        println("Invalid Department selection.")
        return
    }
    val department = when (departmentNumber) {
        1 -> Departments.HR
        2 -> Departments.IT
        3 -> Departments.SALES
        4 -> Departments.MARKETING
        5 -> Departments.FINANCE
        else -> return
    }

    println("Enter Role:\n1. Intern \n2. Frontend Developer \n3. Backend Developer \n4. Database Engineer \n5. Team Lead")
    val roleNumber: Int? = readln().trim().toIntOrNull()
    if (roleNumber == null || roleNumber !in 1..5) {
        println("Invalid Role selection.")
        return
    }
    val role = when (roleNumber) {
        1 -> Roles.INTERN
        2 -> Roles.FRONTEND_DEVELOPER
        3 -> Roles.BACKEND_DEVELOPER
        4 -> Roles.DATABASE_ENGINEER
        5 -> Roles.TEAM_LEAD
        else -> return
    }

    val validManagers = employeeManager.getReportingManagerIds()
    println("Valid Reporting Manager IDs: ${validManagers.joinToString(", ")}")
    println("Enter Updated Reporting Manager ID:")
    val newReportingTo = readln().trim().lowercase()

    if (newReportingTo !in validManagers) {
        println("Invalid Reporting Manager ID!")
    } else {
        employeeManager.updateEmployeeList(empId, newFirstName, newLastName, department.toString(), role.toString(), newReportingTo)
    }
}

fun deleteEmployee(employeeManager: EmployeeManager) {
    print("Enter Employee ID to delete: ")
    val empId = readln().trim().lowercase()
    if (!employeeManager.isEmployeeExist(empId)) {
        println("Invalid Employee ID.")
        return
    }
    employeeManager.deleteEmployee(empId)
}

fun manualCheckIn(employeeManager: EmployeeManager) {
    print("Enter Employee ID: ")
    val inputId = readln().trim()
    if (!employeeManager.isEmployeeExist(inputId)) {
        println("Invalid Employee ID.")
        return
    }

    print("Enter date and time (YYYY-MM-DD HH:MM) or press Enter for current time: ")
    val dateInputRaw = readln().trim()
    val dateInput = if (dateInputRaw.isEmpty()) null else dateInputRaw

    employeeManager.checkIn(inputId, dateInput)
}

fun manualCheckOut(employeeManager: EmployeeManager) {
    print("Enter Employee ID for manual check-out: ")
    val empId = readln().trim().lowercase()
    if (!employeeManager.isEmployeeExist(empId)) {
        println("Invalid Employee ID.")
        return
    }
    print("Enter Date and Time (yyyy-MM-dd HH:mm): ")
    val dateTime = readln().trim()
    employeeManager.checkOut(empId, dateTime)
}

fun viewAttendance(employeeManager: EmployeeManager) {
    employeeManager.showAllAttendance()
}

fun viewStillWorking(employeeManager: EmployeeManager) {
    employeeManager.workingTill()
}

fun calculateWorkingHours(employeeManager: EmployeeManager) {
    print("Enter From Date (yyyy-MM-dd): ")
    val fromDate = readln().trim()
    print("Enter To Date (yyyy-MM-dd): ")
    val toDate = readln().trim()
    employeeManager.calculateTotalWorkingHour(fromDate, toDate)
}

fun start(employeeManager: EmployeeManager) {
    while (true) {
        println(
            """
            ========== EMPLOYEE ATTENDANCE SYSTEM ==========
            1. Add New Employee
            2. Update Employee
            3. Delete Employee
            4. Show All Employees
            5. Manual Check-In
            6. Manual Check-Out
            7. View Attendance
            8. View Still Working Employees
            9. Calculate Total Working Hours
            0. Exit
            ===============================================
            Enter your choice: 
        """.trimIndent()
        )

        when (readln().trim()) {
            "1" -> addEmployee(employeeManager)
            "2" -> updateEmployee(employeeManager)
            "3" -> deleteEmployee(employeeManager)
            "4" -> employeeManager.showAllEmployees()
            "5" -> manualCheckIn(employeeManager)
            "6" -> manualCheckOut(employeeManager)
            "7" -> viewAttendance(employeeManager)
            "8" -> viewStillWorking(employeeManager)
            "9" -> calculateWorkingHours(employeeManager)
            "0" -> {
                println("Exiting... Bye!")
                return
            }
            else -> println("Invalid option! Please try again.")
        }

        println() // spacing
    }
}

fun main() {
    val employeeManager = EmployeeManager()
    start(employeeManager)
}
