<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Statistics</title>
	<link rel="stylesheet" href="/polarion/license-analyzer-admin/static/bABzNTQJ.css"/>
	<link rel="stylesheet" href="/polarion/license-analyzer-admin/static/style.css"/>
	<script src="/polarion/license-analyzer-admin/js/chart.umd.min.js"></script>
	<script src="/polarion/license-analyzer-admin/js/jquery-1.10.2.js"></script>
	<style>
	    #pieChartOnline {
	      width: 100%; 
	      width: 200px !important;
	      height: 200px !important;
	    }
	</style>
</head>
<body>


<h1>Statistics for online users</h1>
<br/>
<div class="online-statistic-container">
	<div id="onlineUsersInfo"></div>
	<canvas id="pieChartOnline"></canvas>
</div>
<br/>

<h1>License statistic (Last month)</h1>
<br/>
<div id="statistics-charts" class="statistic-container"></div>

<script>
  getUsers();
  getStatisticIds();
  
  function getStatisticIds(){
	  jQuery.ajax({
	        method: "GET",
	        async: false,
	        headers: {
	            'Content-Type': 'application/json',
	            'Accept': 'application/json'
	        },
	        url: '/polarion/license-analyzer/rest/internal/statistics/ids',
	        success: function(data) {
	        	var ids = data.data;
	        	for(var i = 0; i < ids.length; i++){
	        		getStatisticSessionsById(ids[i]);
	        	}
	        },
	        error: function(XMLHttpRequest, textStatus, errorThrown) {
	        	var message = XMLHttpRequest.responseJSON.datail;
	            if (XMLHttpRequest.status == 404) {
	            	$("body").empty().append("<p class='warning-message'>"+message+"</p>");
	            } else if (XMLHttpRequest.status == 403) {
	            	$("body").empty().append("<p class='warning-message'>You don`t have permissions to view this page.</p>");
	            } else if (XMLHttpRequest.status == 401) {
	            	$("body").empty().append("<p class='warning-message'>"+message+"</p>");
	            } else {
	            	$("body").empty().append("<p class='warning-message'>"+message+"</p>");
	            }
	        },
	        complete:function(){
	        }
	    });
  }
  
  function getStatisticSessionsById(statisticId){
	  var dateNow = new Date();
	  var dateLastMonth = new Date();
	  dateLastMonth.setMonth(dateLastMonth.getMonth() - 1);
	  
	  jQuery.ajax({
	        method: "GET",
	        async: false,
	        headers: {
	            'Content-Type': 'application/json',
	            'Accept': 'application/json'
	        },
	        url: '/polarion/license-analyzer/rest/internal/statistics/'+statisticId+'/sessions/range/'+dateLastMonth.toISOString().split('T')[0]+'/to/'+dateNow.toISOString().split('T')[0],
	        success: function(data) {
	        	renderStatisticGraph(statisticId, data.data);
	        },
	        error: function(XMLHttpRequest, textStatus, errorThrown) {
	        	var message = XMLHttpRequest.responseJSON.datail;
	            if (XMLHttpRequest.status == 404) {
	            	$("body").empty().append("<p class='warning-message'>"+message+"</p>");
	            } else if (XMLHttpRequest.status == 401) {
	            	$("body").empty().append("<p class='warning-message'>"+message+"</p>");
	            } else {
	            	$("body").empty().append("<p class='warning-message'>"+message+"</p>");
	            }
	        },
	        complete:function(){
	        }
	    });
  }
  
  function renderStatisticGraph(statisticId, sessions){
	  var divContainer = $("<div class='statistic-graph'></div>");
	  var headingId = $("<h3>" + statisticId + "</h3>")
	  divContainer.append(headingId);
	  var chartId = "chart-" + statisticId;
	  var chartCanvas = $("<canvas id="+chartId+"></canvas>");
	  divContainer.append(chartCanvas);
	  $("#statistics-charts").append(divContainer);
	  
	  renderLineChart(chartId, sessions);
  }
  
  function renderLineChart(chartId, sessions){
	  var labels = [];
	  var data = [];
	  
	  for(var indx = 0; indx < sessions.length; indx++){
		  labels.push(sessions[indx].sessionDate);
	  }
	  
	  for(var indx = 0; indx < sessions.length; indx++){
		  data.push(sessions[indx].current);
	  }
	  
	  var ctx = document.getElementById(chartId).getContext('2d');
	  var myChart = new Chart(ctx, {
	    type: 'line',
	    data: {
	      labels: labels.reverse(),
	      datasets: [{
	        label: 'Current',
	        data: data.reverse(),
	        fill: false,
	        borderColor: 'rgb(75, 192, 192)',
	        tension: 0.1
	      }]
	    },
	    options: {
	        plugins: {
	            legend: {
	                display: false
	            }
	        },
	    }
	  });
  }
  
  function getUsers(){
    jQuery.ajax({
        method: "GET",
        async: false,
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        },
        url: '/polarion/license-analyzer/rest/internal/users',
        success: function(data) {
        	getOnlineUsers(data.totalCount);
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
        	var message = XMLHttpRequest.responseJSON.datail;
            if (XMLHttpRequest.status == 404) {
            	$("body").empty().append("<p class='warning-message'>"+message+"</p>");
            } else if (XMLHttpRequest.status == 403) {
            	$("body").empty().append("<p class='warning-message'>You don`t have permissions to view this page.</p>");
            } else if (XMLHttpRequest.status == 401) {
            	$("body").empty().append("<p class='warning-message'>"+message+"</p>");
            } else {
            	$("body").empty().append("<p class='warning-message'>"+message+"</p>");
            }
        },
        complete:function(){
        }
    });
  }
  
  function getOnlineUsers(users){
    jQuery.ajax({
        method: "GET",
        async: false,
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        },
        url: '/polarion/license-analyzer/rest/internal/online',
        success: function(data) {
        	
        	var usersCount = parseInt(users);
        	var onlineUsers = data.data.length;
        	pieChart(onlineUsers, usersCount - onlineUsers);
        	generateOnlineUsersTable(data.data);
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
        	var message = XMLHttpRequest.responseJSON.datail;
            if (XMLHttpRequest.status == 404) {
            	$("body").empty().append("<p class='warning-message'>"+message+"</p>");
            } else if (XMLHttpRequest.status == 403) {
            	$("body").empty().append("<p class='warning-message'>You don`t have permissions to view this page.</p>");
            } else if (XMLHttpRequest.status == 401) {
            	$("body").empty().append("<p class='warning-message'>"+message+"</p>");
            } else {
            	$("body").empty().append("<p class='warning-message'>"+message+"</p>");
            }
        },
        complete:function(){
        }
    });
  }
  
  function generateOnlineUsersTable(users){
	  
	  var table = $("<table class='table-class'></table>");
	  var headerRow = $("<tr class='table-th-class'></tr>");
	  
	  headerRow.append("<th>User id</th>");
	  headerRow.append("<th>License type</th>");
	  headerRow.append("<th>License kind</th>");
	  headerRow.append("<th>Last recorded session</th>");
	  table.append(headerRow);
	  
	  for(var i = 0; i < users.length; i++){
		var user = users[i];
		  var row = $("<tr class='table-td-class'></tr>");
		  row.append("<td>" + user.userId + "</td>");
		  row.append("<td>" + user.licenseType + "</td>");
		  row.append("<td>" + user.licenseKind + "</td>");
		  var sessionsHistory = user.sessionHistory;
		  if(sessionsHistory.length > 0){
			  var sessionInfo = "Login date: " + sessionsHistory[0].dateLogin;
			  if(sessionsHistory[0].dateLogout){
				  sessionInfo += "<br/> Logout date: " + sessionsHistory[0].dateLogout;
			  }
			  row.append("<td>" + sessionInfo + "</td>");
		  }else{
			  row.append("<td>Not found.</td>");
		  }
		  
		  table.append(row);
	  }
	  
	  $("#onlineUsersInfo").empty().append(table);
  }
  
  function pieChart(onlineUsers, offlineUsers){
	  var ctx = document.getElementById('pieChartOnline').getContext('2d');
	  ctx.width = 200;
	  ctx.height = 200;
	  var myChart = new Chart(ctx, {
	    type: 'pie',
	    data: {
	      labels: ['Online', 'Offline'],
	      datasets: [{
	        label: 'Users online',
	        data: [onlineUsers, offlineUsers],
	        backgroundColor: [
	          'rgba(255, 99, 132, 1)',
	          'rgba(54, 162, 235, 1)'
	        ],
	        borderColor: [ 
	          'rgba(255, 99, 132, 1)',
	          'rgba(54, 162, 235, 1)'
	        ],
	        borderWidth: 1
	      }]
	    },
	    options: {
	    	plugins: {
                tooltip: {
                    callbacks: {
                        label: function(context) {
                            var label = context.label || '';
                            var value = context.parsed || 0;
                            return label + ': ' + value;
                        }
                    }
                }
            }
	    }
	  });
	}
</script>

</body>
</html>