<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<html>
    <head>
        <link rel = "stylesheet" href = "firststyle.css">
		<%
         String id = (String)session.getAttribute("sessionID"); 
		%>
    </head>
    
    <script type="text/javascript">
    function goTaxi(){
		location.href="board.jsp";
	}
    // ��� ��ư Ŭ���� �α��� ȭ������ �̵�
    function goLogin(){
		location.href="LoginPage.jsp";
	}
    function goJoin(){
		location.href="member.jsp";
	}
    function goLogout(){
		location.href="Logout.jsp";
	}
	</script>
    <body>
        <img id = "img" src = "3.jpg">
        <div class = "header">
            <div class = "menu">
                <div id = "logo">
                </div>
                <div id = "m_blank">
                </div>
                <div id = "m_link">
                    <ul>
                        <li>
                            <a href = "#"></a>
                        </li>
                        <li>
                            <a href = "#"></a>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
        <div class = "main">
            <div class = "up">

            </div>
            <div class = "center">
                <img src = "5.png" style="height: 100px">
                <br>
                Let's take<br> a taxi
                <br>
                <button class = "button" onclick="goTaxi()"><span>�ý� Ÿ�� ����</span></button>
                <%
                if(id == null)
                	out.print("<button class = \"button\" onclick=\"goLogin()\"><span>�α���</span></button>");
                else
                	out.print("<button class = \"button\" onclick=\"goLogout()\"><span>�α׾ƿ�</span></button>");
                %>
                
                <button class = "button" onclick="goJoin()"><span>ȸ������</span></button>
            </div>
            <div class = "down">
        
            </div>
        </div>
    </body>
</html>