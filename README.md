<h1 align="center">Amazon Kindle Store</h1>

![Screenshot 1](screenshots/screenshot1.png)

## Overview

This is a clone of the Amazon Kindle Store created as the project for my Spring Advanced course at Software University.
The store supports a hierarchy of eBook categories, multiple currencies, pagination, filtering, sorting, users, roles,
authors, publishers, wishlists, shopping carts and orders. Customers can add, delete, and update ratings and reviews and
also search for books by title, author(s) name(s) and publisher name. Project Statistics: 193000 eBooks, 5909
categories, 13200 authors, 152 publishers, and 8 currencies.

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

You can find below a short video presentation of my clone project uploaded on YouTube. If access to YouTube is
restricted on your computer, you can download it from here [Video Presentation](add-the-link-here)

## Installation

1. Clone or download this repository
2. Configure the following environment variables related to the database that you would like to use and the exchange
   rate API key:

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
# Free API key for getting the latest exchange rates with 1500 monthly requests
# You can use this key or get your own key by registering at https://www.exchangerate-api.com/
exchangerate.api.key=a9efd8f3e3c147edd16e527e
```

3. Run the application

Whenever the application is run, the InitialDatabaseSetup component checks "if (this.roleRepository.count() < 2 ||
this.publisherRepository.count() == 0)" and, if true, generates the database. The TaskStartupRunner component checks if
the database has been backed up and if the foreign exchange rates have been updated today. If not, it backs up the
database, updates the exchange rates, and then updates the book prices in foreign currency. The database generation,
backup, and updating the exchange rates and book prices takes about 6 minutes on my laptop. You can also use
this [SQL dump file](add-the-link-here), if you prefer to not generate the database.

> [!NOTE]
> The original book data used in this project is sourced from
> the [Kyushu University / book-dataset](https://github.com/uchidalab/book-dataset/tree/master/Task2) public repository.
> The CSV file included in my repository has been modified for use in this specific application. I have used the book
> titles, cover image urls, and categories from the original dataset. These categories have been filtered and mapped
> to the categories that can be seen on the official Kindle Store. The InitialDatabaseSetup has generated the rest 
> of the data - author names, publisher names, count of ratings, average rating, count of purchases, publication 
> dates, subcategory names, placeholder reviews, etc.

## Usage

You need to enter an email and a password in order to log in to the application. The InitialDatabaseSetup component
generates the following two accounts

| Email          | Password |
|----------------|----------|
| admin@mail.com | 1234     |
| user@mail.com  | 1234     |

admin@mail.com has the roles "ADMIN, USER", and user@mail.com has the role "USER". Only accounts with the ADMIN role can
access the admin panel, which allows them to add and remove the ADMIN role from other accounts.
