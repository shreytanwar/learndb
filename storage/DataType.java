public interface  DataType {
    public int getSize();
}

class Int implements  DataType {
    private final int size = 4;
    
    public int getSize() {
        return size;
    }

}
class UnsignedInt implements DataType {
    private final int size = 4;
    
    public int getSize() {
        return size;
    }

}
class BigInt implements DataType {
    private final int size = 8;
    
    public int getSize() {
        return size;
    }

}
class UnsignedBigInt implements DataType {
    private final int size = 8;
    
    public int getSize() {
        return size;
    }

}

class Bool implements DataType {
    private final int size = 2;
    
    public int getSize() {
        return size;
    }
}

class VarChar implements DataType{
    private final int size;

    public VarChar(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }
}