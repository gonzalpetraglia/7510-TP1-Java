package ar.uba.fi.tdd.rulogic.model;

import ar.uba.fi.tdd.rulogic.model.exceptions.InvalidFactException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

import static org.mockito.MockitoAnnotations.initMocks;

public class FactTest {

    @InjectMocks
    private FactCollection factCollection;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
    }

    @Test
    public void testBasicFactMatches(){
        Fact fact = new Fact("name",new String[]{"param1","param2"});
        Query query = new Query("name",new String[]{"param1","param2"});

        Assert.assertTrue(fact.matches(query));
    }

    @Test
    public void testEmptyFactCollection(){
        factCollection = new FactCollection(new Fact[0]);
        Query query = new Query("name",new String[]{"param1","param2"});

        Assert.assertFalse(factCollection.matches(query));
    }

    @Test
    public void testSingleElementFactCollection(){
        Fact fact = new Fact("name",new String[]{"param1","param2"});
        Fact[] facts = new Fact[1];
        facts[0] = fact;
        factCollection = new FactCollection(facts);
        Query query = new Query("name",new String[]{"param1","param2"});

        Assert.assertTrue(factCollection.matches(query));
    }

    @Test
    public void testMultipleElementFactCollection(){
        Fact fact = new Fact("name",new String[]{"param1","param2"});
        Fact[] facts = new Fact[2];
        facts[0] = fact;
        fact = new Fact("name2",new String[]{"param3","param4"});
        facts[1] = fact;
        factCollection = new FactCollection(facts);
        Query query = new Query("name",new String[]{"param1","param2"});
        Query query2 = new Query("name2",new String[]{"param3","param4"});

        Assert.assertTrue(factCollection.matches(query));
        Assert.assertTrue(factCollection.matches(query2));

    }

    @Test
    public void testMultipleElementFactCollectionInvertedNames(){
        Fact fact = new Fact("name",new String[]{"param1","param2"});
        Fact[] facts = new Fact[2];
        facts[0] = fact;
        fact = new Fact("name2",new String[]{"param3","param4"});
        facts[1] = fact;
        factCollection = new FactCollection(facts);
        Query query = new Query("name2",new String[]{"param1","param2"});
        Query query2 = new Query("name3",new String[]{"param3","param4"});

        Assert.assertFalse(factCollection.matches(query));
        Assert.assertFalse(factCollection.matches(query2));

    }
}
