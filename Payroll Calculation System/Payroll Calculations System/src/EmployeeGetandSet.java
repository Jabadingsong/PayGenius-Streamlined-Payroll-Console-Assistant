public class EmployeeGetandSet 
{
    private String employeeName;
    private double baseHourlyRate;
    private double totalHoursWorked;

    public EmployeeGetandSet(String employeeName, double baseHourlyRate, double totalHoursWorked) 
    {
        this.employeeName = employeeName;
        this.baseHourlyRate = baseHourlyRate;
        this.totalHoursWorked = totalHoursWorked;
    }

    // Getters and setters
    public String getName() 
    {
        return employeeName;
    }

    public void setName(String employeeName) 
    {
        this.employeeName = employeeName;
    }

    public double getBaseHourlyRate() 
    {
        return baseHourlyRate;
    }

    public void setBaseHourlyRate(double baseHourlyRate) 
    {
        this.baseHourlyRate = baseHourlyRate;
    }

    public double getHoursWorked()
    {
        return totalHoursWorked;
    }

    public void setHoursWorked(double totalHoursWorked) 
    {
        this.totalHoursWorked = totalHoursWorked;
    }
}