package de.thkoeln.undergroundcity;

public class Infrastruktur extends Bauwerk {

    @Override
    public int getLebensqualitaet(){
        return 4;
    }

    @Override
    public void spielrunde(int gesamteQualitaet){
        this.einnahmen = gesamteQualitaet * 10;
        this.kosten = gesamteQualitaet * 5;
    }

    @Override
    protected String getZeichnung(){
        return type;
    }
}
