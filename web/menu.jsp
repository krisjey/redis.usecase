<%@ page session="false" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
	<div class="wrap">
		<span>10억명(1,000,000,000) 대상 Counter</span>
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
				<a href="<%=request.getContextPath() %>/autocomplete_test.jsp">자동완성 테스트</a>
				<a href="<%=request.getContextPath() %>/autocomplete_add.jsp">자동완성 키워드 추가</a>
				<a href="<%=request.getContextPath() %>/pv_daily.jsp">일간 누적방문 조회</a>
				<a href="<%=request.getContextPath() %>/uv_daily.jsp">일간 순 방문 조회</a>
				<a href="<%=request.getContextPath() %>/uv_weekly.jsp">주간 순 방문 조회</a>
				<a href="<%=request.getContextPath() %>/uv_user_list.jsp">주간 순 방문자 목록 조회</a>
			</div>
		</div>
	</div>