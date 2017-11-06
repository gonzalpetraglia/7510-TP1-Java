package ar.uba.fi.tdd.rulogic.model;

import java.util.LinkedList;
import java.util.List;

public class RuleCollection {
    private Rule[] rules;

    public RuleCollection(Rule[] rules){
        this.rules = rules;
    }

    public Conjuction[] getEquivalentConjuctions(Query query){
        List<Conjuction> conjuctionList = new LinkedList<Conjuction>();
        for(Rule rule:rules)
            try {
                conjuctionList.add(rule.getEquivalentConjuction(query));
            }catch(DoesntMatchQuery exc){}
        return conjuctionList.toArray(new Conjuction[0]);
    }
}
