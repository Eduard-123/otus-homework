import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Main {

    public static void main(String... args) {
        DIYArrayList<Integer> numbers = new DIYArrayList<>();
        for (int i = 0; i < 30; i++) {
            numbers.add(new Random().nextInt(1000));
        }
        DIYArrayList<Integer> nums = new DIYArrayList<>();
        nums.addAll(numbers);
        System.out.println(nums.size());

        DIYArrayList<Integer> nums2 = new DIYArrayList<>(numbers.size());
        Collections.copy(nums2,numbers);
        System.out.println(nums2.size());

        DIYArrayList<Integer> nums3 = new DIYArrayList<>(numbers);
        System.out.println("----Before sorting----");
        nums3.forEach(System.out::println);
        Collections.sort(nums3);
        System.out.println("----After sorting----");
        nums3.forEach(System.out::println);
    }

}
