public class Main {

   public static void main(String...args) {
       Logger.create(Logging.class).calculation(5);
       Logger.create(Logging.class).calculation(10,"ten");
       Logger.create(Logging.class).calculation(15,20,25);

   }

}
