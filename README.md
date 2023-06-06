# Code Generation: Banking API (Cucumber & JUnit Testing)

This project is a Spring Boot application that provides a banking API, allowing users to perform various banking
operations through a set of endpoints.

This README file describes how to run the unit tests with valid JWT tokens. NOTE: This description assumes the use of IntelliJ IDEA.

## Testing

1. Navigate to [Swagger2SpringBoot](src/main/java/io/swagger/Swagger2SpringBoot.java) (
   src/main/java/io/swagger/Swagger2SpringBoot.java)
2. Click the triangular run button to the left of "public class Swagger2SpringBoot implements CommandLineRunner". NOTE:
   Make sure port 8080 is available!
3. Open your browser and navigate to the [SwaggerUI](http://localhost:8080/swagger-ui/#/). You should now see the
   Swagger UI, including all the endpoints.
4. Open the "Authentication" tab and click "Try it out" in the top right corner.
5. In the request body, fill in the following credentials for role admin:

<pre>
{
   "Password": "secret123",
   "Username": "BrunoMarques123"
}
</pre>
6. The response will provide a JWT Token. Copy this token.
7. Now go back to IntelliJ, navigate to the [Tokenholder](src/test/java/io/swagger/TokenHolder.java) class (src/test/java/io/swagger/TokenHolder.java), and paste the JWT token you just copied into the "VALID_TOKEN_ADMIN" field.

You can do exactly the same for the other fields. Don't place a valid JWT token into the "INVALID_TOKEN" field.

VALID_TOKEN_USER credentials:
<pre>
{
   "Password": "secret123",
   "Username": "SanderHarks123"
}
</pre>

VALID_TOKEN_THIEF credentials:
<pre>
{
   "Password": "secret123",
   "Username": "dummyCustomer1"
}
</pre>

8. When all valid tokens are inserted, do the following:
   - Right-click on the [swagger](src/test/java/io/swagger) directory (src/test/java/io/swagger)
   - Select "More Run/Debug" and "Run Tests in io.swagger with Coverage"
9. A window should appear on the right of your IDE where you can inspect the tests' code coverage of each class.