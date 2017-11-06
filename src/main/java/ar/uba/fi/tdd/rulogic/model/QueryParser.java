package ar.uba.fi.tdd.rulogic.model;

import ar.uba.fi.tdd.rulogic.model.exceptions.InvalidQueryException;
import ar.uba.fi.tdd.rulogic.model.exceptions.InvalidStructureException;


public class QueryParser {
    static private QueryParser instance = null;

    private QueryParser(){
    }

    static public QueryParser getInstance(){
        if(instance == null)
            instance = new QueryParser();
        return instance;
    }

    public Query parse(String query) throws InvalidQueryException{
        try{
            query = query.trim();
            if(query.length() == 0)
                throw new InvalidQueryException();
            if(query.charAt(query.length()-1) == '.')
                query = query.substring(0, query.length()-1);

            ParsedStructure structure = CommonStructureParser.getInstance().parse(query);
            return new Query(structure.getName(), structure.getParams());
        }
        catch(InvalidStructureException exc){
            throw new InvalidQueryException();
        }

    }
}
