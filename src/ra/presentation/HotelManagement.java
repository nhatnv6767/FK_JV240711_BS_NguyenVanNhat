package ra.presentation;

import ra.DAO.CustomerBusiness;
import ra.DAO.RoomBusiness;
import ra.entity.Customer;
import ra.entity.Room;
import ra.validation.Validator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

enum CustomerMenu {
    LIST(1, "List all customers"),
    ADD(2, "Add new customer"),
    UPDATE(3, "Update customer"),
    DELETE(4, "Delete customer"),
    SEARCH_BY_CONDITION(5, "Search customer by name or phone number or id card"),
    BACK(0, "Back to main menu");

    private final int value;
    private final String description;

    CustomerMenu(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public int getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }
}

enum RoomMenu {
    LIST(1, "List all rooms"),
    ADD(2, "Add new room"),
    UPDATE(3, "Update room"),
    DELETE(4, "Delete room"),
    SEARCH_BY_TYPE(5, "Search room by type"),
    SEARCH_BY_PRICE(6, "Search room by price"),
    SEARCH_BY_STATUS(7, "Search room by status"),
    ROOM_AVAILABILITY(8, "Get rooms availability"),
    BACK(0, "Back to main menu");

    private final int value;
    private final String description;

    RoomMenu(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public int getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }
}

