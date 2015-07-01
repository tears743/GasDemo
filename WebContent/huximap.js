
$(function(){
	
	
	// 百度地图API功能
	var map = new BMap.Map($("#allmap").attr("id"));
	var sensors =[];
	var smaker =  new BMap.Marker(new BMap.Point(106.309263,29.602351)); 
	var SW = new BMap.Point(106.307287,29.602115);
  	var NE = new BMap.Point(106.313332,29.604651);
	
	var pStart = new BMap.Point(106.309133,29.602241);
	
	var pEnd = new BMap.Point(106.309333,29.602641);
	var rectangle = new BMap.Polygon([
	new BMap.Point(pStart.lng,pStart.lat),
	new BMap.Point(pEnd.lng,pStart.lat),
	new BMap.Point(pEnd.lng,pEnd.lat),
	new BMap.Point(pStart.lng,pEnd.lat)
	], 	{strokeColor:"blue", strokeWeight:2, strokeOpacity:0.5});  
	groundOverlayOptions = {
    opacity: 0.8,
    displayOnMinLevel: 10,
    displayOnMaxLevel: 19
  }
   var groundOverlay = new BMap.GroundOverlay(new BMap.Bounds(SW, NE), groundOverlayOptions);
	groundOverlay.setImageURL('img/overlay.png');
	
	map.addOverlay(rectangle); 
	map.addOverlay(smaker);        
	map.centerAndZoom(pStart, 19); 
	map.addOverlay(groundOverlay); 
	map.addControl(new BMap.MapTypeControl());  
	map.setCurrentCity("重庆");         
	map.enableScrollWheelZoom(true);  
	
	
		for(var i=0;i<sensorPoints.data.length;i++){
			
			sensors.push(new BMap.Point(sensorPoints.data[i][0],sensorPoints.data[i][1]));
			
		}
	   
	
		var options = {
				size: BMAP_POINT_SIZE_SMALL,
            	shape: BMAP_POINT_SHAPE_STAR,
            	color: 'green'
				}
		var pointCollection = new BMap.PointCollection(sensors,options);
		map.addOverlay(pointCollection);
		var i=0;
//		setInterval(function(){starschange(sensors,options,map,i);},1000);
		
});

function starschange(sensors,options,map,i){
	
	 //i=this.i;
	if(options.color=='green'){
		options.color='red';
	}
else{
		options.color='green';
	}
	this.options = options;
	if(i<sensors.length){
		
		var pointCollection = new BMap.PointCollection(sensors,options);
		//i++;
	}
	else{i=0;} 
	map.addOverlay(pointCollection);
	//this.i=i;
	}


