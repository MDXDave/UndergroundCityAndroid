package de.thkoeln.undergroundcity;

import java.util.ArrayList;

class Bauebene {

    ArrayList<Bauwerk> bauwerkeArrayList = new ArrayList<>();
    int freieSlots;

    Bauebene(int freieSlots){
        this.freieSlots = freieSlots;
    }

    public void bauwerkEinfuegen(Bauwerk b){
        bauwerkeArrayList.add(b);
        freieSlots -= b.benoetigteSlots();
    }

    public int getFreieSlots(){
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

    String zeichneSlots(){
        StringBuilder ebene = new StringBuilder();
        for (Bauwerk b: bauwerkeArrayList)
            ebene.append(b.zeichnung());
        return String.valueOf(ebene);
    }

    void zeichneInfo(){

    }

    public int getEinwohner(){
        int einwohner = 0;
        for(Bauwerk b: bauwerkeArrayList)
            einwohner += b.getEinwohner();
        return einwohner;
    }

    public int getAusgaben(){
        int ausgaben = 0;
        for(Bauwerk b: bauwerkeArrayList)
            ausgaben += b.getAusgaben();
        return ausgaben;
    }

    public int getEinnahmen(){
        int einnahmen = 0;
        for(Bauwerk b: bauwerkeArrayList)
            einnahmen += b.getEinnahmen();
        return einnahmen;
    }

    public int getLebensqualitaet(){
        int lebensqualitaet = 0;
        for(Bauwerk b: bauwerkeArrayList)
            lebensqualitaet += b.getLebensqualitaet();
        return lebensqualitaet;
    }

    public void ausgabe(){

    }

    public ArrayList<Bauwerk> getBauwerke(){
        return bauwerkeArrayList;
    }

    public int getBauwerkeAnzahl(){
        return bauwerkeArrayList.size();
    }
}
