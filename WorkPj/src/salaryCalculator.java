import java.util.ArrayList;

public class salaryCalculator {



    public static int totalSalary(ArrayList<employee> employees)
    {
        int total = 0;
        for(employee each : employees) {
            total += each.getSalary();
        }
        System.out.println("Total salary is " + total);
        return total;
    }

    public static int[] totalSalaryByRole(ArrayList<employee> employees,ArrayList<String> Roles)
    {
        int[] totals = new int[Roles.size()];
        for(employee each : employees)
        {
            for(int j = 0; j < Roles.size(); j++)
            {
                if(each.getRole().equals(Roles.get(j)))
                {
                    totals[j] += each.getSalary();
                }
            }
        }
        for(int i = 0; i < totals.length; i++)
        {
            System.out.println("Total for " + Roles.get(i) + "s is " + totals[i]);
        }
        return totals;
    }

    public static void salaryInDifferentValues(ArrayList<employee> employees)
    {
        int total = totalSalary(employees);
        int monthly = total/12;
        int weekly = total/52;
        int hourly = total/2080;
        System.out.println("Total salary in hours : $" + hourly + "\nTotal salary in weeks : $" + weekly + "\nTotal salary in months : $" + monthly);
    }
    public static void salaryValuesByRole(ArrayList<employee> employees,ArrayList<String> Roles)
    {
        int[] totals = totalSalaryByRole(employees, Roles);
        for(int i = 0; i < totals.length; i++)
        {
            int monthly = totals[i]/12;
            int weekly = totals[i]/52;
            int hourly = totals[i]/2080;
            System.out.println("For " +Roles.get(i) + "\nTotal salary in hours : $" + hourly + "\nTotal salary in weeks : $" + weekly + "\nTotal salary in months : $" + monthly);
        }
    }

}
