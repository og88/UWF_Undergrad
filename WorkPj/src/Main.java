import java.util.ArrayList;

public class Main {

    /**
     * The main method is used to validate the users input. The salary calculator receives
     *the name of the csv file and the type of calculation to perform.
     * @param args array of arguments passed in by the command line
     */
    public static void main(String[] args) {
        if(!startCalculator(args[0], args[1]))  //Check to see if the calculator ran successfully
        {
            System.out.println("Please include a file name and calculation type.\n" +  //Explain to the user the proper way to launch the program
                    "Main <file name> <calculation typ>\n" +
                    "a : Total salary in dollars\n" +
                    "b : Total salary in dollars grouped by role\n" +
                    "c : Total salary in dollars by time unit (hours, weeks, and month)\n" +
                    "d : Total salary in dollars by time unit grouped by role");
        }
    }

    /**
     * The startCalculator method sets up and runs the specified calculation.
     * For this class, the calculation type is specified by a, b, c, or d.
     * @param file A string that holds the file name. This will be the CSV file the user wants to parse.
     * @param condition A string that holds the calculation type.
     */
    public static Boolean startCalculator(String file, String condition)
    {
        if(condition != null && file != null) {   //Check to see if a file name and calculation type where passed through
            informationGather fp = new informationGather();  //Create a file parse to gather information from a csv file.
            fp.setEmployees(file);  //Use file parser to extract employees and the different roles from a provided CSV file.

            ArrayList<employee> employees = fp.getEmployees();  //Retrieve an ArrayList of employees parsed from the CSV file.
            ArrayList<String> Roles = fp.getRoles();            //Retrieve an ArrayList of distinct roles from the CSV file.

            Boolean isType = false;  //Boolean used to check whether the requested calculation type is valid/exists.

            if(condition.equals("a")){  //Runs method to calculate the total salary of all employees in the CSV
                salaryCalculator.totalSalary(employees);
                isType = true;
            }
            if(condition.equals("b")){  //Runs method to calculate total salaries of employees grouped by roles
                salaryCalculator.totalSalaryByRole(employees,Roles);
                isType = true;
            }
            if(condition.equals("c")){  //Runs method to calculate total salary in different time units
                salaryCalculator.salaryInDifferentValues(employees);
                isType = true;
            }
            if(condition.equals("d")){  //Runs method to calculate total salaries in different time units, but also grouped by roles
                salaryCalculator.salaryValuesByRole(employees,Roles);
                isType = true;
            }

            fp.close();  //close file parser
            return isType;  //Return whether the type request was valid
        }
        return false;  //return false to inform user of issue with parameters
    }
}
