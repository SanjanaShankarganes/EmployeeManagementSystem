package project;
import java.io.File;

import java.io.FileWriter;

import java.io.FileNotFoundException;

import java.io.IOException;

import java.util.InputMismatchException;

import java.util.Scanner;

import java.util.ArrayList;

import java.util.List;

class Employee1 {

private String firstName, lastName, post, department, uniqueID, email;

private int joiningYear;

private long mobile;

private static int idCounter = 0;

private static List<Employee1> employeeList = new ArrayList<>();

private static void loadEmployeeData() {

File file = new File("emp.txt");

if (!file.exists()) {

System.out.println("No existing employee data found.");

return;

}

try (Scanner fileScanner = new Scanner(file)) {

while (fileScanner.hasNextLine()) {

String line = fileScanner.nextLine();

String[] details = line.split(",");


if (details.length == 7) {

Employee1 emp = new Employee1();

emp.firstName = details[0].split(" ")[0];

emp.lastName = details[0].split(" ")[1];

emp.post = details[1];

emp.joiningYear = Integer.parseInt(details[2]);

emp.department = details[3];

emp.uniqueID = details[4];

emp.mobile = Long.parseLong(details[5]);

emp.email = details[6];

employeeList.add(emp);

}

}

System.out.println("Employee data loaded from file.");

} catch (FileNotFoundException e) {

System.out.println("An error occurred while loading the employee data.");

e.printStackTrace();

}

}


public void addEmployeeDetails() {

Scanner sc = new Scanner(System.in);

System.out.print("Enter employee's first name: ");

this.firstName = sc.nextLine();

System.out.print("Enter employee's last name: ");

this.lastName = sc.nextLine();

this.joiningYear = inputInteger("Enter employee's year of joining: ", sc);

sc.nextLine(); // Clear the buffer

System.out.print("Enter employee's post: ");

this.post = sc.nextLine();

System.out.print("Enter employee's department: ");

this.department = sc.nextLine();

this.mobile = inputLong("Enter employee's mobile number: ", sc);

sc.nextLine(); // Clear the buffer

this.email = inputEmail("Enter employee's email ID: ", sc);

setUniqueEmployeeID();

saveEmployeeToFile();

employeeList.add(this);

}


private void setUniqueEmployeeID() {

idCounter++;

this.uniqueID = joiningYear + "-" + String.format("%03d", idCounter) + "-" + firstName.charAt(0) + lastName.charAt(0);

}


private void saveEmployeeToFile() {

try (FileWriter writer = new FileWriter("emp.txt", true)) {

writer.write(firstName + " " + lastName + "," + post + "," + joiningYear + "," + department + "," +

uniqueID + "," + mobile + "," + email + "\n");

System.out.println("Employee data saved successfully.");

} catch (IOException e) {

System.out.println("Error while saving employee data.");

}

}

private void editEmployeeDetails(Scanner sc) {

System.out.print("Enter unique ID of the employee to edit: ");

String uniqueID = sc.nextLine();

boolean found = false;

System.out.println(employeeList);

for (Employee1 emp : employeeList) {

if (emp.uniqueID.equals(uniqueID)) {

found = true;

System.out.println("Editing details for: " + emp.firstName + " " + emp.lastName);

// Edit details

System.out.print("Enter new first name (leave blank to keep unchanged): ");

String newFirstName = sc.nextLine();

if (!newFirstName.isEmpty()) emp.firstName = newFirstName;

System.out.print("Enter new last name (leave blank to keep unchanged): ");

String newLastName = sc.nextLine();

if (!newLastName.isEmpty()) emp.lastName = newLastName;

int newJoiningYear = inputInteger("Enter new year of joining (or -1 to keep unchanged): ", sc);

if (newJoiningYear != -1) emp.joiningYear = newJoiningYear;

sc.nextLine();

System.out.print("Enter new post (leave blank to keep unchanged): ");

String newPost = sc.nextLine();

if (!newPost.isEmpty()) emp.post = newPost;

System.out.print("Enter new department (leave blank to keep unchanged): ");

String newDepartment = sc.nextLine();

if (!newDepartment.isEmpty()) emp.department = newDepartment;

long newMobile = inputLong("Enter new mobile number (or -1 to keep unchanged): ", sc);

sc.nextLine();

if (newMobile != -1) emp.mobile = newMobile;

System.out.print("Enter new email ID (leave blank to keep unchanged): ");

String newEmail = sc.nextLine();

if (!newEmail.isEmpty()) emp.email = newEmail;

// Update the file after editing

updateEmployeeFile();

System.out.println("Employee details updated.");

break;

}

}

if (!found) {

System.out.println("Employee with UID " + uniqueID + " not found.");

}

}

private static void updateEmployeeFile() {

try (FileWriter writer = new FileWriter("emp.txt")) {

for (Employee1 emp : employeeList) {

writer.write(emp.firstName + " " + emp.lastName + "," + emp.post + "," +

emp.joiningYear + "," + emp.department + "," + emp.uniqueID + "," +

emp.mobile + "," + emp.email + "\n");

}

System.out.println("Employee file updated successfully.");

} catch (IOException e) {

System.out.println("Error while updating employee file.");

}

}


public static void main(String[] args) {

loadEmployeeData(); 


Scanner sc = new Scanner(System.in);

while (true) {

System.out.println("\n******* Employee Management System *******");

System.out.println("1. Add Employee\n2. Show Employees\n3. Edit Employee\n4. Export to CSV\n5. Delete Employee\n6. Exit");

System.out.print("Choose an option: ");

int choice = inputInteger("", sc);

sc.nextLine();

switch (choice) {

case 1:

Employee1 emp = new Employee1();

emp.addEmployeeDetails();

break;

case 2:

displayAllEmployees();

break;

case 3:

new Employee1().editEmployeeDetails(sc);

break;


case 4:

exportToCSV();

break;


case 5:

deleteEmployeeFile();

break;

case 6:

System.out.println("Exiting.");

sc.close();

System.exit(0);

break;

default:

System.out.println("Invalid choice.");

}

}

}

private static void displayAllEmployees() {

File file = new File("emp.txt");

if (!file.exists()) {

System.out.println("No employee data found. Please add employees first.");

return;

}

try (Scanner fileScanner = new Scanner(file)) {

System.out.println("\nEmployee Records:\n-----------------");

int count = 0;

while (fileScanner.hasNextLine()) {

System.out.println(fileScanner.nextLine());

count++;

}

System.out.println("Total Employees: " + count);

} catch (FileNotFoundException e) {

System.out.println("An error occurred while reading the file.");

e.printStackTrace();

}

}

private static void exportToCSV() {

File file = new File("employees.csv");

try (FileWriter writer = new FileWriter(file)) {

writer.write("First Name,Last Name,Post,Joining Year,Department,Unique ID,Mobile,Email\n");

for (Employee1 emp : employeeList) {

writer.write(emp.firstName + "," + emp.lastName + "," + emp.post + "," + emp.joiningYear + ","

+ emp.department + "," + emp.uniqueID + "," + emp.mobile + "," + emp.email + "\n");

}

System.out.println("Employee data exported to employees.csv successfully.");

} catch (IOException e) {

System.out.println("An error occurred while exporting employee data.");

e.printStackTrace();

}

}


private static int inputInteger(String message, Scanner sc) {

while (true) {

try {

System.out.print(message);

return sc.nextInt();

} catch (InputMismatchException e) {

System.out.println("Invalid input. Please enter a valid integer.");

sc.nextLine(); 

}

}

}


private static long inputLong(String message, Scanner sc) {

while (true) {

try {

System.out.print(message);

return sc.nextLong();

} catch (InputMismatchException e) {

System.out.println("Invalid input. Please enter a valid mobile number.");

sc.nextLine(); 

}

}

}

private static void deleteEmployeeFile() {

File file = new File("emp.txt");

if (file.delete()) {

employeeList.clear(); 

System.out.println("All employee data deleted successfully.");

} else {

System.out.println("No employee data to delete or deletion failed.");

}

}

private String inputEmail(String message, Scanner sc) {

String email;

while (true) {

System.out.print(message);

email = sc.nextLine();

if (email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")) {

return email;

} else {

System.out.println("Invalid email format. Please enter a valid email.");

}

}

}

}

