# Running The Application
### If you do not have gradle installed. gradle wrapper is provided as `gradlew` or `gradle.bat`
### Use Gradle 1.9 ver.

To test the application run the following commands. 
`gradle migrate run`

* To run the tests and codenarc run

`gradle check`

* To package the application

`gradle shadowJar`

* To drop an existing h2 database run.

`gradle dropAll`

* To setup the h2 database run.

`gradle migrate`

* To run the server run.

`gradle run`



