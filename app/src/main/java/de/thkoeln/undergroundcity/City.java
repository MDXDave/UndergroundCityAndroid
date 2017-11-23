package de.thkoeln.undergroundcity;

import java.util.ArrayList;

public class City {

    ArrayList<Bauebene> bauebenenArrayList = new ArrayList<>();
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
        bauebenenArrayList.add(new Bauebene(n));
        this.aktuellesGuthaben -= preis;
    }

    public boolean baueHochhaus(){
        return baueObjekt("H");
    }

    private boolean baueObjekt(String h) {
        Bauwerk bauwerk = new Wohneinheit(h);
        if(hatFreieSlots(bauwerk.benoetigteSlots())) {
            bauebenenArrayList.get(aktuelleEbene).bauwerkEinfuegen(bauwerk);
            this.aktuellesGuthaben -= bauwerk.getAusgaben();
            return true;
        }
        return false;
    }

    public boolean baueVilla(){
        return baueObjekt("V");
    }

    public boolean baueSupermarkt(){
        return baueObjekt("S");
    }

    public boolean bauePark(){
        return baueObjekt("P");
    }

    public boolean hatFreieSlots(int requiredSlots){
        return (bauebenenArrayList.get(aktuelleEbene).freieSlots >= requiredSlots);
    }

    public int getAnzahlEbenen(){
        return bauebenenArrayList.size();
    }

    public ArrayList<Bauebene> getBauebenen(){
        return bauebenenArrayList;
    }
}
