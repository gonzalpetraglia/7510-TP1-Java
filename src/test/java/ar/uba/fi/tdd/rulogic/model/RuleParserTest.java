package ar.uba.fi.tdd.rulogic.model;

import ar.uba.fi.tdd.rulogic.model.exceptions.InvalidRuleException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.MockitoAnnotations.initMocks;

public class RuleParserTest {

    @Mock
    private Rule ruleMock;


    @InjectMocks
    private RuleParser ruleParser;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
    }

    @Test
    public void testSingletonImplementation() {
        Assert.assertNotEquals(RuleParser.getInstance(), null);
        Assert.assertSame(RuleParser.getInstance(), RuleParser.getInstance());

    }

    @Test
    public void testRuleNameParsing() throws InvalidRuleException {
        RuleParser ruleParser = RuleParser.getInstance();
        Rule rule = ruleParser.parse("name(param1):-fact(param1,fixedParam).");
        Assert.assertEquals("name", rule.getName());
    }
    @Test
    public void testRuleNameParsingWithFinalSpace() throws InvalidRuleException{
        RuleParser ruleParser = RuleParser.getInstance();
        Rule rule = ruleParser.parse("name (param1):-fact(param1,fixedParam).");
        Assert.assertEquals("name", rule.getName());
    }
    @Test
    public void testRuleNameParsingWithSpaces() throws InvalidRuleException{
        RuleParser ruleParser = RuleParser.getInstance();
        Rule rule = ruleParser.parse(" name (param1):-fact( param1, fixedParam) . ");
        Assert.assertEquals("name", rule.getName());
    }
    @Test
    public void testRuleParameterParsingWithSpaces() throws InvalidRuleException{
        RuleParser ruleParser = RuleParser.getInstance();
        Rule rule = ruleParser.parse(" name (param1  , param):-fact(param1,fixedParam).");
        String[] expectedParameters = {"param1","param"};
        Assert.assertArrayEquals(expectedParameters, rule.getParams());
        Assert.assertEquals("name", rule.getName());
    }

    @Test
    public void testRuleParameterParsingWithoutSpaces() throws InvalidRuleException{
        RuleParser ruleParser = RuleParser.getInstance();
        Rule rule = ruleParser.parse("name(param1,param2):-fact(param1,fixedParam).");
        String[] expectedParameters = {"param1","param2"};
        Assert.assertArrayEquals(expectedParameters, rule.getParams());
        Assert.assertEquals("name", rule.getName());
    }

    @Test
    public void testRuleParameterParsingWithSpacesInParamsStructure() throws InvalidRuleException{
        RuleParser ruleParser = RuleParser.getInstance();
        Rule rule = ruleParser.parse("name (param1,  param2):-fact(param1,param2).");
        String[] expectedParameters = {"param1","param2"};
        Assert.assertArrayEquals(expectedParameters, rule.getParams());
        Assert.assertEquals("name", rule.getName());
    }

    @Test(expected = InvalidRuleException.class)
    public void testRuleParameterParsingWithInvalidName() throws InvalidRuleException{
        RuleParser ruleParser = RuleParser.getInstance();
        Rule rule = ruleParser.parse("na me (param1):-fact(param1,fixedParam).");
    }
    @Test(expected = InvalidRuleException.class)
    public void testRuleParameterParsingWithInvalidNameBecauseComma() throws InvalidRuleException{
        RuleParser ruleParser = RuleParser.getInstance();
        Rule rule = ruleParser.parse("nam,e (param1):-fact(param1,fixedParam).");
    }

    @Test(expected = InvalidRuleException.class)
    public void testRuleParameterParsingWithInvalidParamBecauseDot() throws InvalidRuleException{
        RuleParser ruleParser = RuleParser.getInstance();
        Rule rule = ruleParser.parse("n.ame( para.m1 , param2):-fact(param1,param2).");
    }
    @Test(expected = InvalidRuleException.class)
    public void testRuleParameterParsingWithInvalidLastParameter() throws InvalidRuleException{
        RuleParser ruleParser = RuleParser.getInstance();
        Rule rule = ruleParser.parse("name( param1 , ):-fact(param1,fixedParam).");
    }
    @Test
    public void testRuleParameterParsingWithOnlyOneParameter() throws InvalidRuleException{
        RuleParser ruleParser = RuleParser.getInstance();
        Rule rule = ruleParser.parse("name( param1 ):-fact(param1,fixedParam). ");
        String[] expectedParameters = {"param1"};
        Assert.assertArrayEquals(expectedParameters, rule.getParams());
        Assert.assertEquals("name", rule.getName());
    }

    @Test(expected = InvalidRuleException.class)
    public void testRuleParameterParsingWithoutName() throws InvalidRuleException{
        RuleParser ruleParser = RuleParser.getInstance();
        Rule rule = ruleParser.parse("  ( param1 ):-fact(param1,fixedParam).  ");
    }


    @Test
    public void testRuleWithOneAbstractQuery() throws InvalidRuleException{
        RuleParser ruleParser = RuleParser.getInstance();
        Rule rule = ruleParser.parse("  name(param1) :-fact(param1,fixedParam). ");

        AbstractQuery[] queries = rule.getAbstractQueries();

        Assert.assertEquals(1, queries.length);
        Assert.assertEquals("fact",queries[0].getName());
        Assert.assertEquals("param1",queries[0].getParams()[0]);
        Assert.assertEquals("fixedParam",queries[0].getParams()[1]);


    }

    @Test
    public void testRuleTwoAbstractQueries() throws InvalidRuleException{
        RuleParser ruleParser = RuleParser.getInstance();
        Rule rule = ruleParser.parse("  name(param1, param2) :-fact(param1,param2), fact2(param2,param1). ");
        AbstractQuery[] queries = rule.getAbstractQueries();

        Assert.assertEquals(2, queries.length);

        Assert.assertEquals("fact",queries[0].getName());
        Assert.assertEquals("param1",queries[0].getParams()[0]);
        Assert.assertEquals("param2",queries[0].getParams()[1]);

        Assert.assertEquals("fact2",queries[1].getName());
        Assert.assertEquals("param2",queries[1].getParams()[0]);
        Assert.assertEquals("param1",queries[1].getParams()[1]);
    }

    @Test(expected = InvalidRuleException.class)
    public void testRuleLastQueryInvalid() throws InvalidRuleException{
        RuleParser ruleParser = RuleParser.getInstance();
        Rule rule = ruleParser.parse("  name (param1, param2):-fact(param1,fixedParam),fact(param1,). ");
    }

    @Test(expected = InvalidRuleException.class)
    public void testRuleWithBadInvalidQueryStructure() throws InvalidRuleException{
        RuleParser ruleParser = RuleParser.getInstance();
        Rule rule = ruleParser.parse("  name (param1, param2):-fact(param1,fixedParam),. ");
    }

    @Test(expected = InvalidRuleException.class)
    public void testRuleWithoutHeader() throws InvalidRuleException{
        RuleParser ruleParser = RuleParser.getInstance();
        Rule rule = ruleParser.parse("  fact(param1,fixedParam), fact2(param1,fixedParam). ");
    }

    @Test(expected = InvalidRuleException.class)
    public void testRuleWithMissingFinalDot() throws InvalidRuleException{
        RuleParser ruleParser = RuleParser.getInstance();
        Rule rule = ruleParser.parse("  name (param1, param2):-fact(param1,fixedParam) ");
    }

    @Test
    public void testRuleWithTwoQueriesWithDifferentLengths() throws InvalidRuleException {
        RuleParser ruleParser = RuleParser.getInstance();
        Rule rule = ruleParser.parse("  name (param1, param2):-fact(param1,fixedParam), fact2(param2) .");


        AbstractQuery[] queries = rule.getAbstractQueries();

        Assert.assertEquals(2, queries.length);
        Assert.assertEquals("fact", queries[0].getName());
        Assert.assertEquals("param1", queries[0].getParams()[0]);
        Assert.assertEquals("fixedParam", queries[0].getParams()[1]);

        Assert.assertEquals("fact2", queries[1].getName());
        Assert.assertEquals("param2", queries[1].getParams()[0]);
    }

    @Test(expected = InvalidRuleException.class)
    public void testRuleWithTwoQueriesWithInvalidQuery() throws InvalidRuleException{
        RuleParser ruleParser = RuleParser.getInstance();
        Rule rule = ruleParser.parse("  name (param1, param2):-(param1,fixedParam), fact2(param). ");
    }



}


