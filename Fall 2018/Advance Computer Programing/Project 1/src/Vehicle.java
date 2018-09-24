import java.util.Random;

public class Vehicle {

    /**
     * Private Variables
     * */
    private String make;
    enum Make {Ford, Chevy, Toyota, Nissan, Hyundai}
    private String model;
    enum Model {Compact, Intermediate, FullSize, SUV}
    private double weight;
    private double engineSize;
    private boolean Import;


    /**
    * Default constructor for class Vehicle
    * */
    public Vehicle()
    {
        make = "";
        model = "";
        weight = 0;
        engineSize = 0;
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
        this.make = make;
        this.model = model;
        this.weight = weight;
        this.engineSize = engineSize;
        this.Import = Import;
    }

    /**
    * Setters for the make
    * */

    public void setMake()
    {
        Random rand = new Random();
        int n = rand.nextInt(5);
        if(n == 0){this.make = Make.Ford.name();}
        if(n == 1){this.make = Make.Chevy.name();}
        if(n == 2){this.make = Make.Toyota.name();}
        if(n == 3){this.make = Make.Hyundai.name();}
        if(n == 4){this.make = Make.Nissan.name();}
    }

    /**
     * Setters for the module
     * */
    public void setModel()
    {
        Random rand = new Random();
        int n = rand.nextInt(4);
        if(n == 0){this.model = Model.SUV.name();}
        if(n == 1){this.model = Model.Compact.name();}
        if(n == 2){this.model = Model.FullSize.name();}
        if(n == 3){this.model = Model.Intermediate.name();}
    }

    /**
     * Setters for the weight
     * @param weight Allows the user to set the values of the weight
     * */
    public int setWeight(double weight)
    {
        if(this.model == Model.Compact.name())
        {
            if(weight < 1500 || weight > 2000) {return 0;}
            else{this.weight = weight; return 1;}
        }
        else if(this.model == Model.Intermediate.name())
        {
            if(weight < 2000 || weight > 2500) {return 0;}
            else{this.weight = weight; return 1;}
        }
        else if(this.model == Model.SUV.name() || this.model == Model.FullSize.name())
        {
            if(weight < 2500 || weight > 4000) {return 0;}
            else{this.weight = weight; return 1;}
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
        this.engineSize = engineSize;
    }

    /**
    * make getters
    * @Returns The name of the make.
    * */
    public String getMake()
    {

        return make;
    }

    /**
     * model getters
     * @Returns The name of the model.
     * */
    public String getModel()
    {

        return model;
    }

    /**
     * weight getters
     * @Returns The weight of the Vehicle in pounds.
     * */
    public double getWeight()
    {
        return weight;
    }

    /**
     * import getters
     * @Returns A booleagn that tells the user whether the vehicle is imported or not.
     * */
    public boolean isImport()
    {
        return Import;
    }

    /**
     * Engine Size getters
     * @Returns The size of the engine.
     * */
    public double getEngineSize()
    {
        return engineSize;
    }

}
