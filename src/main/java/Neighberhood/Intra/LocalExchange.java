package Neighberhood.Intra;

import Components.Client;
import Components.Route;
import Components.Vehicle;

import java.util.ArrayList;
import java.util.List;

public class LocalExchange extends IntraNeighborhoodGenerator {

    public LocalExchange(List<Vehicle> solution, float fitness, int vehicleNbr, int firstIdx, int secondIdx) {
        super(solution, fitness, vehicleNbr, firstIdx, secondIdx);
    }

    @Override
    public void calculateNeighborhood() {
        Vehicle vehicle = solution.get(vehicleNbr).clone();
        fitness = fitness - vehicle.getFitness();
        Client client = vehicle.getRoute().getClients().get(firstIdx);
        vehicle.setClient(vehicle.getRoute().getClients().get(secondIdx), firstIdx);
        vehicle.setClient(client, secondIdx);
        fitness += vehicle.getFitness();
        neighborhood.set(vehicleNbr, vehicle);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof LocalExchange) {
            if (((LocalExchange) obj).firstIdx == this.firstIdx && ((LocalExchange) obj).secondIdx == this.secondIdx &&
                    ((LocalExchange) obj).vehicleNbr == this.vehicleNbr)
                return true;
        }
        return false;
    }

    @Override
    public float fitness() {
        return fitness;
    }

}
