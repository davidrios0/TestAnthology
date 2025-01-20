## Document Comparison Project

This project focuses on the processing and comparison of two documents, evaluating their potential similarity. It is developed using the Spring Boot framework with JAVA 17 and MAVEN, and consists of four parts:
- **Eureka Server**: It is a Spring Boot project, and its role is to register all the microservices, enabling them to connect with each other and with the API Gateway. It also provides the ability to query the status of each service, which becomes useful as the project begins to scale, offering more information for resource management.
- **API Gateway**: It is a Spring Boot project that connects to Eureka Server as a client and is responsible for redirecting paths to the microservices. It is configured on port 8080 and has the path */documents/comparison/*** mapped to the microservice COMPARISON-MICROSERVICE and the path */documents/*** to the microservice MANAGEMENT-MICROSERVICE. 
The URI is configured through the load balancer, in case there are multiple instances of each microservice to keep the project scalable. The port can be changed in the file *application.properties*.
- **Document Management Service**: It is a Spring Boot project, configured as a Eureka client. No port is defined to allow the creation of multiple instances. It reads local *.txt* and *.pdf* files found in the *test-documents* folder.
- **Document Comparison Service**: It is a Spring Boot project, configured as a Eureka client. No port is defined to allow the creation of multiple instances. The processing flow can be summarized in the following steps:
1. It makes a GET request to the Document Management microservice and retrieves the text of two documents for comparison. One text is taken, and an object is created where the *keys* correspond to the words found in it, and the *value* is an array with the indices of where each word is located. This method can be called *indexing*.
2. Now, the other text is taken, and an array is created with just the words (removing punctuation marks). This array is then iterated, and for each word, it is checked whether the word is one of the keys in the object obtained in step 1. If so, the word is stored as a matching word.
3. In the same iteration, we check if the *next word* after the *matching word* in each of the texts is the same. If so, we continue repeating the process with the following words, and they are stored to obtain the *matching sentences*. It is important to note that when a subsequent word is stored, this information is saved to prevent it from being processed again, optimizing the processing. The similarity percentage is obtained by dividing the total number of common words by the total number of words in both texts. <br>
It is important to mention that in each iteration, comparisons could be made with multiple texts, meaning that with a single pass, similarities can be found between one text and others. The only requirement would be to perform the *indexing process* on the other texts, which could already be stored in a database, for example. <br>
Additionally, we can say this process allows us to find matching sentences literally, but a component can be added with a language model to validate that the matching sentences make semantic sense and are not just isolated words. It may not detect paraphrasing, but it does provide a first approximation. That is, if two texts have a high similarity percentage using this method, it doesn’t make sense to spend resources on more complex processing, such as a language model: it would serve as a quick first filter. <br>

## Prerequisites
Before running the project, make sure you have the following tools installed.:
- Java 17
- Maven
It can be verified using the following commands:
```bash
java -version
mvn -v
```

## Running the Project
1. **Installing dependencies**:
Go to each of the project folders and run the Maven command:

1.1 **Eureka Server**
```bash
cd eureka-server
mvn clean install
```

1.2 **Api Gateway**
```bash
cd api-gateway-service
mvn clean install
```

1.3 **Document management microservice**
```bash
cd management-microservice
mvn clean install
```

1.4 **Document comparison microservice**
```bash
cd comparison-microservice
mvn clean install
```

2. **Execution**:
Go to each of the project folders (in the same order), and run the Maven command:

2.1 **Eureka Server**
```bash
cd eureka-server
mvn spring-boot:run
```

2.2 **Api Gateway**
```bash
cd api-gateway-service
mvn spring-boot:run
```

2.3 **Document management microservice**
```bash
cd management-microservice
mvn spring-boot:run
```

2.4 **Document comparison microservice**
```bash
cd comparison-microservice
mvn spring-boot:run
```

Now the services are active, you can verify in a browser at *http://localhost:8761* that the Eureka server instance is running and that the other services are connected to it.
The API Gateway is then located at *http://localhost:8080*, and it has the following paths:
- *http://localhost:8080/documents/compare*: It corresponds to a POST request, and it receives as parameters the names of two documents stored in the test-documents folder. An example of the request body would be:
```bash
{
    "documentId1": "testDocument.pdf",
    "documentId2": "testDocument2.pdf"
}
```
-*http://localhost:8080/documents/{name_document}*: It corresponds to a GET request and returns the text of a document. The document name is passed as a parameter in the URL, for example *http://localhost:8080/documents/testDocument.pdf*.

In the project, there is a Postman collection containing examples for each of the requests.