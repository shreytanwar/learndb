
/// Minimum acceptable page size.
///
/// When in debug mode the minimum value of the page size is calculated so that
/// a normal [`Page`] instance can store at least one valid [`Cell`], which is
/// an aligned cell that can fit [`CELL_ALIGNMENT`] bytes.
///
/// In numbers, at the moment of writing this the page header is 12 bytes, a
/// slot pointer is 2 bytes, and the cell header is 8 bytes. This gives us a
/// total of 22 bytes of metadata for one single payload.
///
 /// [`CELL_ALIGNMENT`] is 8 bytes, so we'll consider that to be the minimum
/// payload. Essentially what we do is add 8 bytes to 22 bytes, giving us 30
/// bytes, and then we align upwards to 32 bytes. So the minimum page size in
/// debug mode can store only 8 bytes worth of data, but we allow this because
/// most tests use really small page sizes for simplicity and because it's
/// easier to debug.
class ToRemove {

    public static final int MAGIC = 0xB74EE;
    public static final int MAX_PAGE_SIZE = 64 << 10; // or 64 << 10

    // TODO: check if we can get size wihtout creating an object
    /// Size of the [`Page`] header. See [`PageHeader`] for details.
    public static final int PAGE_HEADER_SIZE = Utils.getObjectSize(new PageHeader());

    /// Size of the database file header.
    public static final int DB_HEADER_SIZE = Utils.getObjectSize(new DbHeader());

    /// Size of [`CellHeader`].
    public static final int CELL_HEADER_SIZE = Utils.getObjectSize(new CellHeader());

    /// Size of an individual slot (offset pointer).
    public static final int SLOT_SIZE = Short.BYTES;


    /*
        EXAMPLE:
        The page header is 12 bytes,  
        a slot pointer is 2 bytes, and 
        the cell header is 8 bytes.
        This gives us a total of 22 bytes of metadata for one single payload.

        did not understand the comment, its 1AM write now
     */
    public static final int usize = 512;

    class PageHeader {

        /// Amount of free bytes in this page.
        short freeSpace;
        short numSlots;
        short lastUserOffset;
        short padding;
    }

/// 

}
