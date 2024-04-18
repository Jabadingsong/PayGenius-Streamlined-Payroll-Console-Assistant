import java.util.List;
import java.util.Scanner;

public class PayGeniusMain 
{

    static Scanner input = new Scanner(System.in);
    static String employeeDataFile = "payslip.csv";

    public static void main(String[] args) 
    {
        List<EmployeeGetandSet> employees = FileOperations.readEmployeeData(employeeDataFile); // Load employee data from CSV

        // Main menu
        while (true) {
            System.out.println("-------------------------"); // Separator
            System.out.println("1. Add Employee"); // Add employee
            System.out.println("2. Generate Payslip"); // Generate payslip
            System.out.println("3. View All Employees"); // View all employees
            System.out.println("4. Delete Employee"); // Delete employee
            System.out.println("5. Edit Employee"); // Edit employee
            System.out.println("6. Exit"); // Exit
            System.out.print("Choose an option: "); // Prompt user for input

            int option = input.nextInt();
            System.out.println("-------------------------");
            input.nextLine(); // Consume newline left-over

            switch (option) 
            {
                case 1: addEmployee(employees); break; // Add employee
                case 2: generatePayslip(employees); break; // Generate payslip
                case 3: FileOperations.viewAllEmployees(employees); break; // View all employees
                case 4: deleteEmployee(employees); break; // Delete employee
                case 5: editEmployee(employees); break; // Edit employee
                case 6:
                    FileOperations.saveEmployeeData(employees, employeeDataFile);
                    System.exit(0);
                    break; // Exit
                default: System.out.println("Invalid option!"); break; // Invalid option
            }
        }
    }
    
    private static void editEmployee(List<EmployeeGetandSet> employees) 
    {
        if (employees.isEmpty()) 
        {
            System.out.println("No employees to edit.");
            return;
        }

        System.out.println("Select an employee to edit:");

        // Display employees with numbers
        for (int i = 0; i < employees.size(); i++) 
        {
            System.out.println((i + 1) + ". " + employees.get(i).getName());
        }

        System.out.print("Enter employee number to edit: ");
        int employeeNumber = input.nextInt(); // Read user input with error checking
        input.nextLine(); // Consume newline left-over

        if (employeeNumber < 1 || employeeNumber > employees.size()) 
        {
            System.out.println("Invalid employee number.");
            return;
        }

        EmployeeGetandSet emp = employees.get(employeeNumber - 1);

        // Display employee's current details
        System.out.println("Current details for " + emp.getName() + ":"); 
        System.out.println("1. Name: " + emp.getName()); 
        System.out.println("2. Base Hourly Rate: " + emp.getBaseHourlyRate());
        System.out.println("3. Total Hours Worked: " + emp.getHoursWorked());

        System.out.print("Select the detail to edit (1-3): ");
        int detailOption = input.nextInt(); // Read user input with error checking
        input.nextLine(); // Consume newline left-over

        switch (detailOption) 
        {
            case 1:
                System.out.print("Enter new name: ");
                String newName = input.nextLine();
                emp.setName(newName);
                break;
            case 2:
                System.out.print("Enter new base hourly rate: ");
                double newBaseHourlyRate = input.nextDouble(); // Read user input with error checking
                emp.setBaseHourlyRate(newBaseHourlyRate);
                break;
            case 3:
                System.out.print("Enter new total hours worked: ");
                double newTotalHoursWorked = input.nextDouble(); // Read user input with error checking
                emp.setHoursWorked(newTotalHoursWorked);
                break;
            default:
                System.out.println("Invalid option! Please enter a number between 1 and 3.");
                return;
        }

        System.out.println("Employee details updated successfully.");
    }

