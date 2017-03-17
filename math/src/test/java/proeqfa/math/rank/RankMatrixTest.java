package proeqfa.math.rank;

import org.junit.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static java.lang.System.out;

/**
 * Created by moroz on 16.03.17.
 */
public class RankMatrixTest {

    private List<RankChain> docData;

    @Before
    public void setUp() {
        //set rank here for testing purposes
        docData = new ArrayList<>(3);
        //expert A said O1>O3>O2 --> rank=natural order
        RankChain.RankedObject[] expertA = new RankChain.RankedObject[3];
        expertA[0] = new RankChain.RankedObject(1);
        expertA[0].setRank(1);
        expertA[1] = new RankChain.RankedObject(3);
        expertA[1].setRank(2);
        expertA[1].setPreviousObjectRel(RankChain.RankedObjectsLink.MORE);

        expertA[2] = new RankChain.RankedObject(2);
        expertA[2].setRank(3);
        expertA[2].setPreviousObjectRel(RankChain.RankedObjectsLink.MORE);

        RankChain rankChainA = RankChain.fromArray(expertA, new NaturalOrderPosition2Rank());
        rankChainA.setExpertCompetenceFactor(0.5);
        docData.add(rankChainA);

        //expert B said O3>O2>O1 --> rank=natural order
        RankChain.RankedObject[] expertB = new RankChain.RankedObject[3];
        expertB[0] = new RankChain.RankedObject(3);//O3
        expertB[0].setRank(1);

        expertB[1] = new RankChain.RankedObject(2);//O2
        expertB[1].setRank(2);
        expertB[1].setPreviousObjectRel(RankChain.RankedObjectsLink.MORE);

        expertB[2] = new RankChain.RankedObject(1);//O1
        expertB[2].setRank(3);
        expertB[2].setPreviousObjectRel(RankChain.RankedObjectsLink.MORE);

        RankChain rankChainB = RankChain.fromArray(expertB, new NaturalOrderPosition2Rank());
        rankChainB.setExpertCompetenceFactor(0.01);
        docData.add(rankChainB);

        //expert C said O1>O2>O3 --> rank=natural order
        RankChain.RankedObject[] expertC = new RankChain.RankedObject[3];
        expertC[0] = new RankChain.RankedObject(1);//O1
        expertC[0].setRank(1);
        expertC[1] = new RankChain.RankedObject(2);//O2
        expertC[1].setRank(2);
        expertC[1].setPreviousObjectRel(RankChain.RankedObjectsLink.MORE);

        expertC[2] = new RankChain.RankedObject(3);//O3
        expertC[2].setRank(3);
        expertC[2].setPreviousObjectRel(RankChain.RankedObjectsLink.MORE);

        RankChain rankChainC = RankChain.fromArray(expertC, new NaturalOrderPosition2Rank());
        rankChainC.setExpertCompetenceFactor(0.49);
        docData.add(rankChainC);

    }

    @Test
    public void testGetRankedObjectsSums01() {
        out.println("testGetRankedObjectsSums");
        RankMatrix instance = new RankMatrix(3);
        for (RankChain rankChain : docData) {
            assertEquals(rankChain.size(), instance.getRankedObjectsCount());
            instance.addRankChain(rankChain);
        }

        double[] expectedRanks = new double[]{1.02, 2.5, 2.48};//see project docs
        double[] actualRanks = instance.getRankedObjectsSums();
        out.println("actual ranks: "+Arrays.toString(actualRanks));
        assertArrayEquals(expectedRanks, actualRanks, 0);
        double expMaxRank=expectedRanks[1];
        assertEquals(2,instance.getMaxRankObject().getId());
        assertEquals(expMaxRank,instance.getMaxRankObject().getRank(),0);

        double expMinRank=expectedRanks[0];
        assertEquals(1,instance.getMinRankObject().getId());
        assertEquals(expMinRank,instance.getMinRankObject().getRank(),0);

    }
}
