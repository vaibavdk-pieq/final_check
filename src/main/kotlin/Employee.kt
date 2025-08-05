package org.example

class Employee(
    firstName: String,
    lastName: String,
    var department: String,
    var role: String,
    var reportingTo: String
) {
    val id: String
    var firstName: String
    var lastName: String

    companion object {
        private var idCounter = 0
    }

    init {
        // Basic validation
        require(firstName.isNotBlank()) { "First name cannot be blank" }
        require(lastName.isNotBlank()) { "Last name cannot be blank" }


        this.firstName = firstName.trim().lowercase()
        this.lastName = lastName.trim().lowercase()
        this.role=role.lowercase()
        this.department=department.lowercase()
        this.reportingTo=reportingTo.lowercase()

        // Auto-generate ID: e.g., "VK0"
        id = "${this.firstName.first()}${this.lastName.first()}${idCounter++}".lowercase()
    }


    override fun toString(): String {
        return "|ID: $id | First Name: $firstName | Last Name: $lastName | Department: $department | Role: $role | Reporting To: $reportingTo|".lowercase()
    }
}
