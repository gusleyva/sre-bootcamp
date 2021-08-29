# Implementation Checklist
- [ ] API Code
- [ ] Services Code
- [ ] Unit-tests
- [ ] Dockerfile
- [ ] It Compiles
- [ ] It runs

# Api Services
- Receives a valid username and password and returns a JWT.
- Returns protected data with a valid token, otherwise returns unauthenticated.

# SRE bootcamp challenge

- Implementation: Java

## Executing

### Maven
```
sh java/auth_api/build.sh
```

### Docker
```
* cd java/auth_api/
* docker build -t auth-api .
* docker run -it --rm --name=auth-api -p 8000:8000 auth-api
```

## Testing

### /Login
```
curl -d "username=admin&password=secret" http://localhost:8000/login
{
  "data": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c"
}
```
###/protected
```
curl -H 'Accept: application/json' -H "Authorization: Bearer ${TOKEN}" localhost:8000/protected
{
  "data": "You are under protected data"
}
```