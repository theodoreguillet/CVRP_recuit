package Components;

import sun.misc.Cleaner;

public class Vehicle implements Cloneable {
    private static final int qMax = 100;
    private int q;
    private float percentageToFill;
    private Route route;
    // Use a graph structure

    private float fitness;

    public Vehicle(float percentageToFill) {
        this.percentageToFill = percentageToFill;
        route = new Route();
        q = 0;
        fitness = 0;
    }

    public Vehicle(float percentageToFill, int q, Route route, float fitness) {
        this.percentageToFill = percentageToFill;
        this.q = q;
        this.route = route;
        this.fitness = fitness;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public String toString() {
        return route.toString();
    }

    public boolean addClient(Client client) {
        if (client.getQ() + q <= qMax * percentageToFill) {
            route.addClient(client);
            q += client.getQ();
            if (route.getClients().size() > 1)
                fitness += Client.calcDist(route.getClients().get(route.getClients().size() - 2), client);
            return true;
        }
        return false;
    }

    public boolean addClient(Client client, int idx) {
        if (client.getQ() + q <= qMax * percentageToFill) {
            q += client.getQ();
            if (idx == 0)
                fitness = fitness + Client.calcDist(client, route.getClients().get(0));
            else if (idx == route.getClients().size()) {
                fitness = fitness + Client.calcDist(client, route.getClients().get(idx - 1));
            } else {
                fitness = fitness - Client.calcDist(route.getClients().get(idx - 1), route.getClients().get(idx)) + Client.calcDist(client, route.getClients().get(idx - 1)) + Client.calcDist(client, route.getClients().get(idx));
            }
            route.addClient(client, idx);
        }
        return false;
    }


    public boolean removeClient(Client client) {
        if (route.getClients().contains(client)) {
            int idx = route.getClients().indexOf(client);
            if (route.getClients().size() == 1) {
                fitness = 0;
            } else if (idx == route.getClients().size() - 1) {
                fitness = fitness - Client.calcDist(route.getClients().get(idx - 1), client);
            } else if (idx == 0) {
                fitness = fitness - Client.calcDist(route.getClients().get(idx + 1), client);
            } else {
                fitness = fitness - Client.calcDist(route.getClients().get(idx - 1), client) - Client.calcDist(route.getClients().get(idx + 1), client) + Client.calcDist(route.getClients().get(idx + 1), route.getClients().get(idx - 1));
            }
            q = q - client.getQ();
            route.removeClient(client);
            return true;
        }
        return false;
    }

    /*
    public boolean removeClient(Client client) {
        if (route.getClients().contains(client)) {
            int idx = route.getClients().indexOf(client);

            if(idx == 0){
                if(route.getClients().size() == 1)
                    fitness = 0;
                else
                    fitness = fitness - Client.calcDist(route.getClients().get(idx), route.getClients().get(idx + 1));
            }else if(idx == route.getClients().size()-1){
                if(route.getClients().size() == 1)
                    fitness = 0;
                else
                    fitness = fitness - Client.calcDist(route.getClients().get(idx), route.getClients().get(idx - 1));
            } else {
                fitness = fitness - Client.calcDist(route.getClients().get(idx), route.getClients().get(idx - 1)) - Client.calcDist(route.getClients().get(idx), route.getClients().get(idx + 1));
                fitness += Client.calcDist(route.getClients().get(idx + 1), route.getClients().get(idx - 1));
            }
            route.removeClient(client);
            q = q - client.getQ();
            return true;
        }
        return false;
    }

    public boolean removeClient(int idx) {
        if (idx < route.getClients().size()) {
            if(idx == 0){
                fitness = fitness - Client.calcDist(route.getClients().get(idx), route.getClients().get(idx + 1));
            }else if(idx == route.getClients().size()-1){
                fitness = fitness - Client.calcDist(route.getClients().get(idx), route.getClients().get(idx - 1));
            } else {
                fitness = fitness - Client.calcDist(route.getClients().get(idx), route.getClients().get(idx - 1)) - Client.calcDist(route.getClients().get(idx), route.getClients().get(idx + 1));
                fitness += Client.calcDist(route.getClients().get(idx + 1), route.getClients().get(idx - 1));
            }
            q = q - route.getClients().get(idx).getQ();
            route.removeClient(idx);
            return true;
        }
        return false;
    }
*/
    public boolean setClient(Client client, int idx) {
        if (idx < route.getClients().size()) {
            fitness = fitness - Client.calcDist(route.getClients().get(idx), route.getClients().get(idx + 1)) - Client.calcDist(route.getClients().get(idx), route.getClients().get(idx - 1));
            fitness += Client.calcDist(client, route.getClients().get(idx + 1)) + Client.calcDist(client, route.getClients().get(idx - 1));
            route.setClient(client, idx);
            return true;
        }
        return false;
    }

    public void setQ(int q) {
        this.q = q;
    }

    public float getPercentageToFill() {
        return percentageToFill;
    }

    public void setPercentageToFill(float percentageToFill) {
        this.percentageToFill = percentageToFill;
    }

    public float getFitness() {
        return fitness;
    }

    public void setFitness(float fitness) {
        this.fitness = fitness;
    }

    public int getQ() {
        return q;
    }

    public static int getQMax() {
        return qMax;
    }

    public boolean fitsMore(int quantityToFill) {
        return q < quantityToFill && q < qMax;
    }

    public double traveledDist() {
        return route.distanceLength();
    }

    public Route getRoute() {
        return route;
    }

    @Override
    public Vehicle clone() {
        Vehicle clone = new Vehicle(this.percentageToFill, this.q, this.route.clone(), this.fitness);
        return clone;
    }

    public void setPercentageToFill(int percentageToFill) {
        this.percentageToFill = percentageToFill;
    }
}
