import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

public class OMBD {

    String apiKey;
    String title;
    String year;
    String rated;
    String released;

    private OMBD(String title, String year, String rated, String released) {
        this.title = title;
        this.year = year;
        this.rated = rated;
        this.released = released;
    }

    public OMBD() {

        getAPIKey();
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getRated() {
        return rated;
    }

    public void setRated(String rated) {
        this.rated = rated;
    }

    public String getReleased() {
        return released;
    }

    public void setReleased(String released) {
        this.released = released;
    }

    private void getAPIKey() {
        String userHome = System.getProperty("user.home");

        Properties props = new Properties();
        try {
            FileInputStream input = new FileInputStream(userHome + "/Documents/APIKeys/OMDB.txt");
            props.load(input);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        this.apiKey = props.getProperty("apiKey");
    }

    public void getData(String apiKey, String movieTitle) {
        String APIUrl = "http://www.omdbapi.com/?apikey=" + apiKey + "&t=" + movieTitle;
        StringBuffer answer = null;
        try {

            URL url = new URL(APIUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            answer = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                answer.append(inputLine);
            }
            in.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        JSONObject movie = new JSONObject(answer.toString());
        String title = movie.getString("Title");
        String year = movie.getString("Year");
        String rated = movie.getString("Rated");
        String released = movie.getString("Released");

        this.title = title;
        this.year = year;
        this.rated = rated;
        this.released = released;
    }

    public void printMovie() {
        System.out.println("Title: " + getTitle());
        System.out.println("Year: " + getYear());
        System.out.println("Rated: " + getRated());
        System.out.println("Released: " + getReleased());
    }

}
