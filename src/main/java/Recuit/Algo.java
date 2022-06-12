package Recuit;

import Components.Client;
import Components.Vehicle;
import Neighberhood.Inter.CrossExchange;
import Neighberhood.Inter.Exchange;
import Neighberhood.Inter.Relocate;
import Neighberhood.Intra.LocalExchange;
import Neighberhood.Intra.OptNeighborhood;
import Neighberhood.NeighborhoodGenerator;
import Utils.Random;

import java.util.*;

public class Algo {
    public static List<Vehicle> recuit(List<Client> clients, List<Vehicle> initialSolution, float initialFitness, float temperature, float temperatureMin, float cooldown, int nitcooldown) {

        List<Vehicle> minSolution = (ArrayList<Vehicle>) ((ArrayList<Vehicle>) initialSolution).clone();
        float minFitness = initialFitness;

        List<Vehicle> currentSolution = (ArrayList<Vehicle>) (((ArrayList<Vehicle>) initialSolution).clone());

        float currentFitness = initialFitness;

        int ritcooldown = nitcooldown;

        while (temperature > temperatureMin) {
            NeighborhoodGenerator neighbor = null;

            // Generate neighborhoods and choose a random one

            int cptVehicule = Random.randrange(0, currentSolution.size() - 1);
            if(cptVehicule >= currentSolution.size() - 1) {
                continue;
            }

            int secondCptVehicle = Random.randrange(cptVehicule + 1, currentSolution.size());
            if(secondCptVehicle >= currentSolution.size()) {
                continue;
            }

            int firstIdx = Random.randrange(1,  currentSolution.get(cptVehicule).getRoute().getClients().size() - 1);
            if(firstIdx >= currentSolution.get(cptVehicule).getRoute().getClients().size() - 1) {
                continue;
            }

            int secondIdx = Random.randrange(1, currentSolution.get(secondCptVehicle).getRoute().getClients().size() - 1);
            if(secondIdx >= currentSolution.get(secondCptVehicle).getRoute().getClients().size() - 1) {
                continue;
            }

            int localFirstIdx = Random.randrange(1,  currentSolution.get(cptVehicule).getRoute().getClients().size() - 2);
            if(localFirstIdx >= currentSolution.get(cptVehicule).getRoute().getClients().size() - 2) {
                continue;
            }

            int localSecondIdx = Random.randrange(localFirstIdx + 1, currentSolution.get(cptVehicule).getRoute().getClients().size() - 1);
            if(localSecondIdx >= currentSolution.get(cptVehicule).getRoute().getClients().size() - 1) {
                continue;
            }

            int nmethod = Random.randrange(0, 2);
            switch (nmethod) {
                case 0:
                    CrossExchange crossExchange = new CrossExchange(currentSolution, currentFitness, firstIdx, secondIdx, cptVehicule, secondCptVehicle);
                    crossExchange.calculateNeighborhood();
                    if (!crossExchange.isGenerationSuccessed()) {
                        continue;
                    }
                    neighbor = crossExchange;
                    break;
                case 1:
                    Relocate relocate = new Relocate(currentSolution, currentFitness, firstIdx, secondIdx, cptVehicule, secondCptVehicle);
                    relocate.calculateNeighborhood();
                    if (!relocate.isGenerationSuccessed()) {
                        continue;
                    }
                    neighbor = relocate;
                    break;
                case 2:
                    Exchange exchange = new Exchange(currentSolution, currentFitness, firstIdx, secondIdx, cptVehicule, secondCptVehicle);
                    exchange.calculateNeighborhood();
                    if (!exchange.isGenerationSuccessed()) {
                        continue;
                    }
                    neighbor = exchange;
                    break;
                case 3:
                    LocalExchange localExchange = new LocalExchange(currentSolution, currentFitness, cptVehicule, localFirstIdx, localSecondIdx);
                    localExchange.calculateNeighborhood();
                    neighbor = localExchange;
                    break;
                case 4:
                    Neighberhood.Intra.Relocate localRelocate = new Neighberhood.Intra.Relocate(currentSolution, currentFitness, cptVehicule, localFirstIdx, localSecondIdx);
                    localRelocate.calculateNeighborhood();
                    neighbor = localRelocate;
                    break;
                case 5:
                    OptNeighborhood opt = new OptNeighborhood(currentSolution, currentFitness, cptVehicule, localFirstIdx, localSecondIdx);
                    opt.calculateNeighborhood();
                    neighbor = opt;
                    break;
            }

            assert neighbor != null;

            double delta = minFitness - neighbor.getFitness();
            if (delta > 0 || Math.random() < Math.exp(delta / temperature)) {
                minSolution = neighbor.getNeighborhood();
                minFitness = neighbor.getFitness();
            }

            if (ritcooldown <= 0) {
                temperature = temperature * cooldown;
                ritcooldown = nitcooldown;
            }
            ritcooldown--;
        }

        //  System.out.println("minFitness = " + minFitness);
        return minSolution;
    }

    /**********************************************************************************************************************************************************/


