<h1 align="center">Amazon Kindle Store</h1>

![Screenshot 1](screenshots/screenshot1.png)

## Overview
This is a clone of the Amazon Kindle Store created as the project for my Spring Advanced course at Software University. The store supports a hierarchy of book categories, multiple currencies, pagination, filtering, sorting, users, roles, authors, publishers, wishlists, shopping carts and orders. Customers can add, delete, and update ratings and reviews and also search for books by title, author(s) name(s) and publisher name. Project Statistics: 193000 eBooks, 5909 categories, 13200 authors, 152 publishers, and 8 currencies.

### Screenshots
![ER Diagram](screenshots/er_diagram.png)

**The statistics below do not include tests**

![Screenshot 10](screenshots/screenshot10.png)
<p align="center">
  <img src="screenshots/screenshot2.png" alt="Screenshot 2" width="400"/> <img src="screenshots/screenshot3.png" alt="Screenshot 3" width="400"/>
  <img src="screenshots/screenshot4.png" alt="Screenshot 4" width="400"/> <img src="screenshots/screenshot5.png" alt="Screenshot 5" width="400"/>
  <img src="screenshots/screenshot6.png" alt="Screenshot 6" width="400"/> <img src="screenshots/screenshot7.png" alt="Screenshot 7" width="400"/>
  <img src="screenshots/screenshot8.png" alt="Screenshot 8" width="400"/> <img src="screenshots/screenshot9.png" alt="Screenshot 9" width="400"/>
</p>

## Video Presentation

## Installation
1. Clone or download this repository
2. Configure the following environment variables related to the database that you would like to use:
   
```properties
# Database Properties
spring.datasource.driverClassName=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost:3306/ebook_store?useSSL=false&createDatabaseIfNotExist=true&allowPublicKeyRetrieval=true
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}

# Database Backup Properties
backup.database.name=ebook_store
backup.database.user=${DB_USERNAME}
backup.database.password=${DB_PASSWORD}
backup.file.path=src/database_backups
backup.dump.path=/usr/local/mysql/bin/mysqldump
```

3. Run the application

> [!NOTE]
> The original book data used in this project is sourced from the [Kyushu University / book-dataset](https://github.com/uchidalab/book-dataset/tree/master/Task2) public repository. The CSV file included in my repository has been modified for use in this specific application.

## Usage
