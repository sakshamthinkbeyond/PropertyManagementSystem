package amdocs;


import java.util.*;


public class mainApp {
    private static List<Property> propList = new ArrayList<>();
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println(DBA_Connector.getConnection());
        MenuDisplay();
    }

    public static void MenuDisplay() {
        int option;

        System.out.println("\n*** MENU ***");

        do {
            System.out.println("1. Add new property");
            System.out.println("2. Update property cost");
            System.out.println("3. Delete Property");
            System.out.println("4. Find by City");
            System.out.println("5. View all Properties");
            System.out.println("6. Find by Cost");
            System.out.println("7. Find by no of rooms and city");
            System.out.println("8. Exit");
            System.out.println("Enter your option: ");
            
            option = sc.nextInt();

            switch (option) {
                case 1:
                    addProperty();
                    break;
                case 2:
                    updatePropertyCost();
                    break;
                case 3:
                    deleteProperty();
                    break;
                case 4:
                    searchByCity();
                    break;
                case 5:
                    showAllProperties();
                    break;
                case 6:
                    searchByCost();
                    break;
                case 7:
                    searchByNoOfRoomsAndCity();
                    break;
                case 8:
                    System.out.println("Exit");
                    break;
                default:
                    System.out.println("Please enter a valid choice (1 to 8)");
                    break;
            }

        } while (option != 8);

        sc.close();
    }

    //Add Property
    
    private static void addProperty() {
        System.out.print("Enter the property id: ");
        int propertyId = sc.nextInt();
        sc.nextLine(); // Consume the newline character

        System.out.print("Enter no Of Rooms: ");
        String noOfRooms = sc.nextLine();

        System.out.print("Enter the area in sqft: ");
        double areaInSqft = sc.nextDouble();
        sc.nextLine(); // Consume the newline character

        System.out.print("Enter the floor No: ");
        int floorNo = sc.nextInt();
        sc.nextLine(); // Consume the newline character

        System.out.print("Enter the city: ");
        String city = sc.nextLine();

        System.out.print("Enter the state: ");
        String state = sc.nextLine();

        System.out.print("Enter the cost: ");
        double cost = sc.nextDouble();
        sc.nextLine(); // Consume the newline character

        System.out.print("Enter the owner name: ");
        String ownerName = sc.nextLine();

        System.out.print("Enter the owner Contact no: ");
        String ownerContactNo = sc.nextLine();

        Property obj = new Property(
            propertyId,
            noOfRooms,
            areaInSqft,
            floorNo,
            city,
            state,
            cost,
            ownerName,
            ownerContactNo
        );

        propList.add(obj);
        System.out.println("________________________________________");
        System.out.println("\nAdded successfully\n");

        DOA daoObj = new DOA();
        daoObj.insert(obj);
    }

    //Update Property Cost

    private static void updatePropertyCost() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the property ID you want to update: ");
        int propertyId = sc.nextInt();

        System.out.println("Enter the new Cost: ");
        double newCost = sc.nextDouble();

        DOA doaObj = new DOA();

        // Update cost in the database
        doaObj.updateCost(propertyId, newCost);

        System.out.println("_______________________________________");
        System.out.println("Updating completed");
    }


    //Show all Properties
    
    private static void showAllProperties() {
        DOA daoObj = new DOA();
        List<Property> propList = daoObj.getAllDetails();

        for (Property obj : propList) {
            System.out.println("Property Id: " + obj.getPropertyId());
            System.out.println("No. of Rooms: " + obj.getNoOfRooms());
            System.out.println("Area in Sqft: " + obj.getAreaInSqft());
            System.out.println("Floor No: " + obj.getFloorNo());
            System.out.println("City: " + obj.getCity());
            System.out.println("State: " + obj.getState());
            System.out.println("Cost: " + obj.getCost());
            System.out.println("Owner Name: " + obj.getOwnerName());
            System.out.println("Owner Contact No: " + obj.getOwnerContactNo());
            System.out.println("________________________________________");
        }
    }

    //Delete Property

    private static void deleteProperty() {
        System.out.println("Enter the ID of the property you want to delete: ");
        int propertyId = sc.nextInt();

        DOA doaObj = new DOA();
        doaObj.deleteProperty(propertyId);

        System.out.println("Data deleted");
        System.out.println("________________________________________");
    }


    private static void searchByCity() {
        DOA doaObj = new DOA();
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the city you want to search for properties in:");
        String city = sc.nextLine();

        try {
            List<Property> propertiesInCity = doaObj.searchByCity(city);

            if (propertiesInCity.isEmpty()) {
                System.out.println("No properties found in the specified city.");
            } else {
                System.out.println("Properties found in " + city + ":");
                for (Property property : propertiesInCity) {
                    System.out.println(property); // Assuming Property class has a meaningful toString method
                }
            }
        } catch (PropertyNotFoundException e) {
            System.out.println("Error: " + e.getMessage()); // Print the error message
        } finally {
            sc.close();
        }

        System.out.println("________________________________________");
        System.out.println("\nData searched\n");
    }

    //Search By Cost
    
        private static void searchByCost() {
            DOA doaObj = new DOA();
            int minimumPrice, maximumPrice;
            System.out.println("Enter the minimum price of property you want");
            minimumPrice = sc.nextInt();

            System.out.println("Enter the maximum price of property you want");
            maximumPrice = sc.nextInt();

            try {
                doaObj.searchByCost(minimumPrice, maximumPrice);
            } catch (PropertyNotFoundException e) {
                System.out.println(e);
            }

            System.out.println("Property found by price range");
        }
        

        
        //Search By No. of Rooms and City
        
        private static void searchByNoOfRoomsAndCity() {
            DOA doaObj = new DOA();
            Scanner sc = new Scanner(System.in);

            System.out.println("Enter the no of rooms in terms of BHK: ");
            String room = sc.nextLine();

            System.out.println("Enter the city name: ");
            String city = sc.nextLine();

            List<Property> propertiesInCityAndRooms = doaObj.searchByNoOfRoomsAndCity(room, city);

            if (propertiesInCityAndRooms.isEmpty()) {
                System.out.println("No properties found in the specified city with " + room + " rooms.");
            } else {
                System.out.println("Properties found in " + city + " with " + room + " rooms:");
                for (Property property : propertiesInCityAndRooms) {
                    System.out.println(property);
                }
            }

            System.out.println("________________________________________");
            System.out.println("\nData searched\n");
        }

        private static Property findPropertyById(int propertyId)
       {
            for (Property property : propList) {
                if (property.getPropertyId() == propertyId) {
                    return property;
                }
            }
            return null; // Property not found
        }
    }






