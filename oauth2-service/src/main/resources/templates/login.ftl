<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <link href="${request.contextPath}/assets/bootstrap.min.css" rel="stylesheet">
    <style type="text/css">
        .login {
            position: absolute;
            width: 100%;
            height: 100%;
            left: 0;
            background-color: #00294a;
            overflow-x: hidden;
            overflow-y: auto;
        }

        .login .login-container {
            width: 450px;
            min-height: 280px;
            position: absolute;
            top: 50%;
            left: 50%;
            background: rgba(133, 133, 133, 0.1);
            -webkit-transform: translateY(-50%) translateX(-50%);
            transform: translateY(-50%) translateX(-50%);
            /*-moz-box-shadow: 5px 5px 10px #000000;*/
            /*-webkit-box-shadow: 5px 5px 10px #000000;*/
            /*box-shadow: 5px 5px 10px #2f2e2e;*/
            border: 1px solid #337ab7;
        }

        .login .login-title {
            background: #007cb2;
            border-bottom: 1px solid #009CDE;
            position: relative;
            height: 70px;
            text-align: center;
        }
        .login .login-title .head {
            font-weight: bold;
            line-height: 70px;
            margin: 0;
            color: #fff;
        }

        .login .login-body {
            padding: 20px 30px;
        }

        .transparent-input {
            background: rgba(0, 60, 100, 0.6);
            color: #00b1ff;
            font-size: 20px;
            border-radius: 0;
        }

        .border-radius-0 {
            border-radius: 0;
        }

        .text-white {
            color: #ffffff;
        }

        .bg-canvas {
            width: 100%;
            height: 100%;
        }
    </style>
</head>
<body>
<div class="login">
    <div id="bg" class="bg-canvas"></div>
    <div class="login-container">
        <div class="login-title">
            <h2 class="head">统一认证系统</h2>
        </div>
        <div class="login-body">
            <#if RequestParameters['error']??>
                <div class="alert alert-danger">
                    登录失败，请检查用户名和密码输入是否正确。
                </div>
            </#if>
            <form name="loginForm" role="form" action="login" method="post">
                <input type="hidden" id="csrf_token" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <div class="form-content">

                    <div class="form-group">
                        <label for="inputUsername" class="text-white">用户名：</label>
                        <input type="text" name="username" id="inputUsername" placeholder="用户名" autofocus
                               class="form-control input-underline input-lg transparent-input" value="test">
                    </div>

                    <div class="form-group">
                        <label for="inputPassword" class="text-white">密码：</label>
                        <input type="password" name="password" id="inputPassword" placeholder="密码"
                               class="form-control input-underline input-lg transparent-input" value="123456">
                    </div>
                    <div class="form-group">
                        <button type="submit" class="btn btn-block btn-primary btn-lg border-radius-0">
                            <i class="fa fa-spin fa-spinner"></i> 登&nbsp;&nbsp;录
                        </button>
                    </div>
                    <div class="form-group">
                        <p class="text-muted text-xs-center">
                            <#--<a href="javascript:void(0)" class="forgot-btn pull-right">忘记密码？</a>-->
                            <#--还没有账号? <a href="javascript:void(0)">立即注册</a>-->
                        </p>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>

<#--<script src="http://cdn.bootcss.com/jquery/1.12.1/jquery.min.js"></script>-->
<#--<script src="http://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>-->
<script src="${request.contextPath}/assets/particles.js" type="text/javascript"></script>
<script>
    particlesJS.load('bg', '${request.contextPath}/assets/particles.json');
</script>
</body>
</html>