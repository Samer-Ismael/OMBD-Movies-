import org.json.JSONArray;

import java.util.Scanner;

public class MainMenu {
    public MainMenu() {

        DatabaseHandler db = new DatabaseHandler();
        OMBD omdb1 = new OMBD();

        //db.deleteAllMovies();
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Do you want to search by: ");
            System.out.println("1. Title");
            System.out.println("2. Year");
            int choice = scanner.nextInt();
            if (choice == 1) {
                Scanner scan1 = new Scanner(System.in);
                System.out.println("Enter title: ");
                String title = scan1.nextLine().toLowerCase();
                if (!db.getMovieByTitle(title)) {
                    System.out.println("Movie not found in database, searching online...");
                    omdb1.getDataByTitle(omdb1.getApiKey(), title);
                    db.addMovieToDatabase(omdb1.getTitle().toLowerCase(), omdb1.getYear(), omdb1.getRated(), omdb1.getReleased());
                    omdb1.printMovie();
                }
            } else if (choice == 2) {
                Scanner scan2 = new Scanner(System.in);
                System.out.println("Enter year: ");
                String year = scan2.nextLine();
                if (!db.getMovieByYear(year)) {
                    System.out.println("Movie not found in database, searching online...");
                    JSONArray jArray = omdb1.getDataByYear(omdb1.getApiKey(), year);
                    System.out.println(jArray.toString());
                    //db.addMovieToDatabase(omdb1.getTitle().toLowerCase(), omdb1.getYear(), omdb1.getRated(), omdb1.getReleased());

                }
            } else {
                System.out.println("Wrong input!");
                System.exit(0);
            }
        } catch (Exception e) {
            System.out.println("Wrong input!");
            System.out.println(e.getMessage());
            System.exit(0);
        }
    }
}
