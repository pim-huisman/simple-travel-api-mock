Travel API mock
===============

Clone this repo on your local machine. It is the mock backend that your assignment will get its data from. For making
the assignment fork our [empty assignment](https://github.com/Pim-Huisman/original-case), please see that page for more
instructions.

Start the mock (on Windows systems use the gradlew.bat file):

`./gradlew bootRun`

to list all tasks:

`./gradlew tasks`

The mock resources are protected and require authentication through Basic Authentication to gain access. A dummy user is
configured, it's credentials are listed below that can be used to access the mock.

- user: user
- password: secret123

Resource endpoints:
-------------------

**Retrieve a list of airports**:

`http://localhost:8080/airports`

Query params:

- size: the size of the result
- page: the page to be selected in the paged response
- lang: the language, supported ones are nl and en
- term: A search term that searches through code, name and description.

**Retrieve a specific airport**:

`http://localhost:8080/airports/{code}`

Query params:

- lang: the language, supported ones are **nl** and **en**

**Retrieve a fare offer**:

`http://localhost:8080/fares/{origin_code}/{destination_code}`

Query params:

- currency: the requested resulting currency, supported ones are EUR and USD
 

