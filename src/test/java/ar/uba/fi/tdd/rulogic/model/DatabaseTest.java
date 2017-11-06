package ar.uba.fi.tdd.rulogic.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

import static org.mockito.MockitoAnnotations.initMocks;

public class DatabaseTest {

    @InjectMocks
    private Database database;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
    }

    @Test
    public void testEmptyDatabase(){
        database = new Database(new Fact[0], new Rule[0]);
        Query query = new Query("name",new String[]{"param1","param2"});

        Assert.assertFalse(database.answer(query));
    }




    @Test
    public void testOnlyFactsDatabase(){
        Fact fact = new Fact("name", new String[]{"param1", "param2"});
        Fact fact2 = new Fact("name2", new String[]{"param3", "param4"});
        Fact[] facts = new Fact[2];
        facts[0] = fact;
        facts[1] = fact2;
        database = new Database(facts, new Rule[0]);
        Query query = new Query("name", new String[]{"param1","param2"});
        Query query2 = new Query("name2", new String[]{"param3","param4"});

        Assert.assertTrue(database.answer(query));
        Assert.assertTrue(database.answer(query2));
    }

    @Test
    public void testOnlyRulesDatabase(){
        AbstractQuery[] oneAbstractQueries = new AbstractQuery[1];
        AbstractQuery[] anotherAbstractQueries = new AbstractQuery[1];
        Rule[] rules = new Rule[2];
        oneAbstractQueries[0] = new AbstractQuery("name", new String[]{"X","Y"});
        anotherAbstractQueries[0] = new AbstractQuery("name", new String[]{"Y","X"});


        rules[0] = new Rule("rule1", new String[]{"X", "Y"},
                oneAbstractQueries);
        rules[1] = new Rule("rule2", new String[]{"X", "Y"},
                anotherAbstractQueries);
        database = new Database(new Fact[0], rules);
        Query query = new Query("rule2",new String[]{"param1","param2"});

        Assert.assertFalse(database.answer(query));
    }
    @Test
    public void testCombinedDatabase(){
        Fact fact = new Fact("name", new String[]{"param1", "param2"});
        Fact fact2 = new Fact("name2", new String[]{"param3", "param4"});
        Fact[] facts = new Fact[2];
        facts[0] = fact;
        facts[1] = fact2;

        AbstractQuery[] oneAbstractQueries = new AbstractQuery[1];
        AbstractQuery[] anotherAbstractQueries = new AbstractQuery[1];
        Rule[] rules = new Rule[2];
        oneAbstractQueries[0] = new AbstractQuery("name", new String[]{"X","Y"});
        anotherAbstractQueries[0] = new AbstractQuery("name", new String[]{"Y","X"});


        rules[0] = new Rule("rule1", new String[]{"X", "Y"},
                oneAbstractQueries);
        rules[1] = new Rule("rule2", new String[]{"X", "Y"},
                anotherAbstractQueries);

        database = new Database(facts, rules);
        Query query = new Query("name", new String[]{"param1","param2"});
        Query query2 = new Query("name2", new String[]{"param3","param4"});
        Query query3 = new Query("rule1", new String[]{"param1","param2"});
        Query query4 = new Query("rule2", new String[]{"param2","param1"});
        Query falseQuery1 = new Query("rule1", new String[]{"param3","param2"});
        Query falseQuery2 = new Query("name", new String[]{"param","param"});

        Assert.assertTrue(database.answer(query));
        Assert.assertTrue(database.answer(query2));
        Assert.assertTrue(database.answer(query3));
        Assert.assertTrue(database.answer(query4));

        Assert.assertFalse(database.answer(falseQuery1));
        Assert.assertFalse(database.answer(falseQuery2));

    }

}
