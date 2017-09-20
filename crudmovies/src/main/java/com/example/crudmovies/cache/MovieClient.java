package com.example.crudmovies.cache;

import com.example.holentities.entity.Movie;
import com.unknown.cache.client.AbstractIgniteClient;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.cache.query.QueryCursor;
import org.apache.ignite.cache.query.ScanQuery;
import org.apache.ignite.cache.query.SqlFieldsQuery;
import org.apache.ignite.cache.query.SqlQuery;

import javax.cache.Cache;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by spetsiotis on 2/21/17.
 */
public class MovieClient extends AbstractIgniteClient<Integer, Movie> {
    @Override
    protected Class getCachedObjectClass() {
        return Movie.class;
    }


    public List<Movie> getAll(){
        QueryCursor cursor = getCache().query(new ScanQuery());
        return extractValuesFromCursor(cursor);
    }

    public List<IgniteCache<Integer, Movie>> getAll(int page, int limit) {
        int offset=(page - 1) * limit;
        SqlQuery sql = new SqlQuery(getCachedObjectClass(), "limit ? offset ?");
        QueryCursor cursor = getCache().query(sql.setArgs(limit, offset));
        return extractValuesFromCursor(cursor);
    }

    public int count() {
        SqlFieldsQuery sql = new SqlFieldsQuery("select count(*) from movie");
        List<List<Long>> count = getCache().query(sql).getAll();
        return count.get(0).get(0).intValue();
    }

    private List<Movie> extractValuesFromCursor( QueryCursor<Cache.Entry<Integer, Movie>> cursor) {
        List<Movie> l = new ArrayList<>();
        cursor.forEach(i->l.add(i.getValue()));
        return l;
    }
}
