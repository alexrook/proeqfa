package proeqfa.math.rank;

import org.junit.Before;
import org.junit.Ignore;
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
public class RankChainTest {


    private List<RankChain.RankedObject[]> docData, docData02;

    /*
    * test files format:
    * O<id>(<rank>) <link>O<id>(rank) i.e O1(1) >O2(2.5) ~O3(2.5) ... (with space separator)
    * */
    public static final Pattern TEST_DATA_PATTERN =
            Pattern.compile("([>~]?)O(\\d+)\\((\\d+.?\\d?)\\)");// i.e O3(1) or >O2(2)

    public RankChainTest() {
    }

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
        expertA[1].setPreviosObjectRel(RankChain.RankedObjectsLink.MORE);

        expertA[2] = new RankChain.RankedObject(2);
        expertA[2].setRank(3);
        expertA[2].setPreviosObjectRel(RankChain.RankedObjectsLink.MORE);

        docData.add(expertA);
        //expert B said O3>O2>O1 --> rank=natural order
        RankChain.RankedObject[] expertB = new RankChain.RankedObject[3];
        expertB[0] = new RankChain.RankedObject(3);//O3
        expertB[0].setRank(1);

        expertB[1] = new RankChain.RankedObject(2);//O2
        expertB[1].setRank(2);
        expertB[1].setPreviosObjectRel(RankChain.RankedObjectsLink.MORE);

        expertB[2] = new RankChain.RankedObject(1);//O1
        expertB[2].setRank(3);
        expertB[2].setPreviosObjectRel(RankChain.RankedObjectsLink.MORE);

        docData.add(expertB);
        //expert C said O1>O2>O3 --> rank=natural order
        RankChain.RankedObject[] expertC = new RankChain.RankedObject[3];
        expertC[0] = new RankChain.RankedObject(1);//O1
        expertC[0].setRank(1);
        expertC[1] = new RankChain.RankedObject(2);//O2
        expertC[1].setRank(2);
        expertC[1].setPreviosObjectRel(RankChain.RankedObjectsLink.MORE);

        expertC[2] = new RankChain.RankedObject(3);//O3
        expertC[2].setRank(3);
        expertC[2].setPreviosObjectRel(RankChain.RankedObjectsLink.MORE);

        docData.add(expertC);


        docData02 = new ArrayList<>(1);
        //expert A said O1>O2>O3~O4~O5>O6>O7>O8>O9~O10 --> rank=natural order
        RankChain.RankedObject[] expA = new RankChain.RankedObject[10];

        expA[0] = new RankChain.RankedObject(1);
        expA[0].setRank(1);
        expA[1] = new RankChain.RankedObject(2);
        expA[1].setPreviosObjectRel(RankChain.RankedObjectsLink.MORE);
        expA[1].setRank(2);
        expA[2] = new RankChain.RankedObject(3);
        expA[2].setPreviosObjectRel(RankChain.RankedObjectsLink.MORE);
        expA[2].setRank(4);
        expA[3] = new RankChain.RankedObject(4);
        expA[3].setPreviosObjectRel(RankChain.RankedObjectsLink.SAME);
        expA[3].setRank(4);
        expA[4] = new RankChain.RankedObject(5);
        expA[4].setPreviosObjectRel(RankChain.RankedObjectsLink.SAME);
        expA[4].setRank(4);
        expA[5] = new RankChain.RankedObject(6);
        expA[5].setPreviosObjectRel(RankChain.RankedObjectsLink.MORE);
        expA[5].setRank(6);
        expA[6] = new RankChain.RankedObject(7);
        expA[6].setPreviosObjectRel(RankChain.RankedObjectsLink.MORE);
        expA[6].setRank(7);
        expA[7] = new RankChain.RankedObject(8);
        expA[7].setPreviosObjectRel(RankChain.RankedObjectsLink.MORE);
        expA[7].setRank(8);
        expA[8] = new RankChain.RankedObject(9);
        expA[8].setPreviosObjectRel(RankChain.RankedObjectsLink.MORE);
        expA[8].setRank(9.5);
        expA[9] = new RankChain.RankedObject(10);
        expA[9].setPreviosObjectRel(RankChain.RankedObjectsLink.SAME);
        expA[9].setRank(9.5);
        docData02.add(expA);


    }

    @Test
    public void testFromArray_toRankVector_01() {
        System.out.println("test fromArray");

        for (RankChain.RankedObject[] rankedObjects : docData) {

            double[] expectedVector = getRankVector(rankedObjects);

            RankChain instance = RankChain.fromArray(rankedObjects, new NaturalOrderPosition2Rank());
            //  out.println(instance);
            assertEquals(rankedObjects.length, instance.size());

            out.println(Arrays.toString(expectedVector));
            assertArrayEquals(expectedVector, instance.toRankedObjectVector(), 0);

        }

    }

    @Test
    public void testFromArray_toRankVector_02() {
        System.out.println("test fromArray02");
        for (RankChain.RankedObject[] rankedObjects : docData02) {

            double[] expectedVector = getRankVector(rankedObjects);

            RankChain instance = RankChain.fromArray(rankedObjects, new NaturalOrderPosition2Rank());
            out.println(instance);
            assertEquals(rankedObjects.length, instance.size());

            out.println(Arrays.toString(expectedVector));
            assertArrayEquals(expectedVector, instance.toRankedObjectVector(), 0);
        }
    }

    @Test
    public void testFromArray_toRankVector_user_data() {
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

            for (String testDataFile : testDataFiles) {
                out.println("testing data from " + testDataFile);

                BufferedReader reader = new BufferedReader(
                        new FileReader(resDir + "/" + testDataFile)
                );


                String testDataLine;
                while ((testDataLine = reader.readLine()) != null) {
                    out.println("test-data={ " + testDataLine+" }");
                    RankChain.RankedObject[] rankedObjects = getRankChain(testDataLine);
                    double[] expectedVector = getRankVector(rankedObjects);

                    RankChain instance = RankChain.fromArray(rankedObjects, new NaturalOrderPosition2Rank());
                    out.println(instance);

                    assertEquals(rankedObjects.length, instance.size());
                    //out.println(Arrays.toString(expectedVector));
                    assertArrayEquals(expectedVector, instance.toRankedObjectVector(), 0);
                }



            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }

    private static RankChain.RankedObject[] getRankChain(String testDataLine /* ie O1(1) >O2(2.5) ~O3(2.5)*/) {

        final String[] objects = testDataLine.split("\\s");
        final RankChain.RankedObject[] ret = new RankChain.RankedObject[objects.length];

        for (int i = 0; i < objects.length; i++) {
            //out.println(objects[i]);
            Matcher m = TEST_DATA_PATTERN.matcher(objects[i]);
            if (m.matches()) {
                RankChain.RankedObject ro = new RankChain
                        .RankedObject(Integer.valueOf(m.group(2)));
                ro.setRank(Double.valueOf(m.group(3)));
                String link = m.group(1);
                if ((link != null) && (!link.isEmpty())) {
                    ro.setPreviosObjectRel(RankChain.RankedObjectsLink.fromString(link));
                }
                ret[i] = ro;
            } else {
                throw new IllegalMonitorStateException("check test-data files,  wrong chunk:" + objects[i]);
            }
        }
        return ret;
    }

    public static double[] getRankVector(RankChain.RankedObject[] rankedObjects) {
        double[] ret = new double[rankedObjects.length];
        for (RankChain.RankedObject rankedObject : rankedObjects) {
            ret[rankedObject.getRankedObjectId() - 1] = rankedObject.getRank();
        }
        return ret;
    }

}
