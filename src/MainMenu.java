import java.util.Scanner;

public class MainMenu {
    public MainMenu() {

        DatabaseHandler db = new DatabaseHandler();
        OMBD omdb1 = new OMBD();

        try {
            System.out.println("Enter a movie title: ");
            Scanner scanner = new Scanner(System.in);
            String title = scanner.nextLine().toLowerCase();

            if (!db.getMovieFromDatabase(title)) {
                System.out.println("Movie not found in database, searching online...");
                omdb1.getData(omdb1.getApiKey(), title);
                db.addMovieToDatabase(omdb1.getTitle().toLowerCase(), omdb1.getYear(), omdb1.getRated(), omdb1.getReleased());
                omdb1.printMovie();
            }
        } catch (Exception e) {
            System.out.println("Wrong input!");
            System.out.println(e.getMessage());
            System.exit(0);
        }
    }
}
