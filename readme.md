# Credit Decision Engine
This project can be used to decide if a person is eligible to get loan.

### Installing

To install project download project as a zip and extract to under your project folder. 

After extracting folder run following command

```
./gradlew clean build
```

You also need to install redis server to be able to run the app. The redis server should run on 6379 port.

### Running

gradle bootrun is available for this project
```
gradlew bootRun
```

#### Runtime parameters
* Giving credit rules can be managed from application.yaml

#### Result
When you run the application go to following link to see result
```
http://localhost:8090/index
```
