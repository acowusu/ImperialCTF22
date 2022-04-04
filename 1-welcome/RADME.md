# Welcome

There was a hint when signing up that there might be a pin in the source code.

Taking a look at the source code we saw the following:

```html
<div id="section-works" class="page-section pt-0">

  <div class="section m-0">
    <div class="container clearfix">
      <div class="mx-auto center" style="max-width: 900px;">
        <h2 class="mb-0 fw-light ls1">From the creators of <b><a href="https://ichack.org">IC Hack</a></b> and <b><a href="https://fb.me/e/2nGA8TGs1">HuxTheFlag</a></b>, now presenting Imperial's first ever edition of a Cyber Security contest open to all university students.</h2>
        <br/>
        <!-- <h4> SUNURns1ZWVfeTB1X3RoM3IzIX0= </h4> -->
      </div>
    </div>
  </div>
```

The string `SUNURns1ZWVfeTB1X3RoM3IzIX0=` looked suspiciously similar to a base64 encoded string.
We base64 decoded it and it turned out to be a flag.

```text
ICTF{5ee_y0u_th3r3!}
```
