import flask_app
from flask_app.config.mysqlconnection import connectToMySQL
from flask_app import flash
from pprint import pprint

DATABASE = "easy_book"

class Book:
    def __init__(self, data) -> None:
        self.id = data['id']
        self.title = data['title']
        self.description = data['description']
        self.price = data['price']
        self.available = data['available']
        self.rented = data['rented']
        self.genre = data['genre']
        self.isbn = data['isbn']
        self.cover = data['cover']
        self.author_first_name = data['author_first_name']
        self.author_last_name = data['author_last_name']
        if 'first_name' in data:
            self.first_name = data['first_name']
        if 'last_name' in data:
            self.last_name = data['last_name']
        self.created_at = data['created_at']
        self.updated_at = data['updated_at']
    
    # This method will validates form data for book objects
    @staticmethod
    def validate_book(book:dict) -> bool:
        is_valid = True
        if len(book['title']) < 2:
            is_valid = False
            flash("Title must be at least 2 characters.")
        if len(book['description']) < 10:
            is_valid = False
            flash("Description must be at least 10 characters.")
        if float(book['price']) < 0.01:
            is_valid = False
            flash("Price must be greater than 0.")
        if int(book['available']) < 1:
            is_valid = False
            flash("Quantity must be greater than 0.")
        return is_valid
    
    # This method will return all instance of book objects
    @classmethod
    def get_all(cls):
        query = "SELECT * FROM books;"
        results = connectToMySQL(DATABASE).query_db(query)
        books = []
        for book in results:
            books.append( cls(book) )
        return books
    
    # This method will create a new book object with given data
    @classmethod
    def save(cls, data):
        query = "INSERT INTO books (user_id, title, description, price, available, rented, genre, isbn, cover, author_first_name, author_last_name) VALUES (%(user_id)s, %(title)s, %(description)s, %(price)s, %(available)s, %(rented)s, %(genre)s, %(isbn)s, %(cover)s, %(author_first_name)s, %(author_last_name)s);"
        return connectToMySQL(DATABASE).query_db(query, data)
    
    # This method will get a book object given its id
    @classmethod
    def get_by_id(cls, data):
        query = "SELECT * FROM books JOIN users on users.id = books.user_id WHERE books.id = %(id)s;"
        results = connectToMySQL(DATABASE).query_db(query, data)
        book = Book( results[0] )
        return book
    
    # This method will edit a book object given its id and any new data
    @classmethod
    def edit(cls, data):
        query = "UPDATE books SET title = %(title)s, description = %(description)s, price = %(price)s, available = %(available)s, genre = %(genre)s, isbn = %(isbn)s, cover = %(cover)s, author_first_name = %(author_first_name)s, author_last_name = , %(author_last_name)s WHERE books.id = %(id)s;"
        return connectToMySQL(DATABASE).query_db(query, data)
    
    @classmethod
    def rent(clas, data):
        query = "UPDATE books SET available = %(available)s, rented = %(rented)s  WHERE books.id = %(id)s;"
        return connectToMySQL(DATABASE).query_db(query, data)

    # This method will delete a book object given its id
    @classmethod
    def delete(cls, data):
        query = "DELETE FROM books WHERE books.id = %(id)s;"
        return connectToMySQL(DATABASE).query_db(query, data)
