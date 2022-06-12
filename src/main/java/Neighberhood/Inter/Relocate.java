package Neighberhood.Inter;

import Components.Client;
import Components.Vehicle;

import java.util.List;

public class Relocate extends InterNeighborhood {
    public Relocate(List<Vehicle> solution, float fitness, int firstIdx, int secondIdx, int firstVehicleNbre, int secondVehicleNbre) {
        super(solution, fitness, firstIdx, secondIdx, firstVehicleNbre, secondVehicleNbre);
    }

    public boolean getGenerationSuccessed(){
        return generationSuccessed;
    }

    @Override
    public void calculateNeighborhood() {
        Vehicle firstVehicle = solution.get(firstVehicleNbre).clone();
        Vehicle secondVehicle = solution.get(secondVehicleNbre).clone();
        Client client = firstVehicle.getRoute().getClients().get(firstIdx);

        if(client.getQ() + secondVehicle.getQ() <= Vehicle.getQMax()){

            fitness = fitness - firstVehicle.getFitness() - secondVehicle.getFitness();
            secondVehicle.addClient(client, secondIdx);
            firstVehicle.removeClient(client);
            neighborhood.set(firstVehicleNbre, firstVehicle);
            neighborhood.set(secondVehicleNbre, secondVehicle);
            fitness += firstVehicle.getFitness() + secondVehicle.getFitness();
            generationSuccessed = true;
        }

    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Relocate) {
            if(((Relocate) obj).firstIdx == this.firstIdx && ((Relocate) obj).secondIdx == this.secondIdx &&
                    ((Relocate) obj).firstVehicleNbre == this.firstVehicleNbre && ((Relocate) obj).secondVehicleNbre == this.secondVehicleNbre)
                return true;
        }
        return false;
    }

    @Override
    public float fitness() {
        return fitness;
    }
}
