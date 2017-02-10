package service;

import com.google.gson.Gson;
import dbService.DataSets.Etf;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by guran on 2/8/17.
 */
public class ETFParser {

    public static List<String> getGlobalEquities() throws IOException {
        Document doc = Jsoup.connect("http://spdrs.com").get();
        Element allETFs = doc.getElementById("menu_ETFs");
        Elements globalEquitiesList = allETFs.child(1).child(0).child(4).child(1).getElementsByTag("a");
        List<String> names1 = globalEquitiesList.stream().map(Element::text).collect(Collectors.toList());

        Document doc2 = Jsoup.connect("https://www.spdrs.com/product/fund.seam").get();
        Element allETFs2 = doc2.getElementById("performancePanel");
        Elements globalEquitiesList2 = allETFs2.child(3).getElementsByAttributeValue("class", "fund");
        List<String> tickers1 = globalEquitiesList2.stream().map(n->n.child(1).text()).collect(Collectors.toList());

        List<String> result1 = new ArrayList<>();
        if (names1.size() == tickers1.size()){
            for (int i = 0; i < names1.size(); i++){
                result1.add("<tr><td><a href=\"etf?ticker=" + tickers1.get(i) + "\">" + names1.get(i) + "</a></td><tr>");
            }
        }
        return  result1;
    }



    public static List<String> getUSEquities() throws IOException {

        Document doc = Jsoup.connect("http://spdrs.com").get();
        Element allETFs = doc.getElementById("menu_ETFs");
        Elements globalEquitiesList = allETFs.child(1).child(0).child(6).child(1).getElementsByTag("a");
        List<String> names2 =  globalEquitiesList.stream().map(Element::text).collect(Collectors.toList());

        Document doc2 = Jsoup.connect("https://www.spdrs.com/product/fund.seam").get();
        Element allETFs2 = doc2.getElementById("performancePanel");
        Elements usEquitiesList2 = allETFs2.child(5).getElementsByAttributeValue("class", "fund");
        List<String> tickers2 =  usEquitiesList2.stream().map(n->n.child(1).text()).collect(Collectors.toList());

        List<String> result2 = new ArrayList<>();
        if (names2.size() == tickers2.size()){
            for (int i = 0; i < names2.size(); i++){
                result2.add("<tr><td><a href=\"etf?ticker=" + tickers2.get(i) + "\">" + names2.get(i) + "</a></td></tr>");
            }
        }
        return result2;
    }


    public Etf getEtf(String ticker) throws IOException {
        Document doc = Jsoup.connect("https://us.spdrs.com/en/product/fund.seam").data("ticker", ticker).method(Connection.Method.GET).get();

        // parsing fund name
        String name = doc.select("h1").text();

        // parsing description
        String description = doc.getElementsByClass("objective").first().text();

        //parsing country weights
        List<CountryWeight> countryWeightsList = new ArrayList<>();
        doc.getElementById("detail").child(6).child(12).getElementsByTag("tr").
                forEach(n-> {
                    String value = n.getElementsByClass("data").text();
                    value = value.replace("%", "");
                    countryWeightsList.add(new CountryWeight(n.getElementsByClass("label").text(), Double.parseDouble(value)));
                });
        String countryWeights = new Gson().toJson(countryWeightsList);


        // parsing country weights
        List<SectorWeight> sectorWeightsList = new ArrayList<>();
        String sectorWeightsXmlString = doc.getElementById("SectorsAllocChart").text();
        Elements elements = Jsoup.parse(sectorWeightsXmlString).getElementsByTag("attribute");
        elements.forEach(n -> sectorWeightsList.add(new SectorWeight(n.getElementsByTag("label").text(), Double.parseDouble(n.getElementsByTag("rawvalue").text()))));
        String sectorWeights = new Gson().toJson(sectorWeightsList);

        // parsing top 10 holdings
        List<HoldingWeight> top10HoldingsList = new ArrayList<>();
        doc.getElementById("FUND_TOP_TEN_HOLDINGS").getElementsByTag("tr").
                forEach(n-> {
                    String value = n.child(1).text();
                    if (!value.equals("Weight")){
                        value = value.replace(" %", "");
                        top10HoldingsList.add(new HoldingWeight(n.getElementsByClass("label").text(), Double.parseDouble(value)));
                    }
                });

        String top10Holdings = new Gson().toJson(top10HoldingsList);

//        System.out.println("Top 10 Holdings: " + top10Holdings);
//        System.out.println("Country Weights: " + countryWeights);
//        System.out.println("Sector Weights: " + sectorWeights);
//        System.out.println("Name: " + name);
//        System.out.println("Description: " + description);

        return new Etf(name,ticker, description, top10Holdings,countryWeights, sectorWeights);

    }
}