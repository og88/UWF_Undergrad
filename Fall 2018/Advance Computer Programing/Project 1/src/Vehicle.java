import java.util.Random;

public class Vehicle {

    /**
     * Private Variables
     * */
    private String Make;
    enum make {Ford, Chevy, Toyota, Nissan, Hyundai}
    private String Model;
    enum model {Compact, Intermediate, FullSize, SUV}
    private double Weight;
    private double EngineSize;
    private boolean Import;


    /**
    * Default constructor for class Vehicle
    * */
    public Vehicle()
    {
        Make = "";
        Model = "";
        Weight = 0;
        EngineSize = 0;
        Import = false;
    }

    /**
    * Parameterized constructor
    * @param make The make of a specific vehicle
    * @param model The model of a Vehicle
    * @param weight The weight of the Vehicle. compact cars (1500-2000),  intermediate cars (2000-2500), SUVs, full-sized cars, pickups and vans are all in the same weight range (2500-4000).
     *               * **/
    public Vehicle(String make, String model, double weight, double engineSize, boolean Import)
    {
        this.Make = make;
        this.Model = model;
        this.Weight = weight;
        this.EngineSize = engineSize;
        this.Import = Import;
    }

    /**
    * Setters for the make
    * */

    public void setMake()
    {
        Random rand = new Random();
        int n = rand.nextInt(5);
        if(n == 0){this.Make = make.Ford.name();}
        if(n == 1){this.Make = make.Chevy.name();}
        if(n == 2){this.Make = make.Toyota.name();}
        if(n == 3){this.Make = make.Hyundai.name();}
        if(n == 4){this.Make = make.Nissan.name();}
    }

    /**
     * Setters for the module
     * */
    public void setModel()
    {
        Random rand = new Random();
        int n = rand.nextInt(4);
        if(n == 0){this.Model = model.SUV.name();}
        if(n == 1){this.Model = model.Compact.name();}
        if(n == 2){this.Model = model.FullSize.name();}
        if(n == 3){this.Model = model.Intermediate.name();}
    }

    /**
     * Setters for the weight
     * @param weight Allows the user to set the values of the weight
     * */
    public int setWeight(double weight)
    {
        if(this.Model == model.Compact.name())
        {
            if(weight < 1500 || weight > 2000) {return 0;}
            else{this.Weight = weight; return 1;}
        }
        else if(this.Model == model.Intermediate.name())
        {
            if(weight < 2000 || weight > 2500) {return 0;}
            else{this.Weight = weight; return 1;}
        }
        else if(this.Model == model.SUV.name() || this.Model == model.FullSize.name())
        {
            if(weight < 2500 || weight > 4000) {return 0;}
            else{this.Weight = weight; return 1;}
        }
        return 0;
    }

    /**
     * Setters for the import
     * @param anImport Allows the user to set the values of the import
     * */
    public void setImport(boolean anImport)
    {
        Import = anImport;
    }

    /**
     * Setters for the module
     * @param engineSize Allows the user to set the values of the engineSize
     * */
    public void setEngineSize(double engineSize)
    {
        this.EngineSize = engineSize;
    }

    /**
    * make getters
    * @Returns The name of the make.
    * */
    public String getMake()
    {

        return Make;
    }

    /**
     * model getters
     * @Returns The name of the model.
     * */
    public String getModel()
    {

        return Model;
    }

    /**
     * weight getters
     * @Returns The weight of the Vehicle in pounds.
     * */
    public double getWeight()
    {
        return Weight;
    }

    /**
     * import getters
     * @Returns A booleagn that tells the user whether the vehicle is imported or not.
     * */
    public int isImport()
    {
        if(Import){return 1;}
        return 0;
    }

    /**
     * Engine Size getters
     * @Returns The size of the engine.
     * */
    public double getEngineSize()
    {
        return EngineSize;
    }

}
