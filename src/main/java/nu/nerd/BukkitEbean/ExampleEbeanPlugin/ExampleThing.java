package nu.nerd.BukkitEbean.ExampleEbeanPlugin;

import com.avaje.ebean.validation.NotNull;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Simple model for the example plugin
 */
@Entity()
public class ExampleThing {

    @Id
    private int id;

    @NotNull
    private String name;

    public ExampleThing() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
