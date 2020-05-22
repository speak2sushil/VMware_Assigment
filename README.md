## Run the Application 
- Download the zip or clone the Git repository.
- Unzip the zip file (if you downloaded one)
- Open Command Prompt and Change directory (cd) to folder containing pom.xml
- Open Eclipse 
   - File -> Import -> Existing Maven Project -> Navigate to the folder where you unzipped the zip
   - Select the right project
- Choose the Spring Boot Application file (search for @SpringBootApplication)
- Right Click on the file and Run as Java Application

POST http://localhost:8080/api/generate

 {

               "Goal":50,

               "Step":2

            }

## Resources and URI Mappings

Create a Task - POST /api/generate -> 

Retrieve Task Status - GET /api/tasks/{taskId}/status-> /api/tasks/93a03593-4a6e-44ec-8c2a-c55db1feb0ca/status


Retrieve Task data - GET /api/tasks/{UUID of the task}?action=get_numlist ->/api/tasks/93a03593-4a6e-44ec-8c2a-c55db1feb0ca?action=get_numlist