<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<html>
    <head>
	<%
        request.setCharacterEncoding("euc-kr"); 
    %>
        <link rel = "stylesheet" href = "loginstyle.css">
        
    <script type="text/javascript">
        function checkValue()
        {
            inputForm = eval("document.loginInfo");
            if(!inputForm.id.value)
            {
                alert("���̵� �Է��ϼ���");    
                inputForm.id.focus();
                return false;
            }
            if(!inputForm.password.value)
            {
                alert("��й�ȣ�� �Է��ϼ���");    
                inputForm.password.focus();
                return false;
            }
        }
    </script>
    
    </head>

    <body class="align">
        <img id = "img" src = "background.jpg">
        <div class="grid"> 
            <form action = "Login.jsp" method="post" name="loginInfo" class="form login" onsubmit="return checkValue()">
                <header class="login__header">
                    <img src = "N.png" style="height: 100px">
                </header>
                    <div class="login__body">
                        <div class="form__field">
                            <input type="text" name="id" placeholder="���̵�" required>
                        </div>
                  
                        <div class="form__field">
                            <input type="password" name="password" placeholder="�н�����" required>
                        </div>
                    </div>
                  
                    <footer class="login__footer">
                        <input type="submit" value="Login">
                        <p><span class="icon icon--info">?</span><a href="member.jsp">Sign Up</a></p>
                    </footer>
            </form>   
        </div>   
    </body>
</html>
