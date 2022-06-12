package Neighberhood.Intra;

import Components.Client;
import Components.Route;
import Components.Vehicle;
import Neighberhood.NeighborhoodGenerator;

import java.util.*;

public class OptNeighborhood extends IntraNeighborhoodGenerator {

    public OptNeighborhood(List<Vehicle> solution, float fitness, int vehicleNbr, int firstIdx, int secondIdx) {
        super(solution, fitness, vehicleNbr, firstIdx, secondIdx);
    }
   @Override
   public List<Vehicle> getNeighborhood() {
        return neighborhood;
    }

   @Override
   public void calculateNeighborhood() {
       List<Client> route = solution.get(vehicleNbr).getRoute().getClients();
       Vehicle vehicle = new Vehicle(solution.get(vehicleNbr).getPercentageToFill(), 0, new Route(), 0);
       int nbrClients = route.size();
       fitness = fitness - solution.get(vehicleNbr).getFitness();
       // Copy the same route from the beginning until k-1
       for(int k = 0; k <= firstIdx-1; k++) {
           vehicle.addClient(route.get(k));
       }
       for(int k = secondIdx; k>=firstIdx; k--) {
           vehicle.addClient(route.get(k));
       }
       for(int k = secondIdx+1; k < nbrClients; k++) {
           vehicle.addClient(route.get(k));
       }
       neighborhood.set(vehicleNbr, vehicle);
       fitness += vehicle.getFitness();
   }

   public float fitness() {
       return fitness;
   }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof OptNeighborhood) {
            if(((OptNeighborhood) obj).firstIdx == this.firstIdx && ((OptNeighborhood) obj).secondIdx == this.secondIdx &&
                    ((OptNeighborhood) obj).vehicleNbr == this.vehicleNbr)
                return true;
        }
        return false;
    }

}
