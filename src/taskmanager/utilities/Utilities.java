package taskmanager.utilities;

import java.util.Scanner;

public class Utilities {
    public static String parseString(Scanner in, String name) {
        name = in.nextLine();
        while ("".equals(name)) {
            System.out.println("\nВы ничего не ввели.Повторите ввод:");
            name = in.nextLine();
        }

        return name;
    }

    public  static int parseInt(Scanner in, String value) {
        boolean exit = true;
        int valueFinal = 0;
        value = in.nextLine();
        while (exit) {
            if ("".equals(value)) {
                System.out.println("\nВы ничего не ввели.Повторите ввод:");
            } else {
                if (!(value.matches("-?\\d+(\\.\\d+)?"))) {
                    System.out.println("\nНекорректный ввод данных.Повторите ввод:");
                } else {
                    break;
                }
            }
            value = in.nextLine();
        }
        valueFinal = Integer.parseInt(value);
        return valueFinal;
    }

    public static void cls() {
        int ln = 0;
        while (ln < 50) {
            System.out.println();
            ln++;
        }
    }
}
