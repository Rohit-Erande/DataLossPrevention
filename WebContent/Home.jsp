<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Big Data CSA - HOME</title>
</head>
<body>
<h3>File Upload</h3>
Please select the email pcaps to upload

<form action="./DataLoader" method="post" enctype="multipart/form-data">
	<input type="file" name="file"/>
	</br>
	<input type="submit" value="upload email file"/>
</form>

</body>
</html>