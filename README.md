BukkitEbean
==========

**NOTE:** This is still experimental and won't be 100% until after Spigot 1.12 drops.

For most of its existence, Bukkit bundled the Ebean ORM for use in its plugins. Starting with Spigot 1.12 (Bukkit commit eb2c1f23e6e), Ebean will no longer be included by default. So if you have an existing plugin that uses Ebean, or wish to use it in a new one, you're left having to bootstrap database connections entirely on your own instead of having it all done for you.

BukkitEbean is a simple library that you can include as a dependency to have this done for you. With some minor additions to your plugin, it will behave as classic Bukkit always did, using the exact same defaults. Plus, you can change every aspect of the database connection by using the exposed Builder methods.


Basic Usage
-----------

The simplest way to use BukkitEbean is as follows:

```java
EbeanServer db = new EbeanBuilder(this).setClasses(models).build();
```

This will instantiate an EbeanServer using the exact default options of older Bukkit versions. In this example, `models` is a `List<Class<?>>` of every entity class that will be used as a database model. e.g.:

```java
List<Class<?>> models = new ArrayList<Class<?>>();
models.add(ExampleThing.class);
```

Now that you have your `db` reference, you can use it as you always would with the deprecated Bukkit method.


Changing the Defaults
---------------------

The `EbeanBuilder` class exposes several methods that can be used to override the default database settings. It works using the "Builder Pattern," so you can chain them. The `build()` method ends the chain and returns the finished `EbeanServer` object.

Let's try using a MySQL database instead...

```java
EbeanServer db = new EbeanBuilder(this)
    .setName("MyDatabase")
    .setDriver("com.mysql.jdbc.Driver")
    .setURL("jdbc:mysql://localhost/somedatabase")
    .setCredentials("username", "password")
    .setClasses(models)
    .build();
```

If all goes well, you'll get the the same sort of `EbeanServer` back, but it will be connected to a MySQL data source instead.


Maven
-----

Before you can incorporate BukkitEbean into your plugin, you must first build and install it into your local Maven repository:

```shell
git clone https://github.com/NerdNu/BukkitEbean.git
cd BukkitEbean
mvn install
```

Next, make sure you include `BukkitEbean` as a dependency and then shade it in, being sure to relocate it to prevent future conflicts with other plugins. 
Your class files' import statements should still reference `nu.nerd.BukkitEbean`, as Maven will take care of rewriting the relocated paths.

```xml
<build>
    <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-shade-plugin</artifactId>
                <version>2.4.3</version>
                <executions>
                    <execution>
                        <phase>package</phase>
                        <goals>
                            <goal>shade</goal>
                        </goals>
                        <configuration>
                            <relocations>
                                <relocation>
                                    <pattern>nu.nerd.BukkitEbean</pattern>
                                    <shadedPattern>nu.nerd.NerdClanChat.BukkitEbean</shadedPattern>
                                </relocation>
                            </relocations>
                        </configuration>
                    </execution>
                </executions>
            </plugin>
     </plugins>
</build>
```