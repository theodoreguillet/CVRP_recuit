package Neighberhood.Intra;

import Components.Vehicle;
import Neighberhood.NeighborhoodGenerator;

import java.util.List;

public abstract class IntraNeighborhoodGenerator extends NeighborhoodGenerator {
    protected int vehicleNbr;
    protected int firstIdx;
    protected int secondIdx;
    public IntraNeighborhoodGenerator(List<Vehicle> solution, float fitness, int vehicleNbr, int firstIdx, int secondIdx) {
        super(solution, fitness);
        this.firstIdx = firstIdx;
        this.secondIdx = secondIdx;
        this.vehicleNbr = vehicleNbr;
    }



}
