import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class InvalidRatingException extends Exception {
    public InvalidRatingException(String message) {
        super(message);
    }
}

enum Type {
    Cat,
    Dog
}

class User {
    String firstName;
    String lastName;
    String email;
    int budget;

    public User(String firstName, String lastName, String email, int budget) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.budget = budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }
}

class Pet {
    User owner;
    String name;
    Type type;
    String description;
    LocalDate dateOfBirth;
    int price;
    int rating;

    public Pet(String name, String description, LocalDate dateOfBirth, Type type) { // Cat constructor
        this.name = name;
        this.description = description;
        this.dateOfBirth = dateOfBirth;
        this.type = type;
        this.price = getAge();
    }

    public Pet(String name, String description, LocalDate dateOfBirth, Type type, int rating) { // Dog constructor
        this.name = name;
        this.type = type;
        this.description = description;
        this.dateOfBirth = dateOfBirth;
        this.rating = rating;
        this.price = getAge() + rating;
    }

    public int getAge() {
        return LocalDate.now().getYear() - dateOfBirth.getYear();
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public boolean hasOwner() {
        return this.owner != null;
    }

}

class PetStore {
    List<User> users;
    List<Pet> pets;

    public PetStore() {
        users = new ArrayList<>(10);
        pets = new ArrayList<>(20);
    }

    public void addUser(User user) {
        users.add(user);
    }

    public void addPet(String name, String description, LocalDate dateOfBirth, Type type, int rating) throws InvalidRatingException {
        if (rating < 0 || rating > 10)
            throw new InvalidRatingException("Ratings are from 0 to 10 only!");

        if (type.equals(Type.Dog))
            pets.add(new Pet(name, description, dateOfBirth, type, rating));
        else pets.add(new Pet(name, description, dateOfBirth, type));

    }

    public void buyPet() {
        int countBuys = 0;
        int countNotAllowed = 0;
        for (User user : users) {
            Pet pet = pets.stream()
                    .filter(p -> p.owner == null && p.price <= user.budget)
                    .findAny()
                    .orElse(null);
            if (pet != null) {
                pet.setOwner(user);
                countBuys++;
                user.setBudget(user.budget - pet.price);
                if (pet.type.equals(Type.Dog)) {
                    System.out.printf("Woof, dog %s has owner %s\n%n", pet.name, pet.owner.firstName);
                }
                else {
                    System.out.printf("Meow, cat %s has owner %s\n%n", pet.name, pet.owner.firstName);
                }
            }else {
                countNotAllowed++;
            }
        }
        historyLog(LocalDate.now(), countBuys, countNotAllowed);
    }

    private void historyLog(LocalDate now, int countBuys, int countNotAllowed) { // BONUS: history-log
        System.out.printf("Date of execution: %s\nNumber of users that successfully bought a pet: %d\nNumber of users that were not allowed to buy any pet: %d%n", now.toString(), countBuys, countNotAllowed);
    }

    public void listPets() {
        System.out.println("==== LISTING PETS ====");
        System.out.println("Name\tType\tDescription\tPrice\tOwner");
        pets.forEach(p -> System.out.printf("%6s\t%5s\t%10s\t%5d\t%10s%n", p.name, p.type, p.description, p.price, p.hasOwner() ? p.owner.firstName : "No owner"));
    }

    public void listUsers() {

        System.out.println("==== LISTING USERS ====");
        System.out.println("First name\tLast Name\tBudget");
        users.forEach(u -> System.out.printf("%10s\t%9s\t%5d%n", u.firstName, u.lastName, u.budget));
    }
}

public class PetStoreTest {
    public static void main(String[] args) {
        PetStore petStore = new PetStore();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String[] line = scanner.nextLine().split("\\s+");
            if (line[0].equals("exit"))
                break;
            else if (line[0].equalsIgnoreCase("user:")) { // Bonus: adding methods so the data can be specified
                String firstName = line[1];
                String lastName = line[2];
                String email = line[3];
                int budget = Integer.parseInt(line[4]);
                User user = new User(firstName, lastName, email, budget);
                petStore.addUser(user);

            } else if (line[0].equalsIgnoreCase("pet:")) { // Bonus: adding methods so the data can be specified
                String name = line[1];
                Type type = Type.valueOf(line[2]);
                String description = line[3];
                LocalDate localDate = LocalDate.parse(line[4]);
                int rating = Integer.parseInt(line[5]);
                try {
                    petStore.addPet(name, description, localDate, type, rating);
                } catch (InvalidRatingException e) {
                    e.printStackTrace();
                }

            }
        }
        petStore.buyPet();
        petStore.listUsers();
        petStore.listPets();

    }
}

