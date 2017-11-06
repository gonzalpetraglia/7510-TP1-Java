package ar.uba.fi.tdd.rulogic.model;

import java.util.LinkedList;
import java.util.List;

public class Conjuction {
    private List<Query> equivalentQueries;
    public Conjuction(){
        this.equivalentQueries = new LinkedList<Query>();
    }

    public Query[] getEquivalentQueries(){
        return this.equivalentQueries.toArray(new Query[0]);
    }

    public void add(Query q){
        this.equivalentQueries.add(q);
    }
}
