# Requirements
The problem is to store and retrieve UTF-8 strings via REST two endpoint api's that accept and produce JSON .

* Use a generated String ID for the string values registered in the registry.
  * the generated string id must be calculated recursively.
  * I chose not to make the string_id field the index field for the registry entry.  This has a number of implications:
   * The registry entry has three fields: id, string_id, and string
   * The JSON deserializer should ignore the string_id field from the rest api (*TODO*) 
   
* Store the data in a flat plain text file
  * The files should be 'plain text' for debugging:
   * MySQL CSV engine  For MySQL 5.7.5. and later
   * See: http://dev.mysql.com/doc/refman/8.0/en/csv-storage-engine.html 

   * http://dev.mysql.com/doc/refman/5.7/en/server-system-variables.html#sysvar_default_storage_engine
   
   If deploying with docker use the mysql.yml file to configure the mysqld process with the CSV storage engine

  * There are a number of ways to do this:
  * Write a custom persistence layer (More than a week of work)
  * https://en.wikipedia.org/wiki/Flat_file_database
   * Cache data in memory, reading and writing to the text file periodically. (May lose data)
   * Find an SQL DB that can read and write text files natively.  I have found one Java DB that fits the bill -- HSQLDB via its TEXT TABLE feature.  
            	(H2 database can read from a CSV, but can note randomly write into a CSV.)
   * Find a JDBC driver for text files. I have found that the Relational Junction XML and CSV JDBC Drivers claim to allow SQL operations. [http://www.csv-jdbc.com/|Sesame Software Relational Junction]
   
   * Use mongodb in a json mode so that the db can be read as a text file. (Not sure it is possible.)

		select @@datadir;  -- find where the data is stored.

   * How to set this up with JPA?
            	
			
# jhipster_restcoding

This application was generated using JHipster 3.9.1, you can find documentation and help at [https://jhipster.github.io/documentation-archive/v3.9.1](https://jhipster.github.io/documentation-archive/v3.9.1).

## Development

Before you can build this project, you must install and configure the following dependencies on your machine:
1. [Node.js][]: We use Node to run a development web server and build the project.
   Depending on your system, you can install Node either from source or as a pre-packaged bundle.

After installing Node, you should be able to run the following command to install development tools (like
[Bower][] and [BrowserSync][]). You will only need to run this command when dependencies change in package.json.

    npm install

We use [Gulp][] as our build system. Install the Gulp command-line tool globally with:

    npm install -g gulp-cli

Run the following commands in two separate terminals to create a blissful development experience where your browser
auto-refreshes when files change on your hard drive.

    ./mvnw
    gulp

Bower is used to manage CSS and JavaScript dependencies used in this application. You can upgrade dependencies by
specifying a newer version in `bower.json`. You can also run `bower update` and `bower install` to manage dependencies.
Add the `-h` flag on any command to see how you can use it. For example, `bower update -h`.

For further instructions on how to develop with JHipster, have a look at [Using JHipster in development][].

## Building for production

To optimize the jhipster_restcoding application for production, run:

    ./mvnw -Pprod clean package

This will concatenate and minify the client CSS and JavaScript files. It will also modify `index.html` so it references these new files.
To ensure everything worked, run:

    java -jar target/*.war

Then navigate to [http://localhost:8080](http://localhost:8080) in your browser.

Refer to [Using JHipster in production][] for more details.

## Testing

To launch your application's tests, run:

    ./mvnw clean test

### Client tests

Unit tests are run by [Karma][] and written with [Jasmine][]. They're located in `src/test/javascript/` and can be run with:

    gulp test


### Other tests

Performance tests are run by [Gatling][] and written in Scala. They're located in `src/test/gatling` and can be run with:

    ./mvnw gatling:execute

For more information, refer to the [Running tests page][].

## Using Docker to simplify development (optional)

You can use Docker to improve your JHipster development experience. A number of docker-compose configuration are available in the `src/main/docker` folder to launch required third party services.
For example, to start a mysql database in a docker container, run:

    docker-compose -f src/main/docker/mysql.yml up -d

To stop it and remove the container, run:

    docker-compose -f src/main/docker/mysql.yml down

You can also fully dockerize your application and all the services that it depends on.
To achieve this, first build a docker image of your app by running:

    ./mvnw package -Pprod docker:build

Then run:

    docker-compose -f src/main/docker/app.yml up -d

For more information refer to [Using Docker and Docker-Compose][], this page also contains information on the docker-compose sub-generator (`yo jhipster:docker-compose`), which is able to generate docker configurations for one or several JHipster applications.

## Continuous Integration (optional)

To set up a CI environment, consult the [Setting up Continuous Integration][] page.

[JHipster Homepage and latest documentation]: https://jhipster.github.io
[JHipster 3.9.1 archive]: https://jhipster.github.io/documentation-archive/v3.9.1

[Using JHipster in development]: https://jhipster.github.io/documentation-archive/v3.9.1/development/
[Using Docker and Docker-Compose]: https://jhipster.github.io/documentation-archive/v3.9.1/docker-compose
[Using JHipster in production]: https://jhipster.github.io/documentation-archive/v3.9.1/production/
[Running tests page]: https://jhipster.github.io/documentation-archive/v3.9.1/running-tests/
[Setting up Continuous Integration]: https://jhipster.github.io/documentation-archive/v3.9.1/setting-up-ci/

[Gatling]: http://gatling.io/
[Node.js]: https://nodejs.org/
[Bower]: http://bower.io/
[Gulp]: http://gulpjs.com/
[BrowserSync]: http://www.browsersync.io/
[Karma]: http://karma-runner.github.io/
[Jasmine]: http://jasmine.github.io/2.0/introduction.html
[Protractor]: https://angular.github.io/protractor/
