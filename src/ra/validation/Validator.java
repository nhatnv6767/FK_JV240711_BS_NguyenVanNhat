package ra.validation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Validator {

    public LocalDate getDateInput(Scanner scanner, String promp) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date;
        while (true) {
            System.out.print(promp);
            String input = scanner.nextLine().trim();
            try {
                date = LocalDate.parse(input, formatter);
                if (date.getYear() > LocalDate.now().getYear()) {
                    System.err.println("Năm không được lớn hơn năm hiện tại");
                    continue;
                }
                if (LocalDate.now().getYear() - date.getYear() > 120) {
                    System.err.println("Tuổi không được lớn hơn 120 tuổi");
                    continue;
                }

                if (date.getMonthValue() < 1 || date.getMonthValue() > 12) {
                    System.err.println("Tháng không hợp lệ");
                    continue;
                }

                if (date.getDayOfMonth() < 1 || date.getDayOfMonth() > 31) {
                    System.err.println("Ngày không hợp lệ");
                    continue;
                }

                return date;
            } catch (DateTimeParseException e) {
                System.err.println("Vui lòng nhập theo định dạng yyyy-MM-dd");
            }
        }
    }

    public boolean getBooleanInput(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("1") || input.equals("true")) {
                return true;
            } else if (input.equals("0") || input.equals("false")) {
                return false;
            } else {
                System.err.println("Vui lòng nhập 1, 0, true hoặc false.");
            }
        }
    }

    public String getPhoneNumberInput(Scanner scanner, String prompt) {
        String input;
        do {
            System.out.print(prompt);
            input = scanner.nextLine().trim();
            if (!input.matches("\\d{11}")) {
                System.err.println("Số điện thoại phải gồm 11 chữ số.");
            }
        } while (!input.matches("\\d{11}"));
        return input;
    }

    public String getEmailInput(Scanner scanner, String prompt) {
        String input;
        String emailPattern = "^[A-Za-z0-9+_.-]+@(.+)$";
        do {
            System.out.print(prompt);
            input = scanner.nextLine().trim();
            if (!input.matches(emailPattern)) {
                System.err.println("Email không hợp lệ.");
            }
        } while (!input.matches(emailPattern));
        return input;
    }

    public String getNonEmptyStringInput(Scanner scanner, String prompt) {
        String input;
        do {
            System.out.print(prompt);
            input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.err.println("Không được để trống");
            }
        } while (input.isEmpty());
        return input;
    }

    public int getPositiveIntInput(Scanner scanner, String prompt) {
        int input;
        do {
            System.out.print(prompt);
            input = getIntInput(scanner);
            if (input <= 0) {
                System.err.println("Vui lòng nhập số lớn hơn 0");
            }
        } while (input <= 0);
        return input;
    }

    public double getPositiveDoubleInput(Scanner scanner, String prompt) {
        double input;
        do {
            System.out.print(prompt);
            input = getDoubleInput(scanner);
            if (input <= 0) {
                System.err.println("Vui lòng nhập số lớn hơn 0");
            }
        } while (input <= 0);
        return input;
    }

    private int getIntInput(Scanner scanner) {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.err.println("Vui lòng nhập số nguyên");
            }
        }
    }


    private double getDoubleInput(Scanner scanner) {
        while (true) {
            try {
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.err.println("Vui lòng nhập số thực");
            }
        }
    }

    public String getCustomerTypeInput(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (input.equals("1")) {
                return "Regular";
            } else if (input.equals("2")) {
                return "VIP";
            } else {
                System.err.println("Vui lòng nhập 1 hoặc 2 (1 - Regular, 2 - VIP)");
            }
        }
    }
}
