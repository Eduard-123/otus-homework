public class TestLogging implements Logging {

    @Override
    public void calculation(int param) {
        System.out.println("called method calculation with param " + param);
    }

    @Override
    public void calculation(int param, String param2) {
        System.out.println("called method calculation with param " + param + "," + param2);
    }

    @Override
    public void calculation(int param, int param2, int param3) {
        System.out.println("called method calculation with param " + param + ", " + param2 + ", " + param3);
    }

}
