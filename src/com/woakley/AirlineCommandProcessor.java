package com.woakley;

import com.eleet.dragonconsole.CommandProcessor;

import java.sql.*;
import java.util.ArrayList;

/*
 * Created by woakley on 6/25/17.
 */

public class AirlineCommandProcessor extends CommandProcessor {

    static String url = "jdbc:mysql://localhost:3306/airline";
    static String username = "javaClient";
    static String password = "java";

    private boolean readyForCommand = true;
    private int currentCommand = 0;
    private int step = 0;

    private ArrayList<String> data = new ArrayList<String>();

    public Connection connection;

    public AirlineCommandProcessor() {
        super();

        try {
            connection = DriverManager.getConnection(url, username, password);
        }
        catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }

    ///----------------------------------------------\\\
    //            void processCommand()               \\
    //     Overrides default process command in       \\
    //             CommandProcessor                   \\
    //                                                \\
    //    Checks for current command and passes on    \\
    //      input or starts new command string        \\
    //------------------------------------------------\\

    @Override
    public void processCommand(String input){

        if(currentCommand == 0){
            if(input.equals("newFlight")){
                newFlight("");
            }
            else if(input.equals("allFlights")){
                allFlights("");
            }
            else{
                output("\n&r-Error: Command " + input + " not found.\n&ob>> ");
            }
        }
        else if(currentCommand == 1){
            newFlight(input);
        }
    }

    //----------- * ALL CUSTOM COMMANDS BELOW THIS LINE * ------------\\

    void newFlight(String input){
        if(currentCommand == 0){
            currentCommand = 1;
            step = 0;
        }

        switch(step){
            case 0:
                output("\nDeparting Airport: ");
                break;
            case 1:
                data.add(input);
                output("\nArrival Airport: ");
                break;
            case 2:
                data.add(input);
                output("\nDeparture Time: ");
                break;
            case 3:
                data.add(input);
                output("\nArrival Time: ");
                break;
            case 4:
                data.add(input);
                output("\nAirline Code: ");
                break;
            case 5:
                data.add(input);
                output("\nFlight Number: ");
                break;
            case 6:
                data.add(input);
                System.out.println("Flt ID:" + data.get(2) + data.get(5));
                data.add(data.get(2) + data.get(5)); //flight id = departuretime + number
                executeSQL("INSERT INTO flight VALUES ('" + data.get(4) + "','" + data.get(5) + "','" + data.get(0) + "','" + data.get(1) + "','" + data.get(2) + "','" + data.get(3) + "','" + data.get(6) + "')");
                output("\n&g-Success!\n&ob>> ");
                step = -1;
                currentCommand = 0;
                data.clear();
                break;

        }
        step++;
    }

    void allFlights(String input){
        if(currentCommand == 0){
            currentCommand = 2;
            step = 0;
        }

        ResultSet flights = readSQL("SELECT * FROM flight");
        System.out.println(flights);

        try {
            ResultSetMetaData flightMetaData = flights.getMetaData();

            output("\n");
            int numberOfColumns;
            for (numberOfColumns = 1; numberOfColumns < flightMetaData.getColumnCount(); numberOfColumns++) {
                output("&c- | " + flightMetaData.getColumnName(numberOfColumns));
            }
            output(" | \n");

            ResultSet rows = readSQL("SELECT COUNT(*) FROM flight");
            rows.next();
            int numberOfRows = rows.getInt(1);

            flights.next();

            for(int y = 1; y < numberOfRows + 1; y++){
                for(int x = 1; x < numberOfColumns; x++){
                    output("&y-     " + flights.getString(x));
                    output(" ");
                }
                output("\n");
                flights.next();
            }

            output("\n&ob>> ");
            currentCommand = 0;
            step = 0;
        }
        catch(SQLException e){
            throw new IllegalStateException("SQL Read Error", e);
        }

    }

    //----------- * ALL CUSTOM COMMANDS ABOVE THIS LINE * ------------\\

    void executeSQL(String sql){
        try{
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(sql);
            System.out.println("Successfully Executed");
        }
        catch(SQLException e){
            output("\n&r-SQL ERROR");
            throw new IllegalStateException("SQL Query Error", e);
        }
    }

    ResultSet readSQL(String sql){
        try{
            Statement stmt = connection.createStatement();
            ResultSet result = stmt.executeQuery(sql);
            System.out.println("Query Successful");
            return result;
        }
        catch(SQLException e){
            output("\n&r-SQL ERROR");
            throw new IllegalStateException("SQL Query Error", e);
        }
    }

}
