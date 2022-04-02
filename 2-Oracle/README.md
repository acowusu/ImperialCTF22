# Oracle

Within the source of the page was a comment:

```
 <source src="https://github.com/iamroot99/oracle/blob/main/static/oracle.mp4" type="video/mp4"> 
```

By navigating to the Github repo i was able to find the [source of the server](main.py):

I noticed a comment in the source of the page:

```python
 # removed unsafe custom seed
 session['stuff'] = random.sample(range(10000, 1000000000), 10)
```

My guess that the deployed version may still use a custom random seed making a brute force attack possible.

Looking at the commit history revealed:

```python
random.seed(session['REDACTED_FOR_PRIVACY'])     
```
Still without any indication of the seed being used. I Could not progress much further directly.

Following a new angle of attack, I I focussed on how the page functioned:

```python

## Overall logic of program

We know that the flag is:

```python
app = Flask(__name__)
app.secret_key = "REDACTED_FOR_PRIVACY"

flag = "ICTF\{REDACTED_FOR_PRIVACY\}"
```

The main interesting stuff is here

```python
@app.route('/', methods = ['GET', 'POST'])
def index():
```

The program ensures users are logged in by checking the session variable.

```python
 if 'password' not in session:
  data = "You are not logged in. <br><a href = '/login'>" + "Click here to log in.</a>"
  return generate_page(data)
```

If the user is logged in they are asked for a number which is POSTed to the server

```python
 if request.method == 'GET':
  data = '''
   ...
   <form action = "" method = "post">
    <p> <input type = text name = guess /> </p>
    <p> <input type = submit value = Guess /> <p>
   </form>
  '''
  return generate_page(data)
  ```

When recieving a guess we compare it  to the last of the random numbers generated and if they are equal we return the flag.

```python
 if request.method == 'POST':
  attempts_left = len(session['stuff'])
  if attempts_left == 0:
   session.pop('password')
   return redirect(url_for('index'))

  guess = request.form['guess']
  
  options = session['stuff']
  actual = options.pop()
  session['stuff'] = options

  if guess.isnumeric() and int(guess) == actual:
   data = f"""...  {flag} """
   return generate_page(data)
  else:
   data = f'''
    <form action = "" method = "post">
     <p> <input type = text name = guess /> </p>
     <p> <input type = submit value = Guess /> <p>
    </form>
    <p> Incorrect. <b>The Oracle</b> will remember this. You have {attempts_left - 1} tries.</p>
   '''
   return generate_page(data)
```

`login` does different things depending on the request method.

when a GET request is made, it returns the login page.

when a POST request is made:

1. it sets the `session['password']` to the password given by the user
2. it sets the `session['stuff']` to a list of 10 random numbers
3. it redirects to the index page

```python
@app.route('/login', methods = ['GET', 'POST'])
def login():
 if request.method == 'POST':
  session['password'] = request.form['password']
  # removed unsafe custom seed
  session['stuff'] = random.sample(range(10_000, 1_000_000_000), 10)

  return redirect(url_for('index'))

 data =  '''
  <form action = "" method = "post">
   <p> Password: </p>
   <p> <input type = password name = password /> </p>
   <p> <input type = submit value = Login /></p>
  </form>
  '''
 return generate_page(data)
```

`generate_page`  does nothing interesting, it simply returns the data that is passed to it.

```python
def generate_page(data):
 return f"""
  ...
   {data}
  """
```
