# B20 RestAssured Project

[REST Assured](https://rest-assured.io/) brings the simplicity for Testing and validating REST services in Java. 

Here is the [full User Guide](https://github.com/rest-assured/rest-assured/wiki/Usage). 

Here is the maven dependency 
```xml
<dependency>
      <groupId>io.rest-assured</groupId>
      <artifactId>rest-assured</artifactId>
      <version>4.3.2</version>
      <scope>test</scope>
</dependency>
```

We will be using Junit5 as Testing support. 
Here is the dependency for Junit5

```xml
<dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter</artifactId>
    <version>5.7.0</version>
    <scope>test</scope>
</dependency>
```

### Static imports
In order to use REST assured effectively it's recommended to statically import methods from the following classes:
```java
import static io.restassured.RestAssured.* ;
import static io.restassured.matcher.RestAssuredMatchers.* ;
import static org.hamcrest.Matchers.* ;
```

### Hamcrest Assertion Library
RestAssured Use [Hamcrest Matchers](http://hamcrest.org/JavaHamcrest/javadoc/2.2/org/hamcrest/Matchers.html) for assertions while chaining. 
Here is the doc for [Hamcrest](http://hamcrest.org/JavaHamcrest/tutorial). 
