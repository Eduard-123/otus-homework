public class TestResults {

    private int testsComplete;

    private int testsFailed;

    private int totalTests;

    public int getTestsComplete() {
        return testsComplete;
    }

    public void setSuccessCount() {
        this.testsComplete++;
    }

    public int getTestsFailed() {
        return testsFailed;
    }

    public void setFailedCount() {
        this.testsFailed++;
    }

    public int getTotalTests() {
        return totalTests;
    }

    public void setTotalTests(int totalTests) {
        this.totalTests = totalTests;
    }


}
