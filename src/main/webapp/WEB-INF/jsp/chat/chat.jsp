<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Chat</title>
	<link href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
	<link href="/css/chat.css" rel="stylesheet">
</head>
	<body>
		<input type="hidden" id="roomId" value="${roomId}"/>
		<input type="hidden" id="name" value="${name}"/>
		<input type="hidden" id="profileImage" value="${profileImage}"/>
        <div class="col-sm-3 col-sm-offset-4 frame">
            <ul></ul>
            <div>
                <div class="msj-rta macro">                        
                    <div class="text text-r" style="background:whitesmoke !important">
                        <input class="mytext" placeholder="Type a message"/>
                    </div> 

                </div>
                <div style="padding:10px;">
                    <span class="glyphicon glyphicon-share-alt"></span>
                </div>                
            </div>
        </div>
	    <script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
		<script src="//netdna.bootstrapcdn.com/bootstrap/3.0.0/js/bootstrap.min.js"></script>
		<script type="text/javascript" src="/js/EventSource.js"></script>    
        <script type="text/javascript">
	        var me = {};
	        me.profile = $("#profileImage").val();
	        me.name = $("#name").val();
	
	        var you = {};
	        you.profile = "https://ssl.pstatic.net/static/m/mooc/common/prof_img_default.png";
	        //https://a11.t26.net/taringa/avatares/9/1/2/F/7/8/Demon_King1/48x48_5C5.jpg
	        //https://lh6.googleusercontent.com/-lr2nyjhhjXw/AAAAAAAAAAI/AAAAAAAARmE/MdtfUmC0M4s/photo.jpg?sz=48
	
	        function formatAMPM(date) {
	            var hours = date.getHours();
	            var minutes = date.getMinutes();
	            var ampm = hours >= 12 ? 'PM' : 'AM';
	            hours = hours % 12;
	            hours = hours ? hours : 12; // the hour '0' should be '12'
	            minutes = minutes < 10 ? '0'+minutes : minutes;
	            var strTime = hours + ':' + minutes + ' ' + ampm;
	            return strTime;
	        }            
	
	        //-- No use time. It is a javaScript effect.
	        function insertChat(who, name, text, time){
	            if (time === undefined){
	                time = 0;
	            }
	            var control = "";
	            var date = formatAMPM(new Date());
	            
	            if (who == "me"){
	                control = '<li style="width:100%">' +
	                                '<div class="msj macro">' +
	                                '<div class="avatar"><img class="img-circle" style="width:100%;" src="'+ me.profile +'" /></div>' +
	                                    '<div class="text text-l">' +
	                                    	'<p>'+ name +'</p>' +
	                                        '<p>'+ text +'</p>' +
	                                        '<p><small>'+date+'</small></p>' +
	                                    '</div>' +
	                                '</div>' +
	                            '</li>';                    
	            }else{
	                control = '<li style="width:100%;">' +
	                                '<div class="msj-rta macro">' +
	                                    '<div class="text text-r">' +
	                                    	'<p>'+ name +'</p>' +
	                                        '<p>'+text+'</p>' +
	                                        '<p><small>'+date+'</small></p>' +
	                                    '</div>' +
	                                '<div class="avatar" style="padding:0px 0px 0px 10px !important"><img class="img-circle" style="width:100%;" src="'+you.profile+'" /></div>' +                                
	                          '</li>';
	            }
	            setTimeout(
	                function(){                        
	                    $("ul").append(control).scrollTop($("ul").prop('scrollHeight'));
	                }, time);
	            
	        }
	
	        function resetChat(){
	            $("ul").empty();
	        }
	
	        //엔터키 메시지 입력
	        $(".mytext").on("keydown", function(e){
	            if (e.which == 13){
	                var text = $(this).val();
	                if (text !== ""){
	                	
	                	$.post("/pubsub/room/" + $("#roomId").val(), { name: me.name, message: $(this).val(), profileImage: me.profile }, function(data) {
                		    if (data == "success") {
                		    	//insertChat("me", text);              
        	                    $(this).val('');
                		    } else {
                		    	alert("해당하는 room이 존재하지 않습니다.");
                		    }
                		});
	                }
	            }
	        });
	
	        //전송버튼 메시지 입력
	        $('body > div > div > div:nth-child(2) > span').click(function(){
	            $(".mytext").trigger({type: 'keydown', which: 13, keyCode: 13});
	        })
	
	        //-- Clear Chat
	        resetChat();
	      	//-- 테스트용 Print Messages
	        //insertChat("me", "Hello Tom...", 0);  
	        //insertChat("you", "Hi, Pablo", 1500);
	
	        $(document).ready(function(){
	        	const eventSource = new EventSource('http://localhost:8080/chat/events');
	        	//모든 이벤트 처리
	        	/* eventSource.onmessage = function(e) { 
	                console.log('msg: ' + e.data);
	                var room = JSON.parse(e.data);
	                insertChat("you", room.name, room.message, 0); 
	            }; */
	            
	         	// 현재 roomId의 이벤트만 처리
	            eventSource.addEventListener($("#roomId").val(), function(e) {
	            	//console.log('msg: ' + e.data);
	            	var room = JSON.parse(e.data);
	            	
	            	if(room.name == me.name) {
	            		insertChat("me", room.name, room.message, 0);	
	            	} else {
	            		insertChat("you", room.name, room.message, 0);
	            	}
		            
	            }, false);
	        })
        </script>
    </body>
</html>