package ar.uba.fi.tdd.rulogic.model;

import java.util.List;

public class Database {
    FactCollection factCollection;
    RuleCollection ruleCollection;

    public Database(Fact[] factList, Rule[] ruleList){
        this.factCollection = new FactCollection(factList);
        this.ruleCollection = new RuleCollection(ruleList);
    }


    private boolean answer(Conjuction conjuction){
        Query[] queries = conjuction.getEquivalentQueries();
        int i = 0;
        while(i < queries.length){
            if(!factCollection.matches(queries[i]))
                return false;
            i++;
        }
        return true;
    }

    public boolean answer(Query query){
        if(factCollection.matches(query))
            return true;
        Conjuction[] equivalentConjuctions =
                ruleCollection.getEquivalentConjuctions(query);
        int i = 0;
        while(i < equivalentConjuctions.length){
            if(this.answer(equivalentConjuctions[i]))
                return true;
            i++;
        }
        return false;
    }

}
