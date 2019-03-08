class Population {

    private Individual[] individuals;

    Population(int populationSize) {
        individuals = new Individual[populationSize];
    }

    int size() {
        return  individuals.length;
    }

    void initialize() {

    }

    Individual[] getIndividuals() {
        return null;
    }

}
