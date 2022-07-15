package src;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URI;
import java.util.*;


public class Controller {

    public static ObservableList<Guest> guests = FXCollections.observableArrayList();
    public static ObservableList<Item> items = FXCollections.observableArrayList();

    public static HashMap<Item, Integer> ownerSetup = new HashMap<>();

    private Guest selectedGuest;
    private Item selectedItem;

    public Controller() {
    }

    //
    // -----------------------------
    // Menu Bar Variables
    // -----------------------------
    //

    @FXML
    MenuItem saveData, loadData, saveAndExit, documentation;

    @FXML
    Button addItem, removeItem, addGuest, removeGuest, continueLoadingData, cancelLoadingData;

    @FXML
    TextField removeItemNum, removeGuestNum;

    //
    // -----------------------------
    // Item Variables
    // -----------------------------
    //

    @FXML
    TextField itemName, itemPrice, itemNumber, itemOwner;

    @FXML
    TextArea itemNotes;

    @FXML
    ComboBox<Item> itemSelect;

    @FXML
    ComboBox<Guest> ownerSelect;

    //
    // -----------------------------
    // Guest Variables
    // -----------------------------
    //

    @FXML
    TextField lastName, phoneNumber, firstName, email, guestNumber, tShirt, glasses, guestDonation, changeGiven, entryDonation, amountPaid;

    @FXML
    Label paymentNeeded, changeNeeded, totalDue;

    @FXML
    TextArea guestItemList, guestNotes;

    @FXML
    ComboBox<Guest> guestSelect;

    @FXML
    CheckBox auctionPaidByCheck, entryPaidByCheck, orderComplete;


    @FXML
    public void saveData() {
        if (DataManager.fileExists()) {
            Alert a = new Alert(Alert.AlertType.CONFIRMATION);
            a.setTitle("Overwrite Save");
            a.setContentText("There is currently a data.csv file in this directory. If you continue, the previous contents of the file will be overwritten.\nSave Anyways?");
            a.showAndWait();

            if (a.getResult() == ButtonType.OK) {
                DataManager.saveData();
            }
        } else
            DataManager.saveData();
    }

    //
    // -----------------------------
    // Data Management
    // -----------------------------
    //

    @FXML
    public void warnAboutData() {
        if (guests.isEmpty() && items.isEmpty()) {loadData(); return;}

        //Will Show This If There Is Already Data In The Form
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setTitle("Continue Loading Data");
        a.setContentText("If you continue to load data from a file, the information that you have entered up to this point will be lost.\nContinue?");
        a.showAndWait();

        if (a.getResult() == ButtonType.OK) {
            loadData();
        }

    }

    @FXML
    public void stopLoadingData() {

    }

    @FXML
    public void loadData() {
        //Removes Existing Data As To Not Conflict With Imported Data
        removeAllData();

        DataManager.loadData(); //Loads In Data From DataManager

        for (Item i : items) { //Uses Hashmap To Map Items To Owners
            if (ownerSetup.containsKey(i)) {
                Guest g = Guest.getGuestFromID("" + ownerSetup.get(i));
                if (g == null) continue;
                i.setOwner(g);
                ownerSetup.remove(i);
            }
        }
        //Sets Drop Down Menu Items
        guestSelect.setItems(guests);
        itemSelect.setItems(items);
        if (guests.size() > 0) guestSelect.setValue(guests.get(guests.size() - 1));
        if (items.size() > 0) itemSelect.setValue(items.get(guests.size() - 1));

        //Updates Text
        updateItem();
        updateGuest();
    }

    private void removeAllData() {
        guests.clear();
        Guest.clearAllNumbers();
        clearGuestData();
        items.clear();
        Item.clearAllNumbers();
        clearItemData();
    }


    @FXML
    public void loadDocumentation() throws Exception { //Loads Github Page with Documentation
        java.awt.Desktop.getDesktop().browse(new URI("https://github.com/Senarii/CheckoutEWB/blob/master/readme.md"));
    }

    @FXML
    public void saveAndExit() {
        saveData();
        System.exit(0);
    }

    //
    // -----------------------------
    // Item Updates
    // -----------------------------
    //

    @FXML
    public void newItem() {
        Item i = new Item();
        i.setName("[New Item]");
        items.add(i);
        FXCollections.sort(items);
        itemSelect.setItems(items);
        itemSelect.setValue(i);
        updateItemTextField(i);
    }

