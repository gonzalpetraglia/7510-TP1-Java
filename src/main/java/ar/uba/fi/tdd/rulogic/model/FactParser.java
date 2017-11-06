package ar.uba.fi.tdd.rulogic.model;


import ar.uba.fi.tdd.rulogic.model.exceptions.InvalidFactException;
import ar.uba.fi.tdd.rulogic.model.exceptions.InvalidStructureException;

public class FactParser {
    static private FactParser instance = null;

    private FactParser(){
    }

    static public FactParser getInstance(){
        if(instance == null)
            instance = new FactParser();
        return instance;
    }

    public Fact parse(String fact) throws InvalidFactException {
        try{
            fact = fact.trim();


            if(fact.length() == 0
                    || fact.charAt(fact.length()-1) != '.')
                throw new InvalidFactException();

            fact = fact.substring(0,fact.length()-1);
            ParsedStructure structure = CommonStructureParser.getInstance().parse(fact);
            return new Fact(structure.getName(), structure.getParams());
        }
        catch(InvalidStructureException exc){
            throw new InvalidFactException();
        }

    }
}
