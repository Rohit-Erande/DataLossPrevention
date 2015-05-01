if(!d3.chart) d3.chart = {};

d3.chart.scatter = function() {
  var g;
  var data;
  var width = 400;
  var height = 330;
  var cx = 10; //margin
  var dispatch = d3.dispatch(chart, "hover");

  function chart(container) {
    g = container;

    g.append("g")
    .classed("xaxis", true)

    g.append("g")
    .classed("yaxis", true)

    update();
  }
  chart.update = update;
    
  function update() {
      
    //x axis
      var maxCreated = d3.max(data, function(d) { return d.created });
      var minCreated = d3.min(data, function(d) { return d.created });
     // alert(minCreated);
      var createdScale = d3.time.scale()
      .domain([minCreated, maxCreated])
      .range([cx, width])

      var xAxis = d3.svg.axis()
      .scale(createdScale)
      .ticks(5)
      .tickFormat(d3.time.format("%x %H:%M"))
      
      var xg = g.select(".xaxis")
      .classed("axis", true)
      .attr("transform", "translate(" + [0,height] + ")")
      .transition()
      .call(xAxis)
		
		/***
	   var color = d3.scale.category10()
        .domain([0, d3.max(data, function (d) {return d.score;})]) 
		*/
		
	   var color = d3.scale.linear()
        .domain([d3.min(data, function (d) {return d.score;}), d3.mean(data, function (d) {return d.score;}), d3.max(data, function (d) {return d.score;})]) //change
        .range(["#008000","#FFFF00","#FF0000"]);

      //y axis
      var maxScore = d3.max(data, function(d) { return d.score })

      var yScale = d3.scale.linear()
      .domain([0, maxScore+5])
      .range([height, cx])
      
      var yAxis = d3.svg.axis()
      .scale(yScale)
      .ticks(5)
      .orient("left")

      var yg = g.select(".yaxis")
      .classed("axis", true)
      .classed("yaxis", true)
      .attr("transform", "translate(" + [cx - 5,0] + ")")
      .transition()
      .call(yAxis)

      //size
      var commentScale = d3.scale.linear()
      .domain(d3.extent(data, function(d) { return d.score }))
      .range([3, 15])
		
		
	var tooltip = d3.select("body").append("div")
    .attr("class", "tooltip")
    .style("opacity", 0);
      
    var circles = g.selectAll("circle")
    .data(data.sort(function(a, b) { return b.score - a.score; }), function(d) { return d.id })
		
	
    circles.enter()
    .append("circle")
	

	

    circles
    .transition()
    .attr({
          cx: function(d,i) { return createdScale(d.created) },
          cy: function(d,i) { return yScale(d.score) },
          r: function(d) { return commentScale(d.score)},
          title: function(d) { return "Number of comments for " + d.id + ": " + d.score}
    })
     //.style("fill", function(d) {  return d.color })
	  .style("fill", function(d) { return color(d.score);} )
      .style("opacity", 0.75)
	

    circles.exit().remove()

/*	
		var tip = d3.tip()
	  .attr('class', 'd3-tip')
	  .html(function(d) { return d.id; })

	circles
		.call(tip)
		.append('rect')
		.attr('width', 100)
		.attr('height', 100)
	  // Show and hide the tooltip
		.on('mouseover', tip.show)
		.on('mouseout', tip.hide)
	*/
    circles.on("mouseover", function(d) {
	tooltip.transition()
               .duration(200)
               .style("opacity", .9);
          tooltip.html("Word:"+d.id+" "+"Count:"+d.score)
               .style("left", (d3.event.pageX + 5) + "px")
               .style("top", (d3.event.pageY - 28) + "px");
			   
			   
      d3.select(this).style("stroke", "black")
	  //d3.select(this).tip.show
      dispatch.hover([d])
    })
    circles.on("mouseout", function(d) {
	tooltip.transition()
               .duration(500)
               .style("opacity", 0);
      d3.select(this).style("stroke", "")
	//  d3.select(this).tip.hide
      dispatch.hover([])
    })

	

	
	
  }


    
  //highlights elements being hovered elsewhere
    chart.highlight = function(data) {
    var circles = g.selectAll("circle")
    .style("stroke", "")
    .style("stroke-width", "")

        circles.data(data, function(d) { return d.id })
    .style("stroke", "black")
    .style("stroke-width", 3)
  }

  //combination getter and setter for the data attribute of the global chart variable
  chart.data = function(value) {
    if(!arguments.length) return data;
    data = value;
    return chart;
  }
    
  //combination getter and setter for the width attribute of the global chart variable
  chart.width = function(value) {
    if(!arguments.length) return width;
    width = value;
    return chart;
  }
    
  //combination getter and setter for the height attribute of the global chart variable
  chart.height = function(value) {
    if(!arguments.length) return height;
    height = value;
    return chart;
  }

  return d3.rebind(chart, dispatch, "on");
}