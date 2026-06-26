import java.util.Scanner;

public class Main {
    static iTree inventory = new iTree();
    static transactionList ledger = new transactionList();
    static int transactionIdCounter = 1001; 

    public static void main(String[] args) {
        seedInitialBalancedTree();
        Scanner scanner = new Scanner(System.in);
        int userChoice = 0;

        do {
            System.out.println("\n=== CUSTOM DATA STRUCTURE CASHIER APPLICATION (CLI) ===");
            System.out.println("1. Process Payment Checkout");
            System.out.println("2. Print Financial Report (Ledger)");
            System.out.println("3. Sort Transaction History by Item Name (A-Z)");
            System.out.println("4. View Warehouse Stock Sheet (Inventory)");
            System.out.println("5. Exit System");
            System.out.print("Select menu (1-5): ");

            try {
                if (scanner.hasNextInt()) {
                    userChoice = scanner.nextInt();
                    scanner.nextLine(); 
                    
                    switch (userChoice) {
                        case 1 -> processCheckout(scanner);
                        case 2 -> ledger.displayReport(inventory);
                        case 3 -> sortLedgerData();
                        case 4 -> printStockSheet();
                        case 5 -> System.out.println("[!] Closing cashier system. Thank you!");
                        default -> System.out.println("[!] Invalid choice. Please use numbers 1-5.");
                    }
                } else {
                    System.out.println("[!] Input must be a number!");
                    scanner.nextLine(); 
                }
            } catch (Exception e) {
                System.out.println("[!] A system error occurred: " + e.getMessage());
            }
        } while (userChoice != 5);

        scanner.close();
    }

    private static void seedInitialBalancedTree() {
        // Struktur data di-insert manual agar membentuk BST yang seimbang (Balanced Tree)
        inventory.insert(new items("M104", "Premium Blend Coffee", 45000.00, 150));
        inventory.insert(new items("M102", "Artisan Whole Bread", 32000.00, 80));
        inventory.insert(new items("M106", "Organic Almond Milk", 55000.00, 120));
        inventory.insert(new items("M101", "Ceylon Black Tea Box", 28000.00, 200));
        inventory.insert(new items("M103", "Cold Pressed Juice", 48000.00, 60));
        inventory.insert(new items("M105", "Gourmet Dark Chocolate", 75000.00, 90));
        inventory.insert(new items("M107", "Natural Mineral Water", 15000.00, 300));
    }

    private static void processCheckout(Scanner scanner) {
        System.out.println("\n--- START NEW CHECKOUT SESSION ---");
        System.out.print("Enter Item SKU Code: ");
        String productCode = scanner.nextLine().trim();

        items targetedItem = inventory.search(productCode);

        if (targetedItem == null) {
            System.out.println("[Failed] Transaction Cancelled: Item code not found in warehouse.");
            return;
        }

        System.out.printf("Item Found: %s | Available Stock: %d Units\n", targetedItem.getName(), targetedItem.getStock());
        System.out.print("Enter Purchase Quantity: ");
        
        if (!scanner.hasNextInt()) {
            System.out.println("[Failed] Transaction Cancelled: Purchase quantity must be a number.");
            scanner.nextLine();
            return;
        }
        int purchaseQty = scanner.nextInt();
        scanner.nextLine(); 

        // Validasi Stok
        if (purchaseQty <= 0) {
            System.out.println("[Failed] Transaction Cancelled: Minimum purchase quantity is 1 unit.");
            return;
        }
        if (targetedItem.getStock() == 0) {
            System.out.println("[Failed] Transaction Cancelled: Item is out of stock.");
            return;
        }
        if (purchaseQty > targetedItem.getStock()) {
            System.out.printf("[Failed] Transaction Cancelled: Insufficient stock. Requested: %d, Available: %d\n", 
                purchaseQty, targetedItem.getStock());
            return;
        }

        targetedItem.setStock(targetedItem.getStock() - purchaseQty);

        // Penghitung Diskon
        double standardBasePrice = targetedItem.getPrice() * purchaseQty;
        double structuralDiscountAmount = 0;
        
        // Diskon 1: Belanja > 500,000 dapat potongan 5%
        if (standardBasePrice > 500000) {
            structuralDiscountAmount = standardBasePrice * 0.05;
        }
        
        double currentSubTotal = standardBasePrice - structuralDiscountAmount;
        double loyaltyMembershipDiscountAmount = 0;

        // Diskon 2: Cek member (Potongan tambahan 2%)
        System.out.print("Is the customer registered as a Member? (y/n): ");
        String memberResponse = scanner.nextLine().trim().toLowerCase();
        if (memberResponse.equals("y") || memberResponse.equals("yes")) {
            loyaltyMembershipDiscountAmount = currentSubTotal * 0.02; 
        }

        double netBilledAmount = currentSubTotal - loyaltyMembershipDiscountAmount;

        // Cetak Struk Belanja
        System.out.println("\n=======================================================");
        System.out.println("                 OFFICIAL STORE RECEIPT                ");
        System.out.println("=======================================================");
        System.out.printf(" Transaction No : #%d\n", transactionIdCounter);
        System.out.printf(" Product        : [%s] %s\n", targetedItem.getCode(), targetedItem.getName());
        System.out.printf(" Unit Price     : $%,.2f\n", targetedItem.getPrice());
        System.out.printf(" Quantity       : %d units\n", purchaseQty);
        System.out.println("-------------------------------------------------------");
        System.out.printf(" Gross Total    : $%,.2f\n", standardBasePrice);
        
        if (structuralDiscountAmount > 0) {
            System.out.printf(" Bulk Discount (5%%): -$%,.2f\n", structuralDiscountAmount);
        }
        if (loyaltyMembershipDiscountAmount > 0) {
            System.out.printf(" Member Discount (2%%): -$%,.2f\n", loyaltyMembershipDiscountAmount);
        }
        
        System.out.println("-------------------------------------------------------");
        System.out.printf(" FINAL TOTAL    : $%,.2f\n", netBilledAmount);
        System.out.println("=======================================================");

        // menyimpan data transaksi baru ke dalam DLL custom
        transactions freshTransaction = new transactions(transactionIdCounter++, purchaseQty, productCode, netBilledAmount);
        ledger.insertLast(freshTransaction);
        System.out.println("[Success] Transaction successfully recorded in the system.");
    }

    private static void sortLedgerData() {
        System.out.println("\n[!] Sorting history data in memory...");
        ledger.sortByNameAscending(inventory);
        System.out.println("[Success] Transaction history successfully sorted by item name (A-Z).");
    }

    private static void printStockSheet() {
        System.out.println("\n=====================================================================");
        System.out.println("                      ACTIVE WAREHOUSE STOCK SHEET                   ");
        System.out.println("=====================================================================");
        System.out.printf("| %-10s | %-20s | %-14s | %-8s |\n", "SKU Code", "Item Name", "Retail Price", "Remaining Stock");
        System.out.println("---------------------------------------------------------------------");
        inventory.displayInventory();
        System.out.println("=====================================================================");
    }
}