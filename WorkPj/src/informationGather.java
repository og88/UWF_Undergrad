import java.util.ArrayList;

public class informationGather {
    private static fileParser fr; //file parser used to get information from a file
    private static ArrayList<String> roles;  //ArrayList used to store distinct roles
    private static ArrayList<employee> employees;  //Array list used to store Employees generated from the file

    /**
     * Get the array list of roles
     * @return returns an ArrayList with roles
     */
    public static ArrayList<String> getRoles() {
        return roles;
    }

    /**
     * Get the ArrayList of employees
     * @return returns an ArrayList with employee objects
     */
    public static ArrayList<employee> getEmployees() {
        return employees;
    }

    /**
     * Method used to parse information from file. This method retrieves employee names, salary, and role. The method also creates a list
     * of distinct roles for grouping salaries by roles.
     * @param name Name of the file to be parsed. Should be in CSV format
     */
    public static void setEmployees(String name)
    {

        fr = new fileParser(name);  //Create a file parser to get employee information
        roles = new ArrayList<>();  //Initializes ArrayList of rolse
        employees = new ArrayList(); //Initialize ArrayList of employees

        String done = "not done";  //Sting used as a Boolean and to retried words
        while(done != null) {  //Make sure a word was actually retrieved
            employee emp = new employee();  //If a word is found, create an new employee

            if ((done = fr.getNextWord()) != null)  //If a name is retrieved, set pass it into employee object
                emp.setName(done);
            if ((done = fr.getNextWord()) != null)  //If a salary is found, add it into the employee object
                emp.setSalary(done);
            if ((done = fr.getNextWord()) != null)  //If a role is found, add it to employee object
                emp.setRole(done);

            if(done != null) {  //If a role is found, that means other values where also found.
                employees.add(emp);  //Add current employee into employee list

                if(!roles.contains(emp.getRole()))  //If the role ArrayList is doesn't have the current role, add it
                {
                    roles.add(emp.getRole());  //Append new role into list
                }
            }
        }

    }

    /**
     * method used to start file parser closing procedures.
     * @return  returns the status of the close.
     */
    public static int close()
    {
        if(fr != null)  //Check to see if a fileParser is actually opened
            return fr.close();  //Return status of fle parser
        return 0;   //If no fileParser was created, the file is already close.
    }

}
