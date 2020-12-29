<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<h1>로그인(JSP+EL+JSTL)</h1>
<form action='login' method='post'>
이메일: <input type='email' name='email' value='${cookie.email.value}'><br>
암호: <input type='password' name='password'><br>
<input type='checkbox' name='saveEmail'> 이메일 저장<br>
<button>로그인</button>
</form>

<div class="fb-login-button" 
data-size="large" 
data-button-type="login_with" 
data-layout="default" 
data-auto-logout-link="false" 
data-use-continue-as="true" 
data-width="400px"
scope="public_profile,email" 
onlogin="checkLoginState();"></div>

<script type="text/javascript">
function autoServerLogin(accessToken) {
    location.href = "facebookLogin?accessToken=" + accessToken;
}

function checkLoginState() {
    FB.getLoginStatus(function(response) { 
        if (response.status === 'connected') { // 로그인이 정상적으로 되었을 때,
            autoServerLogin(response.authResponse.accessToken);
        
        } else { // 로그인이 되지 않았을 때,
            alert("페이스북 로그인 실패!");
        }
    });
}

window.fbAsyncInit = function() {
  console.log("window.fbAsyncInit() 호출됨!");
  FB.init({
    appId      : '1130286854106486',
    cookie     : true,  
    xfbml      : true,  
    version    : 'v9.0' 
  });
  FB.AppEvents.logPageView();
};

(function(d, s, id) {
  var js, fjs = d.getElementsByTagName(s)[0];
  if (d.getElementById(id)) return;
  js = d.createElement(s); js.id = id;
  js.src = "https://connect.facebook.net/en_US/sdk.js";
  fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));
</script>

