package Neighberhood.Intra;

import Components.Client;
import Components.Vehicle;

import java.util.ArrayList;
import java.util.List;

public class Relocate extends IntraNeighborhoodGenerator {

    public Relocate(List<Vehicle> solution, float fitness, int vehicleNbr, int firstIdx, int secondIdx) {
        super(solution, fitness, vehicleNbr, firstIdx, secondIdx);
    }

    // @Override
    /*public void calculateNeighborhood() {
        Vehicle vehicle = solution.get(vehicleNbr).clone();
        fitness = fitness - vehicle.getFitness();
        Client client = vehicle.getRoute().getClients().get(firstIdx);
        vehicle.removeClient(firstIdx);
        if(firstIdx < secondIdx){
            vehicle.addClient(client, secondIdx-1);
        } else {
            vehicle.addClient(client, secondIdx);
        }
        neighborhood.set(vehicleNbr, vehicle);
        fitness += vehicle.getFitness();
    }*/

    @Override
    public void calculateNeighborhood() {
        Vehicle vehicle = new Vehicle(1);
        fitness = fitness - solution.get(vehicleNbr).getFitness();

        List<Client> clientList = new ArrayList<>(solution.get(vehicleNbr).clone().getRoute().getClients());
       // System.out.println("\n\nClient List : " + clientList);
        Client client = clientList.get(firstIdx);
        clientList.remove(client);
       // System.out.println("Client List after : " + clientList);
        for(int i = 0; i < clientList.size(); i++){
            if(i == secondIdx)
                vehicle.addClient(client);
            vehicle.addClient(clientList.get(i));
        }
       // System.out.println("Client List after add : " + vehicle.getRoute().getClients());
        neighborhood.set(vehicleNbr, vehicle);
        fitness = fitness + vehicle.getFitness();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Relocate) {
            if (((Relocate) obj).firstIdx == this.firstIdx && ((Relocate) obj).secondIdx == this.secondIdx &&
                    ((Relocate) obj).vehicleNbr == this.vehicleNbr)
                return true;
        }
        return false;
    }

    public float fitness() {
        return fitness;
    }


}
