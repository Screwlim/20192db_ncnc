<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ page import="java.sql.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
</head>
<%
	request.setCharacterEncoding("euc-kr");
	String id = request.getParameter("id");
	
	if(id == "" || id == null) out.println("id�� null�Դϴ�");

try {
 
     String driverName = "oracle.jdbc.driver.OracleDriver"; 
 
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
     
     Class.forName(driverName);
     
     //out.println(id); //���̵� ���°� Ȯ����.
 
     Connection con = DriverManager.getConnection(url,"ncnc","ncnc"); //url + id + pw
 
     Statement stmt = con.createStatement();            // SQL ������ ���������� Statement ��ü ����
     
     ResultSet rs = null;
     
     out.println("���̵�� "+id+"�Դϴ�");
     
     String sql = "SELECT ID FROM ACCOUNT WHERE ID="+"'"+id+"'";
     
     rs = stmt.executeQuery(sql);
 
     
     if(!rs.isBeforeFirst()){
    	 out.println("ID ��� �����մϴ�");
     }
     else{
    	out.println("�ߺ��� ID�� �����մϴ�");
     }
     
     con.close();
 
} catch (Exception e) {
 
     out.println("Oracle �����ͺ��̽� db ���ӿ� ������ �ֽ��ϴ�. <hr>");
 
     out.println(e.getMessage());
 
     e.printStackTrace();
}
finally{
	 
   // out.print("<script>location.href='member.jsp?id="+id+"';</script>"); //���⼭ id���� �Ѱ��༭...�𸣰Ե�.

}

%>
<script type="text/javascript">
//	function setPValue(){
		//opener.document.getElementById("pId").value = document.getElementById("cId").value;
		//window.close();
//	}
//<form method="post" name="writeform" action="member_insert.jsp" target="MemberServlet.do"></form>
</script>
<body>
</body>
</html>