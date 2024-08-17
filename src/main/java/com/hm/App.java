package com.hm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.conpool.ConnectionPool;

/**
 * Hello world!
 *
 */
public class App 
{
	
	public class Food {
	    private int itemno;
	    private int quantity;
	    private float price;

	    public Food(int itemno, int quantity) {
	        this.itemno = itemno;
	        this.quantity = quantity;
	        switch (itemno) {
	            case 1:
	                price = quantity * 50;
	                break;
	            case 2:
	                price = quantity * 60;
	                break;
	            case 3:
	                price = quantity * 70;
	                break;
	            case 4:
	                price = quantity * 30;
	                break;
	            default:
	                price = 0;
	                break;
	        }
	    }

	    public void saveToDatabase(int roomId) {
	        String sql = "INSERT INTO Food (itemno, quantity, price, room_id) VALUES (?, ?, ?, ?)";
	        try (Connection conn = ConnectionPool.giveConnection();
	             PreparedStatement pstmt = conn.prepareStatement(sql)) {

	            pstmt.setInt(1, this.itemno);
	            pstmt.setInt(2, this.quantity);
	            pstmt.setFloat(3, this.price);
	            pstmt.setInt(4, roomId);

	            pstmt.executeUpdate();
	            System.out.println("Food order saved to the database.");

	        } catch (SQLException e) {
	            System.err.println("Error while saving food order to the database: " + e.getMessage());
	        }
	    }

	    // Optionally, you can add methods to retrieve, update, or delete records from the database

	    public int getItemno() {
	        return itemno;
	    }

	    public void setItemno(int itemno) {
	        this.itemno = itemno;
	    }

	    public int getQuantity() {
	        return quantity;
	    }

	    public void setQuantity(int quantity) {
	        this.quantity = quantity;
	    }

	    public float getPrice() {
	        return price;
	    }

	    public void setPrice(float price) {
	        this.price = price;
	    }
	}
	
	public class SingleRoom {
	    private String name;
	    private String contact;
	    private String gender;
	    private List<Food> foodList;

	    public SingleRoom(String name, String contact, String gender) {
	        this.name = name;
	        this.contact = contact;
	        this.gender = gender;
	        this.foodList = new ArrayList<>();
	    }

	    public void saveToDatabase(int roomId) {
	        String sql = "INSERT INTO SingleRoom (room_id, name, contact, gender) VALUES (?, ?, ?, ?)";
	        try (Connection conn = ConnectionPool.giveConnection();
	             PreparedStatement pstmt = conn.prepareStatement(sql)) {

	            pstmt.setInt(1, roomId);
	            pstmt.setString(2, this.name);
	            pstmt.setString(3, this.contact);
	            pstmt.setString(4, this.gender);

	            pstmt.executeUpdate();
	            System.out.println("Single room data saved to the database.");

	            // Save associated food orders
	            for (Food food : foodList) {
	                food.saveToDatabase(roomId);
	            }

	        } catch (SQLException e) {
	            System.err.println("Error while saving single room data to the database: " + e.getMessage());
	        }
	    }

	    public void addFood(Food food) {
	        this.foodList.add(food);
	    }

	    // Optionally, you can add methods to retrieve, update, or delete records from the database

	    public String getName() {
	        return name;
	    }

	    public void setName(String name) {
	        this.name = name;
	    }

	    public String getContact() {
	        return contact;
	    }

	    public void setContact(String contact) {
	        this.contact = contact;
	    }

	    public String getGender() {
	        return gender;
	    }

	    public void setGender(String gender) {
	        this.gender = gender;
	    }

	    public List<Food> getFoodList() {
	        return foodList;
	    }

	    public void setFoodList(List<Food> foodList) {
	        this.foodList = foodList;
	    }
	}
	
	public class DoubleRoom extends SingleRoom {
	    private String name2;
	    private String contact2;
	    private String gender2;
	    
