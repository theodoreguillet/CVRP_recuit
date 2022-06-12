package Recuit;


import Components.Client;
import Components.Route;
import Components.Vehicle;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import Graphics.InterfaceGraphique;

public class App {

    static final int qMax = 100;
    static final float percentageToFill = (float)1;

    public static List<Client> getInputData(String filePath) {
        List<Client> clientList = new ArrayList<>();
        try {
            File myObj = new File(filePath);
            Scanner myReader = new Scanner(myObj);
            String line = myReader.nextLine();
            while (myReader.hasNextLine()) {
                line = myReader.nextLine();
                String[] data = line.split(";");
                clientList.add(new Client(Integer.parseInt(data[0]), Integer.parseInt(data[1]), Integer.parseInt(data[2]), Integer.parseInt(data[3])));
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return clientList;
    }

    /**
     * Return the minimal number of full vehicules that need to be used to deliver
     * @param clients, clients list
     * @return the minimal number of full vehicules
     */
    public static int getMinNumberOfFullVeh(List<Client> clients) {
        int totalQ = 0;
        for (Client client:clients) {
            totalQ += client.getQ();
        }
        System.out.println("totalQ : "+totalQ);
        double dMinNumber = totalQ/ (double) Vehicle.getQMax();
        int iMinNumber = (int) dMinNumber;
        if(dMinNumber%iMinNumber != 0) {
            iMinNumber++;
        }
        System.out.print("iMinNumber = "+iMinNumber);
        return iMinNumber;
    }

    /**
     * Set a random route for a vehicule, by choosing a random list of clients to visit, and by filling the vehicule to half
     * @param clients
     * @param vehicle
     * @param deposit
     */
    public static void setRandomRoute(List<Client> clients, Vehicle vehicle, Client deposit, double dPercentageToFill) {
        Set<Client> visitedClients = new HashSet<>();
        Client client;
        Random random = new Random();
        int idx;
        int nbrTotalClients = clients.size();
        vehicle.addClient(deposit);
        while(vehicle.fitsMore((int) (Vehicle.getQMax()*dPercentageToFill)) && visitedClients.size() != nbrTotalClients) {
            idx = random.nextInt(clients.size());
            client = clients.get(idx);
            if(visitedClients.add(client)) {
                if (vehicle.addClient(client)) clients.remove(client);
            }
        }
        vehicle.addClient(deposit);
    }

    /**
     * Generate a random solution
     * @param clientsList the list of the client
     * @param minNumVeh the minimum number of vehicule to use
     * @param deposit object of type Client, represent the deposit
     * @return List of vehicle objects, every vehicle contains the route that has to take
     */
    public static List<Vehicle> generateRandomSolution(List<Client> clientsList, int minNumVeh, Client deposit){
        // Number of vehicle to use
        int numVeh = (int)(minNumVeh * (1/percentageToFill));
        List<Vehicle> vehicules = new ArrayList<>();
        List<Client> clients = new ArrayList<>(clientsList);
        for(int i = 0; i < numVeh; i++) {
            Vehicle vehicle = new Vehicle(percentageToFill);
            setRandomRoute(clients, vehicle, deposit, percentageToFill);
            vehicules.add(vehicle);
        }
        System.out.println("Number of vehicles to use : "+numVeh+"\n------------------");
        return vehicules;
    }

    public static void main(String[] args) {
        List<Client> clients = App.getInputData("src/main/resources/A3205.txt");

        Client deposit = clients.get(0);
        clients.remove(0);
        System.out.println("Client : "+clients);
        int minVeh = getMinNumberOfFullVeh(clients);

        List<Vehicle> vehicules = App.generateRandomSolution(clients, minVeh, deposit);

        float totalDist = 0;
        double vehDist;

        for(int i = 0; i < vehicules.size(); i++) {
         //   System.out.println("Vehicle "+i+" : ");
         //   System.out.println(vehicules.get(i).toString());
            vehDist = vehicules.get(i).traveledDist();
            System.out.println("Vehicle number "+i+" traveled distance : "+ vehicules.get(i).traveledDist());
            System.out.println("Vehicle number "+i+" fitness : "+ vehicules.get(i).getFitness());
         //   System.out.println("Traveled Distance : "+vehDist);
         //   System.out.println("Filled Quantity : "+vehicules.get(i).getQ()+"\n---------------");
            totalDist += vehDist;
        }

       // System.out.println("Position of the deposit : " +deposit.toString());
        System.out.println("\nVehicles : "+vehicules);

        System.out.println("total traveled distance = "+totalDist);

       // System.out.print("\n----------------------------\nNumber of vehicles : "+vehicules.size());
        //System.out.println("\nVehicles : "+vehicules);
        List<Route> oldRoutes = new ArrayList<>();
        for(Vehicle vehicle : vehicules) {
            vehicle.setPercentageToFill(1);
            oldRoutes.add(vehicle.getRoute());
        }


        // TESTING RECUIT
        List<Route> routes = new ArrayList<>();

        List<Vehicle> newSolution = Algo.recuit(clients, vehicules, totalDist, 0.75f, 0.00001f, 0.999f, 10000);
        for(Vehicle vehicle : newSolution) {
            routes.add(vehicle.getRoute());
        }

        double totalDistRes = 0;

       for(int i = 0; i < newSolution.size(); i++) {
            //System.out.println("Vehicle "+i+" : ");
            //System.out.println(newSolution.get(i).toString());
            vehDist = newSolution.get(i).traveledDist();
            //System.out.println("Vehicle number "+i+" traveled distance : "+ newSolution.get(i).traveledDist());
            //System.out.println("Vehicle number "+i+" fitness : "+ newSolution.get(i).getFitness());

           //System.out.println("Traveled Distance : "+vehDist);
            //System.out.println("Filled Quantity : "+newSolution.get(i).getQ()+"\n---------------");
           totalDistRes += vehDist;
       }

        //System.out.println("Position of the deposit : " +deposit.toString());

       System.out.println("total traveled distance = " + totalDistRes);
       System.out.println("\nNew Solution : "+newSolution);
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                InterfaceGraphique.createAndShowGui(oldRoutes);
            }
        });
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                InterfaceGraphique.createAndShowGui(routes);
            }
       });



    }

}
