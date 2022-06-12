package Neighberhood.Inter;

import Components.Vehicle;
import Neighberhood.NeighborhoodGenerator;

import java.util.List;

public abstract class InterNeighborhood extends NeighborhoodGenerator {

    protected int firstIdx;
    protected int secondIdx;
    protected int firstVehicleNbre;
    protected int secondVehicleNbre;
    protected boolean generationSuccessed;

    public InterNeighborhood(List<Vehicle> solution, float fitness, int firstIdx, int secondIdx, int firstVehicleNbre, int secondVehicleNbre) {
        super(solution, fitness);
        this.firstIdx = firstIdx;
        this.secondIdx = secondIdx;
        this.firstVehicleNbre = firstVehicleNbre;
        this.secondVehicleNbre = secondVehicleNbre;
        generationSuccessed = false;
    }

    public boolean isGenerationSuccessed() {
        return generationSuccessed;
    }


}
