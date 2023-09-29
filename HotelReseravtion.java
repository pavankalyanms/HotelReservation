import java.io.*;
import java.util.Random;
import java.util.Scanner;

public class HotelReseravtion {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to the hotel reservation system");
        System.out.println("*******************************************************");
        System.out.println("1: Create an account");
        System.out.println("2: Start your reservation");
        System.out.println("3: Cancel your reservation");
        System.out.println("4: Display all my reservations");
        System.out.println("5: Print total bill");
        System.out.println("Please select the service you need:");
        int s = Integer.parseInt(sc.next());

        Random random = new Random();
        String folder = "/Users/saipavankalyanmudda/Desktop/javaproject";
        String useremail =null;
        long phonenumber = 0;
        String Location ;
        long accountId = 0;
        long BookingId = 0;
        String filename;
        switch (s) {
            case 1:
                try {
                    System.out.println("For creating an account, please provide information:");
                    System.out.println("Please enter useremail:");
                    useremail = sc.next();
                    // Validate email format (you can add a more robust email validation)
                    if (!useremail.contains("@")) {
                        System.out.println("Invalid email format.");
                        return; // Exit the program
                    }
                    System.out.println("Please enter phonenumber:");
                    phonenumber = sc.nextLong();
                    // Validate phone number (you can add more validations)
                    if (phonenumber <= 0) {
                        System.out.println("Invalid phone number.");
                        return; // Exit the program
                    }
                    System.out.println("Please enter Location you are looking to book:");
                    Location = sc.next();
                    accountId = random.nextLong(100000000);
                    filename = accountId + "_account.txt";
                    File file = new File(folder, filename);
                    FileWriter fw = new FileWriter(file);
                    fw.write("     " + "AccountId" + accountId + "\n");
                    fw.write("***************************************\n");
                    fw.write("useremail: " + useremail + "\n");
                    fw.write("phonenumber: " + phonenumber + "\n");
                    fw.write("Location: " + Location + "\n");
                    System.out.println("Account created successfully."+"your account id is:"+accountId);
                    fw.close();
                } catch (IOException e) {
                    System.out.printf("Error occurred while creating an account: " + e.getMessage());
                }
                break;
            case 2:
                System.out.println("please enter account id:");
                accountId= sc.nextLong();
                filename = accountId+ "_account.txt";
                File file = new File(folder, filename);
                if(file.exists()) {
                    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                        String line;
                        boolean usernameFound = false;

                        while ((line = br.readLine()) != null) {
                            // Check if the line contains "useremail:"
                            if (line.startsWith("useremail: ")) {
                                // Extract the username value (the part after "useremail: ")
                                useremail = line.substring("useremail: ".length());
                                System.out.println("useremail for Account ID " + accountId + ": " + useremail);
                                usernameFound = true;
                                break;
                            }
                        }
                    }
                }
                if (useremail.equals(null) || useremail.isEmpty()) {
                    System.out.println("Please create an account first.");
                    return; // Exit the program
                }
                System.out.println("Let's start the reservation for " + useremail);
                System.out.println("Enter date you want to book (date format: YYYY-MM-DD):");
                String dateString = sc.next();
                // Validate date format (you can add more robust date validation)
                if (!dateString.matches("\\d{4}-\\d{2}-\\d{2}")) {
                    System.out.println("Invalid date format.");
                    return; // Exit the program
                }
                System.out.println("Please enter type of room to book (1: single bed, 2: double bed, 3: twinsize double bed):");
                int t = sc.nextInt();
                if (t < 1 || t > 3) {
                    System.out.println("Invalid room type.");
                    return; // Exit the program
                }
                BookingId = random.nextLong(200000);
                System.out.println("Please enter accountId:");
                accountId = sc.nextLong();
                if (accountId <= 0) {
                    System.out.println("Invalid accountId.");
                    return; // Exit the program
                }
                filename = accountId + "_account.txt";
                File reservationFile = new File(folder, filename);
                FileWriter reservationFw = new FileWriter(reservationFile, true);
                BufferedWriter bw = new BufferedWriter(reservationFw);
                System.out.println("************************************************************************");
                switch (t) {
                    case 1:
                        System.out.println("Booked single bed room for the user " + useremail + " on " + dateString);
                        reservationFw.write("booking Id:"+BookingId + "\n" + "Booked single bed room for the " + useremail + " on " + dateString + "\n");
                        break;
                    case 2:
                        System.out.println("Booked Double bed room for the user " + useremail + " on " + dateString);
                        reservationFw.write("booking Id:"+BookingId + "\n" + "Booked Double bed room for the " + useremail + " on " + dateString + "\n");
                        break;
                    case 3:
                        System.out.println("Booked twinsize double bed room for the user " + useremail + " on " + dateString);
                        reservationFw.write("booking Id:"+BookingId + "\n" + "Booked twinsize double bed room for the " + useremail + " on " + dateString + "\n");
                        break;
                }
                reservationFw.close();
                break;
            case 3:
                System.out.println("Cancel Reservation");
                System.out.println("Enter accountId to cancel the reservation:");
                long cancelAccountId = sc.nextLong();
                String cancelFilename = cancelAccountId + "_account.txt";
                File cancelFile = new File(folder, cancelFilename);

                if (!cancelFile.exists()) {
                    System.out.println("Reservation not found for accountId: " + cancelAccountId);
                } else {
                    if (cancelFile.delete()) {
                        System.out.println("Reservation for accountId " + cancelAccountId + " has been canceled.");
                    } else {
                        System.out.println("Unable to cancel reservation for accountId: " + cancelAccountId);
                    }
                }
                break;

            case 4:
                System.out.println("Display All Reservations");
                File[] reservationFiles = new File(folder).listFiles();

                if (reservationFiles == null || reservationFiles.length == 0) {
                    System.out.println("No reservations found.");
                } else {
                    for (File rFile : reservationFiles) {
                        if (rFile.isFile()) {
                            System.out.println("Reservation details from file: " + rFile.getName());
                            try (BufferedReader br = new BufferedReader(new FileReader(rFile))) {
                                String line;
                                while ((line = br.readLine()) != null) {
                                    System.out.println(line);
                                }
                            }
                            System.out.println("----------------------------------");
                        }
                    }
                }
                break;

            case 5:
                System.out.println("Print Total Bill");
                System.out.println("Enter accountId to print the total bill:");
                long billAccountId = sc.nextLong();
                String billFilename = billAccountId + "_account.txt";
                File billFile = new File(folder, billFilename);

                if (!billFile.exists()) {
                    System.out.println("Reservation not found for accountId: " + billAccountId);
                } else {
                    try (BufferedReader br = new BufferedReader(new FileReader(billFile))) {
                        String line;
                        while ((line = br.readLine()) != null) {
                            System.out.println(line);
                        }
                    }
                }
                break;

            default:
                System.out.println("Invalid service selection.");
        }
    }
}
