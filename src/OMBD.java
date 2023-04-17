import org.json.JSONObject;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;
import java.util.Scanner;

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
            this.apiKey = props.getProperty("apiKey");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            getAPIFromUser();
        }
    }

    private void getAPIFromUser() {
        try {
            System.out.println("Do you have an API key? (y/n)");
            Scanner scanner1 = new Scanner(System.in);
            if (scanner1.nextLine().equalsIgnoreCase("y")) {
                System.out.println("Enter your API key: ");
                setApiKey(scanner1.nextLine());
                createNewFileForAPI(getApiKey());
            } else {
                System.out.println("You can get an API key from http://www.omdbapi.com/");
                System.out.println("Enter your API key: ");
                setApiKey(scanner1.nextLine());
            }
        } catch (Exception e) {
            System.out.println("Wrong input!");
            System.out.println(e.getMessage());
            System.exit(0);
        }
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

    private void createNewFileForAPI (String key) {
        String userHome = System.getProperty("user.home");

        String folderPath = userHome + "/Documents/APIKeys";
        String fileName = "OMDB.txt";
        String filePosition = folderPath + "/" + fileName;

        String toWrite = "apiKey=" + key;

        File folder = new File(folderPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        File file = new File(filePosition);
        try {
            file.createNewFile();
            System.out.println("File created successfully!");
        } catch (IOException e) {
            System.out.println("An error occurred while creating the file.");
            System.out.println(e.getMessage());
        }

        try {
            FileWriter writer = new FileWriter(file);
            writer.write(toWrite);
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file.");
            System.out.println(e.getMessage());
        }
    }
}
