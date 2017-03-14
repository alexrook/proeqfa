package proeqfa.math.rank;

import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.System.out;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * @author moroz
 */
public class RankChainFromExpertTest {

    public interface IRankChainTestData {

        List<RankChainFromExpert.RankedObject[]> getOrderedRankedObjectsArrayList();

    }

    private IRankChainTestData docData, docData02;
    private List<IRankChainTestData> rankChainTestDatas;

    private Pattern testDataPattern = Pattern.compile("([>~]?)O(\\d+)\\((\\d+.?\\d?)\\)");

    public RankChainFromExpertTest() {
    }

    @Before
    public void setUp() {

        docData = new IRankChainTestData() {
            @Override
            public List<RankChainFromExpert.RankedObject[]> getOrderedRankedObjectsArrayList() {
                List<RankChainFromExpert.RankedObject[]> ret = new ArrayList<>(3);
                //expert A said O1>O3>O2 --> rank=natural order
                RankChainFromExpert.RankedObject[] expertA = new RankChainFromExpert.RankedObject[3];
                expertA[0] = new RankChainFromExpert.RankedObject(1);
                expertA[0].setRank(1);
                expertA[1] = new RankChainFromExpert.RankedObject(3);
                expertA[1].setRank(2);
                expertA[1].setPreviosObjectRel(RankChainFromExpert.RankedObjectsLink.MORE);

                expertA[2] = new RankChainFromExpert.RankedObject(2);
                expertA[2].setRank(3);
                expertA[2].setPreviosObjectRel(RankChainFromExpert.RankedObjectsLink.MORE);

                ret.add(expertA);
                //expert B said O3>O2>O1 --> rank=natural order
                RankChainFromExpert.RankedObject[] expertB = new RankChainFromExpert.RankedObject[3];
                expertB[0] = new RankChainFromExpert.RankedObject(3);//O3
                expertB[0].setRank(1);

                expertB[1] = new RankChainFromExpert.RankedObject(2);//O2
                expertB[1].setRank(2);
                expertB[1].setPreviosObjectRel(RankChainFromExpert.RankedObjectsLink.MORE);

                expertB[2] = new RankChainFromExpert.RankedObject(1);//O1
                expertB[2].setRank(3);
                expertB[2].setPreviosObjectRel(RankChainFromExpert.RankedObjectsLink.MORE);

                ret.add(expertB);
                //expert C said O1>O2>O3 --> rank=natural order
                RankChainFromExpert.RankedObject[] expertC = new RankChainFromExpert.RankedObject[3];
                expertC[0] = new RankChainFromExpert.RankedObject(1);//O1
                expertC[0].setRank(1);
                expertC[1] = new RankChainFromExpert.RankedObject(2);//O2
                expertC[1].setRank(2);
                expertC[1].setPreviosObjectRel(RankChainFromExpert.RankedObjectsLink.MORE);

                expertC[2] = new RankChainFromExpert.RankedObject(3);//O3
                expertC[2].setRank(3);
                expertC[2].setPreviosObjectRel(RankChainFromExpert.RankedObjectsLink.MORE);

                ret.add(expertC);
                return ret;
            }

        };

        docData02 = new IRankChainTestData() {
            @Override
            public List<RankChainFromExpert.RankedObject[]> getOrderedRankedObjectsArrayList() {
                List<RankChainFromExpert.RankedObject[]> ret = new ArrayList<>(1);
                //expert A said O1>O2>O3~O4~O5>O6>O7>O8>O9~O10 --> rank=natural order
                RankChainFromExpert.RankedObject[] expA = new RankChainFromExpert.RankedObject[10];

                expA[0] = new RankChainFromExpert.RankedObject(1);
                expA[0].setRank(1);
                expA[1] = new RankChainFromExpert.RankedObject(2);
                expA[1].setPreviosObjectRel(RankChainFromExpert.RankedObjectsLink.MORE);
                expA[1].setRank(2);
                expA[2] = new RankChainFromExpert.RankedObject(3);
                expA[2].setPreviosObjectRel(RankChainFromExpert.RankedObjectsLink.MORE);
                expA[2].setRank(4);
                expA[3] = new RankChainFromExpert.RankedObject(4);
                expA[3].setPreviosObjectRel(RankChainFromExpert.RankedObjectsLink.SAME);
                expA[3].setRank(4);
                expA[4] = new RankChainFromExpert.RankedObject(5);
                expA[4].setPreviosObjectRel(RankChainFromExpert.RankedObjectsLink.SAME);
                expA[4].setRank(4);
                expA[5] = new RankChainFromExpert.RankedObject(6);
                expA[5].setPreviosObjectRel(RankChainFromExpert.RankedObjectsLink.MORE);
                expA[5].setRank(6);
                expA[6] = new RankChainFromExpert.RankedObject(7);
                expA[6].setPreviosObjectRel(RankChainFromExpert.RankedObjectsLink.MORE);
                expA[6].setRank(7);
                expA[7] = new RankChainFromExpert.RankedObject(8);
                expA[7].setPreviosObjectRel(RankChainFromExpert.RankedObjectsLink.MORE);
                expA[7].setRank(8);
                expA[8] = new RankChainFromExpert.RankedObject(9);
                expA[8].setPreviosObjectRel(RankChainFromExpert.RankedObjectsLink.MORE);
                expA[8].setRank(9.5);
                expA[9] = new RankChainFromExpert.RankedObject(10);
                expA[9].setPreviosObjectRel(RankChainFromExpert.RankedObjectsLink.SAME);
                expA[9].setRank(9.5);
                ret.add(expA);
                return ret;
            }
        };

        try {

            File resDir = new File(this.getClass()
                    .getClassLoader()
                    .getResource("META-INF/rankchaintest/")
                    .toURI());

            String[] testDataFiles = resDir.list(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.startsWith("rankchaintest.");
                }
            });

