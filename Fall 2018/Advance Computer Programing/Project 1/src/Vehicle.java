
public class Vehicle {

    /**
     * Private Variables
     * */
    private String make;
    private String model;
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
    * @param make Allows the user to set the values of the make
    * */

    public void setMake(String make)
    {

        this.make = make;
    }

    /**
     * Setters for the module
     * @param model Allows the user to set the values of the module
     * */
    public void setModel(String model)
    {
        this.model = model;
    }

    /**
     * Setters for the weight
     * @param weight Allows the user to set the values of the weight
     * */
    public void setWeight(double weight)
    {
        this.weight = weight;
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
