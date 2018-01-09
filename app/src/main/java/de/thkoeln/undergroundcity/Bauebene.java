package de.thkoeln.undergroundcity;

import java.util.ArrayList;

class Bauebene {

    private ArrayList<Bauwerk> bauwerkeArrayList = new ArrayList<>();
    int freieSlots;

    Bauebene(int freieSlots){
        this.freieSlots = freieSlots;
    }

    void bauwerkEinfuegen(Bauwerk b){
        bauwerkeArrayList.add(b);
        freieSlots -= b.benoetigteSlots();
    }

    int getFreieSlots(){
        return freieSlots;
    }

    void spielrunde(){
        int gesamteLebensqualitaet = 0;
        for (Bauwerk b: bauwerkeArrayList) {
            gesamteLebensqualitaet += b.getLebensqualitaet();
        }

        for (Bauwerk b: bauwerkeArrayList) {
            b.spielrunde(gesamteLebensqualitaet);
        }
    }

    int getEinwohner(){
        int einwohner = 0;
        for(Bauwerk b: bauwerkeArrayList)
            einwohner += b.getEinwohner();
        return einwohner;
    }

    int getAusgaben(){
        int ausgaben = 0;
        for(Bauwerk b: bauwerkeArrayList)
            ausgaben += b.getAusgaben();
        return ausgaben;
    }

    int getEinnahmen(){
        int einnahmen = 0;
        for(Bauwerk b: bauwerkeArrayList)
            einnahmen += b.getEinnahmen();
        return einnahmen;
    }

    int getLebensqualitaet(){
        int lebensqualitaet = 0;
        for(Bauwerk b: bauwerkeArrayList)
            lebensqualitaet += b.getLebensqualitaet();
        return lebensqualitaet;
    }

    ArrayList<Bauwerk> getBauwerke(){
        return bauwerkeArrayList;
    }

    public int getBauwerkeAnzahl(){
        return bauwerkeArrayList.size();
    }
}
