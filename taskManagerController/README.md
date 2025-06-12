## Quick Start

Follow these steps to get the application running locally.

### Prerequisites

* **Java Development Kit (JDK):** Version 24.0.1
* **Apache Maven:** (if using Maven, or ensure Maven Wrapper is available).
* **MySQL Server:** Version 9.3.0

### Setup

1.  **Clone the Repository:**
   
2.  **Configure MySQL Database:**
    Log in to your local MySQL server as `root` (e.g., `mysql -u root -p`) and run these SQL commands:

    ```sql
    CREATE DATABASE task_manager_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
    CREATE USER 'task_user'@'localhost' IDENTIFIED BY 'YOUR_LOCAL_DB_PASSWORD';
    GRANT ALL PRIVILEGES ON task_manager_db.* TO 'task_user'@'localhost';
    FLUSH PRIVILEGES;
    exit;
    ```
    
3.  **Set Up Local Credentials (`.env`):**
    * Create your `.env` file from the example: `cp .env.example .env`
    * Open the new `.env` file and fill in the password you chose for `task_user`:
    * **Important:** The `.env` file is in your `.gitignore` and **must not be committed** to Git.

### Running the Application

Navigate to your project's root directory in the terminal:

```bash
./mvnw spring-boot:run