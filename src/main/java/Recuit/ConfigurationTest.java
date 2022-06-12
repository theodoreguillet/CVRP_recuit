package Recuit;

import Components.Client;
import Components.Route;
import Components.Vehicle;
import javafx.util.Pair;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static Recuit.App.getMinNumberOfFullVeh;

public class ConfigurationTest {

    public static void main(String[] args) throws FileNotFoundException {
        String fileName = "A3906";
        List<Client> clients = App.getInputData("src/main/resources/" + fileName + ".txt");

        Client deposit = clients.get(0);
        clients.remove(0);
        System.out.println("Clients : " + clients);
        int minVeh = getMinNumberOfFullVeh(clients);
        File file = new File("src/main/resources/results/Neighborhood/" + fileName + ".csv");
        // Instantiating the PrintStream class
        PrintStream stream = new PrintStream(file);

        List<Vehicle> vehicles = App.generateRandomSolution(clients, minVeh, deposit);
        float totalDist = 0;
        for (int cpt = 0; cpt < vehicles.size(); cpt++) {
            totalDist += vehicles.get(cpt).traveledDist();
            vehicles.get(cpt).setPercentageToFill(1);
        }

        /**
         * Grid search
         */
        float[] temperatureValues = { 0.1f, 0.25f, 0.5f, 0.75f, 1f };
        // float[] temperatureValues = { 0.75f };
        float[] temperatureMinValues = { 0.00001f, 0.0001f, 0.001f, 0.01f, 0.1f };
        // float[] temperatureMinValues = { 0.0001f };
        float[] cooldownValues = { 0.9999f, 0.999f, 0.99f, 0.9f, 0.5f };
        // float[] cooldownValues = { 0.999f };
        int[] nitcooldownValues = { 1, 100, 1000, 10000, 100000 };
        // int[] nitcooldownValues = { 10000 };

        String line = "F0; F; T; Tmin; C; nC; execTime";
        System.out.println(line);
        stream.println(line);

        for(float temperature : temperatureValues) {
            for(float temperatureMin : temperatureMinValues) {
                for(float cooldown : cooldownValues) {
                    for(int nitcooldown : nitcooldownValues) {
                        long start = System.currentTimeMillis();
                        List<Vehicle> solution = Algo.recuit(clients, vehicles, totalDist, 0.75f, 0.00001f, 0.999f, 10000);
                        long end = System.currentTimeMillis();
                        double sec = (end - start) / 1000F;

                        double totalDistRes = 0;
                        for (int cpt = 0; cpt < solution.size(); cpt++) {
                            totalDistRes += solution.get(cpt).traveledDist();
                            vehicles.get(cpt).setPercentageToFill(1);
                        }

                        line = totalDist + ";" + totalDistRes + ";" + temperature + ";" + temperatureMin + ";" + cooldown + ";" + nitcooldown + ";" + sec;
                        System.out.println(line);
                        stream.println(line);
                    }
                }
            }
        }
    }

}