    public void removeItem() {
        if (removeItemNum.getText() == null) return;
        Item i = null;
        try {
            int itemNumber = Integer.parseInt(removeItemNum.getText());
            i = Item.getItemFromID("" + itemNumber);
        } catch (Exception ignored) {
        }
        if (i == null) return;
        items.remove(i);
        i.remove();
        removeItemNum.clear();
        if (selectedItem == i) selectedItem = null;
        updateItem();
        itemSelect.setItems(items);
    }

    public void removeItem(Item i) {
        if (i == null) return;
        items.remove(i);
        i.remove();
        removeItemNum.clear();
        if (selectedItem == i) selectedItem = null;
        updateItem();
        itemSelect.setItems(items);
    }


    private void clearItemData() {
        itemName.setText("");
        itemPrice.setText("");
        itemOwner.setText("");
        itemNotes.setText("");
        itemNumber.setText("");
    }

    @FXML
    public void updateItem() {
        if (selectedItem == null) {
            clearItemData();
            return;
        }
        saveCurrentItemData(selectedItem);
        itemSelect.setItems(items);
        updateItemTextField(selectedItem);
    }

    @FXML
    private void saveCurrentItemData(Item i) {
        i.setName(itemName.getText());
        i.setNotes(itemNotes.getText());

        try {
            int ownerNumber = Integer.parseInt(itemOwner.getText());
            Guest g = Guest.getGuestFromID("" + ownerNumber);
            i.setOwner(g);
            if (g != null && g == selectedGuest)
                updateGuest();

        } catch (Exception ignored) {
        }

        try {
            double d = Double.parseDouble(itemPrice.getText());
            i.setPrice(d);
        } catch (Exception ignored) {
        }

        setItemNumber(); //Do this last

    }

    @FXML
    public void selectItemFromList() {
        Item i = itemSelect.getValue();
        selectedItem = i;
        updateItemTextField(i);
    }

    @FXML
    private void updateItemTextField(Item i) {
        if (i == null) {
            clearItemData();
            return;
        }
        itemName.setText(i.getName());
        itemPrice.setText("" + i.getPrice());
        itemNumber.setText("" + i.getNumber());
        if (selectedItem.getOwner() != null) itemOwner.setText("" + i.getOwner().getNumber());
        else itemOwner.setText("");
        itemNotes.setText(i.getNotes());
    }

    @FXML
    private void setItemNumber() {
        if (selectedItem == null) return;
        int i = -1;
        try {
            i = Integer.parseInt(itemNumber.getText());
        } catch (Exception ignored) {
        }
        if (i < 0) return;
        selectedItem.setNumber(i);
        FXCollections.sort(items);
        itemSelect.setItems(items);
    }

    //
    // -----------------------------
    // Guest Updates
    // -----------------------------
    //

    /**
     * Adds A New Guest To The List
     */
    @FXML
    public void newGuest() {
        Guest g = new Guest();
        g.setFirstName("[New Guest]");
        guests.add(g);
        FXCollections.sort(guests);
        guestSelect.setItems(guests);
        guestSelect.setValue(g);
        updateGuestTextField(g);
    }

    /**
     * Removes A Guest From The List
     */
    @FXML
    public void removeGuest() {
        if (removeGuestNum.getText() == null) return;
        Guest g = null;
        try {
            int guestNumber = Integer.parseInt(removeGuestNum.getText());
            g = Guest.getGuestFromID("" + guestNumber);
        } catch (Exception ignored) {
        }
        if (g == null) return;
        removeGuestNum.clear();
        guests.remove(g);
        g.remove();
        if (selectedGuest == g) {
            selectedGuest = null;
        }
        updateGuest();
        guestSelect.setItems(guests);

    }

    public void removeGuest(Guest g) {
        if (g == null) return;
        guests.remove(g);
        g.remove();
        if (selectedGuest == g) {
            selectedGuest = null;
        }
        updateGuest();
        guestSelect.setItems(guests);

    }


    /**
     * Updates all guest data on button press, saves and sets values in form to actual data
     */
    @FXML
    public void updateGuest() {
        if (selectedGuest == null) {
            clearGuestData();
            return;
        }
        saveCurrentGuestData(selectedGuest);
        guestSelect.setItems(guests);
        updateGuestTextField(selectedGuest);
    }

