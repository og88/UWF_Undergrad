import java.util.ArrayList;

public class informationGather {
    private static fileParser fr;
    private static ArrayList<String> roles;
    private static ArrayList<employee> employees;

    public static ArrayList<String> getRoles() {
        return roles;
    }

    public static ArrayList<employee> getEmployees() {
        return employees;
    }

    public static void setEmployees(String name)
    {

        fr = new fileParser(name);
        roles = new ArrayList<>();
        employees = new ArrayList();

        String done = "not done";
        while(done != null) {
            employee emp = new employee();

            if ((done = fr.getNextWord()) != null)
                emp.setName(done);
            if ((done = fr.getNextWord()) != null)
                emp.setSalary(done);
            if ((done = fr.getNextWord()) != null)
                emp.setRole(done);

            if(done != null) {
                employees.add(emp);

                if(!roles.contains(emp.getRole()))
                {
                    roles.add(emp.getRole());
                }
            }
        }

    }

    public static int close()
    {
        if(fr != null)
            return fr.close();
        return 0;
    }

}
