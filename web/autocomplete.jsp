<%@ page session="false" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE>
<html>
<head>
<title>redis 데모</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, width=device-width" />

<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/c.min.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/js/jquery.ui.autocomplete.css" />

<script src="<%=request.getContextPath() %>/js/jquery-1.8.3.js"></script>
<script src="<%=request.getContextPath() %>/js/jquery-ui-1.9.2.custom.js"></script>
<script src="<%=request.getContextPath() %>/js/jquery.ui.autocomplete.js"></script>

<script type="text/javascript" charset="utf-8">
    $(function() {
        console.log("start auto");
        $("#input_box").autocomplete({
            source : function(request, response) {
                $.ajax({
                    cache : false,
                    type : "GET",
                    url : "./autocomplete_action.jsp?term=" + $("#input_box").val(),
                    contentType : "application/x-www-form-urlencoded;charset=UTF-8",
                    dataType : "json",
                    success : function(msg) {
                        response(msg);
                    }
                })
            },
            minLength : 2
        });

        console.log("end auto");
    });
</script>

</head>
<body class="b">
	<%@ include file="./menu.jsp"%>
	<div id="content" class=" welcome">
		static data test <input type="text" id="input_box" name="input_box" />
	</div>
</body>
</html>
