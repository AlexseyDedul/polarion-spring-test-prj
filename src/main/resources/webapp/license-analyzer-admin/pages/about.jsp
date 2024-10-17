<%@ page contentType="text/html; charset=UTF-8" %>
<jsp:include page="/common/jsp/about.jsp"/>
<%@ page import="com.garantis.polarion.extension.license.analyzer.util.ExtensionInfo" %>
<%@ page import="com.garantis.polarion.extension.license.analyzer.rest.model.Version" %>
<%@ page import="com.garantis.polarion.extension.license.analyzer.util.VersionUtils" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en" >

<%!
	private static final String ABOUT_TABLE_ROW = "<tr class='table-td-class'><td>%s</td><td>%s</td></tr>";
	
	Version version = ExtensionInfo.getInstance().getVersion();
%>
<head>
	<link rel="stylesheet" href="/polarion/license-analyzer-admin/static/bABzNTQJ.css"/>
	<link rel="stylesheet" href="/polarion/license-analyzer-admin/static/style.css"/>
    <title>License analyzer</title>
</head>
<body>
	
	<h1>About</h1>
	
	<div class="grid grid-cols-[1fr_300px] gap-2">
		<p class="svelte-etjk7y">Using a license analyzer for Polarion ALM provides a robust and structured approach 
to license management. It helps organizations maintain compliance, optimize costs, plan for the future, 
and efficiently resolve license-related issues while preserving historical data for reference and audit 
purposes. This proactive approach to license management is essential for any organization seeking to 
maximize the value of its software investments and minimize potential risks.</p>
		
		<div></div>
	</div>
	<h2>How to use:</h2>
	<ul class="list-disc pl-8">
		<li>License Analyzer provides Open API and REST API functions for creating new reports and statistics.</li> 
		<li>The plugin provides some reports about users and polarion license statistics.</li>
	</ul>
	
	<h3 class="svelte-etjk7y">Examples:</h3>
	
	<ul class="list-disc pl-8">
		<li>
			<p class="font-bold svelte-etjk7y">Velocity:</p>
			<code style="font-size:12px;" data-language="velocity"><pre>
#foreach($user in $userSessionHistoryService.getAllUsers()) ##get all users with first session
	$user.getUserId() ## get user id
	$user.getLicenseType() ## get user license type
	$user.getLicenseKind() ## get user license kind
	$user.getLastSession() ## get user session
#end
			</pre></code>
			<code style="font-size:12px;" data-language="velocity"><pre>
#foreach($statistic in $statisticService.getStatistics()) ##get all statistics with first session
	$statistic.getLicenseType() ## get statistic license type
	$statistic.getLicenseKind() ## get statistic license kind
	$statistic.getLimit() ## get limit
	$statistic.getPeak() ## get peak
	$statistic.getLastSessions() ## get statistic session
#end
			</pre></code>
			<p class="svelte-etjk7y">To get more information about other functions, 
			please open JAVA API tab of the plugin.</p>
		</li>
	</ul>
	
	<ul class="list-disc pl-8">
		<li>
			<p class="font-bold svelte-etjk7y">Java script:</p>
			<code style="font-size:12px;" data-language="javascript"><pre>
  // Get statistic for users
  function getStatisticUsers(){
    jQuery.ajax({
        method: "GET",
        async: false,
        headers: {
            'Authorization': 'Bearer {token}',
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        },
        url: '/polarion/license-analyzer/rest/api/users',
        success: function(data) {
             console.log(data.data);
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
        var message = XMLHttpRequest.responseJSON.datail;
            if (XMLHttpRequest.status == 404) {
                alert("Not found.");
            } else if (XMLHttpRequest.status == 401) {
                alert("Unauthorized.");
            } else {
                alert("Error:  " + errorThrown + " (" + message + ")");
            }
        },
        complete:function(){
        }
    });
  }
			</pre></code>
			<p class="svelte-etjk7y">To get more information about other functions, 
			please open Swagger UI tab of the plugin.</p>
		</li>
	</ul>
	
	<h3 class="svelte-etjk7y">Extension info:</h3>
	<table class="table-class">
		<thead>
			<tr class="table-th-class">
				<th>Manifest entry</th>
				<th>Value</th>
			</tr>
		</thead>
		<tbody>
			<%
				out.print(ABOUT_TABLE_ROW.formatted(VersionUtils.BUNDLE_NAME, version.getBundleName()));
				out.print(ABOUT_TABLE_ROW.formatted(VersionUtils.BUNDLE_VENDOR, version.getBundleVendor()));
				out.print(ABOUT_TABLE_ROW.formatted(VersionUtils.BUNDLE_VERSION, version.getBundleVersion()));
				out.print(ABOUT_TABLE_ROW.formatted(VersionUtils.AUTOMATIC_MODULE_NAME, version.getModuleName()));
				out.print(ABOUT_TABLE_ROW.formatted(VersionUtils.BUNDLE_BUILD_TIMESTAMP, version.getBundleBuildTimestamp()));
			%>
		</tbody>
	</table>
	
	<h2 class="svelte-etjk7y">Garantis IT-Solutions Ltd</h2>
	<div class="grid grid-cols-[1fr_500px] gap-2">
		<div>
			<p class="svelte-etjk7y">Garantis IT-Solutions Ltd. provides comprehensive support and maintenance for Polarion ALM projects. We contribute extensive technical expertise in
		        developing project templates, plugins, and widgets, enhancing the customer experience. Our approach combines in-depth knowledge of Polarion with
		        innovative development strategies, significantly improving the quality and reducing development time. We are supporting Polarion ALM projects from
		        training and rollout to long-term maintenance and complex customizations.</p> 
		    <p class="svelte-etjk7y">Website: 
		   		<a href="https://garantis-solutions.com" target="_blank" class="svelte-etjk7y">https://garantis-solutions.com</a>
		    </p>
	    </div> 
        <img alt="Grantis logo" src="/polarion/license-analyzer-admin/assets/grantis.png" class="svelte-etjk7y">
    </div>
</body>

</html>