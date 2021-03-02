## **spread-calc-service**

**Built using :** 
Java 11+
Maven 3.6.3

**Dependencies :** 
>Jackson for object mapping.
>Log4j for logging.
>junit for unit testing.
>mockito for mocking.

NOTE : Considering no use of spring boot or spring mentioned specified in the document. 
I tried to use mostly java libs rather than using other frameworks.

**Running the application :** 

From IDE : 
```sh
Run FxSpreadApplication main method.
```

From CLI : 
```sh
mvn clean package
java -jar ./target/spread-calc-service-1.0.jar`
```

**Basic Properties:** 
```sh
host : http://localhost
port : 8000 (Can be changed in app.properties file in resources)
``` 

**Configurable Properties :**

```sh
application.server.port=8000
mid.price.base.url=https://api.exchangeratesapi.io/latest
application.base.currency=SGD
application.filter.currency.list=HKD,USD,MYR
tier.user.spread.A =0.015
tier.user.spread.B =0.035
tier.user.spread.C =0.040
```

**API:** 

_valid users:_
|User      | Tier          |
|userId:123| pricingTier :A|
|userId:234| pricingTier :B|
|userId:456| pricingTier :C|
|userId:567| pricingTier :A|
|userId:789| pricingTier :B|

**GET - /user/profile/userId**
To get user details :

```sh
Example Request : curl -v http://localhost:8000/user/profile/userId?userId=123
Example Output  : {"userId":"123","pricingTier":"A","emailAddress":"xyz@gmail.com"}
```

**GET - /rates/latest**
To get spread for a user by id
 
```sh
Example Request : curl -v localhost:8000/rates/latest?userId=123
Example Output  : 
{"base":"SGD","rates":{"HKD":{"bid":5.749400844420,"market":5.836955172,"ask":5.924509499580},"USD":{"bid":0.7412880293075,"market":0.7525766795,"ask":0.7638653296925},"MYR":{"bid":3.0025804048075,"market":3.0483049795,"ask":3.0940295541925}}}
```

**POST -rates/latest**
To get spread for a user by sending user 
 
```sh
Example Request : curl -v -X POST localhost:8000/rates/latest -d '{"userId":"123","pricingTier":"A","emailAddress":"xyz@gmail.com"}'
Example Output  : 
{"base":"SGD","rates":{"HKD":{"bid":5.749400844420,"market":5.836955172,"ask":5.924509499580},"USD":{"bid":0.7412880293075,"market":0.7525766795,"ask":0.7638653296925},"MYR":{"bid":3.0025804048075,"market":3.0483049795,"ask":3.0940295541925}}}
```

Unit test Coverage Report : coverage_report.JPG 

_**Assumptions made :**_

Around calculations for bid and ask price on the basis of mid price (market price)
```sh
bid_price =  midprice - (mid*spread)
ask_price =  midprice + (mid*spread)
```