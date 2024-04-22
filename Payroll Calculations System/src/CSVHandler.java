import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CSVHandler 
{
    public static void writeEmployeeData(List<EmployeeGetandSet> employees, String filePath) 
    {
        try (PrintWriter writer = new PrintWriter(new File(filePath)))  
        {
            for (EmployeeGetandSet emp : employees)  
            {
                writer.println(emp.getName() + "," + emp.getBaseHourlyRate() + "," + emp.getHoursWorked()); 
            }
        } catch (FileNotFoundException e) 
        {
            System.out.println("Error writing to file: " + e.getMessage()); 
        }
    }

    public static List<EmployeeGetandSet> readEmployeeData(String filePath)  
    {
        List<EmployeeGetandSet> employees = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) 
        {
            String line;
            while ((line = br.readLine()) != null) 
            {
                String[] data = line.split(",");
                EmployeeGetandSet emp = new EmployeeGetandSet(data[0], Double.parseDouble(data[1]), Double.parseDouble(data[2]));
                employees.add(emp);
            }
        } 
        catch (IOException e)  
        {
            System.out.println("Error reading from file: " + e.getMessage());
        }
        return employees;
    }

    public static void saveEmployeeData(List<EmployeeGetandSet> employees, String filePath) 
    {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) 
        {
            for (EmployeeGetandSet emp : employees) 
            {
                writer.println(emp.getName() + "," + emp.getBaseHourlyRate() + "," + emp.getHoursWorked());
            }
        } 
        catch (IOException e) 
        {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    public static void viewAllEmployees(List<EmployeeGetandSet> employees) 
    {
        if (!employees.isEmpty()) 
        {
            System.out.println("All Employees:");
            for (int i = 0; i < employees.size(); i++) 
            {
                System.out.println((i + 1) + ". Name: " + employees.get(i).getName() + ", Hourly Rate: " + employees.get(i).getBaseHourlyRate() + ", Hours Worked: " + employees.get(i).getHoursWorked());
            }
        } 
        else 
        {
            System.out.println("No employees added yet.");
        }
    }
}
