package src;

public class Item implements Comparable<Item>{

    private static int totalItems = 0; //Total Number Of Guests Attending
    private static boolean[] numberUsed = new boolean[10000]; //Support for up to 10,000 people

    //ID Number:
    private int number = -1;

    //Information
    private String name = null;
    private double price = 0.0;
    private String notes = null;

    private Guest owner = null;


    public Item() {
        for (int i = 0; i < numberUsed.length; i++) {
            if (!numberUsed[i]) {
                number = i;
                numberUsed[i] = true;
                break;
            }
        }
    }

    public Item(boolean fromFile) {
        if(true)return;
        for (int i = 0; i < numberUsed.length; i++) {
            if (!numberUsed[i]) {
                number = i;
                numberUsed[i] = true;
                break;
            }
        }
    }

    public Item(String name, int price) {
        super();
        setName(name);
        this.price = price;
    }

    public void setPrice(double price) {
        if (price > 0) this.price = price;
    }

    public void setName(String name) {
        this.name = DataManager.clean(name);
    }

    public void setOwner(Guest owner) {
        this.owner = owner;
    }

    public void setNotes(String notes) {
        this.notes = DataManager.clean(notes);
    }

    public void setNumber(int newNumber) {
        if (!numberUsed[newNumber]) {
            if (this.number != -1) numberUsed[this.number] = false;
            numberUsed[newNumber] = true;
            number = newNumber;
        }
    }

    public String getName() {
        if (name == null) return "";
        return name;
    }

    public static Item getItemFromID(String ID) {
        for(Item i:Controller.items) {
            if((""+i.getNumber()).equals(ID)) {
                return i;
            }
        }
        return null;
    }

    public double getPrice() {
        return price;
    }

    public void remove() {
        numberUsed[number] = false;
    }

    public int getNumber() {return number;}

    public Guest getOwner() {return owner;}

    public String getNotes() {
        if (notes==null) return "";
        return notes;}

    public String toString() {
        if (name == null || name.equals("")) return ""+number;
       return ""+number+" "+name;
    }

    public int compareTo(Item i) {
        if (i.getNumber() > this.getNumber()) return 1;
        if (i.getNumber() < this.getNumber()) return -1;
        return 0;
    }

    public static void clearAllNumbers() {
        numberUsed = new boolean[10000];
    }

}
