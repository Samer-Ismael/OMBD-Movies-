import java.sql.*;
import java.time.LocalDate;

public class DatabaseHandler {

    Connection conn = null;
    String databaseName = "OMDB";

    public DatabaseHandler() {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:" + databaseName + ".db");
            System.out.println("Connected! \nDatabase name is: " + databaseName);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        createTable();
    }

    private void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS movies (\n" + "id integer PRIMARY KEY,\n" + "title varchar(50),\n" + "year varchar(50),\n" + "rated varchar(50),\n" + "released varchar(50),\n" + "date varchar(50)\n" + ");";

        try {
            conn.prepareStatement(sql).executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void addMovieToDatabase(String title, String year, String rated, String released) {
        LocalDate date = LocalDate.now();
        int month = date.getMonthValue();
        try {
            PreparedStatement pStatement = conn.prepareStatement("INSERT INTO movies (title, year, rated, released, date) VALUES (?,?,?,?,?)");
            pStatement.setString(1, title);
            pStatement.setString(2, year);
            pStatement.setString(3, rated);
            pStatement.setString(4, released);
            pStatement.setInt(5, month);

            int result = pStatement.executeUpdate();

            if (result > 0) {
                deleteOldMovies(); // Delete old movies from database
                System.out.println("Done!");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    boolean getMovieFromDatabase(String title) {
        try {
            PreparedStatement pStatement = conn.prepareStatement("SELECT * FROM movies WHERE title = ?");
            pStatement.setString(1, title);
            ResultSet result = pStatement.executeQuery();
            if (result.next()) {
                System.out.println("Title: " + result.getString("title"));
                System.out.println("Year: " + result.getString("year"));
                System.out.println("Rated: " + result.getString("rated"));
                System.out.println("Released: " + result.getString("released"));

                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    private void deleteOldMovies() {
        LocalDate date = LocalDate.now();
        int month = date.getMonthValue();
        try {
            PreparedStatement pStatement = conn.prepareStatement("DELETE FROM movies WHERE date > ?");
            pStatement.setInt(1, month);
            int result = pStatement.executeUpdate();

            if (result > 0) {
                System.out.println("Old movies deleted!");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteAllMovies() {
        try {
            PreparedStatement pStatement = conn.prepareStatement("DELETE FROM movies");
            int result = pStatement.executeUpdate();

            if (result > 0) {
                System.out.println("All movies deleted!");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
