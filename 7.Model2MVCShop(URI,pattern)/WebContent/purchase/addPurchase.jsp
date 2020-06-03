<%@ page contentType="text/html; charset=EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<html>
<head>
<title>Insert title here</title>
</head>

<body>

<form name="updatePurchase" action="/updatePurchaseView.do?tranNo=${purchase.tranNo}" method="post">

다음과 같이 구매가 되었습니다.

<table border=1>
	<tr>
		<td>물품번호</td>
		<td>${product.prodNo}</td>
		<td></td>
	</tr>
	<tr>
		<td>구매자아이디</td>
		<td>${user.userId}</td>
		<td></td>
	</tr>
	<tr>
		<td>구매방법</td>
		<td>

		<c:if test="${purchase.paymentOption=='1'}">
		현금구매
		</c:if>
		
		<c:if test="${purchase.paymentOption=='2'}">
		신용구매
		</c:if>
				
		</td>
		<td></td>
	</tr>
	<tr>
		<td>구매자이름</td>
		<td>${user.userName}</td>
		<td></td>
	</tr>
	<tr>
		<td>구매자연락처</td>
		<td>${user.phone}</td>
		<td></td>
	</tr>
	<tr>
		<td>구매자주소</td>
		<td>${user.addr}</td>
		<td></td>
	</tr>
		<tr>
		<td>구매요청사항</td>
		<td>${purchase.divyRequest}</td>
		<td></td>
	</tr>
	<tr>
		<td>배송희망일자</td>
		<td>${purchase.divyDate}</td>
		<td></td>
	</tr>
</table>
</form>

</body>
</html>