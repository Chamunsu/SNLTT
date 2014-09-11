<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
	<meta name = "viewport" content="width=device-width, initial-scale=1.0" />
	<script src= "http://code.jquery.com/jquery-latest.js" type="text/javascript" ></script>
	<script src="js/my.js" type="text/javascript"></script>
	<title>SNL - 생각을 모으다</title>
	<link href="bootstrap/css/bootstrap.css" rel ="stylesheet" />
	<style>
                    body{
                        padding-top:150px;
                    }<!-- 상단 메뉴에 컨텐츠가 플롯되는 것을 방지한다.
    </style>
	<link href="bootstrap/css/bootstrap-responsive.css" rel ="stylesheet" />
	<link href="bootstrap/css/custom.css" rel ="stylesheet" />
	<script src="bootstrap/js/bootstrap.min.js" type="text/javascript" ></script>
</head>
<body>
	<div class="navbar navbar-default navbar-fixed-top" role="navigation"><!-- navigation bar start -->
		<div class="container">
			<div class="navbar-header"><!-- menu container -->
	  			<a class="navbar-brand" href="#">SNL - Think together, 생각을 모으다</a>
	  		</div><!-- menu container end -->
	  		<div class="navbar-collapse collapse">
	  		</div><!-- navbar-collapse collapse end -->
	  	</div>
	</div> <!-- navigation bar end -->
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span7"><!-- explaining site start -->
				<h1>SNL - Think Together<br/> 생각을 모으다</h1>
				<p>
					지식을 모아 더 큰 지식을 만드는 SNL입니다.
				</p>
			</div><!-- end of explaining site -->
			<div class="span5"><!-- main container start -->
				<!-- login 처리. -->
				<form class="well form-inline" id ="login" action="Login" method = "POST">
					<input type ="email" class ="input-medium" id= "login_id" name = "login_id" placeholder="E-Mail" />
					<input type = "password" class="input-medium" id = "login_password" name = "login_password"placeholder="Password" />
					<div style="text-align:right;">
					<label class="checkbox">
						<input type = "checkbox" id="login_auto" name="login_auto"/>자동 로그인&nbsp;&nbsp;&nbsp;
					</label>
					<button type="submit" class="btn" id = "login_button">Sign in</button>
					</div>
				</form><!-- login end -->
				<!-- login 처리 끝 -->
				<!-- 회원가입 시작. -->
				<form role="form">
					<p>
						회원 가입
					</p>
					<div class="form-group">
						<label for ="input_email" class="control-label">E-Mail</label>
						<input type="email" id="register_id" class="form-control" placeholder="Enter email" style="width:98%" />
					</div><!-- 1st form-group end -->
					<div class="form-group">
						<label for ="input_name" class="control-label">Name</label>
						<input type="text" id="register_name" class="form-control" placeholder="Enter your name" style="width:98%" />
					</div><!-- 1st form-group end -->
					<div class="form-group">
   						<label for="input_password">Password</label>
    					<input type="password" class="form-control" id="register_password" placeholder="Password" style="width:98%" />
  					</div><!-- 2nd form group end -->
  					<div class="form-group">
   						<label for="confirm_password">Confirm</label>
    					<input type="password" class="form-control" id="register_password2" placeholder="Confirm" style="width:98%" />
  					</div><!-- 3nd form group end -->
  					<p>
  						<button type="button" class="btn btn-default" id="register_button" style="width:49%" onClick="register()">Submit</button>
  						<button type="reset" class="btn btn-default" style="width:49%">Cancel</button>
					</p>
				</form>
				<!-- 회원가입 끝. -->
			</div><!-- main container end -->
		</div><!-- row-fluid -->
	</div><!-- container-fluind -->
</body>
</html>