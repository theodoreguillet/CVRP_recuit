package Neighberhood.Inter;

import Components.Client;
import Components.Vehicle;

import java.util.List;

public class CrossExchange extends InterNeighborhood {

    public CrossExchange(List<Vehicle> solution, float fitness, int firstIdx, int secondIdx, int firstVehicleNbre, int secondVehicleNbre) {
        super(solution, fitness, firstIdx, secondIdx, firstVehicleNbre, secondVehicleNbre);
    }

    @Override
    public void calculateNeighborhood() {
        Vehicle firstModifiedVehicle = solution.get(firstVehicleNbre).clone();
        Vehicle secondModifiedVehicle = solution.get(secondVehicleNbre).clone();
        fitness = fitness - firstModifiedVehicle.getFitness() - secondModifiedVehicle.getFitness();
        List<Client> firstRouteClients = firstModifiedVehicle.getRoute().clone().getClients();
        List<Client> secondRouteClients = secondModifiedVehicle.getRoute().clone().getClients();

        List<Client> firstList = firstRouteClients.subList(firstIdx, firstModifiedVehicle.getRoute().getClients().size() - 1);
        List<Client> secondList = secondRouteClients.subList(secondIdx, secondModifiedVehicle.getRoute().getClients().size() - 1);

        int firstQ = firstList.stream().mapToInt(client -> client.getQ()).sum();
        int secondQ = secondList.stream().mapToInt(client -> client.getQ()).sum();

        if (firstModifiedVehicle.getQ() - firstQ + secondQ <= Vehicle.getQMax() && secondModifiedVehicle.getQ() - secondQ + firstQ <= Vehicle.getQMax()) {
            for (int i = 0; i < firstList.size(); i++) {
                firstModifiedVehicle.removeClient(firstList.get(i));
            }

            for (int i = 0; i < secondList.size(); i++) {
                firstModifiedVehicle.addClient(secondList.get(i), firstModifiedVehicle.getRoute().getClients().size()-1);
                secondModifiedVehicle.removeClient(secondList.get(i));
            }

            for (int i = 0; i < firstList.size(); i++) {
                secondModifiedVehicle.addClient(firstList.get(i), secondModifiedVehicle.getRoute().getClients().size()-1);
            }
            generationSuccessed = true;
            fitness += firstModifiedVehicle.getFitness() + secondModifiedVehicle.getFitness();
            neighborhood.set(firstVehicleNbre, firstModifiedVehicle);
            neighborhood.set(secondVehicleNbre, secondModifiedVehicle);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof CrossExchange) {
            if (((CrossExchange) obj).firstIdx == this.firstIdx && ((CrossExchange) obj).secondIdx == this.secondIdx &&
                    ((CrossExchange) obj).firstVehicleNbre == this.firstVehicleNbre && ((CrossExchange) obj).secondVehicleNbre == this.secondVehicleNbre)
                return true;
        }
        return false;
    }
}
