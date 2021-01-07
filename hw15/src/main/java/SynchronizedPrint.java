import java.util.stream.IntStream;

public class SynchronizedPrint implements Runnable {

    private String lastThread = "thread2";

    @Override
    public void run() {
        while (true) {
            iterate(IntStream.range(1, 11));
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            iterate(IntStream.range(1, 11).map(i -> 11 - i));
        }
    }

    private void iterate(IntStream range) {
        range.forEach(value -> {
                    synchronized (this) {
                        try {
                            while (Thread.currentThread().getName().equals(lastThread)) {
                                this.wait();
                            }
                            System.out.println(String.format("[ %s ] - %d",Thread.currentThread().getName(), value));
                            lastThread = Thread.currentThread().getName();
                            Thread.sleep(500);
                            notifyAll();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
    }

}
