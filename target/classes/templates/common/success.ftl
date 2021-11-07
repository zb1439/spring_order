<html>
<head>
    <meta charset="utf-8">
    <title>Seller Error Notification</title>
    <link href="https://cdn.bootcdn.net/ajax/libs/twitter-bootstrap/3.0.1/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <div class="row clearfix">
        <div class="col-md-12 column">
            <div class="alert alert-success alert-dismissable">
                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
                <h4>
                    SUCCESS!
                </h4>
                <strong>${msg}</strong>
                Redirect automatically in 3 seconds...
            </div>
        </div>
    </div>
</div>
</body>

<script>
    setTimeout('location.href="${url}"', 3000)
</script>
</html>