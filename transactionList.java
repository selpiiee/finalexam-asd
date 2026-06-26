
public class transactionList {

    transactionNode head;
    transactionNode tail;

    public transactionList() {
        this.head = null;
        this.tail = null;
    }

    //untuk menambahkan transaksi dari belakang atau dari yang terakhir
    public void insertLast(transactions tx) {
        transactionNode newNode = new transactionNode(tx);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
    }

    //ini untuk memcetak laporan finansial selama transaksi
    public void displayReport(iTree inventory) {
        if (head == null) {
            System.out.println("\n No Transactions yet!!");
            return;
        }

        System.out.println("\n============================================");
        System.out.println("          Cashier Transactions History        ");
        System.out.println("============================================");
        System.out.printf("| %-6s | %-12s | %-20s | %-5s | %-13s |\n", "TX-ID", "Code", "Item Name", "Qty", "Total Price");
        System.out.println("--------------------------------------------");

        transactionNode current = head;
        double totalIncome = 0;

        while (current != null) {
            items Linkeditems = inventory.search(current.data.getGoods());
            String itemName = (Linkeditems != null) ? Linkeditems.getName() : "Unknown Item";

            System.out.printf("| %-6d | %-12s | %-20s | %-5d | $%,12.2f |\n",
                    current.data.getTcode(), current.data.getGoods(), itemName, current.data.getQuantity(), current.data.getTotalPrice());

            totalIncome += current.data.getTotalPrice();
            current = current.next;
        }
        System.out.println("============================================");
        System.out.printf(" TOTAL REVENUE: $%,.2f\n", totalIncome);
        System.out.println("============================================");
    }

    public void sortByNameAscending(iTree inventory) {
        if (head == null || head.next == null) {
            return; 
        }

        boolean swapped;
        do {
            swapped = false;
            transactionNode current = head;

            while (current.next != null) {
                transactionNode nextNode = current.next;

                items item1 = inventory.search(current.data.getGoods());
                items item2 = inventory.search(nextNode.data.getGoods());

                String name1 = (item1 != null) ? item1.getName() : "";
                String name2 = (item2 != null) ? item2.getName() : "";

                // untuk mengurutkan alfabetnya A-Z
                if (name1.compareTo(name2) > 0) {
                   //untuk menukar value datanya
                    transactions temp = current.data;
                    current.data = nextNode.data;
                    nextNode.data = temp;
                    
                    swapped = true;
                }
                current = current.next;
            }
        } while (swapped);
    }
}
