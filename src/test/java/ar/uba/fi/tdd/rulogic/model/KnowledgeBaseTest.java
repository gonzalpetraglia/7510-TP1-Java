package ar.uba.fi.tdd.rulogic.model;

import static org.mockito.MockitoAnnotations.initMocks;

import ar.uba.fi.tdd.rulogic.model.exceptions.FileNotFoundException;
import ar.uba.fi.tdd.rulogic.model.exceptions.InvalidDatabaseException;
import ar.uba.fi.tdd.rulogic.model.exceptions.InvalidQueryException;
import ar.uba.fi.tdd.rulogic.model.exceptions.NoDatabaseException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

import java.net.URL;
import java.nio.file.NoSuchFileException;

public class KnowledgeBaseTest {

	@InjectMocks
	private KnowledgeBase knowledgeBase;

	@Before
	public void setUp() throws Exception {
		initMocks(this);
		this.knowledgeBase.useDatabase("src/main/resources/rules.db");
	}

    @Test
    public void testJavierEsVaron() throws Exception{

        Assert.assertTrue(this.knowledgeBase.answer("varon (javier)."));

    }


    @Test
    public void testMariaEsMujer() throws Exception{

        Assert.assertTrue(this.knowledgeBase.answer("mujer (maria)"));

    }



    @Test
    public void testAlejandroNoEsMujer() throws Exception{

        Assert.assertFalse(this.knowledgeBase.answer("mujer (alejandro)"));

    }


    @Test
    public void testAlejandroNoEsHijoDePedro() throws Exception{

        Assert.assertFalse(this.knowledgeBase.answer("hijo (alejandro,pedro)"));

    }


    @Test
    public void testAlejandroEsHijoDeRoberto() throws Exception{

        Assert.assertTrue(this.knowledgeBase.answer("hijo (alejandro,roberto)"));

    }


    @Test
    public void testRobertoEsPadreDeCecilia() throws Exception{

        Assert.assertTrue(this.knowledgeBase.answer("padre (roberto,cecilia)"));

    }


    @Test(expected = InvalidQueryException.class)
    public void testInvalidQuery() throws Exception{
        this.knowledgeBase.answer("mujer");

    }



    @Test(expected = InvalidDatabaseException.class)
    public void testInvalidDatabaseOne() throws InvalidDatabaseException,
            FileNotFoundException{
        this.knowledgeBase.useDatabase("src/main/resources/invalid1.db");

    }
    @Test(expected = InvalidDatabaseException.class)
    public void testInvalidDatabaseTwo() throws InvalidDatabaseException,
            FileNotFoundException{
        this.knowledgeBase.useDatabase("src/main/resources/invalid2.db");

    }
    @Test(expected = InvalidDatabaseException.class)
    public void testInvalidDatabaseThree() throws InvalidDatabaseException,
            FileNotFoundException{
        this.knowledgeBase.useDatabase("src/main/resources/invalid3.db");

    }
    @Test(expected = InvalidDatabaseException.class)
    public void testInvalidDatabaseFour() throws InvalidDatabaseException,
            FileNotFoundException{
        this.knowledgeBase.useDatabase("src/main/resources/invalid4.db");

    }

    @Test(expected = InvalidDatabaseException.class)
    public void testInvalidDatabaseFive() throws InvalidDatabaseException,
            FileNotFoundException{
        this.knowledgeBase.useDatabase("src/main/resources/invalid5.db");

    }

    @Test
    public void testAnimalsDatabase() throws InvalidDatabaseException, NoDatabaseException,
            InvalidQueryException, FileNotFoundException{
        this.knowledgeBase.useDatabase("src/main/resources/onlyFacts.db");
        Assert.assertTrue(this.knowledgeBase.answer("cat(silvester)."));
        Assert.assertTrue(this.knowledgeBase.answer("cat(tom)."));
        Assert.assertFalse(this.knowledgeBase.answer("cat(tweety)."));
        Assert.assertTrue(this.knowledgeBase.answer("bird(tweety)."));
        Assert.assertTrue(this.knowledgeBase.answer("dog(pluto)."));
        Assert.assertTrue(this.knowledgeBase.answer("mouse(jerry)."));
        Assert.assertFalse(this.knowledgeBase.answer("cat(dog)."));
    }
    @Test
    public void testAnimalsWithEmptyLinesDatabase() throws InvalidDatabaseException, NoDatabaseException,
            InvalidQueryException, FileNotFoundException{
        this.knowledgeBase.useDatabase("src/main/resources/onlyFactsWithEmptyLines.db");
        Assert.assertTrue(this.knowledgeBase.answer("cat(silvester)."));
        Assert.assertTrue(this.knowledgeBase.answer("cat(tom)."));
        Assert.assertFalse(this.knowledgeBase.answer("cat(tweety)."));
        Assert.assertTrue(this.knowledgeBase.answer("bird(tweety)."));
        Assert.assertTrue(this.knowledgeBase.answer("dog(pluto)."));
        Assert.assertTrue(this.knowledgeBase.answer("mouse(jerry)."));
        Assert.assertFalse(this.knowledgeBase.answer("cat(dog)."));
    }

    @Test(expected = FileNotFoundException.class)
    public void testInexistentDatabase() throws InvalidDatabaseException, NoDatabaseException,
            InvalidQueryException, FileNotFoundException{
        this.knowledgeBase.useDatabase("src/main/resources/inexistentFile.db");
    }

    @Test(expected = NoDatabaseException.class)
    public void testDatabaseNotSet() throws InvalidDatabaseException, NoDatabaseException,
            InvalidQueryException, FileNotFoundException{
        this.knowledgeBase = new KnowledgeBase();
        knowledgeBase.answer("cat(silvester)");
    }
}
