package org.alibaby.Model;

import java.util.Random;

public class NameGenerator {
    private static final String[] FIRST_NAMES = {
            "John", "Mary", "David", "Sarah", "Michael", "Jennifer", "Christopher", "Jessica", "Matthew", "Emily"
    };

    private static final String[] LAST_NAMES = {
            "Smith", "Johnson", "Brown", "Taylor", "Miller", "Anderson", "Wilson", "Clark", "Walker", "Roberts"
    };

    public static void main(String[] args) {
        String randomName = generateRandomName();
        System.out.println("Random name: " + randomName);
    }

    public static String generateRandomName() {
        Random random = new Random();
        String firstName = FIRST_NAMES[random.nextInt(FIRST_NAMES.length)];
        String lastName = LAST_NAMES[random.nextInt(LAST_NAMES.length)];
        return firstName + " " + lastName;
    }
}
