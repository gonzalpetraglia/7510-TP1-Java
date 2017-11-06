package ar.uba.fi.tdd.rulogic.model;

import ar.uba.fi.tdd.rulogic.model.exceptions.InvalidQueryException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

import static org.mockito.MockitoAnnotations.initMocks;

public class QueryParserTest {

    @InjectMocks
    private QueryParser queryParser;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
    }

    @Test
    public void testSingletonImplementation() {
        Assert.assertNotEquals(QueryParser.getInstance(), null);
        Assert.assertSame(QueryParser.getInstance(), QueryParser.getInstance());

    }

    @Test
    public void testQueryNameParsing() throws InvalidQueryException {
        QueryParser queryParser = QueryParser.getInstance();
        Query query = queryParser.parse("name(param1)");
        Assert.assertEquals("name", query.getName());
    }
    @Test
    public void testQueryNameParsingWithFinalSpace() throws InvalidQueryException{
        QueryParser queryParser = QueryParser.getInstance();
        Query query = queryParser.parse("name (param1)");
        Assert.assertEquals("name", query.getName());
    }
    @Test
    public void testQueryNameParsingWithSpaces() throws InvalidQueryException{
        QueryParser queryParser = QueryParser.getInstance();
        Query query = queryParser.parse(" name (param1)");
        Assert.assertEquals("name", query.getName());
    }
    @Test
    public void testQueryParameterParsingWithSpaces() throws InvalidQueryException{
        QueryParser queryParser = QueryParser.getInstance();
        Query query = queryParser.parse(" name (param1,param2)");
        String[] expectedParameters = {"param1","param2"};
        Assert.assertArrayEquals(expectedParameters, query.getParams());
        Assert.assertEquals("name", query.getName());
    }

    @Test
    public void testQueryParameterParsingWithoutSpaces() throws InvalidQueryException{
        QueryParser queryParser = QueryParser.getInstance();
        Query query = queryParser.parse("name(param1,param2)");
        String[] expectedParameters = {"param1","param2"};
        Assert.assertArrayEquals(expectedParameters, query.getParams());
        Assert.assertEquals("name", query.getName());
    }

    @Test
    public void testQueryParameterParsingWithSpacesInParamsStructure() throws InvalidQueryException{
        QueryParser queryParser = QueryParser.getInstance();
        Query query = queryParser.parse("name( param1 , param2)");
        String[] expectedParameters = {"param1","param2"};
        Assert.assertArrayEquals(expectedParameters, query.getParams());
        Assert.assertEquals("name", query.getName());
    }

    @Test(expected = InvalidQueryException.class)
    public void testQueryParameterParsingWithInvalidName() throws InvalidQueryException{
        QueryParser queryParser = QueryParser.getInstance();
        Query query = queryParser.parse("n ame( param1 , param2)");
    }
    @Test(expected = InvalidQueryException.class)
    public void testQueryParameterParsingWithInvalidNameBecauseComma() throws InvalidQueryException{
        QueryParser queryParser = QueryParser.getInstance();
        Query query = queryParser.parse("n,ame( param1 , param2)");
    }

    @Test(expected = InvalidQueryException.class)
    public void testQueryParameterParsingWithInvalidParamBecauseDot() throws InvalidQueryException{
        QueryParser queryParser = QueryParser.getInstance();
        Query query = queryParser.parse("name( para.m1 , param2)");
    }
    @Test(expected = InvalidQueryException.class)
    public void testQueryParameterParsingWithInvalidLastParameter() throws InvalidQueryException{
        QueryParser queryParser = QueryParser.getInstance();
        Query query = queryParser.parse("name( param1 , )");
    }
    @Test
    public void testQueryParameterParsingWithOnlyOneParameter() throws InvalidQueryException{
        QueryParser queryParser = QueryParser.getInstance();
        Query query = queryParser.parse("name( param1 ) ");
        String[] expectedParameters = {"param1"};
        Assert.assertArrayEquals(expectedParameters, query.getParams());
        Assert.assertEquals("name", query.getName());
    }

    @Test(expected = InvalidQueryException.class)
    public void testQueryParameterParsingWithoutName() throws InvalidQueryException{
        QueryParser queryParser = QueryParser.getInstance();
        Query query = queryParser.parse("  ( param1 ) ");
    }


    @Test(expected = InvalidQueryException.class)
    public void testQueryWithoutParamStructure() throws InvalidQueryException{
        QueryParser queryParser = QueryParser.getInstance();
        Query query = queryParser.parse("  name ");
    }

    @Test(expected = InvalidQueryException.class)
    public void testEmptyQuery() throws InvalidQueryException{
        QueryParser queryParser = QueryParser.getInstance();
        Query query = queryParser.parse("   ");
    }


}
