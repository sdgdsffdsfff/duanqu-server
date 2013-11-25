$(function() {

	$(function() {

		if (document.body.clientWidth <= 1024) {
			$("#cssfile").attr("href", "css/w1024.css")
		}
		if ($(document).width() > 1024 & document.body.clientWidth <= 1280) {
			$("#cssfile").attr("href", "css/w1280.css")
		}
		if ($(document).width() > 1280) {
			$("#cssfile").attr("href", "css/w1680.css");
			 
		}

		$(".featherbg").find("li").eq(3).css({
			"margin-right": "0"
		})

		$(".iphone").animate({
			marginLeft: "0",
			opacity: 1
		},
		800); $(".text").animate({
			marginRight: "0"
		},
		1500)
	})

	//图片轮换
	jQuery.focus = function(slid) {
		var sWidth = $(slid).width();
		var len = $(slid).find("ul li").length;
		var index = 0;
		var picTimer;

		var btn = "<div class='btnBg'></div><div class='btn'>";
		for (var i = 0; i < len; i++) {
			var ii = i + 1;
			btn += "<span>" + ii + "</span>";
		}
		btn += "</div><div class='preNext pre'></div><div class='preNext next'></div>";
		$(slid).append(btn);
		$(slid).find("div.btnBg").css("opacity", 0.5);

		$(slid + " div.btn span").css("opacity", 0.4).mouseenter(function() {
			index = $(slid + " .btn span").index(this);
			showPics(index);
		}).eq(0).trigger("mouseenter");

		$(slid + " .preNext").css("opacity", 0.2).hover(function() {
			$(this).stop(true, false).animate({
				"opacity": "0.5"
			},
			300);
		},
		function() {
			$(this).stop(true, false).animate({
				"opacity": "0.2"
			},
			300);
		});

		$(slid + " .pre").click(function() {
			index -= 1;
			if (index == -1) {
				index = len - 1;
			}
			showPics(index);
		});

		//下一页按钮
		$(slid + " .next").click(function() {
			index += 1;
			if (index == len) {
				index = 0;
			}
			showPics(index);
		});

		$(slid + " ul").css("width", sWidth * (len));

		$(slid).hover(function() {
			clearInterval(picTimer);
		},
		function() {
			picTimer = setInterval(function() {
				showPics(index);
				index++;
				if (index == len) {
					index = 0;
				}
			},
			4000);
		}).trigger("mouseleave");

		function showPics(index) {
			var nowLeft = -index * sWidth;
			$(slid + " ul").stop(true, false).animate({
				"left": nowLeft
			},
			300);
			$(slid + " .btn span").removeClass("on").eq(index).addClass("on");
			$(slid + " .btn span").stop(true, false).animate({
				"opacity": "0.4"
			},
			300).eq(index).stop(true, false).animate({
				"opacity": "1"
			},
			300);
		}

	};

});