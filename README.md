*Needed Software

- Maven 3
- Jdk 8
- MySql

* Running the Application

1. Install Oracle XE 18c.
2. Download the shoppingbasket code and extract it to your local directory. Add the Oracle XE database connection configuration to the application.properties. 

3. cd to the shoppingbasket project directory (to the location of pom.xml)
4. Type the following in the command line and hit enter key: 
  
   mvn spring-boot:run

5. For first time run this could take sometime as it will download some dependences. You will see something like below in the standard output, indicating that the app has already started:

2020-08-14 09:17:34.232  INFO 18384 --- [  restartedMain] s.b.c.e.t.TomcatEmbeddedServletContainer : Tomcat started on port(s): 8080 (http)
2020-08-14 09:17:34.241  INFO 18384 --- [  restartedMain] c.s.ShoppingBasketApplication            : Started ShoppingBasketApplication in 3.922 seconds (JVM running for 4.225)


6. Open a browser and put http://localhost:8080 and hit enter.
7. Start playing with the application.
