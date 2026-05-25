/* Paper Reference (Modification Tree): Linard Arquint & Peter Müller. (2018). 
    The Binomial Heap Verification Challenge in Viper. 
    In: Principled Software Development. Springer, Cham. 
    DOI: 10.1007/978-3-319-98047-8_13
*/

import java.util.*;

class BinomialHeap {
    private Node head;

    static class Node {
        int key, degree;
        Node parent, child, sibling;

        Node(int key) {
            this.key = key;
            this.degree = 0;
            this.parent = null;
            this.child = null;
            this.sibling = null;
        }
    }

    public boolean isEmpty() {
        return head == null;
    }

    public int peekMin() {
        if (head == null) throw new RuntimeException("Heap is empty");
        Node curr = head;
        int min = curr.key;
        
        while (curr != null) {
            if (curr.key < min) { min = curr.key; }
            curr = curr.sibling;
        }
        return min;
    }

    private void link(Node y, Node x) {
        y.parent = x;
        y.sibling = x.child;
        x.child = y;
        x.degree++;
    }

    private Node mergeHeaps(Node h1, Node h2) {
        if (h1 == null) return h2;
        if (h2 == null) return h1;

        Node resHead = null;
        Node resTail = null;
        Node t1 = h1, t2 = h2;

        if (t1.degree <= t2.degree) { resHead = t1; t1 = t1.sibling; }
        else { resHead = t2; t2 = t2.sibling; }
        resTail = resHead;

        while (t1 != null && t2 != null) {
            if (t1.degree <= t2.degree) { resTail.sibling = t1; t1 = t1.sibling; } 
            else { resTail.sibling = t2; t2 = t2.sibling; }
            resTail = resTail.sibling;
        }

        if (t1 != null) resTail.sibling = t1;
        else resTail.sibling = t2;

        return resHead;
    }

    public void union(BinomialHeap other) {
        if (other == null || other.head == null) return;
        
        this.head = mergeHeaps(this.head, other.head);
        other.head = null; 

        if (this.head == null) return;

        Node prev = null;
        Node curr = this.head;
        Node next = curr.sibling;

        while (next != null) {
            if ((curr.degree != next.degree) || 
                (next.sibling != null && next.sibling.degree == curr.degree)) {
                prev = curr;
                curr = next;
            } 
            else if (curr.key <= next.key) {
                curr.sibling = next.sibling;
                link(next, curr);
            } 
            else {
                if (prev == null) { this.head = next; } 
                else { prev.sibling = next; }
                link(curr, next);
                curr = next;
            }
            next = curr.sibling;
        }
    }

    public void insert(int key) {
        BinomialHeap tempHeap = new BinomialHeap();
        tempHeap.head = new Node(key);
        this.union(tempHeap);
    }

    public int extractMin() {
        if (head == null) throw new RuntimeException("Heap is empty");

        Node minNode = head;
        Node minNodePrev = null;
        Node curr = head;
        Node prev = null;

        while (curr != null) {
            if (curr.key < minNode.key) {
                minNode = curr;
                minNodePrev = prev;
            }
            prev = curr;
            curr = curr.sibling;
        }

        if (minNodePrev == null) { head = minNode.sibling; } 
        else { minNodePrev.sibling = minNode.sibling; }

        Node child = minNode.child;
        Node revChildHead = null;

        while (child != null) {
            Node nextChild = child.sibling;
            child.sibling = revChildHead;
            child.parent = null; 
            revChildHead = child;
            child = nextChild;
        }

        BinomialHeap tempHeap = new BinomialHeap();
        tempHeap.head = revChildHead;
        this.union(tempHeap);

        return minNode.key;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        BinomialHeap heap = new BinomialHeap();

        System.out.println("===== BINOMIAL HEAP =====");
        System.out.println("1. Insert | 2. Extract Min | 3. Peek Min | 4. Exit");

        while (true) {
            System.out.print("\nPilih opsi: ");
            int opsi = in.nextInt();

            if (opsi == 1) {
                System.out.print("Input: ");
                int val = in.nextInt();
                heap.insert(val);
                System.out.println(val + ". The value has been included");
            } else if (opsi == 2) {
                if (heap.isEmpty()) {
                    System.out.println("The heap is empty");
                } else {
                    System.out.println("Minimum value to be empty: " + heap.extractMin());
                }
            } else if (opsi == 3) {
                if (heap.isEmpty()) {
                    System.out.println("The heap is empty");
                } else {
                    System.out.println("Minimum value at this point: " + heap.peekMin());
                }
            } else if (opsi == 4) {
                System.out.println("exit the program");
                break;
            } else {
                System.out.println("Options invalid");
            }
        }
        in.close();
    }
}