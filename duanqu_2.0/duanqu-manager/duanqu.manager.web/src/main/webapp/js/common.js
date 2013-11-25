$(function(){

	// 全选        
	$(".allselect").click(function(){
		$(":checkbox").each(function(){
			$(this).attr("checked",true);
		});    
	});
	
	
	//取消
	$(".cancel").click(function(){
		$(":checkbox").each(function(){
			$(this).attr("checked",false);
		});      
	});
	 });

//上一页
function prviousPage(indexPage){
    if(parseInt(indexPage)-1 <= 1){
		return 1;
	}else{
		return parseInt(indexPage)-1;
	}
}
//下一页

function nextPage(indexPage,totalPage){
	if(parseInt(indexPage)+1 >= parseInt(totalPage)){
		return parseInt(totalPage);
	}else{
		return parseInt(indexPage)+1;
	}
}

 

//图片预览

$(function(){
	$(".preview").hover(function(){
		if(/.png$|.gif$|.jpg$|.bmp$/.test($(this).attr("bimg"))){
					$(this).parents(".imgpreview").append("<div id='preview'><img src='"+$(this).attr('bimg')+"' /></div>");
				}
							
				$("#preview").css({
					position:"absolute",
					padding:"4px",
					border:"1px solid #f3f3f3",
					backgroundColor:"#eeeeee",
					top:"0px",
					left:"60px",
					zIndex:1000,
					display:"none"
				});
				$("#preview > img").css({
					width:"200px"
				});
					$("#preview").fadeIn();
		},function(){$("#preview").remove();})
	
	})
	
	
	
$(function(){
	$("del").click(function(){
		var dialog = art.dialog({
			id: 'N3690', 
		    title: '警告',
         content: '确认删除?',
		 lock: true,
		 background: '#333',
		 opacity: 0.40,
		 button:[{name: '确定', 
            callback: function () {
                this.content('删除成功').time(2);
				                
                return false;
            },
            focus: true}, {name: '取消'}]});
		
		
		})
		
		
//列表中点击图片查看视频
			$(".videopic").click(function(){
			 	$("#Main").append("'<video width='400' height='400' controls='controls'  id='videoPlay' style='display:none' autoplay><source src=''></video>'");//为了防止每次调用的值不同，需要清空src值再赋值，否则每次的src地址都是相同的。
				e=$(this).attr('data-role')
				$("#videoPlay").attr('src',e)
				
		var dialog = art.dialog({
			lock:true,
			title:'视频播放',
			content: document.getElementById('videoPlay'),
    id: 'EF893L'
		
		})
		})
//查看标签下的内容列表

$(".showtagcontent").click(function(){
	
	$("#Main").append("'<iframe   width='600' height='600' frameborder='0' src='' id='tagcontentview' style='display:none'></iframe>");//为了防止每次调用的值不同，需要清空src值再赋值，否则每次的src地址都是相同的。
				e=$(this).attr('data-role')
				$("#tagcontentview").attr('src',e)
	var dialog = art.dialog({
			lock:true,
			title:'查看该标签下的视频',
			content: document.getElementById('tagcontentview'),
    id: 'tagcontentlist'		
		})
	})
	
	
	})
 
//
 function showdiv(t,w,h,url){
	
//弹出添加等窗口	
	var dialog = art.dialog({id: 'N3690',title: t,width: w,height: h,lock: true,background: '#333',opacity: 0.40});
 
$.ajax({
    url: url,
    success: function (data) {
        dialog.content(data);
    },
    cache: false
});					

	}
	
	
 