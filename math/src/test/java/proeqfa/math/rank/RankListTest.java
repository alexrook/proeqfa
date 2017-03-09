package proeqfa.math.rank;

import java.util.ArrayList;
import java.util.List;
import org.junit.*;
import static org.junit.Assert.*;

/**
 * @author moroz
 */
public class RankListTest {

    public interface IRankListTestData {

        List<RankList.RankedObject[]> getOrderedRankedObjectsArrayList();

    }

    private IRankListTestData docData;

    public RankListTest() {
    }

    @Before
    public void setUp() {
        docData = new IRankListTestData() {
            @Override
            public List<RankList.RankedObject[]> getOrderedRankedObjectsArrayList() {
                List<RankList.RankedObject[]> ret = new ArrayList<>(3);
                RankList.RankedObject[] expert1 = new RankList.RankedObject[3];
                //expert 1 said O1>O3>O2 --> rank=natural order
                expert1[0] = new RankList.RankedObject(1);
                expert1[0].setRank(1);
                expert1[1] = new RankList.RankedObject(3);
                expert1[1].setRank(2);
                expert1[0].setLink(RankList.RankedObjectsLink.MORE);
                expert1[0].setNextRankedObject(expert1[1]);
                expert1[2] = new RankList.RankedObject(2);
                expert1[2].setRank(3);
                expert1[1].setLink(RankList.RankedObjectsLink.MORE);
                expert1[1].setNextRankedObject(expert1[2]);
                ret.add(expert1);
                
                return ret;
            }
        };
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of fromArray method, of class RankList.
     */
    @Test
    public void testFromArray() {
        System.out.println("fromArray");
        fail("The test case is a prototype.");
    }

    /**
     * Test of toRankedObjectVector method, of class RankList.
     */
    @Test
    public void testToRankedObjectVector() {
        System.out.println("toRankedObjectVector");
        fail("The test case is a prototype.");
    }

}
