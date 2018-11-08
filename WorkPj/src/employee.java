public class employee {
    private String name; //Name of employee
    private int salary;  //Yearly salary in dollars
    private String role; //The role of the employee

    /**
     * Method used to retrieve the employees name
     * @return Returns a string that has the employees name
     */
    public String getName() {
        return name;
    }

    /**
     * method used to change or initialize an employees name
     * @param name String containing an employee's name
     */
    public void setName(String name) {
        this.name = name;  //set our private field to name
    }

    /**
     * Method to retrieve an employees yearly salary. The salary is an int value
     * @return Returns yearly salary as an int
     */
    public int getSalary() {
        return salary;
    }

    /**
     * Setter method that changes or initializes employees salary
     * @param salary int variable that is the employees salary
     */
    public void setSalary(int salary) {
        this.salary = salary;  //Sets our private field to salary
    }

    /**
     * Second setter method for salary. This method takes in a string rather than an int.
     * The input string is parsed to an int then set to the private field salary
     * @param salary
     */
    public void setSalary(String salary) {
        this.salary = Integer.parseInt(salary);  //Parse string to in then set the salary to that value
    }

    /**
     * retrieve the role of the employee
     * @return Returns the employee's role as an string
     */
    public String getRole() {
        return role;
    }

    /**
     * Setter to change or initialize an employees role
     * @param role String that contains employees role
     */
    public void setRole(String role) {
        this.role = role;  //Set private field role to input string
    }
}
