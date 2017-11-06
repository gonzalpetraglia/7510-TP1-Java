package ar.uba.fi.tdd.rulogic.model;

import java.util.LinkedList;
import java.util.List;

public class DatabaseBuilder {
    private List<Fact> factList;
    private List<Rule> ruleList;

    public DatabaseBuilder(){
        factList = new LinkedList<Fact>();
        ruleList = new LinkedList<Rule>();
    }
    public Database build(){
        return new Database(factList.toArray(new Fact[0]), ruleList.toArray(new Rule[0]));
    }

    public void addFact(Fact newFact){
        factList.add(newFact);
    }

    public void addRule(Rule newRule){
        ruleList.add(newRule);
    }
}
