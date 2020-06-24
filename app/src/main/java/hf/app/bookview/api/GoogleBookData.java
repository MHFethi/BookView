package hf.app.bookview.api;

public class GoogleBookData {
    private String mTitle;
    private String mAuthors;
    private String mPublishedDate;
    private String mDescription;
    private String mCategories;
    private String mThumbnail;
    private String mrRetailPrice;
    private String mPreview;
    private String mPrice;
    private int pageCount;
    private String mUrl;
    private String isbn;
    private String langue;



    public GoogleBookData(String mTitle, String mAuthors, String mPublishedDate, String mDescription, String mCategories, String mThumbnail,
                           String mPerview , String price, int pageCount , String mUrl , String isbn, String langue) {
        this.mTitle = mTitle;
        this.mAuthors = mAuthors;
        this.mPublishedDate = mPublishedDate;
        this.mDescription = mDescription;
        this.mCategories = mCategories;
        this.mThumbnail = mThumbnail;
        this.mPreview = mPerview;
        this.mPrice = price;
        this.pageCount = pageCount;
        this.mUrl = mUrl;
        this.isbn = isbn;
        this.langue = langue;


    }

    public String getLangue() {
        return langue;
    }

    public void setLangue(String langue) {
        this.langue = langue;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getmUrl() {
        return mUrl;
    }

    public int getPageCount() {
        return pageCount;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getAuthors() {
        return mAuthors;
    }

    public String getPublishedDate() {
        return mPublishedDate;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getCategories() {
        return mCategories;
    }

    public String getThumbnail() {
        return mThumbnail;
    }




    public String getPerview() {
        return mPreview;
    }

    public String getPrice() {
        return mPrice;
    }




}

