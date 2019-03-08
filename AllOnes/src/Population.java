class Population {

    Individual[] individuals;

    Population(int populationSize) {
        individuals = new Individual[populationSize];
    }

    int size() {
        return  individuals.length;
    }

    public void initialize() {

    }

    public Individual[] getIndividuals() {
        return null;
    }

}
