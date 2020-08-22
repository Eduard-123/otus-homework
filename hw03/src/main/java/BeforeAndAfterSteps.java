import annotations.After;
import annotations.Before;

public class BeforeAndAfterSteps {


    @Before
    public void setUp() {
        System.out.println("Setting up the environment");
    }

    @After
    public void tearDown(TestResults results) {

        System.out.println(results.getTestsComplete());
        System.out.println(results.getTestsFailed());
        System.out.println(results.getTotalTests());

    }

}
