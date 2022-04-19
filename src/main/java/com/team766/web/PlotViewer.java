package com.team766.web;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import com.team766.logging.Category;
import com.team766.logging.FormattedLogEntry;
import com.team766.logging.LogWriter;
import com.team766.logging.Logger;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

public class PlotViewer implements WebServer.Handler {
	private static final String ENDPOINT = "/plots";

	private static String makePlotData(List<Vector2D> points) {
		String r = "<script type=\"application/json\" id=\"plot-data\">\n{\n";
		r += "\"labels\": [\n";
		r += points.stream().map(p -> Double.toString(p.getX())).collect(Collectors.joining(","));
		r += "],\n\"datasets\": [ { \"label\": \"Robot Data\", \"data\": [\n";
		r += points.stream().map(p -> Double.toString(p.getY())).collect(Collectors.joining(","));
		r += "\n]}]\n";
		r += "}\n</script>";
		return r;
	}

	@Override
	public String handle(Map<String, Object> params) {
		final String categoryName = (String)params.get("category");
		final String selectedFormat = (String)params.get("format");
		final String fieldStr = (String)params.get("field");
		final int field = fieldStr == null ? -1 : Integer.parseInt(fieldStr);

		final var formatStrings = LogWriter.instance.listFormatStrings();
		final List<FormattedLogEntry> formattedEntries = categoryName == null ?
			List.of() :
			Logger.get(Category.valueOf(categoryName)).recentEntries()
				.stream()
				.filter(e -> (e instanceof FormattedLogEntry))
				.map(e -> ((FormattedLogEntry)e))
				.collect(Collectors.toList());
		
		final int selectedFormatIndex = formatStrings.indexOf(selectedFormat);

		final int numFields = selectedFormat == null ?
			0 :
			(int)selectedFormat.chars().filter(ch -> ch == '%').count();

		return String.join("\n", new String[]{
			"<h1>Plot Viewer: " + categoryName + "</h1>",
			"<form id=\"select-form\" method=\"POST\" action=\"" + ENDPOINT + "\"><p>",
			HtmlElements.buildDropDown(
				"category",
				categoryName,
				Arrays.stream(Category.values()).map(Category::name)::iterator),
			HtmlElements.buildDropDown(
				"format",
				selectedFormat,
				formattedEntries.stream()
					.map(e -> e.getFormatIndex())
					.distinct()
					.map(i -> formatStrings.get(i))
					::iterator),
			HtmlElements.buildDropDown(
				"field",
				fieldStr,
				IntStream.range(0, numFields)
					.mapToObj(Integer::toString)
					::iterator),
			"<input type=\"submit\" value=\"View\">",
			"</p></form>",
			makePlotData(
				field == -1 ? List.of() :
				formattedEntries.stream()
					.filter(e ->
						e.getFormatIndex() == selectedFormatIndex &&
						e.getValues().length > field &&
						(e.getValues()[field] instanceof Number))
					.map(e -> new Vector2D(
						e.getTime().getTime() * 0.001,
						((Number)e.getValues()[field]).doubleValue()))
					.collect(Collectors.toList())),
			"<input type=\"button\" onclick=\"refresh();\" value=\"Refresh\" />",
			"<input type=\"checkbox\" id=\"refresh-enabled\" checked=\"checked\" />",
			"<label for=\"refresh-enabled\">Enable automatic refresh</label>",
			"<div style=\"width: 100%; height: 500px\"><canvas id=\"plot\" width=\"400\" height=\"400\"></canvas></div>",
			"<script src=\"/static/chart.min.js\"></script>",
			"<script>",
			"  const ctx = document.getElementById('plot').getContext('2d');",
			"  const chart = new Chart(ctx, {",
			"    type: 'line',",
			"    data: JSON.parse(document.getElementById('plot-data').innerHTML),",
			"    options: {",
			"      responsive: true,",
			"      maintainAspectRatio: false,",
			"      animation: false,",
			"    },",
			"  });",
			"  function afterLoad(event) {",
			"    var plotData = JSON.parse(document.getElementById('plot-data').innerHTML);",
			"    chart.data = plotData;",
			"    chart.update();",
			"  }",
			"  document.addEventListener('DOMContentLoaded', afterLoad);",
			"  function refresh() {",
			"    var xhttp = new XMLHttpRequest();",
			"    xhttp.onreadystatechange = function() {",
			"      if (this.readyState == 4 && this.status == 200) {",
			"        var newDoc = new DOMParser().parseFromString(this.responseText, 'text/html')",
			"        var oldPlotData = document.getElementById('plot-data');",
			"        oldPlotData.parentNode.replaceChild(",
			"            document.importNode(newDoc.querySelector('#plot-data'), true),",
			"            oldPlotData);",
			"        afterLoad();",
			"      }",
			"    };",
			"    xhttp.open('POST', window.location.href, true);",
			"    var formElement = document.getElementById('select-form');",
			"    xhttp.send(new URLSearchParams(new FormData(formElement)));",
			"  }",
			"  setInterval(function(){",
			"    if (document.getElementById('refresh-enabled').checked) {",
			"      refresh();",
			"    }",
			"  }, 500);",
			"</script>",
		});
	}

	@Override
	public String endpoint() {
		return ENDPOINT;
	}

	@Override
	public String title() {
		return "Plot Viewer";
	}
}
