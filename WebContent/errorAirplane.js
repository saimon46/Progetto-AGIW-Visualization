/* D3.js - Credits to Guarino Aniello */

	var svg = d3.select("body")
        .append("svg")
        .attr("width", 750)
        .attr("height", 650)
	
	var airplane = svg.append("path")
				.attr("d", "M 140 60 L 30 60 L 10 45 L 20 45 L 20 25 L 27 25 L 45 45 L 140 45 L 150 52 L 140 60")
				.attr("stroke", "black") 
				 .attr("stroke-width", "2")
				 .attr("fill","yellow")
				 .on("click", showBomb);
	
    transitionAirPlane();
 
    
    function transitionAirPlane() {
        airplane.attr("d", "M 140 60 L 30 60 L 10 45 L 20 45 L 20 25 L 27 25 L 45 45 L 140 45 L 150 52 L 140 60")
        .transition()
        .duration(7000)
        .attr("d", "M 720 60 L 610 60 L 590 45 L 600 45 L 600 25 L 607 25 L 625 45 L 720 45 L 730 52 L 720 60")
        .each("end", transitionAirPlane)
    }
    

    function showBomb() {
    	var bomb = svg.append("circle")
        .attr("cx", 50)
        .attr("cy", 50)
        .attr("r", 6);
    	
    	var coordinates = [0, 0];
    	coordinates = d3.mouse(this);
    	var PosX = coordinates[0];
    	
    	bomb.attr("cx",PosX)
        bomb.attr("cy",50)
        .transition()
        .duration(2000)
        .attr("cy",630)
        .each("end", function(){
        	d3.select(this).remove();
        	
        	var points = (PosX.toString()).concat(" 600 ",(PosX+5).toString()," 620 ",(PosX+20).toString()," 620 ",(PosX+10).toString(),
        			" 630 ",(PosX+15).toString()," 645 ",PosX.toString()," 635 ",(PosX-15).toString()," 645 ", (PosX-10).toString(),
        			" 630 ",(PosX-20).toString()," 620 ",(PosX-5).toString()," 620")
        	var fire = svg.append("polygon")
        	.attr("points",points)
            .attr("stroke", "red")
            .attr("fill", "yellow")
            .transition()
            .duration(2000)
            .each("end", function(){d3.select(this).remove();})
            
            var msg = "Ops.. la ricerca non ha avuto alcun esisto. Siamo spiacenti!";
        	svg.append("text").text(msg)
        	.attr("x", 150)
        	.attr("y", 200)
        	.attr("font-family", "sans-serif")
        	.attr("font-size", "20px")
        	.attr("fill", "red");
        });
      }
    
