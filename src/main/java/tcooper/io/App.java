package tcooper.io;

import org.apache.commons.dbcp2.BasicDataSource;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import tcooper.io.mappers.OrganisationMapper;
import tcooper.io.model.Organisation;
import tcooper.io.model.User;

import java.util.List;

public class App {

    public static void main(String[] args) {
        
        System.out.println("JDBI Test app!");

        // Setup connection
        var dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:postgresql://localhost:5432/test_data");

        // setup jdbi and open connection to postgres
        Jdbi jdbi = Jdbi.create(dataSource);

        // setup a mapper
        jdbi.registerRowMapper(new OrganisationMapper());

        // execute stuff with handle!
        // get some strings
        jdbi.useHandle(handle -> {
            var userNames = getUserNames(handle);
            System.out.println(userNames);
        });

        // get some rows mapped to POJO
        var users = jdbi.withHandle(App::getUsers);
        System.out.println("Got some user objects...");

        // get some organisation rows mapped to the POJO
        // with a pre-registered mapper
        var orgs = jdbi.withHandle(App::getOrganisations);
        System.out.println("Got some org objects...");
    }


    /**
     * Get the user names as strings
     * @param handle
     * @return List of user names
     */
    private static List<String> getUserNames(Handle handle){

        System.out.println("Get users names..");

        return handle
                .createQuery("select user_name from users")
                .mapTo(String.class)
                .list();
    }

    /**
     * Get the users from the DB mapped to POJO
     * @param handle
     * @return List of User
     */
    private static  List<User> getUsers(Handle handle) {
        return handle
                .createQuery("SELECT id, user_name, email FROM users")
                .map((rs, ctx) -> new User(rs.getLong(1), rs.getString(2), rs.getString(3)))
                .list();
    }

    /**
     * Get the organisations from the DB using the pre registered mapper
     * @param handle
     * @return List of organisations
     */
    private static List<Organisation> getOrganisations(Handle handle) {
        return handle
                .createQuery("SELECT id, name FROM organisation")
                .mapTo(Organisation.class)
                .list();
    }
}
