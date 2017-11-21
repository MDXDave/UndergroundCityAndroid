package de.thkoeln.undergroundcity;

import java.util.ArrayList;

public class City {

    ArrayList<Bauebene> bauebenenArrayList;
    int aktuelleEbene;
    int aktuellesGuthaben;

    City(){

    }

    public void hoch(){

    }

    public void runter(){

    }

    public void spielrunden(int n){
        for(Bauebene bauebene: bauebenenArrayList){
            for(int i=0; i<n; i++)
                bauebene.spielrunde();
        }

    }

    public void ausgabe(){

    }

    public void baueEbene(int preis, int n){

    }

    public void baueHochhaus(){

    }

    public void baueVilla(){

    }
    public void baueSupermarkt(){

    }
    public void bauePark(){

    }
}
