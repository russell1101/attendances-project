let account = document.querySelector("#account");
let password = document.querySelector("#password");
let btn_login = document.querySelector("button.btn-success");

btn_login.addEventListener("click", function() {
	if (!account.value) {
		alert("請輸入使用者名稱");
		return;
	};
	if (!password.value) {
		alert("請輸入密碼");
		return;
	};
	let data = {
		account: account.value,
		passwordHash: password.value
	};
	fetch("front-login", {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json'
		},
		body: JSON.stringify(data)
	})
		.then(resp => resp.json())
		.then(result => {
			if (result.success) {
				// 登入成功接收後端建議跳轉到指定頁面
				location.href = result.location;
			} else {
				console.log(result.errMsg);
				alert("使⽤者名稱或密碼錯誤");
			}
		})
		.catch(err => {
			console.log(err);
			alert("系統錯誤");
		})
})