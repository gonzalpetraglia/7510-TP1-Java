package ar.uba.fi.tdd.rulogic.model;


import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class Rule extends ParsedStructure{

    private AbstractQuery[] abstractQueries;

    public Rule(String name, String[] params, AbstractQuery[] abstractQueries){
        super(name, params);
        this.abstractQueries = abstractQueries;

    }

    public boolean matches(Query query){
        return query.getName().equals(this.getName())
                && query.getParams().length == this.getParams().length;
    }


    public AbstractQuery[] getAbstractQueries() {
        return this.abstractQueries;
    }

    public Conjuction getEquivalentConjuction(Query query) throws DoesntMatchQuery{

        if(!this.matches(query))
            throw new DoesntMatchQuery();

        Conjuction conjuction = new Conjuction();

        Map<String, String> parametersMap = new HashMap<String, String>();

        String[] ownParams = this.getParams();
        String[] queryParams = query.getParams();
        for(int i = 0; i < ownParams.length; i++){
            parametersMap.put(ownParams[i], queryParams[i]);

        }
        for(AbstractQuery absQuery: abstractQueries){
            conjuction.add(absQuery.getInstancedQuery(parametersMap));
        }
        return conjuction;
    }
}