    /**
     * Controls For Selecting Guest From Drop-Down Guest Selection List
     */
    @FXML
    public void selectGuestFromList() {
        Guest g = guestSelect.getValue();
        selectedGuest = g;
        updateGuestTextField(g);
    }

    /**
     * Saves Current Data In The Forms To The Desired Guest's Object
     *
     * @param g Guest To Have Form Data Stored To
     */
    @FXML
    private void saveCurrentGuestData(Guest g) {
        //Direct String Inputs
        g.setLastName(lastName.getText());
        g.setFirstName(firstName.getText());
        g.setPhoneNumber(phoneNumber.getText());
        g.setEmail(email.getText());
        g.setNotes(guestNotes.getText());

        //Numerial Inputs
        try {
            int i = Integer.parseInt(tShirt.getText());
            g.setNumberShirts(i);
        } catch (Exception ignored) {
        }

        try {
            int i = Integer.parseInt(glasses.getText());
            g.setNumberCups(i);
        } catch (Exception ignored) {
        }

        try {
            double d = Double.parseDouble(guestDonation.getText());
            g.setDonation(d);
        } catch (Exception ignored) {
        }

        try {
            double d = Double.parseDouble(changeGiven.getText());
            g.setChangeGiven(d);
        } catch (Exception ignored) {
        }

        try {
            double d = Double.parseDouble(entryDonation.getText());
            g.setEntryDonation(d);
        } catch (Exception ignored) {
        }

        try {
            double d = Double.parseDouble(amountPaid.getText());
            g.setAmountPaid(d);
        } catch (Exception ignored) {
        }

        //Boolean Inputs
        g.setOrderComplete(orderComplete.isSelected());
        g.setPaidAuctionItemsCash(!auctionPaidByCheck.isSelected());
        g.setPaidEntryDonationCash(!entryPaidByCheck.isSelected());
        setGuestNumber();//Do This Last
    }

    /**
     * Will Set All Of The Selected Text In The Forms To The Info Of The Selected Guest
     *
     * @param g Guest That Will Have Form Info Populated By
     */
    @FXML
    private void updateGuestTextField(Guest g) {
        if (g == null) {
            clearGuestData();
            return;
        }
        lastName.setText(g.getLastName());
        firstName.setText(g.getFirstName());
        phoneNumber.setText(g.getPhoneNumber());
        email.setText(g.getEmail());
        tShirt.setText("" + g.getNumberShirts());
        glasses.setText("" + g.getNumberCups());
        guestDonation.setText("" + g.getDonation());
        changeGiven.setText("" + g.getChangeGiven());
        entryDonation.setText("" + g.getEntryDonation());
        guestNotes.setText(g.getNotes());
        amountPaid.setText("" + g.getAmountPaid());
        orderComplete.setSelected(g.getOrderComplete());
        auctionPaidByCheck.setSelected(!g.getPaidAuctionItemsCash());
        entryPaidByCheck.setSelected(!g.getPaidEntryDonationCash());
        guestNumber.setText("" + g.getNumber());
        updateGuestItems(g);
        totalDue.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        totalDue.setText("" + g.checkout());
        updatePrice();
        getChangeNeeded();
    }


    /**
     * Sets Guest's Number Value In The "Guest Number" Form.
     * Does Some Other Updates To Make Sure Everything Works Out
     */
    @FXML
    private void setGuestNumber() {
        if (selectedGuest == null) return;
        int i = -1;
        try {
            i = Integer.parseInt(guestNumber.getText());
        } catch (Exception ignored) {
        }
        if (i < 0) return;
        selectedGuest.setNumber(i);
        FXCollections.sort(guests);
        guestSelect.setItems(guests);
        if (selectedItem != null && selectedItem.getOwner() == selectedGuest)
            itemOwner.setText("" + selectedItem.getOwner().getNumber());
    }

    /**
     * Removes The Current Guest Data From The Form And Sets It To Blank
     */
    private void clearGuestData() {
        lastName.setText("");
        firstName.setText("");
        phoneNumber.setText("");
        email.setText("");
        tShirt.setText("");
        glasses.setText("");
        guestDonation.setText("");
        changeGiven.setText("");
        entryDonation.setText("");
        guestNotes.setText("");
        amountPaid.setText("");
        orderComplete.setSelected(false);
        auctionPaidByCheck.setSelected(false);
        entryPaidByCheck.setSelected(false);
        guestItemList.setText("");
        guestNumber.setText("");
        paymentNeeded.setText("");
        totalDue.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        totalDue.setText("");
        changeNeeded.setText("");
    }