            rankChainTestDatas = new ArrayList<>(testDataFiles.length);

            for (String testDataFile : testDataFiles) {
                out.println(testDataFile);
                BufferedReader reader = new BufferedReader(
                        new FileReader(resDir + "/" + testDataFile)
                );
                String tdata = reader.readLine();
                final String[] objects = tdata.split("\\s");

                final RankChainFromExpert.RankedObject[] reta = new RankChainFromExpert.RankedObject[objects.length];
                for (int i = 0; i < objects.length; i++) {
                    out.println(objects[i]);
                    Matcher m = testDataPattern.matcher(objects[i]);

                    if (m.matches()) {
                        RankChainFromExpert.RankedObject ro = new RankChainFromExpert
                                .RankedObject(Integer.valueOf(m.group(2)));
                        ro.setRank(Double.valueOf(m.group(3)));
                        String link = m.group(1);
                        if ((link!=null)&&(!link.isEmpty())) {
                            ro.setPreviosObjectRel(RankChainFromExpert.RankedObjectsLink.fromString(link));
                        }
                        reta[i] = ro;
                    }
                }

                IRankChainTestData exp = new IRankChainTestData() {
                    @Override
                    public List<RankChainFromExpert.RankedObject[]> getOrderedRankedObjectsArrayList() {
                        ArrayList<RankChainFromExpert.RankedObject[]> ret = new ArrayList<>();
                        ret.add(reta);
                        return ret;
                    }
                };

                rankChainTestDatas.add(exp);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void testFromArray_toRankVector_01() {
        System.out.println("fromArray");
        RankChainFromExpert.RankedObject[] rankedObjects
                = docData.getOrderedRankedObjectsArrayList().get(0);
        RankChainFromExpert instance = RankChainFromExpert.fromArray(rankedObjects, new NaturalOrderPosition2Rank());
        //  out.println(instance);
        assertEquals(rankedObjects.length, instance.size());
        double[] expectedVector = getRankVector(rankedObjects);
        out.println(Arrays.toString(expectedVector));
        assertArrayEquals(expectedVector, instance.toRankedObjectVector(), 0);

        rankedObjects
                = docData.getOrderedRankedObjectsArrayList().get(1);
        instance = RankChainFromExpert.fromArray(rankedObjects, new NaturalOrderPosition2Rank());
        //  out.println(instance);
        assertEquals(rankedObjects.length, instance.size());
        expectedVector = getRankVector(rankedObjects);
        out.println(Arrays.toString(expectedVector));
        assertArrayEquals(expectedVector, instance.toRankedObjectVector(), 0);

        rankedObjects
                = docData.getOrderedRankedObjectsArrayList().get(2);
        instance = RankChainFromExpert.fromArray(rankedObjects, new NaturalOrderPosition2Rank());
        //  out.println(instance);
        assertEquals(rankedObjects.length, instance.size());
        expectedVector = getRankVector(rankedObjects);
        out.println(Arrays.toString(expectedVector));
        assertArrayEquals(expectedVector, instance.toRankedObjectVector(), 0);

    }

    @Test
    public void testFromArray_toRankVector_02() {
        System.out.println("fromArray02");
        RankChainFromExpert.RankedObject[] rankedObjects
                = docData02.getOrderedRankedObjectsArrayList().get(0);
        RankChainFromExpert instance = RankChainFromExpert.fromArray(rankedObjects, new NaturalOrderPosition2Rank());
        out.println(instance);
        assertEquals(rankedObjects.length, instance.size());
        double[] expectedVector = getRankVector(rankedObjects);
        out.println(Arrays.toString(expectedVector));
        assertArrayEquals(expectedVector, instance.toRankedObjectVector(), 0);

    }

    public static double[] getRankVector(RankChainFromExpert.RankedObject[] rankedObjects) {
        double[] ret = new double[rankedObjects.length];
        for (RankChainFromExpert.RankedObject rankedObject : rankedObjects) {
            ret[rankedObject.getRankedObjectId() - 1] = rankedObject.getRank();
        }
        return ret;
    }

}
