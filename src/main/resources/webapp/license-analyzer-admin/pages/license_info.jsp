<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.garantis.polarion.extension.license.analyzer.util.LicenseUtils" %>
<%@ page import="com.garantis.polarion.license.License" %>
<!DOCTYPE html>
<html>
<%!
	private static final String ABOUT_TABLE_ROW = "<tr class='table-td-class'><td>%s</td><td>%s</td></tr>";
	License license = LicenseUtils.getLicense();
%>
<head>
	<meta charset="UTF-8">
	<link rel="stylesheet" href="/polarion/licenseAnalyzerDocs/static/bABzNTQJ.css"/>
	<link rel="stylesheet" href="/polarion/licenseAnalyzerDocs/static/style.css"/>
    <title>License information</title>
</head>
<body>
	<h1>Garantis license information</h1>
	<br/>
	<table class="table-class">
		<thead>
			<tr class="table-th-class">
				<th>License entry</th>
				<th>Value</th>
			</tr>
		</thead>
		<tbody>
			<%
			out.print(ABOUT_TABLE_ROW.formatted("Product name", license.getProduct()));
			out.print(ABOUT_TABLE_ROW.formatted("License version", license.getLicenseVersion()));
			out.print(ABOUT_TABLE_ROW.formatted("Hostname", license.getHostName()));
			out.print(ABOUT_TABLE_ROW.formatted("License type", license.getLicenseType()));
			out.print(ABOUT_TABLE_ROW.formatted("Number full access users", LicenseUtils.getServerFullAccessUsersCount() + "/" + license.getConcurrentFullAccessUsers()));
			out.print(ABOUT_TABLE_ROW.formatted("Number limited access users", LicenseUtils.getServerLimitedAccessUsersCount() + "/" + license.getConcurrentLimitedAccessUsers()));
			out.print(ABOUT_TABLE_ROW.formatted("Date update", license.getDateUpdate()));
			out.print(ABOUT_TABLE_ROW.formatted("Date expire", license.getDateExpire()));
			%>
		</tbody>
	</table>
	
</body>
</html>