package com.adriangrabowski.popularmovies;

import android.content.Context;
import android.net.Uri;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Adrian on 04/10/2017.
 */

public class Movie implements Serializable {

    private static String TAG = "**** Movie ****";


    private String title;
    private String posterPath;
    private String overview;
    private String releaseDate;
    private String posterUrl;
    private Double rating;
    private long id;

    private Set<Review> reviews;
    private Set<Trailer> trailers;


    private transient Context currentContext;

    public Movie(long id, String title, String poster, String overview, String releaseDate, Double rating, Context context) {

        currentContext = context;

        this.id = id;
        this.title = title;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.rating = rating;

        reviews = new HashSet<>();
        trailers = new HashSet<>();

        posterUrl = poster;


    }


    public Movie(JSONObject jsonObject, Context context) {

        currentContext = context;
        reviews = new HashSet<>();
        trailers = new HashSet<>();


        try {
            id = jsonObject.getLong("id");
            title = jsonObject.getString("title");
            posterPath = jsonObject.getString("poster_path");
            overview = jsonObject.getString("overview");
            releaseDate = jsonObject.getString("release_date");
            rating = jsonObject.getDouble("vote_average");
        } catch (JSONException e) {
            e.printStackTrace();
        }


        posterUrl = createPosterUrl(posterPath);


    }

    private String createPosterUrl(String posterPath) {

        String baseUrl = currentContext.getString(R.string.base_url_for_poster_image);
        String size = currentContext.getString(R.string.poster_image_size);

        Uri myUri = Uri.parse(baseUrl).buildUpon()
                .appendPath(size).appendEncodedPath(posterPath).build();

        String pUrl = myUri.toString();

        return pUrl;

    }


    public String getPosterUrl() {
        return posterUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public Double getRating() {
        return rating;
    }

    public long getId() {
        return id;
    }

    public Set<Review> getReviews() {
        return reviews;
    }

    public Set<Trailer> getTrailers() {
        return trailers;
    }

    public void addReview(String id, String author, String content) {
        reviews.add(new Review(id, author, content));
    }

    public void addTrailer(String id, String name, String siteName, String key) {
        trailers.add(new Trailer(id, name, siteName, key));
    }

    public void clearTrailersAndReviews() {
        trailers.clear();
        reviews.clear();
    }

    public class Review implements Serializable {
        private String id;
        private String author;
        private String content;

        public Review(String id, String author, String content) {
            this.id = id;
            this.author = author;
            this.content = content;
        }

        @Override
        public String toString() {
            return "Review{" +
                    "id='" + id + '\'' +
                    ", author='" + author + '\'' +
                    ", content='" + content + '\'' +
                    '}';
        }

        public String getId() {
            return id;
        }

        public String getAuthor() {
            return author;
        }

        public String getContent() {
            return content;
        }
    }

    public class Trailer implements Serializable {
        private String id;
        private String name;
        private String siteName;
        private String key;

        public Trailer(String id, String name, String siteName, String key) {
            this.id = id;
            this.name = name;
            this.siteName = siteName;
            this.key = key;
        }

        @Override
        public String toString() {
            return "Trailer{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    ", siteName='" + siteName + '\'' +
                    ", key='" + key + '\'' +
                    '}';
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getSiteName() {
            return siteName;
        }

        public String getKey() {
            return key;
        }
    }
}
