package cse.uta.elawaves.Carrier;



public class CarrierImplementation {
    private static CarrierImplementation instance;

    public static CarrierImplementation getInstance(){
        if(instance == null)
            instance = new CarrierImplementation();

        return instance;
    }

    private CarrierImplementation(){


    }
}
