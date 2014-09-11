<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ page import="java.util.Iterator" %>
<%@ page import="org.json.simple.JSONObject" %>
<%@ page import="org.json.simple.JSONArray" %>
<%@ page import="model.AppInfo" %>
<%@ page import="model.DBFacade" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
	<meta name = "viewport" content="width=device-width, initial-scale=1.0" />
	<script src= "http://code.jquery.com/jquery-latest.js" type="text/javascript" ></script>
	<script src="js/my.js" type="text/javascript"></script>
	<script src="http://twitter.github.com/bootstrap/assets/js/bootstrap-dropdown.js" type="text/javascript"></script>
	<title>SNL - 생각을 모으다</title>
	<link href="bootstrap/css/bootstrap.css" rel ="stylesheet" />
	<style>
                    body{
                        padding-top:150px;
                    }<!-- 상단 메뉴에 컨텐츠가 플롯되는 것을 방지한다.
    </style>
	<link href="bootstrap/css/bootstrap-responsive.css" rel ="stylesheet" />
	<link href="bootstrap/css/custom.css" rel ="stylesheet" />
	<link href="css/viewer.css" rel="stylesheet" />
	<script src="bootstrap/js/bootstrap.js" type="text/javascript" ></script>
	<script src="bootstrap/js/bootstrap.file-input.js" type="text/javascript"></script>
	<script src="http://code.createjs.com/easeljs-0.7.0.min.js"></script>

