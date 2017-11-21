package de.thkoeln.undergroundcity;

public class Wohneinheit extends Bauwerk {

    @Override
    public void spielrunde(int gesamteQualitaet){
        this.einnahmen = gesamteQualitaet * 10;
        this.kosten = gesamteQualitaet * 8;
        this.einwohner = gesamteQualitaet * 2;
    }

    @Override
    protected String getZeichnung(){
        return "W";
    }
}
