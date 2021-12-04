package queries;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;
import util.DisplayInterface;
class paginatorContest{
    public void paginate(ResultSet rs){
        ArrayList<String> resultRows = new ArrayList<>();
        try{ 
            do {
                resultRows.add(String.format("|%-15d|%-15s|%-10d|%-20s|%-24s|\n", rs.getInt(1), rs.getString(2),rs.getInt(3), rs.getString(4), rs.getString(5)));
            } while (rs.next());
        }catch(SQLException e){
            System.out.println("Error: " + e.getMessage());
        }
        int maxPages = (resultRows.size() + 9)/10;
        int currentPage = 1;
        Scanner scanner = new Scanner(System.in);
        int endPage = 9;
        if(resultRows.size() < 10)endPage = resultRows.size() - 1;
        printPartialTable(resultRows, 0, endPage);
        System.out.println("Page 1 of " + String.valueOf(maxPages));
        System.out.println("Enter 1 to go to previous page, 2 to go to next page, any other key to exit.");
        while(true){
            int choice = Integer.valueOf(scanner.nextLine());
            if (choice != 1 && choice != 2)
                break;
            else {
                if (choice == 1 && currentPage > 1)
                    currentPage--;
                else if (choice == 2 && currentPage < maxPages)
                    currentPage++;
            }
            System.out.print("\033[H\033[2J");
            System.out.flush();
            int startPage = (currentPage - 1)*10;
            endPage = resultRows.size() - 1;
            if(startPage + 9 < endPage)endPage = startPage + 9;
            printPartialTable(resultRows, startPage,  endPage);
            System.out.println("Page " + String.valueOf(currentPage) + " of " + String.valueOf(maxPages));
            System.out.println("Enter 1 to go to previous page, 2 to go to next page, any other key to exit.");
        }
        scanner.close();
    }
    public void printPartialTable(ArrayList<String> rs, int startRow, int endRow){
        System.out.println("+---------------+---------------+----------+--------------------+------------------------+");
        System.out.println("|ContestID      |Author         |Division  |StartTime           |EndTime                 |");
        System.out.println("+---------------+---------------+----------+--------------------+------------------------+");
        for(int i = startRow; i<=endRow; i++){
            System.out.print(rs.get(i));
        }
        System.out.println("+---------------+---------------+----------+--------------------+------------------------+");
    }
}
public class Contest {
    
    public static void printTable(ResultSet rs) {
        paginatorContest pg = new paginatorContest();
        pg.paginate(rs);
    }

    public static void displayAll(Connection con) {
        ResultSet rs = DisplayInterface.displayTable(con,"Contest");
        if(rs==null)
            return;
        printTable(rs);
    }

    public static void insertContest(Connection con, String tuple) {
        try {
            String[] args = tuple.split(" ");
            String query = " insert into Contest(ContestID,Author,Division,StartTime,EndTime) values (?, ?, ?, ?, ?)";
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setInt(1, Integer.parseInt(args[0]));
            preparedStmt.setString(2, args[1]);
            preparedStmt.setInt(3, Integer.parseInt(args[2]));
            preparedStmt.setString(4, args[3]);
            preparedStmt.setString(5, args[4]);
            preparedStmt.execute();
            System.out.println("Inserted successfully");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            System.out.println("Wrong command\nType \"-h\" to get help");
        }
    }

