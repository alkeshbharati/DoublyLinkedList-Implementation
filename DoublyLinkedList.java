package ASB180015;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Iterator;

public class DoublyLinkedList<T> extends SinglyLinkedList<T> {
    static class Entry<E> extends SinglyLinkedList.Entry<E> {
        Entry<E> prev;

        Entry(E x, Entry<E> next, Entry<E> prev) {
            super(x, next);
            this.prev = prev;
        }
    }

    public DoublyLinkedList() {
        head = new Entry<>(null,null, null);
        tail = head;
        size = 0;
    }
// Interface which consists of abstracts method of hasPrev Prev and add( to add element before next node) extends Iterator interface
    public interface dlInterface<T> extends Iterator<T> {
        boolean hasPrev();
        T prev();
        void add(T ent);


    }

    public Iterator<T>  dllIterator() { return new DLLIterator(); }

    public class DLLIterator extends SLLIterator implements dlInterface<T>{

        DLLIterator(){
            super();
        }

        @Override
        public void remove(){
            if (!ready)
                throw new NoSuchElementException();
                if (cursor != tail)      // cursor next element previous will be cursor previous element when cursor is not tail node
                   ((Entry<T>)cursor.next).prev = ((Entry<T>)cursor).prev;
            super.remove();

        }

        public boolean hasPrev() {
            if (((Entry<T>)cursor).prev == null ){  // if cursor points to first node
                throw new NoSuchElementException();
            }
            return true;
        }

        public T prev() {
            cursor = ((Entry<T>)cursor).prev;
            ready = true;
            prev=((Entry<T>) cursor).prev; // assign previous node to cursor previous node
            return cursor.element;
        }

        // Add new elements
       public void add(T x) {  // add element before node
            add(new Entry<>(x, null, null));
        }

        public void add(Entry<T> ent) {
            if(!ready) {
                throw new NoSuchElementException();
            }
            ent.next = (Entry<T>)cursor;
            ent.prev = (Entry<T>)prev;

            prev.next = ent;
            ((Entry<T>)cursor).prev = ent;

            prev = ent;
            size++;
            ready = false;
        }
    }

    @Override
   public void add(T x) {  // To add element in doubly linked list
        add(new Entry<T>(x, null, (Entry<T>) tail));
    }

    public static void main(String[] args) throws NoSuchElementException {
        int n = 10;
        if(args.length > 0) {
            n = Integer.parseInt(args[0]);
        }

        DoublyLinkedList<Integer> lst = new DoublyLinkedList<>();

        dlInterface<Integer> d1 = (dlInterface<Integer>)lst.dllIterator();

        for(int i=1; i<=n; i++) {
            lst.add(i);
        }
        lst.printList();
        System.out.println(" 1 - next() , 2 - add() , 3- remove() , 4- previous()");
        System.out.println("Enter 1 to point to first node and then try prev function to avoid  null point exception");
        Scanner in = new Scanner(System.in);
        whileloop:
        while(in.hasNext()) {
            int com = in.nextInt();
            switch(com) {
                case 1:  // Move to next element and print it
                    if (d1.hasNext()) {
                        System.out.println( d1.next());
                    } else {
                        break whileloop;
                    }
                    break;
                case 2: // insert x before the element that will be returned by a call to next()
                    System.out.println("Enter the value of element:");
                    int val = in.nextInt();
                    d1.add(val);
                    lst.printList();
                    break;
                case 3: // remove element and print the linked list
                    d1.remove();
                    lst.printList();
                    break;
                case 4: // Move to previous element and print it
                    if (d1.hasPrev())
                        System.out.println(d1.prev());
                    else
                        break whileloop;
                    break;
                case 5: // print list
                    lst.printList();
                default:  // Exit loop
                    break whileloop;
            }
        }
    }
}