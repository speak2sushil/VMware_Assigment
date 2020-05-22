## Run the Application 
- Download the zip or clone the Git repository.
- Unzip the zip file (if you downloaded one)
- Open Command Prompt and Change directory (cd) to folder containing pom.xml
- Open Eclipse 
   - File -> Import -> Existing Maven Project -> Navigate to the folder where you unzipped the zip
   - Select the right project
- Choose the Spring Boot Application file (search for @SpringBootApplication)
- Right Click on the file and Run as Java Application

## Examples 

POST http://localhost:8080/api/generate

Request
 {

               "Goal":80,

               "Step":2

            }
            
Response - task:3a48fc3e-19cd-4c1b-826f-f603e6c03fe7    

Request GET localhost:8080/api/tasks/3a48fc3e-19cd-4c1b-826f-f603e6c03fe7/status  

Response

   

{

             "result": "SUCCESS"

            }

Request GET localhost:8080/api/tasks/3a48fc3e-19cd-4c1b-826f-f603e6c03fe7?action=get_numlist

Response 



{

              "result": "80,78,76,74,72,70,68,66,64,62,60,58,56,54,52,50,48,46,44,42,40,38,36,34,32,30,28,26,24,22,20,18,16,14,12,10,8,6,4,2,0"

            }

## Resources and URI Mappings

Create a Task - POST /api/generate -> 

Retrieve Task Status - GET /api/tasks/{taskId}/status-> /api/tasks/93a03593-4a6e-44ec-8c2a-c55db1feb0ca/status


Retrieve Task data - GET /api/tasks/{UUID of the task}?action=get_numlist ->/api/tasks/93a03593-4a6e-44ec-8c2a-c55db1feb0ca?action=get_numlist