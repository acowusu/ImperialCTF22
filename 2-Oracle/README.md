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

I Hypothosised that the deployed version may still use a custom random seed making a brute force attack possible.

Looking at the commit history revealed:

```python
random.seed(session['REDACTED_FOR_PRIVACY'])     
```

## overall logic of program

We know that the flag is:

```python
app = Flask(__name__)
app.secret_key = "REDACTED_FOR_PRIVACY"

flag = "ICTF\{REDACTED_FOR_PRIVACY\}"
```
The main interesting stuff is hereL

```python
@app.route('/', methods = ['GET', 'POST'])
def index():
 if 'password' not in session:
  data = "You are not logged in. <br><a href = '/login'>" + "Click here to log in.</a>"
  return generate_page(data)

 if request.method == 'GET':
  data = '''
   <h1> <b>Welcome, adventurer!</b></h1>
   <p> "<b>The Oracle</b> has been expecting you," the voice says. You look to your friends and then up and see a giant crystall ball. <br/>
    "<b>The Oracle</b> is thinking of a number. Guess right and you live to hack another day. Guess wrong and you all
    shall suffer." </p>
   <p> You look again to your friends and realise that they are lost. <b>The Oracle</b> only speaks in your mind. </p>
   <form action = "" method = "post">
    <p> <input type = text name = guess /> </p>
    <p> <input type = submit value = Guess /> <p>
   </form>
  '''
  return generate_page(data)

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
   data = f"""
    <h4> The earth shatters around you as <b>The Oracle</b> hisses and explodes into a bright light. You close your eyes until you feel the blinding light fade away. <b> The Oracle </b> has dissapeared and left a flag behind. </h4>
    <h4>
    {flag} </br>
    <b>The Oracle</b> has been defeated! GG! </h4>"""
   return generate_page(data)
  else:
   data = f'''
    <h1> Welcome! The Oracle has been expecting you. </h1>
    <h3> The Oracle is thinking of a number. Can you guess it? </p>
    <form action = "" method = "post">
     <p> <input type = text name = guess /> </p>
     <p> <input type = submit value = Guess /> <p>
    </form>
    <p> Incorrect. <b>The Oracle</b> will remember this. You have {attempts_left - 1} tries.</p>
   '''
   return generate_page(data)

`login` does different things depending on the request method.

when a GET request is made, it returns the login page.

when a POST request is made:

1. it sets the session['password'] to the password given by the user
2. it sets the session['stuff'] to a list of 10 random numbers
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