	    public DoubleRoom(String name1, String contact1, String gender1, String name2, String contact2, String gender2) {
	        super(name1, contact1, gender1);
	        this.name2 = name2;
	        this.contact2 = contact2;
	        this.gender2 = gender2;
	    }

	    @Override
	    public void saveToDatabase(int roomId) {
	        String sql = "INSERT INTO DoubleRoom (room_id, name1, contact1, gender1, name2, contact2, gender2) VALUES (?, ?, ?, ?, ?, ?, ?)";
	        try (Connection conn = ConnectionPool.giveConnection();
	             PreparedStatement pstmt = conn.prepareStatement(sql)) {

	            pstmt.setInt(1, roomId);
	            pstmt.setString(2, getName());
	            pstmt.setString(3, getContact());
	            pstmt.setString(4, getGender());
	            pstmt.setString(5, this.name2);
	            pstmt.setString(6, this.contact2);
	            pstmt.setString(7, this.gender2);

	            pstmt.executeUpdate();
	            System.out.println("Double room data saved to the database.");

	            // Save associated food orders
	            for (Food food : getFoodList()) {
	                food.saveToDatabase(roomId);
	            }

	        } catch (SQLException e) {
	            System.err.println("Error while saving double room data to the database: " + e.getMessage());
	        }
	    }

	    // Optionally, you can add methods to retrieve, update, or delete records from the database

	    public String getName2() {
	        return name2;
	    }

	    public void setName2(String name2) {
	        this.name2 = name2;
	    }

	    public String getContact2() {
	        return contact2;
	    }

	    public void setContact2(String contact2) {
	        this.contact2 = contact2;
	    }

	    public String getGender2() {
	        return gender2;
	    }

	    public void setGender2(String gender2) {
	        this.gender2 = gender2;
	    }
	}
	

	public static class Hotel {	    
	    static Scanner sc = new Scanner(System.in);
	    
	    public static void CustDetails(int i, int rn) {
	        String name, contact, gender;
	        String name2 = null, contact2 = null;
	        String gender2 = "";
	        System.out.print("\nEnter customer name: ");
	        name = sc.next();
	        System.out.print("Enter contact number: ");
	        contact = sc.next();
	        System.out.print("Enter gender: ");
	        gender = sc.next();
	        if (i < 3) {
	            System.out.print("Enter second customer name: ");
	            name2 = sc.next();
	            System.out.print("Enter contact number: ");
	            contact2 = sc.next();
	            System.out.print("Enter gender: ");
	            gender2 = sc.next();
	        }

	        try (Connection conn = ConnectionPool.giveConnection()) {
	            String sql;
	            switch (i) {
	                case 1:
	                case 2:
	                    sql = "INSERT INTO DoubleRoom (room_id, name1, contact1, gender1, name2, contact2, gender2) VALUES (?, ?, ?, ?, ?, ?, ?)";
	                    break;
	                case 3:
	                case 4:
	                    sql = "INSERT INTO SingleRoom (room_id, name, contact, gender) VALUES (?, ?, ?, ?)";
	                    break;
	                default:
	                    System.out.println("Wrong option");
	                    return;
	            }

	            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
	                pstmt.setInt(1, rn);
	                pstmt.setString(2, name);
	                pstmt.setString(3, contact);
	                pstmt.setString(4, gender);
	                if (i < 3) {
	                    pstmt.setString(5, name2);
	                    pstmt.setString(6, contact2);
	                    pstmt.setString(7, gender2);
	                }
	                pstmt.executeUpdate();
	                System.out.println("Room Booked");
	            }
	        } catch (SQLException e) {
	            System.out.println("Error while booking room: " + e.getMessage());
	        }
	    }
	    
