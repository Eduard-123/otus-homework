import annotations.After;
import annotations.Before;

public class BeforeAndAfterSteps {


    @Before
    public void setUp() {
        System.out.println("Setting up the environment");
    }

    @After
    public void tearDown(TestResults results) {
        System.out.println("Tests complete: " + results.getTestsComplete());
        System.out.println("Tests failed: " + results.getTestsFailed());
        System.out.println("Tests total: " + results.getTotalTests());
        System.out.println("Clearing up the environment");
    }

}
