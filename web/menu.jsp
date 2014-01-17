<%@ page session="false" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
	<div class="wrap">
		<span>1,000,000,000 user based counter</span>
	</div>
<script type="text/javascript">
$(function() {
    var menu = [
            "autocomplete_test.jsp",
            "autocomplete_add.jsp", 
            "pv_daily.jsp",
            "uv_daily.jsp",
            "uv_weekly.jsp",
            "uv_user_list.jsp"
            ];

    /**
    혼동하기 쉬운 표현.
    $(this).attr("href") 상대적. 즉, Selector에서 잡아온놈.
    $(location).attr("href")
    */
    $('#nav div.wrap a').each(function(idx)	{
        if ($(location).attr("href").lastIndexOf(menu[idx]) > 0)	{
            console.log($(location).attr("href") + "][" + menu[idx]);
            $(this).addClass("on");
        }
    });
});
</script>
	<div id="header" class="welcome">
		<div id="nav">
			<div class="wrap">
				<a href="<%=request.getContextPath() %>/autocomplete_test.jsp">Autocomplete</a>
				<a href="<%=request.getContextPath() %>/autocomplete_add.jsp">add a autocomplete phrase</a>
				<a href="<%=request.getContextPath() %>/pv_daily.jsp">daily total visit count</a>
				<a href="<%=request.getContextPath() %>/uv_daily.jsp">daily unique visit count</a>
				<a href="<%=request.getContextPath() %>/uv_weekly.jsp">weekly unique visit count</a>
				<a href="<%=request.getContextPath() %>/uv_user_list.jsp">weekly unique visit user list</a>
			</div>
		</div>
	</div>