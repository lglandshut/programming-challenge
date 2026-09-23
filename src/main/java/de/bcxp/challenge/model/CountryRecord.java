package de.bcxp.challenge.model;

/**
 * Record class for country data
 */
public record CountryRecord(String name, String capital, String accession, long population, long area, long gdp,
                            double hdi, int meps) {

    public double populationDensity() {
        return (double) population / area;
    }
}
