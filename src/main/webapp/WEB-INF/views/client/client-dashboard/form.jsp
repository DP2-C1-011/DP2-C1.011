<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<h2>
	<acme:message code="client.dashboard.form.title.general-indicators"/>
</h2>

<table class="table table-sm">
	<tr>
		<th scope="row">
			<acme:message code="client.dashboard.form.label.average-contract-budget"/>
		</th>
		<td>
			<acme:print value="${averageContractBudget}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="client.dashboard.form.label.max-contract-budget"/>
		</th>
		<td>
			<acme:print value="${maxContractBudget}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="client.dashboard.form.label.min-contract-budget"/>
		</th>
		<td>
			<acme:print value="${minContractBudget}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="client.dashboard.form.label.deviation-contract-budget"/>
		</th>
		<td>
			<acme:print value="${deviationContractBudget}"/>
		</td>
	</tr>		
</table>

<h2>
	<acme:message code="client.dashboard.form.title.progressLogs-completeness"/>
</h2>

<div>
	<canvas id="myCanvas"></canvas>
</div>

<script type="text/javascript">
	$(document).ready(function() {
		var data = {
			labels : [
					"BELOW 25", "BETWEEN 25 AND 50", "BETWEEN 50 AND 75", "ABOVE 75"
			],
			datasets : [
				{
					data : [
						<jstl:out value="${progressLogsCompletenessBelow25}"/>, 
						<jstl:out value="${progressLogsCompletenessBetween25And50}"/>, 
						<jstl:out value="${progressLogsCompletenessBetween50And75}"/>,
						<jstl:out value="${progressLogsCompletenessAbove75}"/>
					],
		            backgroundColor: [
		                'rgba(255, 99, 132, 0.2)',   // Color for "BELOW 25"
		                'rgba(54, 162, 235, 0.2)',   // Color for "BETWEEN 25 AND 50"
		                'rgba(255, 206, 86, 0.2)',   // Color for "BETWEEN 50 AND 75"
		                'rgba(75, 192, 192, 0.2)'    // Color for "ABOVE 75"
		            ],
		            borderColor: [
		                'rgba(255, 99, 132, 1)',     // Border color for "BELOW 25"
		                'rgba(54, 162, 235, 1)',     // Border color for "BETWEEN 25 AND 50"
		                'rgba(255, 206, 86, 1)',     // Border color for "BETWEEN 50 AND 75"
		                'rgba(75, 192, 192, 1)'      // Border color for "ABOVE 75"
		            ],
		            borderWidth: 1
				}
			]
		};
		var options = {
			scales : {
				yAxes : [
					{
						ticks : {
							suggestedMin : 0.0,
							suggestedMax : 1.0
						}
					}
				]
			},
			legend : {
				display : false
			}
		};
	
		var canvas, context;
	
		canvas = document.getElementById("myCanvas");
		context = canvas.getContext("2d");
		new Chart(context, {
			type : "bar",
			data : data,
			options : options
		});
	});
</script>


<acme:return/>