    //
    // -----------------------------
    // Item Updates
    // -----------------------------
    //


    /**
     * Called from the "Remove Item Owner" Button On The Interface
     * Will Remove All Ownership Of This Item.
     */
    @FXML
    public void removeItemOwner() {
        if (selectedItem == null) return;
        selectedItem.setOwner(null);
        selectedGuest.getItems().remove(selectedItem);
        itemOwner.clear();
    }

    //
    // -----------------------------
    // Dynamic Label Updates
    // -----------------------------
    //

    /**
     * Will Update The "Total Due" category and the "Payment Required" Labels
     * Dynamically Updates As Inputs Are Put Into The Forms
     */
    @FXML
    public void updatePrice() {
        if (selectedGuest == null) return;

        try {
            int i = Integer.parseInt(entryDonation.getText());
            selectedGuest.setEntryDonation(i);
        } catch (Exception ignored) {
        }

        try {
            int i = Integer.parseInt(tShirt.getText());
            selectedGuest.setNumberShirts(i);
        } catch (Exception ignored) {
        }

        try {
            int i = Integer.parseInt(glasses.getText());
            selectedGuest.setNumberCups(i);
        } catch (Exception ignored) {
        }

        try {
            double d = Double.parseDouble(guestDonation.getText());
            selectedGuest.setDonation(d);
        } catch (Exception ignored) {
        }

        try {
            double d = Double.parseDouble(amountPaid.getText());
            selectedGuest.setAmountPaid(d);
        } catch (Exception ignored) {
        }

        totalDue.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        totalDue.setText("" + selectedGuest.checkout());

        if (selectedGuest.getAmountPaid() < selectedGuest.checkout()) {
            paymentNeeded.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
            paymentNeeded.setTextFill(Color.RED);
            paymentNeeded.setText("*Payment Required*");
        } else paymentNeeded.setText("");

        getChangeNeeded();

    }

    /**
     * Will Update The "Change Needed" Label Dynamically
     * Has Minor Error Checking Built In To Prevent Errors In Making Change
     * Dynamically Updates As Inputs Are Put Into Forms
     */
    @FXML
    private void getChangeNeeded() {
        if (selectedGuest == null) return;
        try {
            double d = Double.parseDouble(amountPaid.getText());
            selectedGuest.setAmountPaid(d);
        } catch (Exception ignored) {
        }

        try {
            double d = Double.parseDouble(changeGiven.getText());
            selectedGuest.setChangeGiven(d);
        } catch (Exception ignored) {
        }

        double checkout = selectedGuest.checkout();
        double amountChange = selectedGuest.getAmountPaid() - checkout - selectedGuest.getChangeGiven();
        if (checkout > 0) {
            if ((selectedGuest.getAmountPaid() <= checkout && selectedGuest.getChangeGiven() > 0) || (amountChange < 0 && selectedGuest.getAmountPaid() >= checkout)) {
                changeNeeded.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
                changeNeeded.setTextFill(Color.DARKRED);
                changeNeeded.setText("*ERROR IN PAYMENT*");

            } else if (amountChange > 0) {
                changeNeeded.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
                changeNeeded.setTextFill(Color.GREEN);
                changeNeeded.setText("*Change Needed: $" + amountChange + "*");
            } else changeNeeded.setText("");
        } else changeNeeded.setText("");

    }


    //
    // Misc.
    //

    /**
     * Updates The Item List Of A Guest Object
     *
     * @param g Guest To Add Items To
     */
    private void updateGuestItems(Guest g) {
        //TODO: Find a way to make this method more efficient

        StringBuilder owned = new StringBuilder();
        g.getItems().clear();
        for (Item i : items) {
            if (i.getOwner() == g) {
                owned.append("[$").append(i.getPrice()).append("]  #[").append(i.getNumber()).append("]    ").append(i.getName()).append("\n");
                g.getItems().add(i);
            }
        }
        guestItemList.setText(owned.toString());
    }

}
