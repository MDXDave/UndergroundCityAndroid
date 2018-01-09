package de.thkoeln.undergroundcity;

import android.util.Log;

abstract class Bauwerk {

    private int slots;
    private int alter;
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

    int benoetigteSlots(){
        switch (this.type){
            case "P": return 5;
            case "S": return 2;
            case "H": return 3;
            case "V": return 1;
            default: return 0;
        }
    }

    int getEinnahmen(){
        switch (this.type){
            case "P": return -2500;
            default: return einnahmen;
        }

    }

    int getAusgaben(){
        switch (this.type){
            case "P": return 7500;
            case "S": return 3500;
            case "H": return 8000;
            case "V": return 2500;
            default: return 0;
        }
    }

    int getEinwohner(){
        return einwohner;
    }

    public int getLebensqualitaet(){
        switch (this.type){
            case "P": return 30;
            case "S": return 10;
            case "H": return -5;
            case "V": return 0;
            default: return 0;
        }
    }

    String zeichnung(){
        StringBuilder slotZeichnung = new StringBuilder();
        for(int i= 0;i<slots;i++)
            slotZeichnung.append(this.getZeichnung());

        Log.i("ZEICHNUNG", String.valueOf(slotZeichnung));
        return String.valueOf(slotZeichnung);
    }

    void spielrunde(int gesamteQualitaet){
        switch (type){
            case "S":
                einnahmen = gesamteQualitaet * 100;
                einwohner = gesamteQualitaet * 10;
                break;
            case "H":
                einnahmen = gesamteQualitaet * -40;
                einwohner = Math.max(0, gesamteQualitaet * -50);
                break;
            case "V":
                einnahmen = gesamteQualitaet * 50;
                einwohner = gesamteQualitaet * 20;
                break;
        }
        alter++;
    }

    protected abstract String getZeichnung();
}
