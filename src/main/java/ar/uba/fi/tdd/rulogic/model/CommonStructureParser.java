package ar.uba.fi.tdd.rulogic.model;

import java.util.LinkedList;
import java.util.List;

import ar.uba.fi.tdd.rulogic.model.exceptions.InvalidStructureException;

public class CommonStructureParser {

    static private CommonStructureParser instance = null;

    private CommonStructureParser(){
    }

    static public CommonStructureParser getInstance(){
        if(instance == null)
            instance = new CommonStructureParser();
        return instance;
    }

    private int endOfFirstWord(String string){
        int i;
        for( i = 0; i < string.length(); i++){
            char currentChar = string.charAt(i);
            if(! (Character.isLetter(currentChar) || Character.isDigit(currentChar)))
                break;
        }
        return i;
    }

    private boolean isAlphanumeric(String string){
        return this.endOfFirstWord(string) == string.length();
    }



    private List<String> getParameters(String parametersStructure) throws InvalidStructureException {
        /*
        @params parametersStructure should be a string containing the parameters to be
        extracted a valid example is " (  param1, param2  ,param3,param4)
        If it is not valid, an exception will be thrown
         */

        List<String> parameters = new LinkedList<String>();
        parametersStructure = parametersStructure.trim();
        if(parametersStructure.length() < 2 ||
                parametersStructure.charAt(0) != '(' ||
                parametersStructure.charAt(parametersStructure.length()-1) != ')')
            throw new InvalidStructureException();

        parametersStructure = parametersStructure.substring(1, parametersStructure.length()-1);

        String[] parametersArray = parametersStructure.split(",",-1);

        for(String parameter:parametersArray){
            parameter = parameter.trim();
            if(!this.isAlphanumeric(parameter) || parameter.length() == 0 )
                throw new InvalidStructureException();
            parameters.add(parameter);
        }
        return parameters;
    }

    public ParsedStructure parse(String query) throws InvalidStructureException{
        query = query.trim();

        int lastCharOfFirstWord = this.endOfFirstWord(query);


        String name = query.substring(0,lastCharOfFirstWord);
        if(name.length() == 0)
            throw new InvalidStructureException();
        String restOfQuery = query.substring(lastCharOfFirstWord);
        restOfQuery = restOfQuery.trim();

        List<String> params = this.getParameters(restOfQuery);
        return new ParsedStructure(name, params.toArray(new String[0]));

    }

}
