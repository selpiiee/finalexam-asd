public class iTree {
    iNode root;

    public iTree() {
        this.root = null;
    }

    //untuk menambahkan barang baru
    public void insert(items item) {
        root = insertRecursive(root, item);
    }

    iNode insertRecursive(iNode current, items item) {
        if (current == null) {
            return new iNode(item);
        }

        int comparison = item.getCode().compareTo(current.item.getCode());

        if (comparison < 0) {
            current.left = insertRecursive(current.left, item);
        } else if (comparison > 0) {
            current.right = insertRecursive(current.right, item);
        }
        return current;
    }

    //untuk mencari barang berdasarkan kode nya
    public items search(String code) {
        iNode current = root;
        while (current != null) {
            int comparison = code.compareTo(current.item.getCode());
            if (comparison == 0) {
                return current.item;
            } else if (comparison < 0) {
                current = current.left;
            } else {
                current = current.right;
            }
        }
        return null;
    }

    public void displayInventory() {
        displayInOrder(root);
    }

    private void displayInOrder(iNode node) {
        if (node != null) {
            displayInOrder(node.left);
            System.out.printf("| %-10s | %-20s | $%,12.2f | %-8d |\n", 
                node.item.getCode(), node.item.getName(), node.item.getPrice(), node.item.getStock());
            displayInOrder(node.right);
        }
    }
}