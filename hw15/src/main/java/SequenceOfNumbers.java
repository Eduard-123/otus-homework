public class SequenceOfNumbers {

    public static void main(String[] args) {
        SynchronizedPrint synchronizedPrint = new SynchronizedPrint();

        Thread thread1 = new Thread(synchronizedPrint);
        thread1.setName("thread1");

        Thread thread2 = new Thread(synchronizedPrint);
        thread2.setName("thread2");

        thread1.start();
        thread2.start();
    }

}
