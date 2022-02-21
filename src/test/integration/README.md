# Conventions for writing integration tests

## API tests

- Test cases are in **&lt;basic_class&gt;/endpoint_tests** directories and they inherit from **
  &lt;api_name&gt;TestTemplate**.
- TestTemplates have **init&lt;name&gt;** methods for initialization resources  
  e.g adding list of users to DB
- TestTemplates resources (e.g. list of users) and protected/public methods are named in following naming convention
  int **_&lt;name&gt;** e.g. _userList, _getMockDrivePath