    public static void updateAuthor(Connection con, String ContestID, String Author) {
        try {
            String query = "update Contest set Author = ? where ContestID = ? ";
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setInt(1, Integer.parseInt(ContestID));
            preparedStmt.setString(2, Author);
            int rs = preparedStmt.executeUpdate();
            if (rs == 0) {
                System.out.println("Update failed!!!");
            } else {
                System.out.println("Updated successfully");
            }
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Wrong command\nType \"-h\" to get help");
        }
    }

    public static void updateDivision(Connection con, String ContestID, String Division) {
        try {
            String query = "update Contest set Division = ? where ContestID = ? ";
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setInt(1, Integer.parseInt(Division));
            preparedStmt.setInt(2, Integer.parseInt(ContestID));
            int rs = preparedStmt.executeUpdate();
            if (rs == 0) {
                System.out.println("Update failed!!!");
            } else {
                System.out.println("Updated successfully");
            }
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Wrong command\nType \"-h\" to get help");
        }
    }

    public static void updateStartTime(Connection con, String ContestID, String StartTime) {
        try {
            String query = "update Contest set StartTime = ? where ContestID = ? ";
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setString(1, StartTime);
            preparedStmt.setInt(2, Integer.parseInt(ContestID));
            int rs = preparedStmt.executeUpdate();
            if (rs == 0) {
                System.out.println("Update failed!!!");
            } else {
                System.out.println("Updated successfully");
            }
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Wrong command\nType \"-h\" to get help");
        }
    }

    public static void updateEndTime(Connection con, String ContestID, String EndTime) {
        try {
            String query = "update Contest set EndTime = ? where ContestID = ? ";
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setString(1, EndTime);
            preparedStmt.setInt(2, Integer.parseInt(ContestID));
            int rs = preparedStmt.executeUpdate();
            if (rs == 0) {
                System.out.println("Update failed!!!");
            } else {
                System.out.println("Updated successfully");
            }
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Wrong command\nType \"-h\" to get help");
        }
    }

    public static void searchByContestId(Connection con, String ContestID) {
        try {
            String query = "select * from Contest where ContestID = ?";
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setInt(1, Integer.parseInt(ContestID));
            ResultSet rs = preparedStmt.executeQuery();
            if (rs.next() == false) {
                System.out.println("No Result from Contest");
            } else {
                printTable(rs);
            }
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Wrong command\nType \"-h\" to get help");
        }
    }

    public static void searchByAuthor(Connection con, String Author) {
        try {
            String query = "select * from Contest where Author = ?";
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setString(1, Author);
            ResultSet rs = preparedStmt.executeQuery();
            if (rs.next() == false) {
                System.out.println("No Result from Contest");
            } else {
                printTable(rs);
            }
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Wrong command\nType \"-h\" to get help");
        }
    }

    public static void searchByDivision(Connection con, String Division, String operator) {
        try {
            String query = "select * from Contest where Division " + operator + " ?";
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setInt(1, Integer.parseInt(Division));
            ResultSet rs = preparedStmt.executeQuery();
            if (rs.next() == false) {
                System.out.println("No Result from Contest");
            } else {
                printTable(rs);
            }
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Wrong command\nType \"-h\" to get help");
        }
    }

    public static void searchByStartTime(Connection con, String StartTime) {
        try {
            String query = "select * from Contest where StartTime = ?";
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setString(1, StartTime);
            ResultSet rs = preparedStmt.executeQuery();
            if (rs.next() == false) {
                System.out.println("No Result from Contest");
            } else {
                printTable(rs);
            }
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Wrong command\nType \"-h\" to get help");
        }
    }

    public static void searchByEndTime(Connection con, String EndTime) {
        try {
            String query = "select * from Contest where EndTime = ?";
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setString(1, EndTime);
            ResultSet rs = preparedStmt.executeQuery();
            if (rs.next() == false) {
                System.out.println("No Result from Contest");
            } else {
                printTable(rs);
            }
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Wrong command\nType \"-h\" to get help");
        }
    }

    public static void deleteContests(Connection con, String id) {
        try {
            String query = "delete from Contest where ContestID=?";
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setInt(1, Integer.parseInt(id));
            int rs = preparedStmt.executeUpdate();
            if (rs == 0) {
                System.out.println("Id " + id + " is not a registered Contest.");
            } else {
                System.out.println("Deleted");

            }
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Wrong command\nType \"-h\" to get help");
        }
    }

    public static void sortContests(Connection con, String sort) {
        try {
            String query = "select * from Contest order by " + sort;
            PreparedStatement preparedStmt = con.prepareStatement(query);
            ResultSet rs = preparedStmt.executeQuery();
            if (rs.next() == false) {
                System.out.println("No Result from Contest");
            } else {
                printTable(rs);
            }
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Wrong command\nType \"-h\" to get help");
        }
    }

}
