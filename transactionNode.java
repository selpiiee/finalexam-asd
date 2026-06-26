public class transactionNode {
    transactions data;
    transactionNode next;
    transactionNode prev;

    public transactionNode(transactions data) {
        this.data = data;
        this.next = null;
        this.prev = null;
    }
}