<html>
<head>
  <link href="http://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<#if RequestParameters['error']??>
<div class="alert alert-danger">
  There was a problem logging in. Please try again.
</div>
</#if>
<div class="container">
  <form role="form" action="login" method="post">
    <div class="form-group">
      <label for="username">Username:</label>
      <input type="text" class="form-control" id="username" name="username" value="test"/>
    </div>
    <div class="form-group">
      <label for="password">Password:</label>
      <input type="password" class="form-control" id="password" name="password" value="123456"/>
    </div>
    <input type="hidden" id="csrf_token" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <button type="submit" class="btn btn-primary">Submit</button>
  </form>
</div>
<script src="http://cdn.bootcss.com/jquery/1.12.1/jquery.min.js"></script>
<script src="http://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</body>
</html>