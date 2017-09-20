package com.example.booking.cache;

import com.example.holentities.entity.Booking;
import com.example.holentities.entity.MovieProgram;
import com.unknown.cache.client.AbstractIgniteClient;
import org.apache.ignite.cache.query.QueryCursor;
import org.apache.ignite.cache.query.ScanQuery;
import org.apache.ignite.cache.query.SqlFieldsQuery;

import javax.cache.Cache;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by spetsiotis on 2/28/17.
 */
public class BookingClient extends AbstractIgniteClient<MovieProgram, Booking> {
    @Override
    protected Class getCachedObjectClass() {
        return Booking.class;
    }

    public List<?> getAll() {
        QueryCursor cursor = getCache().query(new ScanQuery());
        return cursor.getAll();
    }

    public List<?> getDay (int movieId) {
        SqlFieldsQuery sql = new SqlFieldsQuery("SELECT day FROM booking WHERE movieId = ?");
        QueryCursor cursor = getCache().query(sql.setArgs(movieId));
        return extractItemsFromCursor(cursor);
    }

    public List<?> getTimeslot (int movieId, int day) {
        SqlFieldsQuery sql = new SqlFieldsQuery("SELECT startTime FROM booking WHERE movieId = ? and day = ?");
        QueryCursor cursor = getCache().query(sql.setArgs(movieId, day));
        return extractItemsFromCursor(cursor);
    }

//    public Cache.Entry<MovieProgram, Booking> getBooking (int movieId, int day, int startTime) {
//        SqlQuery sql = new SqlQuery(getCachedObjectClass(), "movieId = ? and day = ? and startTime = ?");
//        QueryCursor<Cache.Entry<MovieProgram, Booking>> cursor = getCache().query(sql.setArgs(movieId, day, startTime));
//        return extractItemFromCursor(cursor);
//    }
//
 public String getTheater (int movieId, int day, int startTime) {
     SqlFieldsQuery sql = new SqlFieldsQuery("select hall from booking where movieId = ? and day = ? and startTime = ?");
        QueryCursor cursor = getCache().query(sql.setArgs(movieId, day, startTime));

        return extractItemsFromCursor(cursor).get(0).toString();
    }

    /**
     *
     * Collects all the items from a list of lists to a list.
     *
     * @param cursor
     * @return
     */
    private List<?> extractItemsFromCursor(QueryCursor<List<?>> cursor) {
        return cursor.getAll().stream().flatMap(List::stream).collect(Collectors.toList());
    }

    /**
     * Gets a single IgniteCache record from a list
     *
     * @param cursor
     * @return
     */
    private Cache.Entry<MovieProgram, Booking> extractItemFromCursor(QueryCursor<Cache.Entry<MovieProgram, Booking>> cursor) {
        return cursor.getAll().get(0);
    }
}
