package de.thkoeln.undergroundcity;

public class Infrastruktur extends Bauwerk {

    public Infrastruktur(String type){
        this.type = type;
    }

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
        return zeichnung();
    }
}