public class HotelManagement {
    private static CustomerBusiness customerBusiness = new CustomerBusiness();
    private static RoomBusiness roomBusiness = new RoomBusiness();
    private static final Validator validator = new Validator();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("*************HOTEL MANAGEMENT*************");
            System.out.println("1. Customer Management");
            System.out.println("2. Room Management");
            System.out.println("0. Exit");
            System.out.print("Please choose: ");
            choice = getIntInput(scanner);
            switch (choice) {
                case 1:
                    customerMenu(scanner);
                    break;
                case 2:
                    roomMenu(scanner);
                    break;
                case 0:
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.err.println("Invalid choice. Please choose again");
            }
        } while (choice != 0);
    }

    private static void roomMenu(Scanner scanner) {
        int choice;
        do {
            System.out.println("*************ROOM MANAGEMENT*************");
            for (RoomMenu menu : RoomMenu.values()) {
                System.out.println(menu.getValue() + ". " + menu.getDescription());
            }
            System.out.print("Please choose: ");
            choice = getIntInput(scanner);

            switch (choice) {
                case 1:
                    displayAllRooms();
                    break;
                case 2:
                    addRoom(scanner);
                    break;
                case 3:
                    updateRoom(scanner);
                    break;
                case 4:
                    deleteRoom(scanner);
                    break;
                case 5:
                    searchRoomByType(scanner);
                    break;
                case 6:
                    searchRoomByPrice(scanner);
                    break;
                case 7:
                    searchRoomByStatus(scanner);
                    break;
                case 8:
                    getRoomAvailability();
                    break;
                case 0:
                    break;
                default:
                    System.err.println("Invalid choice. Please choose again");
            }
        } while (choice != 0);
    }

    private static void addRoom(Scanner scanner) {
        Room room = new Room();
        room.inputData(scanner, validator);
        roomBusiness.insert(room);
    }

    private static void displayAllRooms() {
        Room[] rooms = roomBusiness.getAll();
        System.out.println("Room list:");
        for (Room room : rooms) {
            room.displayData();
            System.out.println("====================================");
        }
    }

    private static void updateRoom(Scanner scanner) {
        System.out.print("Enter room ID: ");
        int roomId = getIntInput(scanner);
        Room room = roomBusiness.get(roomId);
        if (room == null) {
            System.err.println("Room not found");
            return;
        }

        System.out.println("Room information:");
        room.displayData();

        int choice;
        do {
            System.out.println("Choose field to update:");
            System.out.println("1. Update room number");
            System.out.println("2. Update room type");
            System.out.println("3. Update price");
            System.out.println("4. Update status");
            System.out.println("0. Cancel");
            System.out.print("Please choose: ");
            choice = getIntInput(scanner);

            switch (choice) {
                case 1:
                    String newRoomNumber = validator.getNonEmptyStringInput(scanner, "Enter new room number: ");
                    if (!newRoomNumber.isEmpty()) {
                        room.setRoomNumber(newRoomNumber);
                    }
                    break;
                case 2:
                    String newRoomType = validator.getRoomTypeInput(scanner, "Enter new room type (1 - Single, 2 - Double, 3 - Family, 4 - VIP): ");
                    if (!newRoomType.isEmpty()) {
                        room.setRoomType(newRoomType);
                    }
                    break;
                case 3:
                    double newPrice = validator.getPositiveDoubleInput(scanner, "Enter new price: ");
                    if (newPrice > 0) {
                        room.setPrice(newPrice);
                    }
                    break;
                case 4:
                    String newStatus = validator.getRoomStatusInput(scanner, "Enter new status (1 - Available, 2 - Occupied): ");
                    if (!newStatus.isEmpty()) {
                        room.setStatus(newStatus);
                    }
                    break;
                case 0:
                    System.out.println("Cancel");
                    break;
                default:
                    System.err.println("Invalid choice. Please choose again");
            }

        } while (choice != 0);

        roomBusiness.update(room);
    }

    private static void deleteRoom(Scanner scanner) {
        int roomId = validator.getPositiveIntInput(scanner, "Enter room ID: ");
        Room room = roomBusiness.get(roomId);
        if (room == null) {
            System.err.println("Room not found");
            return;
        }
        roomBusiness.delete(room);
    }

    private static void searchRoomByType(Scanner scanner) {
        String roomType = validator.getRoomTypeInput(scanner, "Enter room type (1 - Single, 2 - Double, 3 - Family, 4 - VIP): ");
        List<Room> rooms = roomBusiness.searchRoomByType(roomType);
        if (rooms.isEmpty()) {
            System.err.println("Room not found");
            return;
        }
        for (Room room : rooms) {
            room.displayData();
            System.out.println("====================================");
        }
    }

    private static void searchRoomByPrice(Scanner scanner) {
        double minPrice = validator.getPositiveDoubleInput(scanner, "Enter minimum price: ");
        double maxPrice = validator.getPositiveDoubleInput(scanner, "Enter maximum price: ");
        if (minPrice > maxPrice) {
            System.err.println("Invalid price range");
            return;
        }
        List<Room> rooms = roomBusiness.searchRoomByPrice(minPrice, maxPrice);
        if (rooms.isEmpty()) {
            System.err.println("Room not found");
            return;
        }
        for (Room room : rooms) {
            room.displayData();
            System.out.println("====================================");
        }
    }

    private static void searchRoomByStatus(Scanner scanner) {
        String status = validator.getRoomStatusInput(scanner, "Enter room status (1 - Available, 2 - Occupied): ");
        List<Room> rooms = roomBusiness.searchRoomByStatus(status);
        if (rooms.isEmpty()) {
            System.err.println("Room not found");
            return;
        }
        for (Room room : rooms) {
            room.displayData();
            System.out.println("====================================");
        }
    }

    private static void getRoomAvailability() {
        List<Room> rooms = roomBusiness.getAvailableRoom();
        if (rooms.isEmpty()) {
            System.err.println("No available room");
            return;
        }
        for (Room room : rooms) {
            room.displayData();
            System.out.println("====================================");
        }
    }


    private static void customerMenu(Scanner scanner) {
        int choice;
        do {
            System.out.println("*************CUSTOMER MANAGEMENT*************");
            for (CustomerMenu menu : CustomerMenu.values()) {
                System.out.println(menu.getValue() + ". " + menu.getDescription());
            }
            System.out.print("Please choose: ");
            choice = getIntInput(scanner);

            switch (choice) {
                case 1:
                    displayAllCustomers();
                    break;
                case 2:
                    addCustomer(scanner);
                    break;
                case 3:
                    updateCustomer(scanner);
                    break;
                case 4:
                    deleteCustomer(scanner);
                    break;
                case 5:
                    searchCustomerByCondition(scanner);
                    break;
                case 0:
                    break;
                default:
                    System.err.println("Invalid choice. Please choose again");
            }
        } while (choice != 0);
    }


    private static void addCustomer(Scanner scanner) {
        Customer customer = new Customer();
        customer.inputData(scanner, validator);
        customerBusiness.insert(customer);
    }

    private static void updateCustomer(Scanner scanner) {
        System.out.print("Enter customer ID: ");
        int customerId = getIntInput(scanner);
        Customer customer = customerBusiness.get(customerId);
        if (customer == null) {
            System.err.println("Customer not found");
            return;
        }

        System.out.println("Customer information:");
        customer.displayData();

        Map<Integer, BiConsumer<Customer, Scanner>> updateOptions = new HashMap<>();
        updateOptions.put(1, (c, s) -> c.setCustomerName(validator.getNonEmptyStringInput(s, "Enter new customer name: ")));
        updateOptions.put(2, (c, s) -> c.setPhoneNumber(validator.getPhoneNumberInput(s, "Enter new phone number: ")));
        updateOptions.put(3, (c, s) -> c.setIdCard(validator.getNonEmptyStringInput(s, "Enter new id card: ")));
        updateOptions.put(4, (c, s) -> c.setAddress(validator.getNonEmptyStringInput(s, "Enter new address: ")));
        updateOptions.put(5, (c, s) -> c.setCustomerType(validator.getCustomerTypeInput(s, "Enter new customer type (1 - Regular, 2 - VIP): ")));
        updateEntity(customer, scanner, validator, customerBusiness::update, updateOptions);
    }

    private static void deleteCustomer(Scanner scanner) {
        int customerId = validator.getPositiveIntInput(scanner, "Enter customer ID: ");
        Customer customer = customerBusiness.get(customerId);
        if (customer == null) {
            System.err.println("Customer not found");
            return;
        }
        customerBusiness.delete(customer);
    }

    private static void searchCustomerByCondition(Scanner scanner) {
        String condition = validator.getNonEmptyStringInput(scanner, "Enter customer name or phone number or id card: ");
        Customer[] customers = customerBusiness.searchCustomerByCondition(condition);
        if (customers.length == 0) {
            System.err.println("Customer not found");
            return;
        }
        for (Customer customer : customers) {
            customer.displayData();
        }
    }

    private static void displayAllCustomers() {
        Customer[] customers = customerBusiness.getAll();
        for (Customer customer : customers) {
            customer.displayData();
            System.out.println("====================================");
        }
    }


    private static int getIntInput(Scanner scanner) {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number");
            }
        }
    }

    private static <T> void updateEntity(T entity, Scanner scanner, Validator validator, Consumer<T> updateFunction, Map<Integer, BiConsumer<T, Scanner>> updateOptions) {
        int choice;
        do {
            System.out.println("Choose field to update:");
            updateOptions.forEach((key, value) -> System.out.println(key + ". " + value));
            System.out.println("0. Cancel");
            System.out.print("Please choose: ");
            choice = getIntInput(scanner);
            if (choice != 0 && updateOptions.containsKey(choice)) {
                updateOptions.get(choice).accept(entity, scanner);
            } else if (choice != 0) {
                System.err.println("Invalid choice. Please choose again");
            }

        } while (choice != 0);
        updateFunction.accept(entity);
    }
}
