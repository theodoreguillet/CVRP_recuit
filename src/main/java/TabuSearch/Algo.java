package TabuSearch;

import Components.Client;
import Components.Vehicle;
import Neighberhood.Inter.CrossExchange;
import Neighberhood.Inter.Exchange;
import Neighberhood.Inter.Relocate;
import Neighberhood.Intra.LocalExchange;
import Neighberhood.Intra.OptNeighborhood;
import Neighberhood.NeighborhoodGenerator;

import java.util.*;

public class Algo {
    public static List<Vehicle> recuit(List<Client> clients, List<Vehicle> initialSolution, float initialFitness, float temperature, float cooldown, int nitcooldown) {

        List<Vehicle> minSolution = (ArrayList<Vehicle>) ((ArrayList<Vehicle>) initialSolution).clone();
        float minFitness = initialFitness;

        List<Vehicle> currentSolution = (ArrayList<Vehicle>) (((ArrayList<Vehicle>) initialSolution).clone());

        float currentFitness = initialFitness;

        int ritcooldown = nitcooldown;

        while (temperature > 0.0001) {
            List<NeighborhoodGenerator> neighbors = new ArrayList<NeighborhoodGenerator>();

            //Generate neighborhoods and choose a random one
            for (int cptVehicule = 0; cptVehicule < currentSolution.size(); cptVehicule++) {
                if (cptVehicule <= currentSolution.size() - 2) {
                    for (int secondCptVehicle = cptVehicule + 1; secondCptVehicle < currentSolution.size(); secondCptVehicle++) {
                        for (int firstIdx = 1; firstIdx < currentSolution.get(cptVehicule).getRoute().getClients().size() - 1; firstIdx++) {
                            for (int secondIdx = 1; secondIdx < currentSolution.get(secondCptVehicle).getRoute().getClients().size() - 1; secondIdx++) {

                                CrossExchange crossExchange = new CrossExchange(currentSolution, currentFitness, firstIdx, secondIdx, cptVehicule, secondCptVehicle);
                                crossExchange.calculateNeighborhood();
                                if (crossExchange.isGenerationSuccessed()) {
                                    neighbors.add(crossExchange);
                                }
                                /*
                                Relocate relocate = new Relocate(currentSolution, currentFitness, firstIdx, secondIdx, cptVehicule, secondCptVehicle);
                                relocate.calculateNeighborhood();
                                if (relocate.isGenerationSuccessed()) {
                                    neighbors.add(relocate);
                                }


                                Exchange exchange = new Exchange(currentSolution, currentFitness, firstIdx, secondIdx, cptVehicule, secondCptVehicle);
                                exchange.calculateNeighborhood();
                                if (exchange.isGenerationSuccessed()) {
                                    neighbors.add(exchange);
                                }
                                */
                            }
                        }
                    }
                }
                /*
                for (int firstIdx = 1; firstIdx < currentSolution.get(cptVehicule).getRoute().getClients().size() - 2; firstIdx++) {
                    for (int secondIdx = firstIdx + 1; secondIdx < currentSolution.get(cptVehicule).getRoute().getClients().size() - 1; secondIdx++) {
                        // Neighberhood.Intra.Relocate opt = new Neighberhood.Intra.Relocate(currentSolution, currentFitness, cptVehicule, firstIdx, secondIdx);
                        LocalExchange localExchange = new LocalExchange(currentSolution, currentFitness, cptVehicule, firstIdx, secondIdx);
                        localExchange.calculateNeighborhood();
                        neighbors.add(localExchange);

                        //
                        Neighberhood.Intra.Relocate relocate = new Neighberhood.Intra.Relocate(currentSolution, currentFitness, cptVehicule, firstIdx, secondIdx);
                        relocate.calculateNeighborhood();
                        neighbors.add(relocate);

                        OptNeighborhood opt = new OptNeighborhood(currentSolution, currentFitness, cptVehicule, firstIdx, secondIdx);
                        opt.calculateNeighborhood();
                        neighbors.add(opt);
                    }
                }
                */
            }

            NeighborhoodGenerator neighbor = neighbors.get((int)Math.floor(Math.random() * neighbors.size()));

            double delta = minFitness - neighbor.getFitness();
            if (delta > 0 || Math.random() < Math.exp(delta / temperature)) {
                minSolution = neighbor.getNeighborhood();
                minFitness = neighbor.getFitness();
            }

            if(ritcooldown <= 0) {
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