  /*  public static List<Vehicle> tabuSearch(List<Client> clients, List<Vehicle> initialSolution){

        List<Vehicle> bestSolution = new ArrayList<>();
        List<Vehicle> result = new ArrayList<>();
        bestSolution = initialSolution;
        HashSet<NeighborhoodGenerator> tabuList = new HashSet<>();
        Double minValue = Double.MAX_VALUE;
        NeighborhoodGenerator minNeighborhoodGenerator = null;

        for(int cpt = 0; cpt < 1000; cpt++) {
            HashMap<NeighborhoodGenerator, Double> neighborhoods = new HashMap<>();
            int vehicleNumber = 0;
            for(Vehicle vehicle : bestSolution) {
                Route route = vehicle.getRoute();
                generateNeighborhoods(bestSolution, tabuList, neighborhoods, vehicleNumber, route);
                vehicleNumber++;
            }
            minNeighborhoodGenerator = getMinNeighbor(minValue, neighborhoods, minNeighborhoodGenerator);
            if(fitness(bestSolution) < minValue) {
                tabuList.add(minNeighborhoodGenerator);
                minValue = fitness(bestSolution);
            }
            bestSolution = minNeighborhoodGenerator.getNewSolution();
        }
        double tmpFitnessMin = Double.MAX_VALUE;
        for(NeighborhoodGenerator neighborhood : tabuList) {
            if(tmpFitnessMin > neighborhood.fitness()){
                tmpFitnessMin = neighborhood.fitness();
                result = neighborhood.getNewSolution();
            }

        }
        return result;
    }*/



   /* private static NeighborhoodGenerator getMinNeighbor(Double minValue, HashMap<NeighborhoodGenerator, Double> neighborhoods, NeighborhoodGenerator minNeighborhoodGenerator) {
        //Select x+1 such as f(x+1) = min neighbor
        for(Entry<NeighborhoodGenerator, Double> entry: neighborhoods.entrySet()) {
            if (minValue > entry.getValue()) {
                minNeighborhoodGenerator = entry.getKey();
                minValue = entry.getValue();
            }
        }
        return minNeighborhoodGenerator;
    }

    //TODO :
    private static HashMap<NeighborhoodGenerator, Double> generateInterNeighborhoods(List<Vehicle> bestSolution, HashMap<NeighborhoodGenerator, Double> tabuList, int vehicleNumber) {
        HashMap<NeighborhoodGenerator, Double> neighborhoods = new HashMap<>();
        for (int i = 1; i < bestSolution.get(vehicleNumber).getRoute().getClients().size() - 3; i++) {
            for (int j = i + 1; j < bestSolution.get(vehicleNumber).getRoute().getClients().size() - 1; j++) {
                OptNeighborhood neighborhood = new OptNeighborhood(bestSolution, vehicleNumber, i, j);
                neighborhood.nOptSwap();
                if (!checkIfTabuListContains(tabuList, neighborhood))
                    neighborhoods.put(neighborhood, neighborhood.fitness()); //TODO : Optimiser le calcule de la fitness
            }
        }
        return neighborhoods;
    }*/

    /*private static void generateNeighborhoods(List<Vehicle> bestSolution, HashSet<NeighborhoodGenerator> tabuList, HashMap<NeighborhoodGenerator, Double> neighborhoods, int vehicleNumber) {
        for (int i = 1; i < bestSolution.get(vehicleNumber).getRoute().getClients().size() -3; i++) {
            for (int j = i+1; j < bestSolution.get(vehicleNumber).getRoute().getClients().size()-1; j++) {
                OptNeighborhood neighborhood = new OptNeighborhood(bestSolution, vehicleNumber, i, j);
                neighborhood.nOptSwap();
                if(!checkIfTabuListContains(tabuList, neighborhood))
                    neighborhoods.put(neighborhood, neighborhood.fitness());
            }
        }


        /*for (int i = 1; i <  route.getClients().size()-2; i++) {
            for (int j = i + 1; j <  route.getClients().size() - 1; j++) {
                LocalExchangeNeighborhood neighborhood = new LocalExchangeNeighborhood(bestSolution, vehicleNumber, i, j);
                neighborhood.localExchange();
                if(!checkIfTabuListContains(tabuList, neighborhood))
                    neighborhoods.put(neighborhood, neighborhood.fitness());
            }
        }*/
    // }
/*
    private static void generateInterNeighborhoods(List<Vehicle> bestSolution, HashSet<NeighborhoodGenerator> tabuList, HashMap<NeighborhoodGenerator, Double> neighborhoods, int firstVehicleNumber, int secondVehicleNumber, Route firstRoute, Route secondRoute) {
        for(int i = 1; i < firstRoute.getClients().size()-1; i++) {
            for(int j = 1; j < secondRoute.getClients().size()-1; i++) {
                Relocate relocate = new Relocate(bestSolution, i, j, firstVehicleNumber, secondVehicleNumber);
                relocate.relocate();
            }
        }
    }

    private static boolean checkIfTabuListContains(HashMap<NeighborhoodGenerator, Double> tabuList, NeighborhoodGenerator neighborhood) {
        boolean isEquals = false;
        for(NeighborhoodGenerator object: tabuList.keySet()) {
            if(object.compareTo(neighborhood) == 0) {
                isEquals = true;
            }
        }
        return isEquals;
    }
    */


}
