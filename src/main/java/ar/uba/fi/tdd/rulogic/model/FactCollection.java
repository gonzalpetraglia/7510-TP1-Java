package ar.uba.fi.tdd.rulogic.model;

public class FactCollection {
    private Fact[] facts;

    public FactCollection(Fact[] facts){
        this.facts = facts;
    }

    public boolean matches(Query query){
        for(Fact fact:facts)
            if(fact.matches(query))
                return true;
        return false;
    }
}
