package nu.nerd.BukkitEbean.ExampleEbeanPlugin;

import com.avaje.ebean.EbeanServer;
import com.avaje.ebeaninternal.api.SpiEbeanServer;
import com.avaje.ebeaninternal.server.ddl.DdlGenerator;
import nu.nerd.BukkitEbean.EbeanBuilder;
import org.bukkit.plugin.java.JavaPlugin;

import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.List;


/**
 * Example Bukkit plugin using Ebean
 */
public class ExampleEbeanPlugin extends JavaPlugin {

    EbeanServer db;

    /**
     * Build an EbeanServer object with the defaults classic Bukkit would use
     */
    public void onEnable() {
        this.db = new EbeanBuilder(this).setClasses(models()).build();
        createTables();
    }

    /**
     * List of model classes to register with Ebean
     */
    private List<Class<?>> models() {
        List<Class<?>> classes = new ArrayList<Class<?>>();
        classes.add(ExampleThing.class);
        return classes;
    }

    /**
     * Create tables in the database if they don't exist yet
     */
    private void createTables() {
        try {
            db.find(ExampleThing.class).findRowCount();
        } catch (PersistenceException ex) {
            getLogger().info("Initializing database tables.");
            SpiEbeanServer spi = (SpiEbeanServer) db;
            DdlGenerator ddl = spi.getDdlGenerator();
            ddl.runScript(false, ddl.generateCreateDdl());
        }
    }

    /**
     * Accessor to get the DB object from other classes
     */
    public EbeanServer getDB() {
        return db;
    }

}


