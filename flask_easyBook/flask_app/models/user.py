import flask_app
from flask_app.config.mysqlconnection import connectToMySQL
from flask_app import flash
from pprint import pprint
from flask_app.models import book

import re
EMAIL_REGEX = re.compile(r'^[a-zA-Z0-9.+_-]+@[a-zA-Z0-9._-]+\.[a-zA-Z]+$')

DATABASE = "easy_book"

class User:
    def __init__(self, data) -> None:
        self.id = data['id']
        self.first_name = data['first_name']
        self.last_name = data['last_name']
        self.email = data['email']
        self.password = data['password']
        self.phone_number = data['phone_number']
        self.is_admin = data['is_admin']
        self.rentals = []
        self.created_at = data['created_at']
        self.updated_at = data['updated_at']
    
    # This method will validate form data for user objects
    @staticmethod
    def validate_user(user:dict) -> bool:
        is_valid = True
        if len(user['first_name']) < 2:
            is_valid = False
            flash("First name must be at least 2 characters.")
        if len(user['last_name']) < 2:
            is_valid = False
            flash("Last name must be at least 2 characters.")
        if not EMAIL_REGEX.match(user['email']):
            is_valid = False
            flash("Invalid email address.")
        if len(user['password']) < 8:
            is_valid = False
            flash("Password must be longer than 8 characters.")
        if user['password'] != user['confirm_password']:
            is_valid = False
            flash("Passwords do not match.")
        return is_valid
    
    # This method will return all instance of user objects
    @classmethod
    def get_all(cls):
        query = "SELECT * FROM users;"
        results = connectToMySQL(DATABASE).query_db(query)
        users = []
        for user in results:
            users.append( cls(user) )
        return users
    
    # This method will create a new user object with given data
    @classmethod
    def save(cls, data):
        query = "INSERT INTO users (first_name, last_name, email, phone_number, password) VALUES (%(first_name)s, %(last_name)s, %(email)s,  %(phone_number)s, %(password)s);"
        return connectToMySQL(DATABASE).query_db(query, data)
    
    # This method will retrieve a user object given its email
    @classmethod
    def get_by_email(cls, data):
        query = "SELECT * FROM users WHERE email = %(email)s;"
        result = connectToMySQL(DATABASE).query_db(query, data)
        if len(result) > 0:
            return User(result[0])
        else:
            return False
    
    @classmethod
    def get_admin_by_email(cls, data):
        query = "SELECT * FROM users WHERE email = %(email)s AND is_admin = 1;"
        result = connectToMySQL(DATABASE).query_db(query, data)
        if len(result) > 0:
            return User(result[0])
        else:
            return False
    
    # This method will retrieve a given user with all the books they have rented.
    @classmethod
    def get_by_user_id(cls , data):
        query = "SELECT * FROM users LEFT JOIN rentals ON rentals.user_id = users.id LEFT JOIN books ON rentals.book_id = books.id WHERE users.id = %(id)s;"
        results = connectToMySQL(DATABASE).query_db(query, data)
        user = cls( results[0] )
        for db_row in results:
            if db_row['books.id'] == None:
                break
            book_data = {
                    "id" : db_row["books.id"],
                    "title" : db_row["title"],
                    "first_name" : db_row["first_name"],
                    "last_name" : db_row["last_name"],
                    "description" : db_row["description"],
                    "price" : db_row["price"],
                    "available" : db_row["available"],
                    "rented" : db_row["rented"],
                    "created_at" : db_row["books.created_at"],
                    "updated_at" : db_row["books.updated_at"]
            }
            user.rentals.append( book.Book( book_data ) )
        return user
    
    # This method adds a new book to the user's rental list
    @classmethod
    def make_rental(cls, data):
        query = "INSERT INTO rentals (user_id, book_id) VALUES (%(user_id)s, %(book_id)s);"
        return connectToMySQL(DATABASE).query_db(query, data)
    
    # This method will return all rentals
    @classmethod
    def get_all_rentals(cls):
        query = "SELECT * FROM rentals JOIN users ON users.id = rentals.user_id;"
        results = connectToMySQL(DATABASE).query_db(query)
        rentals = []
        for book in results:
            rentals.append( cls(book) )
        return rentals
    
    # This method will delete a user object given its id
    @classmethod
    def delete(cls, data):
        query = "DELETE FROM users WHERE users.id = %(id)s;"
        return connectToMySQL(DATABASE).query_db(query, data)