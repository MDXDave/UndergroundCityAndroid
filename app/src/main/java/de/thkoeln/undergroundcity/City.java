package de.thkoeln.undergroundcity;

import java.util.ArrayList;

class City {

    private ArrayList<Bauebene> bauebenenArrayList = new ArrayList<>();
    int aktuelleEbene = -1;
    int aktuellesGuthaben;
    int aktuelleSpielrunde = 0;

    City(){

    }

    void spielrunden(int n){
        aktuelleSpielrunde = aktuelleSpielrunde+n;
        for(Bauebene bauebene: bauebenenArrayList){
            for(int i=0; i<n; i++) {
                bauebene.spielrunde();
                aktuellesGuthaben += bauebene.getEinnahmen();
            }
        }

    }

    void baueEbene(int preis, int n){
        bauebenenArrayList.add(new Bauebene(n));
        this.aktuellesGuthaben -= preis;
        if(bauebenenArrayList.size() == 1)
            aktuelleEbene = 0;
    }

    boolean baueHochhaus(){
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

    boolean baueVilla(){
        return baueObjekt("V");
    }

    boolean baueSupermarkt(){
        return baueObjekt("S");
    }

    boolean bauePark(){
        return baueObjekt("P");
    }

    private boolean hatFreieSlots(int requiredSlots){
        return (bauebenenArrayList.get(aktuelleEbene).freieSlots >= requiredSlots);
    }

    int getAnzahlEbenen(){
        return bauebenenArrayList.size();
    }

    ArrayList<Bauebene> getBauebenen(){
        return bauebenenArrayList;
    }
}
