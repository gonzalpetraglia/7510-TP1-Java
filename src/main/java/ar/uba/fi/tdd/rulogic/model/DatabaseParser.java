package ar.uba.fi.tdd.rulogic.model;

import ar.uba.fi.tdd.rulogic.model.exceptions.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class DatabaseParser {
    static private DatabaseParser instance = null;

    private DatabaseParser(){

    }

    static public DatabaseParser getInstance(){
        if(instance == null)
            instance = new DatabaseParser();
        return instance;
    }
    public Database parse(String fileName) throws InvalidDatabaseException ,
        FileNotFoundException{

        DatabaseBuilder databaseBuilder = new DatabaseBuilder();
        try {

            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = br.readLine()) != null)
                try {
                    databaseBuilder.addFact(FactParser.getInstance().parse(line));
                } catch (InvalidFactException exc) {
                    databaseBuilder.addRule(RuleParser.getInstance().parse(line));
                }
        }catch(InvalidClauseException exc){
            throw new InvalidDatabaseException();
        }catch (IOException exc){
            throw new FileNotFoundException();
        }
        return databaseBuilder.build();
    }
}
