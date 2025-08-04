package org.example

class EmployeeList : ArrayList<Employee>() {

    init {
        // Predefined manager-level employees
        val predefined = listOf(
            Employee("Anita", "Sharma", Departments.HR.name, Roles.TEAM_LEAD.role, "cto"),
            Employee("Rahul", "Verma", Departments.IT.name, Roles.TEAM_LEAD.role, "cto"),
            Employee("Sanya", "Patel", Departments.MARKETING.name, Roles.TEAM_LEAD.role, "cto"),
            Employee("Deepak", "Kumar", Departments.IT.name, Roles.TEAM_LEAD.role, "cto"),
            Employee("Priya", "Nair", Departments.IT.name, Roles.TEAM_LEAD.role, "cto"),
            Employee("Karan", "Singh", Departments.IT.name, Roles.TEAM_LEAD.role, "cto"),
            Employee("Meera", "Raj", Departments.FINANCE.name, Roles.TEAM_LEAD.role, "cto"),
            Employee("Arjun", "Das", Departments.SALES.name, Roles.TEAM_LEAD.role, "cto"),
            Employee("Riya", "Menon", Departments.HR.name, Roles.TEAM_LEAD.role, "cto"),
            Employee("Vikram", "Iyer", Departments.IT.name, Roles.TEAM_LEAD.role, "cto")
        )


        predefined.forEach {
            super.add(it)
            println("Predefined employee added: ${it.id} - ${it.firstName} ${it.lastName}")
        }
    }

    override fun add(employee: Employee): Boolean {
        println("Employee ${employee.firstName} ${employee.lastName} added successfully with ID: ${employee.id}")
        return super.add(employee)
    }

    override fun toString(): String {
        return if (this.isEmpty()) {
            "No employees found."
        } else {
            this.joinToString("\n") { it.toString() }
        }
    }
}
