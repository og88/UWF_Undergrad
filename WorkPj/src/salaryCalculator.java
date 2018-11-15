import java.util.ArrayList;


public class salaryCalculator {
    private static int month = 12;  //The number of months in a year
    private static int week  = 52;  //The number of weeks in a year
    private static int hour = 2080; //The number of hours in a work year


    /**
     * Method to calculate the total salary of employees. The method takes in an employee object with
     * a getSalary method. The method sums up all the salaries in the employees list.
     * @param employees ArrayList containing employee objects.
     * @return Returns an int that contains total salary.
     */
    public static int totalSalary(ArrayList<employee> employees)
    {
        int total = 0;  //Total is initialize by 0
        for(employee each : employees) {   //For loop to retrieve salary from every employee in the list
            total += each.getSalary();   //Add current salary to total
        }
        return total;  //returns total salary as int
    }

    /**
     * Method calculates the total salary and groups them by roles. The method creates an array of salaries.
     * @param employees ArrayList of employees. The employees should have a get salary method.
     * @param Roles ArrayList of roles. This list is used to sort values added to a group.
     * @return Returns an array of total salaries.
     */
    public static int[] totalSalaryByRole(ArrayList<employee> employees,ArrayList<String> Roles)
    {
        int[] totals = new int[Roles.size()]; //Create an array with the same size as the role list.
        for(employee each : employees)   //go through each employee in the ArrayList and sum up the salaries
        {
            for(int j = 0; j < Roles.size(); j++)  //Go through the list od roles and add salaries to appropriate object
            {
                if(each.getRole().equals(Roles.get(j)))  //Compare employees role with roles in ArrayList
                {
                    totals[j] += each.getSalary();  //Add employees salary to appropriate role slot.
                }
            }
        }
        return totals; //Returns array of salaries
    }

    /**
     * Uses totalSalary method to get total salary. Uses methods to calculate the total salary in time units.
     * @param employees Takes in an ArrayList of employees.
     */
    public static void salaryInDifferentValues(ArrayList<employee> employees)
    {
        int total = totalSalary(employees);  //Use the totalSalary method to calculate the total salary
        totalMonth(total,"Total");  //Total salary in months
        totalWeek(total,"Total");  //Total salary in weeks
        totalHour(total,"Total");  //Total salary in hours
    }

    /**
     * Method used to calculate total salaries based on roles. The method then calculates the totals in different time units.
     * @param employees  ArrayList of employee objects
     * @param Roles  ArrayList of roles
     */
    public static void salaryValuesByRole(ArrayList<employee> employees,ArrayList<String> Roles)
    {
        int[] totals = totalSalaryByRole(employees, Roles);  //Retrieve an array of total salaries
        for(int i = 0; i < totals.length; i++)  //Calculate the salaries based on time units
        {
            totalMonth(totals[i],Roles.get(i));  //Calculate the salary for a role in months
            totalWeek(totals[i],Roles.get(i));   //Calculate the salary for a role in weeks
            totalHour(totals[i],Roles.get(i));   //Calculate the salary for a role in hours
        }
    }

    /**
     * Simple method to convert a yearly salary into a months
     * @param total value of total salary
     * @param role Name of who's salary is being calculated. Total is calculating total salary.
     */
    public static void totalMonth(int total, String role)
    {
        int monthly = total/month; //Compute the total salary in months
        System.out.println(role + " salary in months : $" + monthly);
    }

    /**
     * Simple method to convert a yearly salary into a weeks
     * @param total value of total salary
     * @param role Name of who's salary is being calculated. Total is calculating total salary.
     */
    public static void totalWeek(int total, String role)
    {
        int weekly = total/week;   //Calculate the total salary in weeks
        System.out.println(role + " salary in weeks : $" + weekly);
    }

    /**
     * Simple method to convert a yearly salary into a hours
     * @param total value of total salary
     * @param role Name of who's salary is being calculated. Total is calculating total salary.
     */
    public static void totalHour(int total, String role)
    {
        int hourly = total/hour;   //Calculate the total salary in hours
        System.out.println(role + " salary in hours : $" + hourly);
    }

}