	    public static void bookRoom(int i) {
	        try (Connection conn =ConnectionPool.giveConnection()) {
	            String query;
	            switch (i) {
	                case 1:
	                    query = "SELECT room_id FROM DoubleRoom WHERE room_id BETWEEN 1 AND 10 AND name1 IS NULL LIMIT 1";
	                    break;
	                case 2:
	                    query = "SELECT room_id FROM DoubleRoom WHERE room_id BETWEEN 11 AND 30 AND name1 IS NULL LIMIT 1";
	                    break;
	                case 3:
	                    query = "SELECT room_id FROM SingleRoom WHERE room_id BETWEEN 31 AND 40 AND name IS NULL LIMIT 1";
	                    break;
	                case 4:
	                    query = "SELECT room_id FROM SingleRoom WHERE room_id BETWEEN 41 AND 60 AND name IS NULL LIMIT 1";
	                    break;
	                default:
	                    System.out.println("Enter valid option");
	                    return;
	            }

	            try (Statement stmt = conn.createStatement();
	                 ResultSet rs = stmt.executeQuery(query)) {
	                if (rs.next()) {
	                    int rn = rs.getInt("room_id");
	                    CustDetails(i, rn);
	                } else {
	                    System.out.println("No available rooms of the selected type.");
	                }
	            }
	        } catch (SQLException e) {
	            System.out.println("Error while booking room: " + e.getMessage());
	        }
	    }

	    public static void features(int i) {
	        switch (i) {
	            case 1:
	                System.out.println("Number of double beds : 1\nAC : Yes\nFree breakfast : Yes\nCharge per day:4000 ");
	                break;
	            case 2:
	                System.out.println("Number of double beds : 1\nAC : No\nFree breakfast : Yes\nCharge per day:3000  ");
	                break;
	            case 3:
	                System.out.println("Number of single beds : 1\nAC : Yes\nFree breakfast : Yes\nCharge per day:2200  ");
	                break;
	            case 4:
	                System.out.println("Number of single beds : 1\nAC : No\nFree breakfast : Yes\nCharge per day:1200 ");
	                break;
	            default:
	                System.out.println("Enter valid option");
	                break;
	        }
	    }

	    public static void availability(int i) {
	        try (Connection conn = ConnectionPool.giveConnection()) {
	            String query;
	            switch (i) {
	                case 1:
	                    query = "SELECT COUNT(*) FROM DoubleRoom WHERE room_id BETWEEN 1 AND 10 AND name1 IS NULL";
	                    break;
	                case 2:
	                    query = "SELECT COUNT(*) FROM DoubleRoom WHERE room_id BETWEEN 11 AND 30 AND name1 IS NULL";
	                    break;
	                case 3:
	                    query = "SELECT COUNT(*) FROM SingleRoom WHERE room_id BETWEEN 31 AND 40 AND name IS NULL";
	                    break;
	                case 4:
	                    query = "SELECT COUNT(*) FROM SingleRoom WHERE room_id BETWEEN 41 AND 60 AND name IS NULL";
	                    break;
	                default:
	                    System.out.println("Enter valid option");
	                    return;
	            }

	            try (Statement stmt = conn.createStatement();
	                 ResultSet rs = stmt.executeQuery(query)) {
	                if (rs.next()) {
	                    int count = rs.getInt(1);
	                    System.out.println("Number of rooms available: " + count);
	                }
	            }
	        } catch (SQLException e) {
	            System.out.println("Error while checking availability: " + e.getMessage());
	        }
	    }

