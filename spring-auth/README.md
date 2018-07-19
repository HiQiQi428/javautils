# spring-auth
##### install
```
<dependency>
    <groupId>org.luncert</groupId>
    <artifactId>spring-auth</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```
##### configure
```
@Import(AuthConfigurer.class)
@Configuration
public class Config implements WebMvcConfigurer {}
```
##### grant user with identity
```
@Authwired
privte AuthManager authManager;

public boolean validate(String name, String password) {
    User user = userMapper.queryByName(name);
    if (user != null && CipherHelper.hashcode(password).equals(user.getPassword())) {
        authManager.grant(Identity.NormalUser, user);
        return true;
    }
    else false;
}
```
* You can grant user with survival time like this:
```
authManager.grant(Identity.NormalUser, user, 600000); // ttl = 10min
```
##### configure the API that needs access control in Controller
```
@AuthRequired(legalObjects = {Identity.NormalUser, Identity.Administrator})
@GetMapping("openDir")
public String openDir(HttpServletRequest request, String path) {
    User user = (User)request.getAttribute("user");
}
```
* If user is authorized and user's identity is legal, the User entity object will be add to this request automatically.
* The default value of annotation ```@AuthRequired``` is ```Identity.NormalUser```. And you can extend ```org.luncert.springauth.Identity``` to add customary identities.