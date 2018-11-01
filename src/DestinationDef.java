public enum DestinationDef {
    CONSTANTA("Constanta", 11), IASI("Iasi", 10), TIMISOARA("Timisoara", 9),
    ARAD("Arad", 9), PLOIESTI("Ploiesti", 3), BAIAMARAE("Baia Mare", 11), CRAIOVA("Craiova", 8),
    SUCEAVA("Suceava", 10), TARGUMURES("Targu Mures", 4), DEVA("Deva", 6), ORADEA("Oradea", 7);

    private final String name;
    private final int dist;

    DestinationDef(String name, int dist) {
        this.name = name;
        this.dist = dist;
    }

    public String getName() {
        return name;
    }

    public int getDist() {
        return dist;
    }
}
