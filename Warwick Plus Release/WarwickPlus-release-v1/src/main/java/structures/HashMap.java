// This line allows us to cast our object to type (E) without any warnings.
// For further detais, please see: http://docs.oracle.com/javase/1.5.0/docs/api/java/lang/SuppressWarnings.html
@SuppressWarnings("unchecked") 
public class HashMap<K extends Comparable<K>,V> implements IMap<K,V> {

    protected KeyValuePairLinkedList[] table;
    
    public HashMap() {
        /* for very simple hashing, primes reduce collisions */
        this(11);
    }
    
    public HashMap(int size) {
        table = new KeyValuePairLinkedList[size];
        initTable();
    }

    // INCOMPLETE.
    public int find(K key) {
        //returns the number of comparisons required to find element using Linear Search.
        ListElement<KeyValuePair<K,V>> temp = table[hash(key)%table.length].head;
        int count = 0;
        while(temp != null) {
            count+=1;
            if(temp.getValue().getKey().equals(key)) {
                return count;
            }
            
            temp = temp.getNext();
        }
        
        return -1;
    }
    
    protected void initTable() {
        for(int i = 0; i < table.length; i++) {
            table[i] = new KeyValuePairLinkedList<>();
        }
    }
    
    protected int hash(K key) {
        int code = key.hashCode();
        return code;    
    }
    

    public void add(K key, V value) {
        int hash_code = hash(key);
        int location = hash_code % table.length;
        
        System.out.println("Adding " + value + " under key " + key + " at location " + location);
        
        table[location].add(key,value);
    }

    public V get(K key) {
        int hash_code = hash(key);
        int location = hash_code % table.length;
        
        ListElement<KeyValuePair> ptr = table[location].head;
        
        return (V)table[location].get(key).getValue();
    }

    public void getAll(K key) {
        head = get(key);
        while (head.next != null) {

            head = head.next; 
        }
    }

    //removes a given rating from the hash table, by accessing the list with the key, iterating through till the rating with value V is found and then setting the previous elements pointer to the next and vice versa
    public Calender removeRating(K key, V value) {
        int hash_code = hash(key);
        int location = hash_code % table.length;
        ListElement<KeyValuePair> ptr = table[location].head;

        if (ptr == null) {
            return false;
        }

        while (ptr.getNext()!= null) {
            //user id stored at index 0
            if (ptr.getValue()[0] == value) {
                //remove element by having no pointers pointing to it 
                ptr.getPrev().setNext(ptr.getNext()); 
                ptr.getNext().setPrev(ptr.getPrev());
                return ptr.getValue()[2]; //return the timestamp, so it can be used to help in removing the entry in ratingsByTimestamp    
            }
            ptr = ptr.getNext();
        }
        return false; 
    }

    public void removeByTimestamp(K key, ) {
        int hash_code = hash(key);
        int location = hash_code % table.length;
        ListElement<KeyValuePair> ptr = table[location].head;

        if (ptr == null) {
            return false;
        }

        while (ptr.getNext()!= null) {
            //user id stored at index 0
            if (ptr.getValue()[0] == value) {
                //remove element by having no pointers pointing to it 
                ptr.getPrev().setNext(ptr.getNext()); 
                ptr.getNext().setPrev(ptr.getPrev());
                return ptr.getValue()[2]; //return the timestamp, so it can be used to help in removing the entry in ratingsByTimestamp    
            }
            ptr = ptr.getNext();
        }
        return false; 

    }
}
