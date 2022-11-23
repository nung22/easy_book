from flask_app import app, render_template, request, redirect,bcrypt, flash, session
from flask_app.models.book import Book
from flask_app.models.user import User


# Display book dashboard
@app.route('/all_books')
def all_books():
    return render_template('show_books.html', title = "All Books", all_books = Book.get_all())


# Display information for an individual book
@app.route('/show_book/<int:id>')
def book_info(id):
    data = { 'id' : id }
    book_info = Book.get_by_id(data)
    print(book_info)
    return render_template('book_info.html', title=book_info.title, book=book_info)

# Display form to create new book
@app.route('/books/new')
def books_new():
    if 'user_id' not in session:
        return redirect('/logout')
    return render_template('book_add.html', title = "Add New Book", user_id = session['user_id'])

# Create new book and redirect to book dashbaord
@app.route('/new_book',methods=['POST'])
def new_book():
    print(request.form)
    if not Book.validate_book(request.form):
        return redirect('/books/new')
    Book.save(request.form)
    return redirect('/dashboard_books')

# Display form to edit an existing book
@app.route('/books/<int:id>/edit')
def books_edit(id):
    if 'user_id' not in session:
        return redirect('/logout')
    data = { 'id' : id }
    return render_template('books_edit.html', title = "Edit Book Info", book = Book.get_by_id(data))

# Edit an existing book and redirect to book dashboard
@app.route('/edit_book', methods=['POST'])
def edit():
    print(request.form)
    if not Book.validate_book(request.form):
        book_id = request.form['id']
        return redirect(f'/books/{book_id}/edit')
    Book.edit(request.form)
    return redirect('/books')

# Buy an existing book and update its available and rented numbers
@app.route('/buy_book/<int:book_id>/<int:available>/<int:rentald>')
def buy(book_id, available, rented):
    user_data = {
        'user_id' : session['user_id'],
        'book_id' : book_id 
    }
    book_data = {
        'id' : book_id,
        'available' : available - 1,
        'num_rentald' : rented + 1
    }
    User.make_rental(user_data)
    Book.buy(book_data)
    return redirect(f'/books/{book_id}')

@app.route('/add_book')
def add_book():
    return render_template('admin/book_add.html',title="Add Book", admin_tools="active", current_page="Admin Tools")

# Delete a book given its id
@app.route('/books/<int:id>/destroy')
def delete(id):
    data = { 'id' : id }
    Book.delete(data)
    return redirect('/books')