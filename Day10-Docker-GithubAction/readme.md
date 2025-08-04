

docker version
docker pull ubuntu
docker images
docker container ls -a
docker run -it -d ubuntu
Exit
docker container ls -a
docker container stop
docker container prune
	
exit
docker login 
docker rmi -f 356 

1. Create own Dockerfile and name it as Dockerfile
- FROM ubuntu
RUN echo Hi! This is ubuntu file

2. Build an image from this docker file 
docker build -t "customubuntu" .

3. Docker container stop 43
-------MySQl Container--------

4. Docker pull mysql or docker pull mysql:8
5. docker run --name my-mysql \
  -e MYSQL_ROOT_PASSWORD=rootpassword \
  -e MYSQL_DATABASE=testdb \
  -e MYSQL_USER=testuser \
  -e MYSQL_PASSWORD=testpass \
  -p 3306:3306 \
  -d mysql:8
6. docker exec -it 96 mysql -u root -p
7.use testdb
8. CREATE TABLE users (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  email VARCHAR(100) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  age INT,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
9.INSERT INTO users (name, email, password, age) 
VALUES ('Ashu', 'ashu@example.com', 'hashed_password_here', 30);

10.docker rm  <container> 

Deploy Spring project on docker container
---------------------------------------
1. Refer Project /Users/ashujauhari/Data1/ClientBasedContent/DB-2025/Day10/SpringBoot-H2-Product-Base-Project-Intellij
2. Update pom.xml for jar 
3. View -> ToolWindow -> Maven -> Double Click on Clean and then package
4. Verify jar file
5. Write Dockerfile
FROM eclipse-temurin:21-jdk-alpine
EXPOSE 9091
COPY ./target/crud.jar crud.jar
ENTRYPOINT ["java","-jar","/crud.jar"]

6. Build an image. = docker build -t h2-crud-proj.jar .
7. Run it on container=  docker run -p 9091:9091 crud-app

--------------Multi-Container----------------/Users/ashujauhari/Data1/ClientBasedContent/DB-2025/Day10/SpringBoot-H2-Product-Base-Project-Intellij
1.  Update pom.xml for jar 
2. View -> ToolWindow -> Maven -> Double Click on Clean and then package
3. >  docker build -t  product-mysql-multi .
4. docker network create product-mysql-net
5. docker run -d \         
  --name mysql-container \
  --network product-mysql-net \
  -e MYSQL_ROOT_PASSWORD=root \
  -e MYSQL_DATABASE=microdb \
  -e MYSQL_USER=testuser \
  -e MYSQL_PASSWORD=testpass \
  -p 3303:3303 \
  mysql:latest
6.  	









Push image to your docker hub account
-----------------------------------
D:\SourceCode-1-Jul-2020\TestAWSPracticeCheck\aws-employee>docker image build -t ij005-emp .
D:\SourceCode-1-Jul-2020\TestAWSPracticeCheck\aws-employee>docker image tag ij005-emp ashujauhari/ij005-emp
D:\SourceCode-1-Jul-2020\TestAWSPracticeCheck\aws-employee>docker image push ashujauhari/ij005-emp
D:\SourceCode-1-Jul-2020\TestAWSPracticeCheck\aws-employee>docker pull ashujauhari/ij005-emp
D:\SourceCode-1-Jul-2020\TestAWSPracticeCheck\aws-employee>docker container run -d -p 7000:7000 --network emp-dept-net   --name emp-service ashujauhari/ij005-emp
Test : http://localhost:7000/api/emp/employees


D:\SourceCode-1-Jul-2020\TestAWSPracticeCheck\aws-dept-feign>docker image build -t ij005-dept .
D:\SourceCode-1-Jul-2020\TestAWSPracticeCheck\aws-dept-feign>docker image tag ij005-dept ashujauhari/ij005-dept
D:\SourceCode-1-Jul-2020\TestAWSPracticeCheck\aws-dept-feign>docker image push ashujauhari/ij005-dept
D:\SourceCode-1-Jul-2020\TestAWSPracticeCheck\aws-dept-feign>docker pull ashujauhari/ij005-dept
D:\SourceCode-1-Jul-2020\TestAWSPracticeCheck\aws-dept-feign>docker run -d -p 7100:7100 --network emp-dept-net --env EMPLOYEE_SERVICE=http://emp-service:7000 --name dept-service ashujauhari/ij005-dept
http://localhost:7100/api/dept/allemp



