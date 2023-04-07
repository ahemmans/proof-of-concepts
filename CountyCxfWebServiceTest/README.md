# CountyCxfWebServiceTest

## Command Line Params
-DskipTests.unit=

-DskipTests.it=

-DskipTests.jmeter=

## Running Integration Tests
mvn verify

## Running Performance Tests
mvn verify -DskipTests.jmeter=false

## Clear Data/Reset Tests
mvn integration-test -DskipTests.unit=true -Dcucumber.filter.tags="@Clear"

mvn spring-boot:run -Dspring-boot.run.profiles=default,laptop
mvn verify -Dspring.profiles.active=default,laptop
java -jar --spring.profiles.active=default,laptop
