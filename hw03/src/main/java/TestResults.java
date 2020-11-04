public class TestResults {

    private int testsComplete;

    private int testsFailed;

    private long totalTests;

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

    public long getTotalTests() {
        return totalTests;
    }

    public void setTotalTests() {
        this.totalTests++;
    }


}
