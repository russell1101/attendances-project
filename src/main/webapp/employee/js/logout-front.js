let logout_btn = document.querySelector("button#logout");
logout_btn.addEventListener("click", function() {
	let result = confirm("是否登出");
	if (result) {
		fetch("logout-front")
			.then(resp => resp.json())
			.then(result => {
				if (result.success) {
					alert("登出成功");
					location.href = "login-front.html";
				}
			})
			.catch(err => {
				console.log(err);
				alert("系統錯誤");
				location.href = "login-front.html";
			})
	}
})