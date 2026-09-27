package functional;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class ProgramFunctionalInterface {
    public static void main(String[] args) {
        Function<String, Integer> countFunction = s -> {
            int count = 0;
            for (int i = 0; i < s.length(); i++) {
                if (Character.isLetter(s.charAt(i))) {
                    count++;
                }
            }
            return count;
        };

        Predicate<Integer> numberPredicate = number -> number % 2 ==1;

        Consumer<Integer> printNumber = System.out::println;

        List<String> words = new ArrayList<>();
        words.add("hello");
        words.add("my world");
        words.add("java");

        Stream<String> wordsStream = words.stream();

        Stream<Integer> countStream = wordsStream.map(countFunction);

        Stream<Integer> numberStream = countStream.filter(numberPredicate);

        numberStream.forEach(printNumber);
        System.out.println("*************");

        words.stream().map(s -> {
            int count = 0;
            for (int i = 0; i < s.length(); i++) {
                if (Character.isLetter(s.charAt(i))) {
                    count++;
                }
            }
            return count;
        }).filter(number -> number % 2 == 1).forEach(System.out::println);
    }
}