</head>
<body>
	<div class="navbar navbar-default navbar-fixed-top" role="navigation"><!-- navigation bar start -->
		<div class="container">
			<div class="navbar-header"><!-- menu container -->
	  			<a class="navbar-brand" href="index.jsp">SNL</a>
	  		</div><!-- menu container end -->
	  		<form class="form-search"><!-- searching form start -->
  				<input type="text" class="input-large-minjune search-query">
  				<button type="submit" class="btn"><i class="icon-search"></i></button>
				<ul class="nav pull-right"><!-- right side drop down menu start -->
  					<li id="fat-menu" class="dropdown">
                      <a href="#" id="drop3" role="button" class="dropdown-toggle" data-toggle="dropdown">Setting<b class="caret"></b></a>
                      <ul class="dropdown-menu" role="menu" aria-labelledby="drop3">
                        <li role="presentation"><a role="menuitem" tabindex="-1" href="#">Action</a></li>
                        <li role="presentation"><a role="menuitem" tabindex="-1" href="#">Another action</a></li>
                        <li role="presentation"><a role="menuitem" tabindex="-1" href="#">Something else here</a></li>
                        <li role="presentation" class="divider"></li>
                        <li role="presentation"><a role="menuitem" tabindex="-1" href="#">Separated link</a></li>
                      </ul>
                    </li>
				</ul><!-- right side drop down menu end -->
			</form><!-- searching form end -->
	  	</div>
	</div> <!-- navigation bar end -->
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span4" id="side_menu_container"><!-- side menu start -->
				
				<!-- user profile start -->
				<div class="span5">
					<a href="#" class="thumbnail-minjunel"><!-- thumbnail start -->
						<img src="https://fbcdn-profile-a.akamaihd.net/hprofile-ak-xaf1/t1.0-1/c94.0.320.320/p320x320/252231_1002029915278_1941483569_n.jpg" alt="프로필 이미지" />
					</a><!-- thumbnail end -->
				</div>
				<div class="span7">
					<%
						String emailAddress = new String();
						String userId = new String();
						String userName = new String();
						try{
							//user profile에 정보를 입력하기 위해서 session에 등록되어 있는 유저의 정보를 가져옴.
							JSONObject userInfo = (JSONObject)session.getAttribute("userInfo");
							emailAddress = (String)userInfo.get("user_id");
							userId = (String)userInfo.get("user_id");
							userName = (String)userInfo.get("user_name");
							System.out.println("userId" +userId);
						}catch(NullPointerException e){//로그인 세션이 time out 되었을 경우에 대한 try, catch 구문
							RequestDispatcher dispatcher=request.getRequestDispatcher("./");
							dispatcher.forward(request, response);
						}
					%>
					<i class="icon-user"></i> Name : <%=userName %><br />
					<i class="icon-envelope"></i> email : <%=emailAddress %>
				</div>
				<!-- user profile end -->
				
				<!-- My Follower Group start -->
				<div class="span12" id="my-follower-group">
					--hard coding--<br />
					
				</div>
				<!-- My Follower Group end -->
			</div><!-- side menu end -->
			<div class="span8" id="main_container"><!-- main_container start -->
				<form class="form-horizontal-minjunel" enctype="multipart/form-data" action="FileUpload" method="POST"><!-- 자료 업로드 부분. title과 summary, 파일 업로드로 구성되어 있음. -->
					<input type="hidden" id="user_id" name="user_id" value="<%=userId%>"></input>
					<div class="control-group"><!-- 자료의 title을 입력한다. -->
						<div class = "controls">
							<input type="text" id="inputTitle" name = "inputTitle"placeholder="업로드하실 문서의 제목을 입력해주세요." style="width:100%;"/>
						</div>
					</div>
					<div class="control-group"><!-- 자료의 summary를 입력받는다. -->
						<div class = "controls">
							<textarea id="inputSummary" name="inputSummary" style="width:100%; overflow:visible;" onkeyup="resize(this);" placeholder="업로드하실 문서를 요약해주세요."></textarea>
						</div>
					</div>
					<!-- The file input field used as target for the file upload widget -->
					<div class="control-group">
						<input type = "file" style="height:20px;" name="upload"/>
					</div>
					<div style="clear:both;"></div>
					<div class="form-actions" style="padding:0px 20px 0px !important; margin-top:0px !important; text-align:right;">
  						<button type="submit" class="btn btn-primary">Save Articles</button>
  						<button type="button" class="btn">Cancel</button>
					</div>
				</form>
				<div class="span12" style="margin-left:0px;"><!-- 타일 출력 부분. -->
				<%//유저의 타임라인에 표시할 리스트를 불러온다. 리스트 전체는 테이블을 통해서 출력된다.
					DBFacade db = new DBFacade();
					JSONArray datas = new JSONArray();
					datas = db.getMainDatas(userId);
					Iterator it = datas.iterator();
					int i = 0;
					while(it.hasNext()){
						JSONObject temp = (JSONObject)it.next();
						System.out.println("[MinjuneL] (main.jsp) title = "+temp.get("title")+" 출력중....");
				%>
				<script type="text/javascript">
					var jsonValue<%=i%> = <%=temp.toString()%>
					var serverName = "<%=AppInfo.SERVER_PATH%>";
					jsonValue<%=i%>.folder = serverName + jsonValue<%=i%>.folder;
				</script>
				<div class="custom-table" onclick="viewerInit(jsonValue<%=i%>)">
				<table class="table table-bordered table-condensed" class="tile" style="height:150px; width:100%">
				<tr >
					<td rowspan = 2 style="text-align:center; width:30%;"><img src="<%=AppInfo.SERVER_PATH%>/<%=temp.get("folder") %>/1.png" style="height:150px; max-width:150px;"></img></td>
					<td  style="height:20%;"><%=temp.get("title") %></td>
				</tr>
				<tr>
					<td><%=temp.get("summary") %></td>
				</tr>
				</table>
				</div>
				<%		i++;
						System.out.println("[MinjuneL] (main.jsp) title = "+temp.get("title")+" 출력완료    - ");
					}
				%>
				</div><!-- 타일출력부분 끝. -->
			</div><!-- main_container end -->
		</div><!-- div row-fluid end -->
	</div><!-- div container-fluid end -->
	
	<div id="back" style="display:none;">
		<div id="viewer_close" onclick="document.getElementById('back').setAttribute('style', 'display:none;')">
		</div>
		<div id="viewer">
			<div id="displayview">
				<div id="tools">
					<div id="text" onclick="clickToolbox(0);">text</div>
					<div id="line" onclick="clickToolbox(2);">line</div>
					<div id="color">
						<div id="red" style = "background-color: #FF0000;" onclick = "colorSelect('#FF0000');"></div>
						<div id="blue" style = "background-color:#0000FF; " onclick = "colorSelect('#0000FF');"></div>
						<div id="yellow" style = "background-color:#FFFF00; " onclick = "colorSelect('#FFFF00');"></div>
						<div id="green" style = "background-color:#00FF00; " onclick = "colorSelect('#00FF00');"></div>
					</div>
				</div>
				<div id="view" >
					<div id="overlay"></div>
				</div>
				<div id="controller">
					<div id="nav">
						<div id="first" onclick="handleControl(0);">&lt;&lt;</div>
						<div id="previous" onclick="handleControl(1);">&lt;</div>
						<div id="next" onclick="handleControl(2);">&gt;</div>
						<div id="last" onclick="handleControl(3);">&gt; &gt;</div>
					</div>
					<div id="pagenum">
						<input type="text" id="currentnum"></input><span id="totalnum"></span>
					</div>
				</div>
			</div>
			<!-- <div id="viewercomment"></div>-->
		</div>
	</div>
</body>
</html>