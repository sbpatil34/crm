#### Customer REST API
This is a REST application which supports performing CRUD operations on customer data. 

Please refer to Customer-RFA.pdf for more details regarding requirements, REST endpoint details and required payloads to run these endpoints. 

Following are a few APIs supported by this module.   

1. Get all customers  GET  http://localhost:8080/crm/v1/customers
   
2. Add a customer POST http://localhost:8080/crm/v1/customers
   
    Customer payload =
    ```json
       {
      "suffix":"Ms.",
      "prefix":"sr",   
      "firstName":"new",
      "middleName":"m",
      "lastName":"newlast",
      "email":"new@gg.com",
      "phoneNumber":"1394567890"
    }
    ```
3. Get customer by email  http://localhost:8080/crm/v1/customers/{newg.com}
   
4. Update a customer PUT http://localhost:8080/crm/v1/customers/{1}  

    Update customer payload =
    ```json
    {
        "suffix":"Ms.",
        "prefix":"sr",   
        "firstName":"updating first ",
        "middleName":"m",
        "lastName":"updating last",
        "email":"new@gg.com",
        "phoneNumber":"1394567890"
    }
    ```
5. Delete a customer DELETE http://localhost:8080/crm/v1/customers/{1}

#### Install instructions

#### Using Docker compose

Note : This is a maven based build so maven is needed along with docker and docker compose. 

1. Clone this repo
   
3. Run start.sh from application root
   - This script will build the code and create target jar
   - Create application docker image
   - Start docker compose linking application container to mysql.
     
4. Visit the REST endpoint using the above URLS.
   
   http://localhost:8080/crm/v1/customers

#### Using helm charts

1. Clone this repo
   
3. Navigate to deployment folder
   
4. Perform helm install
   helm install crm-dev crm/ --values crm/values.yaml
   
5. Open a tunnel between host and cluster.
   kubectl  expose deployment crm-app-service  --type=NodePort --port=8080
   Note : I have tested this on minikube.
   
6. Visit the REST endpoint using the above URLS.
   
   http://<mapped-ip>:8080/crm/v1/customers
   

