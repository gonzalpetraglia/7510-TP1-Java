package ar.uba.fi.tdd.rulogic.model;


import java.util.Arrays;

public class Fact extends ParsedStructure {

    public Fact(String name, String[] params){
        super(name,params);
    }

    public boolean matches(Query query){
        return query.getName().equals(this.getName())
                && Arrays.equals(query.getParams(),this.getParams());

    }

}