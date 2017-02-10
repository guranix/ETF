package dbService.DataSets;

/**
 * Created by guran on 2/7/17.
 */
public class Etf {

    private long id;
    private String name;
    private String ticker;
    private String description;
    private String top10Holdings;
    private String countryWeight;
    private String sectorWeight;

    public Etf(long id, String name, String ticker, String description, String top10Holdings, String countryWeight, String sectorWeight) {
        this.id = id;
        this.name = name;
        this.ticker = ticker;
        this.description = description;
        this.top10Holdings = top10Holdings;
        this.countryWeight = countryWeight;
        this.sectorWeight = sectorWeight;
    }

    public Etf(String name, String ticker, String description, String top10Holdings, String countryWeight, String sectorWeight) {
        this(-1, name, ticker, description, top10Holdings, countryWeight, sectorWeight);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getTicker() {
        return ticker;
    }

    public String getDescription() {
        return description;
    }

    public String getTop10Holdings() {
        return top10Holdings;
    }

    public String getCountryWeight() {
        return countryWeight;
    }

    public String getSectorWeight() {
        return sectorWeight;
    }

    @Override
    public String toString() {
        return "Etf{" +
                "name='" + name + '\'' +
                ", ticker='" + ticker + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
