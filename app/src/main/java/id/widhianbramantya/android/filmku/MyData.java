package id.widhianbramantya.android.filmku;

class MyData {
    private String imdbID, title, poster, plot, rating;

    public MyData(String imdbID, String title, String poster, String plot, String rating) {
        this.imdbID = imdbID;
        this.title = title;
        this.poster = poster;
        this.plot = plot;
        this.rating = rating;
    }

    public String getImdbID() {
        return imdbID;
    }

    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
