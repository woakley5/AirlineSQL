package com.woakley;

import com.eleet.dragonconsole.*;

public class Main {


    public static void main(String[] args) {

        DragonConsole dc = new DragonConsole(true, false);
        dc.setCommandProcessor(new AirlineCommandProcessor());
        DragonConsoleFrame dcframe = new DragonConsoleFrame("Airline SQL", dc);
        dcframe.setVisible(true);
        dcframe.getConsole().append("&c-Airline Database Simulator v 1.0\n");
        dcframe.getConsole().append("&m-Type Help For Commands\n");
        dcframe.getConsole().append("&ob>> ");

    }

}