	    public static void bill(int rn, int rtype) {
	        double amount = 0;
	        String[] list = {"Sandwich", "Pasta", "Noodles", "Coke"};
	        System.out.println("\n*******");
	        System.out.println(" Bill:-");
	        System.out.println("*******");

	        try (Connection conn = ConnectionPool.giveConnection()) {
	            String roomChargeSql = "";
	            switch (rtype) {
	                case 1:
	                    amount += 4000;
	                    roomChargeSql = "SELECT item_id, quantity FROM FoodOrder WHERE room_id = ? AND (item_id BETWEEN 1 AND 4)";
	                    break;
	                case 2:
	                    amount += 3000;
	                    roomChargeSql = "SELECT item_id, quantity FROM FoodOrder WHERE room_id = ? AND (item_id BETWEEN 1 AND 4)";
	                    break;
	                case 3:
	                    amount += 2200;
	                    roomChargeSql = "SELECT item_id, quantity FROM FoodOrder WHERE room_id = ? AND (item_id BETWEEN 1 AND 4)";
	                    break;
	                case 4:
	                    amount += 1200;
	                    roomChargeSql = "SELECT item_id, quantity FROM FoodOrder WHERE room_id = ? AND (item_id BETWEEN 1 AND 4)";
	                    break;
	                default:
	                    System.out.println("Not valid");
	                    return;
	            }

	            System.out.println("Room Charge - " + amount);

	            try (PreparedStatement pstmt = conn.prepareStatement(roomChargeSql)) {
	                pstmt.setInt(1, rn);
	                try (ResultSet rs = pstmt.executeQuery()) {
	                    System.out.println("\nFood Charges:- ");
	                    System.out.println("===============");
	                    System.out.println("Item   Quantity    Price");
	                    System.out.println("-------------------------");
	                    while (rs.next()) {
	                        int itemId = rs.getInt("item_id");
	                        int quantity = rs.getInt("quantity");
	                        double price = getItemPrice(conn, itemId);
	                        double total = price * quantity;
	                        amount += total;
	                        String format = "%-10s%-10s%-10s%n";
	                        System.out.printf(format, list[itemId - 1], quantity, total);
	                    }
	                    System.out.println("\nTotal Amount- " + amount);
	                }
	            }
	        } catch (SQLException e) {
	            System.out.println("Error while generating bill: " + e.getMessage());
	        }
	    }

