import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

// Byte comparator
interface BytesCmp {
    /**
     * Compares two byte arrays and returns an integer value that indicates
     * their ordering. The comparison method is left to the implementation.
     *
     * @param a The first byte array.
     * @param b The second byte array.
     * @return A negative integer, zero, or a positive integer as the first
     *         argument is less than, equal to, or greater than the second.
     */
    int bytesCmp(byte[] a, byte[] b);
}

class FixedSizeMemCmp implements BytesCmp {
    private final int numBytes;

    public FixedSizeMemCmp(int numBytes){
        this.numBytes = numBytes;
    }

    // Method to compare 2 slices of bytes
    @Override
    public int bytesCmp(byte[] a, byte[] b){
       // Ensure the arrays are at least the size specified
        if (a.length < this.numBytes || b.length < this.numBytes) {
            throw new IllegalArgumentException("Input arrays must be at least " + this.numBytes + " bytes long.");
        }
        
        // Compare the specified number of bytes from each array
        return Arrays.compare(a, 0, this.numBytes, b, 0, this.numBytes);
    }

    public static FixedSizeMemCmp tryFrom(DataType dataType) throws IllegalArgumentException{
        if(dataType instanceof  VarChar || dataType instanceof Bool || dataType == null){
            throw new IllegalArgumentException("Invalid DataType: " + dataType);
        } else {
            // TODO: GET SIZE FROM TUPLE UTIL
            // byte_length_of_integer_type
            int size = dataType.getSize();
            return new FixedSizeMemCmp(size);
        }
    } 
}

class StringCmp implements BytesCmp {
    // prefix bytes which contains the length of the string
    private final int lengthPrefixBytes;

    public StringCmp(int lengthPrefixBytes){
        this.lengthPrefixBytes = lengthPrefixBytes;
    }

    @Override
    public int bytesCmp(byte[] a, byte[] b){
        if(lengthPrefixBytes > 4){
            throw new IllegalArgumentException("Strings longer than 4bytes (255) not supported");
        }

        // wrap -> Wraps a byte array into a buffer. from 0 to length prefix bytes
        int lenA = ByteBuffer.wrap(a, 0, lengthPrefixBytes).getInt();
        int lenB = ByteBuffer.wrap(b, 0, lengthPrefixBytes).getInt();

        // get strings from after prefixBytes and by their length
        String strA = new String(a, lengthPrefixBytes, lenA, StandardCharsets.UTF_8);
        String strB = new String(b, lengthPrefixBytes, lenB, StandardCharsets.UTF_8);
    
        return strA.compareTo(strB);
    }
}


class BTreeKeyComparator {
    private final BytesCmp comparator;

    public BTreeKeyComparator(BytesCmp comparator) {
        this.comparator = comparator;
    }

    public static BTreeKeyComparator from(DataType dataType){
        if(dataType == null){
            throw new IllegalArgumentException("dataType can not be null");
        }
        if(dataType instanceof VarChar){
            return new BTreeKeyComparator(new StringCmp(dataType.getSize())); 
        } else {
            return new BTreeKeyComparator(new FixedSizeMemCmp(dataType.getSize())); 
        }
    }

    public int compare(byte[] a, byte[] b){
        return comparator.bytesCmp(a, b);
    }   

    // class Search {
    //     public 
    // }
}

