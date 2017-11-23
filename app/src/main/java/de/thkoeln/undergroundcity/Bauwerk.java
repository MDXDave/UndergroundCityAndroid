package de.thkoeln.undergroundcity;

import android.util.Log;

abstract class Bauwerk {

    int slots;
    int alter;
    int kosten;
    int einnahmen;
    int einwohner;
    String name;
    String type;

    Bauwerk(String type){
        this.type = type;
    }

    Bauwerk(){
        this.type = "W";
    }

    public int benoetigteSlots(){
        switch (this.type){
            case "P": return 5;
            case "S": return 2;
            case "H": return 3;
            case "V": return 1;
            default: return 0;
        }
    }

    public int getEinnahmen(){
        return 0;
    }

    public int getAusgaben(){
        switch (this.type){
            case "P": return 7500;
            case "S": return 3500;
            case "H": return 8000;
            case "V": return 2500;
            default: return 0;
        }
    }

    public int getEinwohner(){
        return 0;
    }

    public int getLebensqualitaet(){
        return 0;
    }

    public String zeichnung(){
        StringBuilder slotZeichnung = new StringBuilder();
        for(int i= 0;i<slots;i++)
            slotZeichnung.append(this.getZeichnung());

        Log.i("ZEICHNUNG", String.valueOf(slotZeichnung));
        return String.valueOf(slotZeichnung);
    }

    void spielrunde(int gesamteQualitaet){
        alter++;
    }

    protected abstract String getZeichnung();
}