	    private static double getItemPrice(Connection conn, int itemId) throws SQLException {
	        String sql = "SELECT price FROM MenuItem WHERE item_id = ?";
	        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
	            pstmt.setInt(1, itemId);
	            try (ResultSet rs = pstmt.executeQuery()) {
	                if (rs.next()) {
	                    return rs.getDouble("price");
	                } else {
	                    throw new SQLException("Item not found");
	                }
	            }
	        }
	    }

	    public static void deallocate(int rn, int rtype) {
	        try (Connection conn = ConnectionPool.giveConnection()) {
	            String deleteSql;
	            switch (rtype) {
	                case 1:
	                    deleteSql = "DELETE FROM DoubleRoom WHERE room_id = ?";
	                    break;
	                case 2:
	                    deleteSql = "DELETE FROM DoubleRoom WHERE room_id = ?";
	                    break;
	                case 3:
	                    deleteSql = "DELETE FROM SingleRoom WHERE room_id = ?";
	                    break;
	                case 4:
	                    deleteSql = "DELETE FROM SingleRoom WHERE room_id = ?";
	                    break;
	                default:
	                    System.out.println("\nEnter valid option : ");
	                    return;
	            }

	            try (PreparedStatement pstmt = conn.prepareStatement(deleteSql)) {
	                pstmt.setInt(1, rn);
	                int rowsAffected = pstmt.executeUpdate();
	                if (rowsAffected > 0) {
	                    bill(rn, rtype);
	                    System.out.println("Deallocated successfully");
	                } else {
	                    System.out.println("Room is already empty");
	                }
	            }
	        } catch (SQLException e) {
	            System.out.println("Error while deallocating room: " + e.getMessage());
	        }
	    }

	    public static void order(int rn, int rtype) {
	        try (Connection conn = ConnectionPool.giveConnection()) {
	            System.out.println("\n==========\n   Menu:  \n==========\n\n1.Sandwich\tRs.50\n2.Pasta\t\tRs.60\n3.Noodles\tRs.70\n4.Coke\t\tRs.30\n");
	            int i, q;
	            char wish;
	            do {
	                i = sc.nextInt();
	                System.out.print("Quantity- ");
	                q = sc.nextInt();

	                String sql = "INSERT INTO FoodOrder (room_id, item_id, quantity) VALUES (?, ?, ?) ON CONFLICT (room_id, item_id) DO UPDATE SET quantity = quantity + EXCLUDED.quantity";
	                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
	                    pstmt.setInt(1, rn);
	                    pstmt.setInt(2, i);
	                    pstmt.setInt(3, q);
	                    pstmt.executeUpdate();
	                }
	                System.out.println("Do you want to order anything else ? (y/n)");
	                wish = sc.next().charAt(0);
	            } while (wish == 'y' || wish == 'Y');
	        } catch (SQLException e) {
	            System.out.println("Error while placing order: " + e.getMessage());
	        }
	    }
	}
	
	
	
    public static void main( String[] args )
    {
    	Scanner sc = new Scanner(System.in);
        int ch, ch2;
        char wish;

        try {
            Connection conn = ConnectionPool.giveConnection();

            x:
            do {
                System.out.println("\nEnter your choice :\n1.Display room details\n2.Display room availability\n3.Book\n4.Order food\n5.Checkout\n6.Exit\n");
                ch = sc.nextInt();
                switch (ch) {
                    case 1:
                        System.out.println("\nChoose room type :\n1.Luxury Double Room\n2.Deluxe Double Room\n3.Luxury Single Room\n4.Deluxe Single Room\n");
                        ch2 = sc.nextInt();
                        Hotel.features(ch2);
                        break;
                    case 2:
                        System.out.println("\nChoose room type :\n1.Luxury Double Room\n2.Deluxe Double Room\n3.Luxury Single Room\n4.Deluxe Single Room\n");
                        ch2 = sc.nextInt();
                        Hotel.availability(ch2);
                        break;
                    case 3:
                        System.out.println("\nChoose room type :\n1.Luxury Double Room\n2.Deluxe Double Room\n3.Luxury Single Room\n4.Deluxe Single Room\n");
                        ch2 = sc.nextInt();
                        Hotel.bookRoom(ch2);
                        break;
                    case 4:
                        System.out.print("Room Number - ");
                        ch2 = sc.nextInt();
                        if (ch2 > 60) {
                            System.out.println("Room doesn't exist");
                        } else if (ch2 > 40) {
                            Hotel.order(ch2 - 41, 4);
                        } else if (ch2 > 30) {
                            Hotel.order(ch2 - 31, 3);
                        } else if (ch2 > 10) {
                            Hotel.order(ch2 - 11, 2);
                        } else if (ch2 > 0) {
                            Hotel.order(ch2 - 1, 1);
                        } else {
                            System.out.println("Room doesn't exist");
                        }
                        break;
                    case 5:
                        System.out.print("Room Number - ");
                        ch2 = sc.nextInt();
                        if (ch2 > 60) {
                            System.out.println("Room doesn't exist");
                        } else if (ch2 > 40) {
                            Hotel.deallocate(ch2 - 41, 4);
                        } else if (ch2 > 30) {
                            Hotel.deallocate(ch2 - 31, 3);
                        } else if (ch2 > 10) {
                            Hotel.deallocate(ch2 - 11, 2);
                        } else if (ch2 > 0) {
                            Hotel.deallocate(ch2 - 1, 1);
                        } else {
                            System.out.println("Room doesn't exist");
                        }
                        break;
                    case 6:
                        break x;
                    default:
                        System.out.println("Invalid choice, please try again.");
                        break;
                }

                System.out.println("\nContinue : (y/n)");
                wish = sc.next().charAt(0);
                if (!(wish == 'y' || wish == 'Y' || wish == 'n' || wish == 'N')) {
                    System.out.println("Invalid Option");
                    System.out.println("\nContinue : (y/n)");
                    wish = sc.next().charAt(0);
                }

            } while (wish == 'y' || wish == 'Y');

            sc.close();
            conn.close();
            
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }
}
