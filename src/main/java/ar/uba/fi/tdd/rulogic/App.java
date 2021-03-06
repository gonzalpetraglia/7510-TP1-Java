package ar.uba.fi.tdd.rulogic;

import ar.uba.fi.tdd.rulogic.model.KnowledgeBase;
import ar.uba.fi.tdd.rulogic.model.exceptions.FileNotFoundException;
import ar.uba.fi.tdd.rulogic.model.exceptions.InvalidDatabaseException;
import ar.uba.fi.tdd.rulogic.model.exceptions.InvalidQueryException;
import ar.uba.fi.tdd.rulogic.model.exceptions.NoDatabaseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Console application.
 *
 */
public class App
{
	public static void main(String[] args) throws IOException{
		boolean shouldContinue = true;
        KnowledgeBase knowledgeBase = new KnowledgeBase();
        String line;
		System.out.println("I shall answer all your questions!");
        System.out.println("\n\nUsage: \n Commands: \n\t/exit\tExits from the app." +
                "\n\t/use <database_file_name>\t Uses the file named as the database.");

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		while(shouldContinue)  {
            System.out.print(">");
            line = br.readLine();
            shouldContinue = !line.equals("/exit");
            if(!shouldContinue)
                continue;
            if(line.trim().length() == 0 )
                continue;

            if(line.length() >= 4 &&
                    line.split(" ")[0].equals("/use")){
                try{
                    knowledgeBase.useDatabase(line.split(" ")[1]);

                }catch(IndexOutOfBoundsException exc) {
                    System.out.println("Incorrect usage of /use.");
                }catch (InvalidDatabaseException exc){
                    System.out.println("The database provided is invalid.");
                }catch (FileNotFoundException exc){
                    System.out.println("The file wasnt found. ");
                }
                continue;
            }
            try{
                System.out.println(knowledgeBase.answer(line));
            }catch (InvalidQueryException exc){
                System.out.println("Invalid query");
            }catch (NoDatabaseException exc){
                System.out.println("No database in usage. Please use the /use command");
            }

        }

    }
}
