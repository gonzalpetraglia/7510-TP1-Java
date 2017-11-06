package ar.uba.fi.tdd.rulogic.model;

import ar.uba.fi.tdd.rulogic.model.exceptions.InvalidRuleException;
import ar.uba.fi.tdd.rulogic.model.exceptions.InvalidStructureException;

import java.util.LinkedList;
import java.util.List;

public class RuleParser{
    static private RuleParser instance = null;

    private RuleParser(){
    }

    static public RuleParser getInstance(){
        if(instance == null)
            instance = new RuleParser();
        return instance;
    }

    private String getHeader(String rule) throws InvalidRuleException {
        try{
            return rule.substring(0,rule.indexOf(":-"));
        }
        catch(IndexOutOfBoundsException exc){
            throw new InvalidRuleException();
        }
    }

    private String[] getQueries(String rule) throws InvalidRuleException{

        try {
            int startOfNextQuery = rule.indexOf(":-") + 2;
            int endOfNextQuery;
            List<String> queries = new LinkedList<String>();
            while (startOfNextQuery > 0) {
                endOfNextQuery = rule.indexOf(")", startOfNextQuery);
                queries.add(rule.substring(startOfNextQuery,endOfNextQuery+1));
                startOfNextQuery = rule.indexOf(",", endOfNextQuery) + 1;
            }
            return queries.toArray(new String[0]);
        }catch(IndexOutOfBoundsException exc){
            throw new InvalidRuleException();
        }
    }

    private AbstractQuery[] buildEquivalentQueries(String[] equivalentQueries) throws InvalidRuleException{
        try{
            int i = 0;
            AbstractQuery[] result = new AbstractQuery[equivalentQueries.length];
            for(String query:equivalentQueries){
                ParsedStructure absQuery = CommonStructureParser.getInstance().parse(query);
                result[i] = new AbstractQuery(absQuery.getName(), absQuery.getParams());
                i++;
            }
            return result;
        }
        catch(InvalidStructureException exc){
            throw new InvalidRuleException();
        }

    }

    public Rule parse(String rule) throws InvalidRuleException{
        try{
            rule = rule.trim();
            if(rule.charAt(rule.length()-1) != '.')
                throw new InvalidRuleException();

            String header = this.getHeader(rule);
            String[] equivalentQueries = this.getQueries(rule);
            ParsedStructure structure = CommonStructureParser.getInstance().parse(header);

            return new Rule(structure.getName(), structure.getParams(),
                    this.buildEquivalentQueries(equivalentQueries));
        }
        catch(InvalidStructureException exc){
            throw new InvalidRuleException();
        }

    }
}
