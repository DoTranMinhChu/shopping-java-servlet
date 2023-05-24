
<%@page import="main.dto.User"%>

<header>
    <% 
        User account = (User) request.getSession().getAttribute("account_infomation");  
    %>
    <nav class="navbar">
        <h1>Header</h1>

        <% if(account != null){%>
        <h2>
            Fullname :<%=account.getFullname()%>
        </h2>
        <%}%>


    </nav>



</header>