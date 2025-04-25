## **HDB BTO Management System (CLI-Based)**
### **Overview**
This Java-based CLI application simulates the management of Build-To-Order (BTO) flat applications under HDB (Housing Development Board) regulations. It supports different user roles such as **Applicants, HDB Officers, and HDB Managers**, each with unique capabilities and access control.

The system is structured using Object-Oriented Programming principles and emphasises modularity, role-based logic, and file-based data handling.

### **Features**
#### **Applicant**
- Login with NRIC (Singpass format)
- View eligible BTO projects based on marital status and age
- Apply for one project only
- Track application status
- Book flat (if selected)
- Send enquiries to HDB

#### **HDB Officer**
- View applicants for assigned project
- View and answer enquiries for assigned project
- Manage shortlisting of applicants

#### **HDB Manager**
- Create and manage new BTO projects
- Assign officers to projects
- Perform higher-level administrative tasks  

### **Technologies Used**
- Java (JDK 17 or higher)
- CLI interface (no GUI)
- File-based storage (no DB, JSON, or XML)

### **Design Highlights**
- Strict role-based access control for all users
- Separation of concerns using packages (entity, control, interfaces)
- Each user class only has access to permitted operations
- Clear error handling and constraint enforcement

### **What We Learned**
- Importance of modular and scalable design.
- Applying core Java concepts like inheritance, polymorphism, and interfaces.
- Real-world problem modeling using programming.
- Collaboration and use of Git for version control.
