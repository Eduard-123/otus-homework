public class Main {

   public static void main(String...args) {
       Logger.create(ITestLogging.class).calculation(5);
       Logger.create(ITestLogging.class).calculation(10,"ten");
       Logger.create(ITestLogging.class).calculation(15,20,25);

   }

}
