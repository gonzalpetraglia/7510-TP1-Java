package ar.uba.fi.tdd.rulogic.model;

import ar.uba.fi.tdd.rulogic.model.exceptions.FileNotFoundException;
import ar.uba.fi.tdd.rulogic.model.exceptions.InvalidDatabaseException;
import ar.uba.fi.tdd.rulogic.model.exceptions.InvalidQueryException;
import ar.uba.fi.tdd.rulogic.model.exceptions.NoDatabaseException;

public class KnowledgeBase {

    private Database database;
    public void useDatabase(String filename) throws InvalidDatabaseException, FileNotFoundException{
        database = null;
        database = DatabaseParser.getInstance().parse(filename);
    }

	public boolean answer(String queryString) throws InvalidQueryException,
            NoDatabaseException{
        Query query = QueryParser.getInstance().parse(queryString);
        try{
            return database.answer(query);
        }catch (NullPointerException exc){
            throw new NoDatabaseException();
        }
	}

}
