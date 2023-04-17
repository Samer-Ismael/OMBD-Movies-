import java.util.Scanner;

public class MainMenu {
    public MainMenu() {

        DatabaseHandler db = new DatabaseHandler();
        OMBD omdb1 = new OMBD();

        System.out.println("Do you have an API key? (y/n)");
        Scanner scanner1 = new Scanner(System.in);
        if (scanner1.nextLine().equalsIgnoreCase("y")) {
            System.out.println("Enter your API key: ");
            omdb1.setApiKey(scanner1.nextLine());
        } else {
            System.out.println("You can get an API key from http://www.omdbapi.com/");
            System.out.println("Enter your API key: ");
            omdb1.setApiKey(scanner1.nextLine());
        }

        System.out.println("Enter a movie title: ");
        Scanner scanner = new Scanner(System.in);
        String title = scanner.nextLine().toLowerCase();

        if (!db.getMovieFromDatabase(title)) {
            System.out.println("Movie not found in database, searching online...");
            omdb1.getData(omdb1.getApiKey(), title);
            db.addMovieToDatabase(omdb1.getTitle().toLowerCase(), omdb1.getYear(), omdb1.getRated(), omdb1.getReleased());
            omdb1.printMovie();
        }
    }
}
