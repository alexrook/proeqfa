package proeqfa.math.rank;

import java.util.ArrayList;
import java.util.List;
import org.junit.*;
import static org.junit.Assert.*;
import static java.lang.System.out;
import java.util.Arrays;

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
                //expert A said O1>O3>O2 --> rank=natural order
                RankList.RankedObject[] expertA = new RankList.RankedObject[3];
                expertA[0] = new RankList.RankedObject(1);
                expertA[0].setRank(1);
                expertA[1] = new RankList.RankedObject(3);
                expertA[1].setRank(2);
                expertA[0].setLink(RankList.RankedObjectsLink.MORE);
                expertA[0].setNextRankedObject(expertA[1]);
                expertA[2] = new RankList.RankedObject(2);
                expertA[2].setRank(3);
                expertA[1].setLink(RankList.RankedObjectsLink.MORE);
                expertA[1].setNextRankedObject(expertA[2]);
                ret.add(expertA);
                //expert B said O3>O2>O1 --> rank=natural order
                RankList.RankedObject[] expertB = new RankList.RankedObject[3];
                expertB[0] = new RankList.RankedObject(3);//O3
                expertB[0].setRank(1);
                expertB[1] = new RankList.RankedObject(2);//O2
                expertB[1].setRank(2);
                expertB[0].setLink(RankList.RankedObjectsLink.MORE);
                expertB[0].setNextRankedObject(expertB[1]);
                expertB[2] = new RankList.RankedObject(1);//O1
                expertB[2].setRank(3);
                expertB[1].setLink(RankList.RankedObjectsLink.MORE);
                expertB[1].setNextRankedObject(expertB[2]);
                ret.add(expertB);
                //expert C said O1>O2>O3 --> rank=natural order
                RankList.RankedObject[] expertC = new RankList.RankedObject[3];
                expertC[0] = new RankList.RankedObject(1);//O1
                expertC[0].setRank(1);
                expertC[1] = new RankList.RankedObject(2);//O2
                expertC[1].setRank(2);
                expertC[0].setNextRankedObject(expertC[1],
                        RankList.RankedObjectsLink.MORE);
                expertC[2] = new RankList.RankedObject(3);//O3
                expertC[2].setRank(3);
                expertC[1].setNextRankedObject(expertC[2],
                        RankList.RankedObjectsLink.MORE);
                ret.add(expertC);
                return ret;
            }

        };
    }

    @Test
    public void testFromArray_toRankVector_01() {
        System.out.println("fromArray");
        RankList.RankedObject[] rankedObjects
                = docData.getOrderedRankedObjectsArrayList().get(0);
        RankList instance = RankList.fromArray(rankedObjects);
        //  out.println(instance);
        assertEquals(rankedObjects.length, instance.size());
        double[] expectedVector = getRankVector(rankedObjects);
        out.println(Arrays.toString(expectedVector));
        assertArrayEquals(expectedVector, instance.toRankedObjectVector(), 0);

        rankedObjects
                = docData.getOrderedRankedObjectsArrayList().get(1);
        instance = RankList.fromArray(rankedObjects);
        //  out.println(instance);
        assertEquals(rankedObjects.length, instance.size());
        expectedVector = getRankVector(rankedObjects);
        out.println(Arrays.toString(expectedVector));
        assertArrayEquals(expectedVector, instance.toRankedObjectVector(), 0);

        rankedObjects
                = docData.getOrderedRankedObjectsArrayList().get(2);
        instance = RankList.fromArray(rankedObjects);
        //  out.println(instance);
        assertEquals(rankedObjects.length, instance.size());
        expectedVector = getRankVector(rankedObjects);
        out.println(Arrays.toString(expectedVector));
        assertArrayEquals(expectedVector, instance.toRankedObjectVector(), 0);

    }

    public static double[] getRankVector(RankList.RankedObject[] rankedObjects) {
        double[] ret = new double[rankedObjects.length];
        for (int i = 0; i < rankedObjects.length; i++) {
            ret[rankedObjects[i].getRankedObjectId() - 1] = rankedObjects[i].getRank();
        }
        return ret;
    }

}
