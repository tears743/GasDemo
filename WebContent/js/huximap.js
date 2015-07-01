<script type="text/javascript">
	// 百度地图API功能
	var map = new BMap.Map("allmap");
	var sensors = new BMap.Point(106.309243,29.602441);
	var smaker =  new BMap.Marker(sensors); 
	var pStart = new BMap.Point(106.309133,29.602241);
	
	var pEnd = new BMap.Point(106.309333,29.602641);
	var rectangle = new BMap.Polygon([
	new BMap.Point(pStart.lng,pStart.lat),
	new BMap.Point(pEnd.lng,pStart.lat),
	new BMap.Point(pEnd.lng,pEnd.lat),
	new BMap.Point(pStart.lng,pEnd.lat)
	], 	{strokeColor:"blue", strokeWeight:2, strokeOpacity:0.5});  
	map.addOverlay(rectangle); 
	map.addOverlay(smaker);        
	map.centerAndZoom(sensors, 19);  // 初始化地图,设置中心点坐标和地图级别
	map.addControl(new BMap.MapTypeControl());   //添加地图类型控件
	map.setCurrentCity("重庆");          // 设置地图显示的城市 此项是必须设置的
	map.enableScrollWheelZoom(true);     //开启鼠标滚轮缩放
</script>