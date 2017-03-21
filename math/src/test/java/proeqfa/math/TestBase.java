package proeqfa.math;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * Created by moroz on 20.03.17.
 */
public class TestBase {

    protected Logger log;
    protected ByteArrayOutputStream baos;
    protected PrintStream out;


    public TestBase() {
        log = LogManager.getLogger(this.getClass().getName());
    }

    public void setUp() {
        baos = new ByteArrayOutputStream();
        out = new PrintStream(baos);
    }

    public void tearDown() {
        log.debug(new String(baos.toByteArray(), java.nio.charset.StandardCharsets.UTF_8));
    }
}
