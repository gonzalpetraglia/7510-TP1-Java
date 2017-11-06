package ar.uba.fi.tdd.rulogic.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

import static org.mockito.MockitoAnnotations.initMocks;

public class DatabaseParserTest {

    @InjectMocks
    private DatabaseParser dbParser;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
    }

    @Test
    public void testSingletonImplementation() {
        Assert.assertNotEquals(DatabaseParser.getInstance(), null);
        Assert.assertSame(DatabaseParser.getInstance(), DatabaseParser.getInstance());

    }

}
