package ar.uba.fi.tdd.rulogic.model;


public class ParsedStructure {
    private String[] params;
    private String name;

    public ParsedStructure(String name, String[] params){
        this.name = name;
        this.params = params;
    }
    public String getName(){
        return name;
    }

    public String[] getParams(){
        return params;
    }

}
