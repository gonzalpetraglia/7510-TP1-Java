package ar.uba.fi.tdd.rulogic.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

import static org.mockito.MockitoAnnotations.initMocks;

public class RuleTest {

    @InjectMocks
    private FactCollection factCollection;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
    }

    @Test
    public void testBasicRule(){
        AbstractQuery[] oneAbstractQueries = new AbstractQuery[1];
        AbstractQuery[] anotherAbstractQueries = new AbstractQuery[1];
        oneAbstractQueries[0] = new AbstractQuery("name", new String[]{"X","Y"});
        anotherAbstractQueries[0] = new AbstractQuery("name", new String[]{"Y","X"});


        Rule ruleOne = new Rule("rule1", new String[]{"X", "Y"},
                oneAbstractQueries);
        Rule anotherRule = new Rule("rule2", new String[]{"X", "Y"},
                anotherAbstractQueries);
        Query queryRule1 = new Query("rule1", new String[]{"param1","param2"});
        Query queryRule2 = new Query("rule2", new String[]{"param2","param3"});
        Query queryRuleWrongLength = new Query("rule2", new String[]{"param2"});
        Query queryRuleWrongName = new Query("rule", new String[]{"param2","param3"});

        Assert.assertTrue(ruleOne.matches(queryRule1));
        Assert.assertTrue(anotherRule.matches(queryRule2));
        Assert.assertFalse(ruleOne.matches(queryRule2));
        Assert.assertFalse(anotherRule.matches(queryRuleWrongLength));
        Assert.assertFalse(ruleOne.matches(queryRuleWrongName));
        try {
            Conjuction conjuction = ruleOne.getEquivalentConjuction(queryRule1);
            Fact fact = new Fact("name", new String[]{"param1","param2"});
            Assert.assertTrue(fact.matches(conjuction.getEquivalentQueries()[0]));
        }catch(DoesntMatchQuery exc){
            Assert.assertFalse(true);

        }

    }

    @Test(expected = DoesntMatchQuery.class)
    public void testWrongRuleUsage() throws DoesntMatchQuery{
        AbstractQuery[] oneAbstractQueries = new AbstractQuery[1];
        oneAbstractQueries[0] = new AbstractQuery("name", new String[]{"X","Y"});

        Rule ruleOne = new Rule("rule1", new String[]{"X", "Y"},
                oneAbstractQueries);
        Query queryRule1 = new Query("rule2", new String[]{"param1","param2"});

        ruleOne.getEquivalentConjuction(queryRule1);
    }


    @Test(expected = DoesntMatchQuery.class)
    public void testWrongLengthRuleUsage() throws DoesntMatchQuery{
        AbstractQuery[] oneAbstractQueries = new AbstractQuery[1];
        oneAbstractQueries[0] = new AbstractQuery("name", new String[]{"X","Y"});

        Rule ruleOne = new Rule("rule1", new String[]{"X", "Y", "Z"},
                oneAbstractQueries);
        Query queryRule1 = new Query("rule1", new String[]{"param1","param2"});

        ruleOne.getEquivalentConjuction(queryRule1);
    }

    @Test
    public void testRuleWithFixedParameter() throws DoesntMatchQuery{
        AbstractQuery[] oneAbstractQueries = new AbstractQuery[1];
        oneAbstractQueries[0] = new AbstractQuery("name", new String[]{"X","Y", "fixed"});

        Rule ruleOne = new Rule("rule1", new String[]{"X", "Y"},
                oneAbstractQueries);
        Query queryRule1 = new Query("rule1", new String[]{"param1","param2"});
        Fact fact = new Fact("name", new String[]{"param1","param2","fixed"});
        Conjuction conjuction = ruleOne.getEquivalentConjuction(queryRule1);
        Assert.assertTrue(fact.matches(conjuction.getEquivalentQueries()[0]));
    }
}
