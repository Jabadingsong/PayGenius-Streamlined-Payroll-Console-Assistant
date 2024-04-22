public class PayslipCalculation 
{
    public static final double OVERTIME_RATE = 1.25; // 25% increase for overtime
    public static final double TAX_RATE = 0.20; // Tax rate to 20% of gross pay to simplify
    public static final double SSS = 0; 
    public static final double PHILHEALTH = 0.05; // PhilHealth contribution is 5% of gross pay
    public static final int HOURS_MONTH = 160; // Assuming standard working hours in a month

    public static double MonthlyPayCalculation(double baseHourlyRate, double totalHoursWorked)  
    {
        double regularHours; // Regular hours worked
    
        if (totalHoursWorked <= HOURS_MONTH) 
        {
            regularHours = totalHoursWorked; 
            return baseHourlyRate * regularHours; 
        } 
        else 
        {
            double overtimeHours; // Overtime hours worked
            double halfMonthHours = totalHoursWorked / 2; // Half of the total hours worked
    
            if (halfMonthHours <= HOURS_MONTH / 2)  
            {
                regularHours = halfMonthHours; 
                overtimeHours = 0; 
            } 
            else 
            {
                regularHours = HOURS_MONTH / 2; 
                overtimeHours = halfMonthHours - (HOURS_MONTH / 2); 
            }
    
            double regularPay = baseHourlyRate * regularHours; 
            double overtimePay = OvertimeEarningsCalculations(baseHourlyRate, overtimeHours); 
    
            return regularPay + overtimePay; 
        }
    }

    public static double SSSDeduction(double grossPay) 
    {
        // Define the ranges and corresponding EE values
        double[] SalaryRanges = {4250.0, 4750.0, 5250.0, 5750.0, 6250.0, 6750.0, 7250.0, 7750.0, 8250.0, 8750.0,
                        9250.0, 9750.0, 10250.0, 10750.0, 11250.0, 11750.0, 12250.0, 12750.0, 13250.0,
                        13750.0, 14250.0, 14750.0, 15250.0, 15750.0, 16250.0, 16750.0, 17250.0, 17750.0,
                        18250.0, 18750.0, 19250.0, 19750.0, 20250.0, 20750.0, 21250.0, 21750.0, 22250.0,
                        22750.0, 23250.0, 23750.0, 24250.0, 24750.0};
        double[] eeValues = {180.0, 202.5, 225.0, 247.5, 270.0, 292.5, 315.0, 337.5, 360.0, 382.5, 405.0, 427.5,
                        450.0, 472.5, 495.0, 517.5, 540.0, 562.5, 585.0, 607.5, 630.0, 652.5, 675.0, 697.5,
                        720.0, 742.5, 765.0, 787.5, 810.0, 832.5, 855.0, 877.5, 900.0, 922.5, 945.0, 967.5,
                        990.0, 1012.5, 1035.0, 1057.5, 1080.0, 1102.5, 1125.0, 1350.0};
    
        // Find the appropriate range
        int index = -1;
        for (int i = 0; i < SalaryRanges.length; i++)  
        {
            if (grossPay < SalaryRanges[i])  
            {
                index = i;
                break;
            }
        }
    
        // If grossPay is greater than the last range, set EE to the last value
        if (index == -1) 
        {
            return eeValues[eeValues.length - 1]; 
        }
    
        return eeValues[index]; 
    }

    public static double DeductionsCalculations(double grossPay)  
    {
        double tax = grossPay * TAX_RATE / 2; // Tax is 25% of gross pay
        double SSS_CONTRIBUTION = SSSDeduction(grossPay) / 2; 
        double philHealth = grossPay * PHILHEALTH / 2; // PhilHealth is 5% of gross pay
        return tax + SSS_CONTRIBUTION + philHealth; 
    }

    public static double NetPayCalculations(double grossPay, double deductions) 
    {
        return grossPay - deductions; 
    }

    public static double OvertimeEarningsCalculations(double hourlyRate, double overtimeHours)  
    {
        double overtimeRate = hourlyRate * OVERTIME_RATE; // Overtime pay is 25% more than regular rate
        return overtimeHours * overtimeRate; 
    }
}
