package Components;

import java.util.*;

public class Route implements Cloneable {
    private List<Client> clients;
    private List<Integer> clientsXValues;
    private List<Integer> clientsYValues;

    public Route() {
        clients = new ArrayList<>();
        clientsXValues = new ArrayList<>();
        clientsYValues = new ArrayList<>();
    }

    public Route(List<Client> clients) {
        this.clients = clients;
        clientsXValues = new ArrayList<>();
        clientsYValues = new ArrayList<>();
        for (Client client : clients) {
            clientsXValues.add(client.getX());
            clientsYValues.add(client.getY());
        }
    }

    public Route(List<Client> clients, List<Integer> clientsXValues, List<Integer> clientsYValues) {
        this.clients = clients;
        this.clientsXValues = clientsXValues;
        this.clientsYValues = clientsYValues;
    }

    public void addClient(Client client) {
        clients.add(client);
        clientsXValues.add(client.getX());
        clientsYValues.add(client.getY());
    }

    public void addClient(Client client, int idx) {
        clients.add(idx, client);
        clientsXValues.add(idx, client.getX());
        clientsYValues.add(idx, client.getY());
    }

    public boolean removeClient(Client client) {
        if (clients.contains(client)) {
            int idx = clients.indexOf(client);
            clients.remove(client);
            clientsXValues.remove(idx);
            clientsYValues.remove(idx);
            return true;
        }
        return false;
    }

    public boolean removeClient(int idx) {
        if (idx < clients.size()) {
            clients.remove(idx);
            clientsXValues.remove(idx);
            clientsYValues.remove(idx);
            return true;
        }
        return false;
    }

    public boolean setClient(Client client, int idx) {
        if (idx < clients.size()) {
            clients.set(idx, client);
            clientsXValues.set(idx, client.getX());
            clientsYValues.set(idx, client.getY());
            return true;
        }
        return false;
    }

    public double distanceLength() {
        double dist = 0;
        for (int i = 0; i < clients.size() - 1; i++) {
            dist += Client.calcDist(clients.get(i), clients.get(i + 1));
        }
        return dist;
    }

    public Set<Route> localExchange() {
        Set<Route> neighborhoods = new HashSet<>();
        List<Client> neighborhood;
        for (int i = 1; i < clients.size() - 2; i++) {
            for (int j = i + 1; j < clients.size() - 1; j++) {
                neighborhood = new ArrayList<>();
                neighborhood.addAll(clients);
                Client tmpClient = neighborhood.get(i);
                neighborhood.set(i, neighborhood.get(j));
                neighborhood.set(j, tmpClient);
                neighborhoods.add(new Route(neighborhood));
            }
        }
        return neighborhoods;
    }


    /**
     * TODO
     *
     * @return
     */
    public Set<Route> relocate() {
        Set<Route> neighborhoods = new HashSet<>();
        List<Client> neighborhood;
        for (int i = 1; i < clients.size() - 2; i++) {
            for (int j = i + 1; j < clients.size() - 1; j++) {
                neighborhood = new ArrayList<>();
                neighborhood.addAll(clients);
                Client tmpClient = neighborhood.remove(j);
                neighborhood.add(i, tmpClient);
                neighborhoods.add(new Route(neighborhood));
            }
        }
        return neighborhoods;
    }

    public List<Client> inversion() {
        int idx = (int) Math.random() * clients.size();
        return null;
    }

    public List<Client> getClients() {
        return clients;
    }

    public String toString() {
        return clients.toString();
    }

    public List<Integer> getClientsXPos() {
        return clientsXValues;
    }

    public List<Integer> getClientsYPos() {
        return clientsYValues;
    }

    @Override
    public Route clone() {
        List<Client> clientList = new ArrayList<>(this.clients);
        List<Integer> clientXList = new ArrayList<>(clientsXValues);
        List<Integer> clientYList = new ArrayList<>(clientsYValues);
        Route clone = new Route(clientList, clientXList, clientYList);
        return clone;
    }
}
