package com.nikesh.musicplaylistapi.constants;

/**
 * @author Nikesh Maharjan
 *         nikeshmhr@gmail.com
 */
public final class ApplicationConstants {

    public interface DataSourceConstants {

        String CONNECTION_URL = "connection.url";
        String CONNECTION_USER = "connection.user";
        String CONNECTION_PASS = "connection.pass";
        String CONNECTION_DRIVER_CLASS = "connection.driverClass";

        String HIBERNATE_DIALECT = "hibernate.dialect";
        String HIBERNATE_SHOW_SQL = "hibernate.show_sql";
        String HIBERNATE_HBM2DDL_AUTO = "hibernate.hbm2ddl.auto";
        String ENTITY_MANAGER_PACKAGES_TO_SCAN = "entitymanager.packages.to.scan";

    }

    public interface ApiConstants {

        String API_BASE_VERSION = "/v1";
    }

    public interface UserResourceConstants {

        String USER_COLLECTION_BASE = "/users";
        String USER_BY_ID = "/users/{userId}";
    }

    public interface SongResourceConstants {

        String SONG_COLLECTION_BASE = "/songs";
        String SONG_BY_ID = "/songs/{songId}";
    }

}
