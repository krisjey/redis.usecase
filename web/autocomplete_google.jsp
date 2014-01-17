<%@ page session="false" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE>
<html>
<head>
<title>redis demo</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, width=device-width" />

<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/c.min.css" />
<link rel="stylesheet" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.10.1/themes/base/minified/jquery-ui.min.css" type="text/css" />

<script type="text/javascript" src="http://code.jquery.com/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="http://code.jquery.com/ui/1.10.1/jquery-ui.min.js"></script>

<script type="text/javascript" charset="utf-8">
    $(function() {
        console.log("start auto");

        //autocomplete
        $(".auto").autocomplete({
            source : "autocomplete_action.jsp",
            minLength : 1
        });

        console.log("end auto");
    });
</script>

</head>
<body class="b">
	<%@ include file="./menu.jsp"%>
	<div id="content" class=" welcome">
		<p>
			<label>Country:</label><input type='text' name='country' value='' class='auto'>
		</p>
	</div>
</body>
</html>
