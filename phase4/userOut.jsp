<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ page import="java.sql.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">


<%
	//세션정보 가져오기
	try {
		String driverName = "oracle.jdbc.driver.OracleDriver";

		String url = "jdbc:oracle:thin:@localhost:1521:xe";

		Statement stmt = null;
		boolean is_admin = false;
		ResultSet rs0 = null;

		Class.forName(driverName);

		Connection con = DriverManager.getConnection(url, "ncnc", "ncnc");

		stmt = con.createStatement();

		String sql0 = "select is_admin from account where id = \'" + session.getAttribute("sessionID") + "\'";

		rs0 = stmt.executeQuery(sql0);

		if (rs0.next()) {
			if (rs0.getString("is_admin").equals("T")) {
				is_admin = true;

				rs0.close();

				rs0 = stmt.executeQuery("SELECT COUNT(*) FROM ACCOUNT WHERE is_admin = 'T'");

				if (rs0.next()) {
					if (rs0.getInt(1) == 1) {
						out.println("<script language='javascript'>");
						out.println("alert('유일한 관리자는 탈퇴를 할 수 없습니다.')");
						
						out.println("location.href='userInfo.jsp'");
						
						out.println("</script>");
						rs0.close();
					} else {
						String sql = "DELETE FROM ACCOUNT WHERE ID = \'" + session.getAttribute("sessionID")
								+ "\'";
						int rs = stmt.executeUpdate(sql);
						out.println("<script language='javascript'>");
						out.println("alert('지금까지 저희 서비스를 이용해 주셔서 감사합니다.')");
						session.invalidate();
						
						out.println("self.opener = self");
						out.println("window.close()");
						
						out.println("</script>");
					}
				}
			} else {

				rs0.close();

				rs0 = stmt.executeQuery("SELECT COUNT(BUYER), COUNT(SELLER) FROM ORDER_INFO WHERE BUYER = \'"
						+ session.getAttribute("sessionID") + "\' OR SELLER  = \'"
						+ session.getAttribute("sessionID") + "\'");
				
				if (rs0.next()) {
					if (rs0.getInt(1) > 0 || rs0.getInt(2) > 0) {
						out.println("<script language='javascript'>");
						out.println("alert('거래기록이 남아있습니다. 탈퇴가 불가능 합니다.')");
						out.println("location.href='userInfo.jsp'");						
						out.println("</script>");
						rs0.close();

					} else {
						String sql = "DELETE FROM ACCOUNT WHERE ID = \'" + session.getAttribute("sessionID")
								+ "\'";
						int rs = stmt.executeUpdate(sql);
						out.println("<script language='javascript'>");
						out.println("alert('지금까지 저희 서비스를 이용해 주셔서 감사합니다.')");
						session.invalidate();
						
						out.println("self.opener = self");
						out.println("window.close()");
						
						out.println("</script>");
						
					}
				}
			}
		}
	} catch (Exception e) {
		out.println("Oracle Database Connection Something Problem. <hr>");

		out.println(e.getMessage());

		e.printStackTrace();

	}
%>