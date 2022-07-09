package src;

import java.util.ArrayList;

public class Guest implements Comparable<Guest> {
    //Class wide, number-selection variables
    private static boolean[] numberUsed = new boolean[10000]; //Support for up to 10,000 people

    //ID Number:
    private int number = -1;


    //Personal Information
    private String lastName;
    private String firstName;
    private String phoneNumber;
    private String email;
    private String notes;

    //Entry Donation Info
    private double entryDonation;
    private boolean paidEntryDonationCash = true;

    //Add-On Item Info
    private int numberShirts = 0;
    private int numberCups = 0;
    private double donation;

    //Auction Items
    private ArrayList<Item> items = new ArrayList<Item>();
    private boolean paidAuctionItemsCash = true;
    private double amountPaid;
    private double changeGiven; //Amount of change given back to guest

    private boolean orderComplete; //Whether everything with this guest has been completed


    public Guest() {
        for (int i = 0; i < numberUsed.length; i++) {
            if (!numberUsed[i]) {
                number = i;
                numberUsed[i] = true;
                break;
            }
        }
    }

    public Guest(boolean importedData) {
        if (importedData) return;
        //Set Up Current Guest Number
        for (int i = 0; i < numberUsed.length; i++) {
            if (!numberUsed[i]) {
                number = i;
                numberUsed[i] = true;
                break;
            }
        }
    }

    public Guest(String lastName, String firstName, String phoneNumber, String email, String notes) {
        for (int i = 0; i < numberUsed.length; i++) {
            if (!numberUsed[i]) {
                number = i;
                numberUsed[i] = true;
                break;
            }
        }

        setLastName(lastName);
        setFirstName(firstName);
        setPhoneNumber(phoneNumber);
        setEmail(email);
        setNotes(notes);

    }

    //
    //-------------
    //  Setters
    //-------------
    //

    public void setLastName(String lastName) {
        this.lastName = DataManager.clean(lastName);
    }

    public void setFirstName(String firstName) {
        this.firstName = DataManager.clean(firstName);
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = DataManager.clean(phoneNumber);
    }

    public void setDonation(double donation) {
        this.donation = donation;
    }

    public void setEmail(String email) {
        this.email = DataManager.clean(email);
    }

    /**
     * Sets number of shirts guest will purchase
     *
     * @param numberShirts amount being purchased
     */
    public void setNumberShirts(int numberShirts) {
        if (numberShirts < 0) this.numberShirts = 0;
        this.numberShirts = numberShirts;
    }

    /**
     * Sets number of cups guest will purchase
     *
     * @param numberCups amount being purchased
     */
    public void setNumberCups(int numberCups) {
        if (numberCups < 0) this.numberCups = 0;
        this.numberCups = numberCups;
    }

    /**
     * Sets amount of money guest has paid to enter event
     *
     * @param amount Amount paid
     */
    public void setEntryDonation(double amount) {
        if (amount < 0) entryDonation = 0;
        entryDonation = amount;
    }

    /**
     * Sets whether guest has paid for entry donation with cash or with check.
     *
     * @param paidCash Whether Guest has paid cash for entry
     */
    public void setPaidEntryDonationCash(boolean paidCash) {
        if (paidCash) paidEntryDonationCash = true;
        else paidEntryDonationCash = false;
    }

    /**
     * Sets whether guest has paid for auction items with cash or with check.
     *
     * @param paidCash Whether Guest has paid cash for auction items
     */
    public void setPaidAuctionItemsCash(boolean paidCash) {
        if (paidCash) paidAuctionItemsCash = true;
        else paidAuctionItemsCash = false;
    }

    /**
     * Adds an item to the list of items owned by guest
     *
     * @param item
     */
    public void addItem(Item item) {
        items.add(item);
    }

    /**
     * Sets amount of money guest has paid for auction items
     *
     * @param amountPaid Amount guest has paid
     */
    public void setAmountPaid(double amountPaid) {
        this.amountPaid = amountPaid;
    }

    public void removeItem(Item item) {
        items.remove(item);
    }

    public void setNumber(int newNumber) {
        if (!numberUsed[newNumber]) {
            if (this.number != -1) numberUsed[this.number] = false;
            numberUsed[newNumber] = true;
            number = newNumber;
        }
    }


    /**
     * Sets notes about specific guest
     *
     * @param notes Additional notes to make
     */
    public void setNotes(String notes) {
        this.notes = DataManager.clean(notes);
        ;
    }

    /**
     * Sets how much change is given back to this guest
     *
     * @param changeGiven Amount
     */
    public void setChangeGiven(double changeGiven) {
        this.changeGiven = changeGiven;
    }

    /**
     * Sets whether everything with this guest's order is complete
     *
     * @param orderComplete Order completed
     */
    public void setOrderComplete(boolean orderComplete) {
        this.orderComplete = orderComplete;
    }

    //
    //-------------
    //  Getters
    //-------------
    //

    public int getNumber() {
        return number;
    }

    public String getLastName() {
        if (lastName == null) return "";
        return lastName;
    }

    public String getFirstName() {
        if (firstName == null) return "";
        return firstName;
    }

    public String getPhoneNumber() {
        if (phoneNumber == null) return "";
        return phoneNumber;
    }

    public String getEmail() {
        if (email == null) return "";
        return email;
    }

    public double getDonation() {
        return donation;
    }

    public double getEntryDonation() {
        return entryDonation;
    }

    public boolean getPaidEntryDonationCash() {
        return paidEntryDonationCash;
    }

    public int getNumberShirts() {
        return numberShirts;
    }

    public int getNumberCups() {
        return numberCups;
    }

    public ArrayList<Item> getItems() {
        if (items == null)
            items = new ArrayList<Item>();
        return items;
    }

    public boolean getPaidAuctionItemsCash() {
        return paidAuctionItemsCash;
    }

    public double getChangeGiven() {
        return changeGiven;
    }

    public String getNotes() {
        if (notes == null) return "";
        return notes;
    }

    public double getAmountPaid() {
        return amountPaid;
    }

    public boolean getOrderComplete() {
        return orderComplete;
    }

    /**
     * Sums up all items in inventory
     *
     * @return Sum To Pay
     */
    public double checkout() {
        double sum = 0;
        sum += (numberShirts*10); //Do Calculation For Shirts
        sum += (numberCups / 2) * 12; //Do Calculation With 2-For-12
        if (numberCups % 2 != 0) sum += 7; //Do Calculation With 1-For-7

        if (!items.isEmpty()) //Only Check Items List If It Isn't Empty
            for (Item i : items) {
                sum += i.getPrice();
            }

        sum += entryDonation; //Entry Donation
        sum += donation; //Additional Donation

        return sum;
    }

    public String getGuestID() {
        return "" + number;
    }

    public static Guest getGuestFromID(String ID) {
        for (Guest g : Controller.guests) {
            if (g.getGuestID().equals(ID)) {
                return g;
            }
        }
        return null;
    }

    public void remove() {
        numberUsed[number] = false;
    }


    public String toString() {
        if ((lastName == null || lastName.equals("")) && (firstName == null || firstName.equals("")))
            return "" + number;
        if ((lastName == null || lastName.equals(""))) return "" + number + " " + firstName;
        if (firstName == null || firstName.equals("")) return "" + number + " " + lastName;
        return "" + number + " " + lastName + ", " + firstName;
    }

    public int compareTo(Guest g) {
        if (g.getNumber() > this.getNumber()) return 1;
        if (g.getNumber() < this.getNumber()) return -1;
        return 0;
    }

    public static void clearAllNumbers() {
        numberUsed = new boolean[10000];
    }

}
