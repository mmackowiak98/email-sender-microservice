# About
    Microservice with config server to propagate emails

# EmailSender
  - Uses H2 Database to store emails,
  - Allows to perform CRUD operations on emails,
  - Expose endpoint to send email with <b>Subject</b> & <b>Content</b> through SMTP Gmail Host,
  - Downloads configuration from <b>Config Server</b> with use of Spring Cloud,
  - Unit & Integration tests with JUnit and Mockito

## Installation
 - Clone repository ```https://github.com/mmackowiak98/email-sender-microservice.git```
 - Load both Maven modules from <b>EmailSender</b> and <b>EmailConfigServer</b>
 #### If you don't want to use Docker:
   - Start ```ConfigServer Application``` then ```EmailSender Application``` - Properties for SMTP host are being downloaded from Config Server so remember to run Config Server before running actual EmailSender API
   - When it finish booting up you're ready to go and hit exposed endpoints
 #### If you want to use Docker container: 
   - Run ```mvn clean install``` on both applications to generate <b>.jar</b> file,
   - Generate image with use of Dockerfile - Go to both application directories and run command ```docker build . -t api/emailsender``` for EmailSender and ```docker build . -t api/configserver``` for ConfigServer
   - After images are generated you're ready to use ```docker-compose up``` in EmailSender directory 
   - Containers should start after few seconds and they are ready to use (It might restart once because of ConfigServer not being fully started)
   
 ## Exposed Endpoints
  - `/api/v1/email/save` - POST method that accepts Email body like `{"emailAddress":"example@gmail.com"}`,
  - `/api/v1/email/get` - GET method that accepts parameter email `?email=example@gmail.com`,
  - `/api/v1/email/getById` - GET method that accepts parameter id `?id=1`,
  - `/api/v1/email/update` - PUT method that accepts parameter id and body of email `?id=1` `{"emailAddress":"updated@gmail.com"}`,
  - `/api/v1/email/delete` - DELETE method that accepts parameter id `?id=1`,
  - `/api/v1/email/send` - POST method that accepts EmailContent body like `{"subject"="Test Subject","content"="Test Content"}`
  
  ## Postman Collection to test endpoints
      https://www.postman.com/planetary-firefly-390924/workspace/emailsender/overview
      
  ## Logs
   - Application creates 2 logging files `info.log` that stores general logs and `requests.log` that stores logs from incoming requests, you can find them in `logs` directory 
        

   
