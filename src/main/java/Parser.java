
import org.jsoup.Jsoup;

        import java.io.IOException;
        import java.net.URL;
        import  org.jsoup.nodes.Document;
        import  org.jsoup.nodes.Element;
        import org.jsoup.select.Elements;

import java.util.ArrayList;

public class Parser {


    private  Document getPage(String url) throws IOException {
        Document page =  Jsoup.parse(new URL("https:"+url),3000);
        return page;
    }

    public ArrayList<String>  dates(Document page){
        ArrayList<String> dates = new ArrayList<>();
        Elements divWth = page.select("div[class= weather__content_tabs clearfix]"); //tabs
        Elements names = divWth.select("div[class=weather__content_tab current],div[class=weather__content_tab], div [class=weather__content_tab current dateFree],div[class=weather__content_tab dateFree]");
        for (Element name :names){
            dates.add(name.select("p[class=weather__content_tab-day],p[class=weather__content_tab-date day_red],p[class=weather__content_tab-month]").text());
        }
        return dates;
    }

    public   ArrayList<String> weeklyWeather(Document page){
        Elements divWth = page.select("div[class= weather__content_tabs clearfix]"); //tabs
        Elements names = divWth.select("div[class=weather__content_tab current],div[class=weather__content_tab], div [class=weather__content_tab current dateFree],div[class=weather__content_tab dateFree]");
       ArrayList<String> weeklyWth = new ArrayList<>();

        //pogoda na tydzień
        for (Element name :names){

            String date = name.select("p[class=weather__content_tab-day],p[class=weather__content_tab-date day_red],p[class=weather__content_tab-month]").text();
            String minTemp =name.select("div[class=min]").select("b").text();
            String grafic = name.select("label").text();
            String maxTemp =name.select("div[class=weather__content_tab-temperature]").select("b").last().text();//nie ma div w temp na max w pierwszym dniu

            weeklyWth.add(date+selectEmoji(grafic)+"\nMin "+minTemp+" Max "+maxTemp+"\n"+ grafic+"\n\n");

            System.out.println(date+"\nMin "+minTemp+" Max "+maxTemp+"\n");

        }
        return weeklyWth;
    }

    private String selectEmoji(String graficLabel){
        String emoji="";
        if (graficLabel.toLowerCase().contains("burze")&&graficLabel.contains("deszcz")){
            return "⛈";
        }
        if (graficLabel.toLowerCase().contains("burze")){
            return "🌩";
        }
        if (graficLabel.toLowerCase().contains("deszcz")){
        return "🌧";
    }
        if (graficLabel.toLowerCase().contains("zachmurzenie zmienne")){
            return "⛅️";
        }
        if (graficLabel.toLowerCase().contains("zachmurzenie całkowite")){
            return "☁️️";
        }
        if (graficLabel.toLowerCase().contains("zachmurzenie")){
            return "🌥";
        }
        if (graficLabel.toLowerCase().contains("pogodnie")){
            return "☀️";
        }


        return emoji;
    }

    public  ArrayList<String> dailyWeather(Document page) {
        //glówna informacja
        ArrayList<String> arrayDaily = new ArrayList<>();
        Elements divArtWth = page.select("div[class= weather__content_article clearfix]"); //article
        Elements dailyWth=divArtWth.select("ul[class=weather__article_main_right-table clearfix]").select("li");

        arrayDaily.add(page.select("div[class= weather__content_tabs clearfix]").select("div[class=weather__content_tab current],div[class=weather__content_tab current dateFree]").select("p[class=weather__content_tab-day],p[class=weather__content_tab-date day_red],p[class=weather__content_tab-month]").text()+"\n\n");

        for (Element daily :dailyWth) {
            String sky = daily.select("div[class=table__time_img]").select("label").text();

            arrayDaily.add("Godzina : "+daily.select("div[class=table__time_hours]").text()+selectEmoji(sky)+"\n"
            +"Temp. : "+daily.select("div[class=table__temp]").text()+"\n"
            +"Ciśnienie : "+daily.select("div[class=table__pressure]").text()+"\n"
            +"Wilgotność : "+daily.select("div[class=table__humidity]").text()+"\n"
            +"Wiatr : "+daily.select("div[class=table__wind wind-NW],div[class=table__wind wind-sW],div[class=table__wind wind-W],div[class=table__wind wind-S],div[class=table__wind wind-SE],div[class=table__wind wind-W],div[class=table__wind wind-N],div[class=table__wind wind-NE]").select("span").text()+"\n"
            +sky+"\n\n");
        }
        return arrayDaily;
    }

    public  String actualTemp(Document page) {
        String actualTmp;
        Elements divArtWth = page.select("div[class= weather__content_article clearfix]");
        actualTmp="Aktualna temperatura : "+divArtWth.select("div[class=weather__article_main_temp]").text();
        return actualTmp;
    }
    public  String sunsetInfo(Document page) {
        String sunsetInf ;
        Elements divArtWth = page.select("div[class= weather__content_article clearfix]");
        sunsetInf=("SunsetInfo : "+divArtWth.select("div[class=ss_wrap pl]").text());
        return sunsetInf;
    }

    public ArrayList<String> getOtherDaysUrl(Document page,String url){
        Elements names = page.select("div[class=weather__content_tab],div[class=weather__content_tab dateFree]").select("a");
        ArrayList<String> urlDay = new ArrayList<>();
        urlDay.add(url);
        for (Element name : names){
            urlDay.add(name.attributes().toString().substring(37).replace("\"",""));
         //   System.out.println(urlDay);
        }
        return urlDay;
    }



}