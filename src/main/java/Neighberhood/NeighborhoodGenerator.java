package Neighberhood;


import Components.Vehicle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class NeighborhoodGenerator {
    protected List<Vehicle> solution;
    protected List<Vehicle> neighborhood;
    protected float fitness;

    // TODO : verify if we can clone the list of solution
    public NeighborhoodGenerator(List<Vehicle> solution, float fitness) {
        this.solution = solution;
        this.fitness = fitness;
        neighborhood = new ArrayList<>(solution);
    }

    public List<Vehicle> getNeighborhood(){
        return neighborhood;
    }
    public abstract void calculateNeighborhood();

    public float fitness(){
        return this.fitness;
    }

    public float getFitness(){
        return fitness;
    }
}
