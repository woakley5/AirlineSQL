package com.woakley;

import com.eleet.dragonconsole.CommandProcessor;
import javafx.beans.binding.IntegerBinding;

import java.sql.*;
import java.util.ArrayList;
import java.util.UUID;

/*
 * Created by woakley on 6/25/17.
 */

public class AirlineCommandProcessor extends CommandProcessor {

    static String url = "jdbc:mysql://Wills-MacBook-Pro.local:3306/airline";
    static String username = "javaClientLaptop";
    static String password = "1234";

    public static int currentCommand = 0;
    public static int step = 0;

    public static ArrayList<String> data = new ArrayList<String>();

    public Connection connection;

    public AirlineCommandProcessor() {
        super();

        openConnection();
    }

    void openConnection(){
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
            else if(input.equals("newReservation")){
                newReservation("");
            }
            else if(input.equals("allReservations")){
                allReservations("");
            }
            else{
                output("\n&r-Error: Command " + input + " not found.\n&ob>> ");
            }
        }
        else if(currentCommand == 1){
            newFlight(input);
        }
        else if(currentCommand == 3){
            newReservation(input);
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
            throw new IllegalStateException("SQL Parse Error", e);
        }

    }

    void newReservation(String input){
        if(currentCommand == 0){
            currentCommand = 3;
            step = 0;
        }

        ResultSet response;

        switch(step) {
            case 0:
                output("\nDeparting Airport: ");
                break;

            case 1:
                data.add(input);
                output("\nArrival Airport: ");
                break;

            case 2: //searching for all flights
                data.add(input);
                response = readSQL("SELECT carrier, fltNum, departs, arrival FROM flight WHERE origin = \"" + data.get(0) + "\" AND destin = \"" + data.get(1) + "\"");

                try{
                    if(!response.isBeforeFirst()) {
                        System.out.println("No Flights found");
                        output("&r-\nNO FLIGHTS FOUND\n");
                        output("&o->> ");
                        currentCommand = 0;
                        step = 0;
                        break;
                    }
                }
                catch(SQLException e){
                    throw new IllegalStateException("SQL Query Error", e);
                }

                try {
                    ResultSetMetaData flightMetaData = response.getMetaData();

                    output("\n   ");
                    int numberOfColumns;
                    for (numberOfColumns = 1; numberOfColumns <= flightMetaData.getColumnCount(); numberOfColumns++) {
                        output("&c- | " + flightMetaData.getColumnName(numberOfColumns));
                    }
                    output(" | \n");

                    ResultSet rows = readSQL("SELECT COUNT(*) FROM flight WHERE origin = \"" + data.get(0) + "\" AND destin = \"" + data.get(1) + "\"");
                    rows.next();
                    int numberOfRows = rows.getInt(1);

                    response.next();
                    for (int y = 1; y < numberOfRows + 1; y++) {
                        output("&c- "+ y + ": ");
                        for (int x = 1; x < numberOfColumns; x++) {
                            output("&y-     " + response.getString(x));
                            output(" ");
                        }
                        output("\n");
                        response.next();
                    }
                    output("\n&o-Pick Flight: ");
                } catch (SQLException e) {
                    throw new IllegalStateException("SQL Parse Error", e);
                }
                break;

            case 3:
                data.add(input);
                response = readSQL("SELECT carrier,fltNum,idflight FROM flight WHERE origin = \"" + data.get(0) + "\" AND destin = \"" + data.get(1) + "\" LIMIT 1 OFFSET " + (Integer.parseInt(data.get(2)) - 1));
                try{
                    response.next();
                    data.add(response.getString(3));
                    output("\n&g-New Reservation for " + response.getString(1) + " " + response.getString(2));
                    output("\n&o-Name: ");
                }
                catch(SQLException e){
                    throw new IllegalStateException("SQL Query Error", e);
                }

                break;

            case 4:
                data.add(input);
                output("\nDOB: ");
                break;
            case 5:
                data.add(input);
                String pnr = UUID.randomUUID().toString();
                pnr = pnr.substring(0,5);
                pnr = pnr.toUpperCase();
                data.add(pnr);
                System.out.println(data.toString());
                executeSQL("INSERT INTO reservations VALUES (" + data.get(6) + ",'" + data.get(4) + "','" + data.get(5) + "','" + Integer.parseInt(data.get(3)) + "')");
                output("\n&g-Successfully created with PNR: &D-" + data.get(6));
                output("\n&o->> ");
                currentCommand = 0;
                step = 0;
                data.clear();
                break;
        }
        step++;
    }

    void allReservations(String input){
        if(currentCommand == 0){
            currentCommand = 4;
            step = 0;
        }

        ResultSet reservations = readSQL("SELECT * FROM reservations");

        try {
            ResultSetMetaData reservationMetaData = reservations.getMetaData();

            output("\n");
            int numberOfColumns;
            for (numberOfColumns = 1; numberOfColumns < reservationMetaData.getColumnCount(); numberOfColumns++) {
                output("&c- |      " + reservationMetaData.getColumnName(numberOfColumns));
                output("        ");
            }
            output("| \n");

            ResultSet rows = readSQL("SELECT COUNT(*) FROM reservations");
            rows.next();
            int numberOfRows = rows.getInt(1);

            reservations.next();

            for(int y = 1; y < numberOfRows + 1; y++){
                for(int x = 1; x < numberOfColumns; x++){
                    output("&y-     " + reservations.getString(x));
                    output("       ");
                }
                output(" \n");
                reservations.next();
            }

            output("\n&ob>> ");
            currentCommand = 0;
            step = 0;
        }
        catch(SQLException e){
            throw new IllegalStateException("SQL Parse Error", e);
        }

    }

    //----------- * ALL CUSTOM COMMANDS ABOVE THIS LINE * ------------\\

    void executeSQL(String sql){
        System.out.println("Executing: " + sql);
        try{
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(sql);
            System.out.println("Successfully Executed");
        }
        catch(SQLException e){
            output("\n&r-SQL EXECUTION ERROR\n&ob>> ");
            currentCommand = 0;
            step = 0;
            data.clear();
            throw new IllegalStateException("SQL Query Error", e);
        }
    }

    ResultSet readSQL(String sql) {
        System.out.println("Reading: " + sql);
        try{
            Statement stmt = connection.createStatement();
            ResultSet result = stmt.executeQuery(sql);
            System.out.println("Query Successful");
            return result;
        }
        catch(SQLException e){
            output("\n&r-SQL QUERY ERROR\n&ob>> ");
            currentCommand = 0;
            step = 0;
            data.clear();
            throw new IllegalStateException("SQL Query Error", e);
        }
    }

}
