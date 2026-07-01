
import java.util.*;
import java.nio.file.Path;

// TODO: remove
public class COVERCLASS {

    // Default values for page size and max journal buffered pages
    final int DEFAULT_PAGE_SIZE = 4096;
    final int DEFAULT_MAX_JOURNAL_BUFFERED_PAGES = 10;

//     DbErrorpub enum DbError {
//     /// Files, sockets, etc.
//     Io(io::Error),
//     /// [`sql::parser`] error.
//     Parser(ParserError),
//     /// Other SQL error not related to syntax.
//     Sql(SqlError),
//     /// Something in the database file or journal file is corrupted/unexpected.
//     Corrupted(String),
//     /// Query too large or out of memory.
//     NoMem,
//     /// Uncategorized custom error.
//     Other(String),
// }
// No unsigned int int java so storing page number in long instead
    public class PageNumber {

        public long value;
    }

    // Builder Pattern
    public class Builder {

        // file system block size
        // or preferred I/O read/write buffer size
        private Optional<Integer> blockSize;
        // high level page size
        private int pageSize;
        // Path to  journal file
        // private Path journalFilePath;
        // // Max pages to buffer before writing to the journal
        // // TODO: what?
        // private int maxJournalBufferedPages;

        public Builder() {
            blockSize = Optional.empty();
            pageSize = DEFAULT_PAGE_SIZE;
            // journalFilePath = Path.of("");
            // maxJournalBufferedPages = DEFAULT_MAX_JOURNAL_BUFFERED_PAGES;
        }

        public Builder setPageSize(int pageSize) {
            this.pageSize = pageSize;
            return this;
        }

        public Builder setBlockSize(int blockSize) {
            this.blockSize = Optional.of(blockSize);
            return this;
        }
        // public Builder setJournalFilePath(Path journalFilePath){
        //     this.journalFilePath = journalFilePath;
        //     return this;
        // }
        // public Builder setMaxJournalBufferedPages (int maxJournalBufferedPages ){
        //     this.maxJournalBufferedPages  = maxJournalBufferedPages ;
        //     return this;
        // }

        public <F> Pager<F> build(F file) {
            // if blockSize is not set
            // use pageSize as the deafult block size
            int effectiveBlockSize = blockSize.orElse(pageSize);

            return new Pager<>(
                    BlockIo.newInstance(file, pageSize, effectiveBlockSize),
                    effectiveBlockSize,
                    pageSize
            );
        }
    }

    // Generic Pager class
    public class Pager<F> {
        // The I/O hanlder for storage operations

        private BlockIo<F> file;
        // file system block size
        // or preferred I/O read/write buffer size
        private final int blockSize;
        // High-level page size.
        private final int pageSize;
        // Trasaction journal
        // stores original values as far as I know
        // private Journal<F> journal;

        // // Pages writted in to journal file
        // private HashSet<PageNumber> journalPages;
        public Builder builder() {
            return new Builder();
        }

        public Pager(BlockIo<F> file, int blockSize, int pageSize) {
            this.file = file;
            this.blockSize = blockSize;
            this.pageSize = pageSize;
            // this.journal = journal;
            // this.journalPages = new HashSet<>();
        }

        public int getBlockSize() {
            return blockSize;
        }

        public int getPageSize() {
            return pageSize;
        }

        // public Journal<F> getJournal() {
        //     return journal;
        // }
        // public HashSet<PageNumber> getJournalPages() {
        //     return journalPages;
        // }
        @Override
        public boolean equals(Object o) {
            // same object
            if (this == o) {
                return true;
            }

            // not instance of Pager class
            if (!(o instanceof Pager<?>)) {
                return false;
            }

            //type cast for matching
            Pager<?> pager = (Pager<?>) o;
            return blockSize == pager.blockSize
                    && pageSize == pager.pageSize
                    && Objects.equals(file, pager.file);
            // && Objects.equals(journal, pager.journal)
            // && Objects.equals(journalPages, pager.journalPages);
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.file, this.blockSize, this.pageSize);
            // this.journal, journalPages);
        }

        // for debugging purpose
        @Override
        public String toString() {
            return "Pager{"
                    + "blockSize=" + blockSize
                    + ", pageSize=" + pageSize
                    // + ", journalMaxBufPages=" + (journal != null ? journal.getMaxPages() : 0)
                    + '}';
        }

        public int read(PageNumber pageNumber, byte[] buf) throws IOException {
            return this.file.read(pageNumber, buf);
        }

        public int write(PageNumber pageNumber, byte[] buf) throws IOException {
            return this.file.write(pageNumber, buf);
        }

        public void flush() throws IOException {
            this.file.flush();
        }

        public void sync() throws IOException {
            this.file.sync();
        }

        public void init() throws IOException {
            PageZero pageZero = PageZero.alloc(this.pageSize);
            Arrays.fill(pageZero.asMutable)
        }
    }

}
