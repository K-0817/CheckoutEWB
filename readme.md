# UPDATE:
### CheckoutEWB has been recoded and moved to https://github.com/Senarii/CheckoutEWB2. This version of CheckoutEWB is no longer being updated/maintained

# Overview
CheckoutEWB is a system designed for the EWB UMass chapter to better keep track of details during the yearly auction, and to improve efficiency of the checkout process.

### Guests
The program allows storage of guest data, ranging from their name, to their preferred payment method. In order to access guest information, either import a data file or click on the "New Guest" button on the top of the page to make a new guest. To select the current person, simply click on the dropdown menu labelled "Guest Number" and click on the desired number/name. As you fill out the data, it can be accessed later by coming back to the selected guest number from the dropdown menu.


**IMPORTANT!!!** In order for your changes to be saved, you *MUST* click on "Update Guest Profile" after entering information into the sheet.

### Items
The item system works in a similar way to the guest system, but it is able to keep track of all auction items. A unique feature is that all auction items have an "Owner" category, which allows an item to be given to a specific guest.


### Importing/Exporting Data
In order to import data into the program, make a file named "data.csv" and place it in the same directory as the executable jar file (e.g. if the jar file is in Downloads, place the data.csv file in Downloads too). The program will load the user/item data directly into the program when you go to File -> Load.


## User Interface

### Header
![alt text](https://i.imgur.com/2SCYq6A.png)

#### File:
##### Save Data
- The program will either overwrite the existing "data.csv" in the current directory, or create a new one. The file will be in the correct format to be loaded by the program later, and has all of the data in an easy to read format for people. A CSV file can be opened and edited by Microsoft Excel.
##### Load Data
- The program will search for a file named "data.csv" in the same directory as the executable jar file. If the file is not found, no data will be loaded. In order for the data to load correctly, it must follow the correct template. In order to generate a template file, simply input some data into the program and use the "Save Data" option under the "File" menu, and a new "data.csv" file will be generated in the same directory as the program. A CSV file can be opened and edited by Microsoft Excel.
##### Save and Exit
- This will save all of the data to a "data.csv" file, as specified above, and will then close the program.
#### Help:
- Under this menu, a link to this page is provided for reference. An internet connection is necessary in order to correctly load the documentation.
#### New Item:
- Creates a new Item, and adds it to the list of all the items. The Item Menu will automatically select this item and display it. The ID number generated will be the first one available from 0 that is not being used, but this can be changed manually by editing the "Item Number" category to the right of the drop-down selector.
#### New Guest:
- Creates a new Guest, and adds it to the list of all the guests. The Guest Menu will automatically select this item and display it. The ID number generated will be the first one available from 0 that is not being used, but this can be changed manually by editing the "Guest Number" category to the right of the drop-down selector.
#### Remove Item:
- Once a number is inputted into this box, the guest associated with that ID number will be deleted, and all associated data with it will be removed too. **WARNING: THIS ACTION CAN NOT BE UNDONE**
#### Remove Guest
- Once a number is inputted into this box, the item associated with that ID number will be deleted, and all associated data with it will be removed too. **WARNING: THIS ACTION CAN NOT BE UNDONE**

### Guest Information
![alt text](https://i.imgur.com/pboLqRc.png)

#### Guest Number:
- The guest number is the unique identifier assigned to each guest. A guest number must be unique, and the program will not allow you to assign the same number to two people. All operations done with a specific guest reference the person by their number, not their first or last name.
#### First Name:
- This field represents the first name of the guest, which will be shown on the dropdown selector with the guest number.
#### Last Name:
- This field represents the last name of the guest, which will be shown on the dropdown selector with the guest number.
#### Phone Number:
- This is for the guest's phone number, which is not directly used in the program, but can be saved to an exported data sheet for external use.
#### Email:
- This is for the guest's email address, which is not directly used in the program, but can be saved to an exported data sheet for external use.
#### Notes:
- Any notes about the guest can be written here, and will show up once the data is exported. This is the field for small comments or concerns about anything that the specific person has done/needs to do.
#### Entry Donation:
- The amount of money the guest donated to enter the event, inputted as a floating point value (eg: $10.00 or $15.50).
#### Paid By Check:
- Select this box if the guest paid their entry donation with a check instead of with cash.
#### Number of T-Shirts:
- The number of EWB T-shirts the guest will purchase, inputted as an integer. The price per T-shirt is $10.00 and will be updated accordingly in the "Total Due:" category.
#### Number of Glasses:
- The number of EWB pint glasses the guest will purchase, inputted as an integer. The price per glass is $7.00 for 1 and $12.00 for 2. The cost is automatically calculated based on the above prices, and will be updated accordingly in the "Total Due:" category.
#### Donation:
- Any additional donation the guest wishes to make can be inputted here. If the guest pays more money than the total, that money should be recoded as a donation in this field. (eg: Total cost $40, guest pays $50. Input $10 to the this field).
#### Auction Items:
- A list of items the guest has won at the auction. This is automatically generated from the item list and requires no inputs. Each item will have its price, ID number, and name shown in this box.
#### Total Due:
- The total amount of money that is owed, counting the entry donation, T-shirts, cups, additional donations, and any auction items. This value is automatically calculated and will update based on the other inputs such as number of T-shirts and auction items.
#### Paid By Check:
- Select this box if the guest paid for their auction items with a check.
#### Amount Paid:
- The total amount of money that the guest paid, before change.
#### Change Given:
- How much change was given back to the guest after payment.
#### Update Guest Profile:
- Click this button to save the guest's data. **IMPORTANT:** In order for the data to be saved when switching between guests, you **MUST** click this button after changing any fields for the current guest.


### Item Information
![alt text](https://i.imgur.com/6tOSIgF.png)

#### Item Number:
- The item number is the unique identifier assigned to each item in the auction. An item number must be unique, and the program will not allow you to assign the same number to two items. Generally, this will be set in stone before the auction begins, and this value should not be changed during the auction itself, unless without specific permission.
#### Item Name:
- The name of the item, which will display to the item owner and next to the item number in the drop-down selector.
#### Price:
- The price the guest will pay for the item, inputted as a floating point value (eg: $10.00 or $15.50).
#### Owner:
- The guest ID number of who won this item in the auction. After this value is set and the "Update Item Profile" button is clicked, the item will appear in the guest's list of owned items.
#### Notes:
- Any notes about the item. These are not visible to each guest, and is a good spot for general notes and comments.
#### Update Item Profile:
- Click this button to save the item's data. **IMPORTANT:** In order for the data to be saved when switching between items, you **MUST** click this button after changing any fields for the current item.
