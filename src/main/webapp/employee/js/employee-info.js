fetch("getInfo")
	.then(resp => resp.json())
	.then(member => {
		username.value = member.username;
		nickname.value = member.nickname;
	});