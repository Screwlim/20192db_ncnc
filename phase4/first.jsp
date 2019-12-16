<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="utf-8"%>
<html>
    <head>
        <link rel = "stylesheet" href = "firststyle.css">
      <%
         String id = (String)session.getAttribute("sessionID"); 
      %>
    </head>
    
    <script type="text/javascript">
    function goNCAR(){
      location.href="board.jsp";
   }
    // 취소 버튼 클릭시 로그인 화면으로 이동
    function goLogin(){
      location.href="LoginPage.jsp";
   }
    function goJoin(){
      location.href="member.jsp";
   }
    function goLogout(){
      location.href="Logout.jsp";
   }
	function goUserinfo(){
		var x_ = (window.screen.width / 2) - 165;
		var y_ = (window.screen.height / 2) - 240;
		if (!(window.open("userInfo.jsp", "childForm",
				"width=325, height=480 , left=" + x_ + ", top=" + y_
						+ ", screenX=" + x_ + ", screenY=" + y_
						+ ", resizable = no, scrollbars = no, status = no"))) {
			return false;
		}
	}
   </script>
    <body>
        <img id = "img" src = "background.gif">
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
                <img src = "N.png" style="height: 100px">
                <br>
                Let's purchase<br> a car
                <br>
                <button class = "button" onclick="goNCAR()"><span>매물 조회</span></button>
                <%
                if(id == null)
                   out.print("<button class = \"button\" onclick=\"goLogin()\"><span>로그인</span></button>");
                else
                   out.print("<button class = \"button\" onclick=\"goLogout()\"><span>로그아웃</span></button>");
                %>
                
                <%
                if(id == null)
                   out.print("<button class = \"button\" onclick=\"goJoin()\"><span>회원가입</span></button>");
                else
                   out.print("<button class = \"button\" onclick=\"goUserinfo()\"><span>회원정보</span></button>");
                %>
            </div>
            <div class = "down">
        
            </div>
        </div>
    </body>
</html>