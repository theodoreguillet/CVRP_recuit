package Neighberhood.Inter;

import Components.Client;
import Components.Vehicle;

import java.util.List;

public class Exchange extends InterNeighborhood {
    public Exchange(List<Vehicle> solution, float fitness, int firstIdx, int secondIdx, int firstVehicleNbre, int secondVehicleNbre) {
        super(solution, fitness, firstIdx, secondIdx, firstVehicleNbre, secondVehicleNbre);
    }

    @Override
    public void calculateNeighborhood() {
        Vehicle firstModifiedVehicle = solution.get(firstVehicleNbre).clone();
        Vehicle secondModifiedVehicle = solution.get(secondVehicleNbre).clone();
        Client firstClient = firstModifiedVehicle.getRoute().getClients().get(firstIdx);
        Client secondClient = secondModifiedVehicle.getRoute().getClients().get(secondIdx);

        if((firstModifiedVehicle.getQ() - firstClient.getQ()+secondClient.getQ() <= Vehicle.getQMax()) && (secondModifiedVehicle.getQ() - secondClient.getQ()+firstClient.getQ() <= Vehicle.getQMax()) ) {
            fitness = fitness - firstModifiedVehicle.getFitness() - secondModifiedVehicle.getFitness();
            firstModifiedVehicle.setClient(secondClient, firstIdx);
            secondModifiedVehicle.setClient(firstClient, secondIdx);
            fitness = fitness + firstModifiedVehicle.getFitness() + secondModifiedVehicle.getFitness();
            neighborhood.set(firstVehicleNbre, firstModifiedVehicle);
            neighborhood.set(secondVehicleNbre, secondModifiedVehicle);
            generationSuccessed = true;
        }

    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Exchange) {
            if(((Exchange) obj).firstIdx == this.firstIdx && ((Exchange) obj).secondIdx == this.secondIdx &&
                    ((Exchange) obj).firstVehicleNbre == this.firstVehicleNbre && ((Exchange) obj).secondVehicleNbre == this.secondVehicleNbre)
                return true;
        }
        return false;
    }

    @Override
    public float fitness() {
        return fitness;
    }

}
