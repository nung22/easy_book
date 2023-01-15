from flask_app import app, render_template, request, redirect, bcrypt, session, flash
from flask_app.models.user import User
from flask_app.models.book import Book

# Redirect From Index To users Page
@app.route('/')
def index():
    return redirect('/home')

@app.route('/home')
def home():
    return render_template('home.html', title = "Home")

@app.route('/account')
def account():
    if 'user_id' not in session:
        return redirect('/login_page')
    return render_template('user_account.html', title="Account")

# Load login page
@app.route('/login_page')
def login_page():
    return render_template('login_reg/login.html',title="Login")

# Log user in and redirect to book dashboard
@app.route('/login', methods=['POST'])
def login():
    print(request.form)
    user = User.get_by_email(request.form)
    if not user:
        flash("Invalid Credentials.")
        return redirect('/login_page')
    pass_valid = bcrypt.check_password_hash(user.password, request.form['password'])
    if not pass_valid:
        flash("Invalid Credentials.")
        return redirect('/login_page')
    session['user_id'] = user.id
    session['first_name'] = user.first_name
    session['last_name'] = user.last_name
    session['is_admin'] = user.is_admin
    return redirect('/')

# Load login page
@app.route('/register_user')
def register_user():
    return render_template('login_reg/reg.html',title="Create Account")

# Create new user object
@app.route('/register',methods=['POST'])
def register():
    print(request.form)
    if not User.validate_user(request.form):
        return redirect('/register_user')
    hashed_pass = bcrypt.generate_password_hash(request.form['password'])
    print(hashed_pass)
    data = {
        'first_name' : request.form['first_name'],
        'last_name' : request.form['last_name'],
        'email' : request.form['email'],
        'phone_number' : request.form['phone_number'],
        'password' : hashed_pass
    }
    user_id = User.save(data)
    session['user_id'] = user_id
    session['first_name'] = request.form['first_name']
    session['last_name'] = request.form['last_name']
    session['is_admin'] = 0
    return redirect('/')

# Logs user out and clears session data
@app.route('/logout')
def logout():
    session.clear()
    return redirect('/')

@app.route('/admin_dashboard')
def admin_dashboard():
    if session['is_admin'] != 1:
        return redirect('/logout')
    return render_template('admin/admin_dashboard.html', 
    total_books=len(Book.get_all()), total_users=len(User.get_all()), total_rentals=0, 
    title="Admin Dashboard", dashboard="active", current_page="Dashboard")

@app.route('/dashboard_users')
def dashboard_users():
    if session['is_admin'] != 1:
        return redirect('/logout')
    return render_template('admin/dashboard_users.html', 
    total_books=len(Book.get_all()), total_users=len(User.get_all()), total_rentals=0, 
    all_books=Book.get_all(), all_users=User.get_all(), all_rentals=[], 
    title="Dashboard Users", dashboard="active", current_page="User Dashboard")

@app.route('/dashboard_books')
def dashboard_books():
    if session['is_admin'] != 1:
        return redirect('/logout')
    return render_template('admin/dashboard_books.html', 
    total_books=len(Book.get_all()), total_users=len(User.get_all()), total_rentals=0, 
    all_books=Book.get_all(), all_users=User.get_all(), all_rentals=[], 
    title="Dashboard Books", dashboard="active", current_page="Book Dashboard")

@app.route('/dashboard_rentals')
def dashboard_rentals():
    if session['is_admin'] != 1:
        return redirect('/logout')
    return render_template('admin/dashboard_rentals.html', 
    total_books=len(Book.get_all()), total_users=len(User.get_all()), total_rentals=0, 
    all_books=Book.get_all(), all_users=User.get_all(), all_rentals=[], 
    title="Dashboard Rentals", dashboard="active", current_page="Rental Dashboard")

# Delete a user given its id
@app.route('/users/<int:id>/destroy')
def delete_user(id):
    data = { 'id' : id }
    User.delete(data)
    return redirect('/')