    private static void deleteEmployee(List<EmployeeGetandSet> employees) 
    {
        if (employees.isEmpty()) 
        {
            System.out.println("No employees to delete.");
            return;
        }

        System.out.println("Select an employee to delete:");

        // Display employees with numbers
        for (int i = 0; i < employees.size(); i++) 
        {
            System.out.println((i + 1) + ". " + employees.get(i).getName());
        }

        System.out.print("Enter employee number to delete: ");
        int employeeNumber = input.nextInt(); // Read user input with error checking
        input.nextLine(); // Consume newline left-over

        if (employeeNumber < 1 || employeeNumber > employees.size()) 
        {
            System.out.println("Invalid employee number.");
            return;
        }

        EmployeeGetandSet deletedEmployee = employees.remove(employeeNumber - 1);
        System.out.println("Employee " + deletedEmployee.getName() + " deleted successfully.");
    }

    private static void addEmployee(List<EmployeeGetandSet> employees) 
    {
        System.out.print("Enter employee name: ");
        String name = input.nextLine(); 
        System.out.print("Enter base hourly rate: ");
        double baseHourlyRate = input.nextDouble(); 
        System.out.print("Enter total hours worked (Hours Worked for the whole month): ");
        double totalHoursWorked = input.nextDouble(); 
        input.nextLine(); // Consume newline left-over

        EmployeeGetandSet emp = new EmployeeGetandSet(name, baseHourlyRate, totalHoursWorked); // Create a new employee object
        employees.add(emp);

        // Update the CSV file with the new employee data
        FileOperations.saveEmployeeData(employees, employeeDataFile); // Save the employee data to the CSV file
    }

    private static void generatePayslip(List<EmployeeGetandSet> employees) 
    {
        if (employees.isEmpty()) 
        {
            System.out.println("No employees added yet.");
            return;
        }

        System.out.println("Select an employee:");

        // Display employees with numbers
        for (int i = 0; i < employees.size(); i++) 
        {
            System.out.println((i + 1) + ". " + employees.get(i).getName());
        }

        System.out.print("Enter employee number: ");
        int employeeNumber = input.nextInt(); // Read user input with error checking
        input.nextLine(); // Consume newline left-over

        if (employeeNumber < 1 || employeeNumber > employees.size()) 
        {
            System.out.println("Invalid employee number.");
            return;
        }

        EmployeeGetandSet emp = employees.get(employeeNumber - 1);

        double totalHoursWorked = emp.getHoursWorked(); // Total hours worked in a month
        double monthlyGrossPay = Payroll.MonthlyPayCalculation(emp.getBaseHourlyRate(), totalHoursWorked); // Calculate monthly gross pay
        double monthlyDeductions = Payroll.DeductionsCalculations(monthlyGrossPay); // Calculate monthly deductions
        double monthlyNetPay = Payroll.NetPayCalculations(monthlyGrossPay, monthlyDeductions); // Calculate monthly net pay
        double overtimeEarnings = Payroll.OvertimeEarningsCalculations(emp.getBaseHourlyRate(), totalHoursWorked - Payroll.HOURS_MONTH); // Calculate overtime earnings

        System.out.println("Payslip for: " + emp.getName()); // Display payslip for selected employee
        System.out.printf("Monthly Gross Pay: %.2f\n", monthlyGrossPay); // Display monthly gross pay
        System.out.printf("Tax: %.2f\n", monthlyGrossPay * Payroll.TAX_RATE); // Display tax
        System.out.printf("SSS Contribution: %.2f\n", monthlyGrossPay * Payroll.SSS); // Display SSS contribution
        System.out.printf("PhilHealth Contribution: %.2f\n", monthlyGrossPay * Payroll.PHILHEALTH); // Display PhilHealth contribution
        System.out.printf("Overtime Earnings: %.2f\n", overtimeEarnings); // Display overtime earnings
        System.out.printf("Monthly Deductions: %.2f\n", monthlyDeductions); // Display monthly deductions
        System.out.printf("Monthly Net Pay: %.2f\n", monthlyNetPay); // Display monthly net pay
        System.out.println("-------------------------");
    }
}