let btn_get_data = document.querySelector("button#get_data");
let tbody = document.querySelector("tbody");

btn_get_data.addEventListener("click", function() {
	fetch("adminUser-main-page")
		.then(resp => resp.json())
		.then(result => {
			if (result.success) {
				let employees = result.data;
				for (let employee of employees) {

					tbody.innerHTML += `

				                    <tr>

				                        <td>${employee.employeeId}</td>

				                        <td>${employee.name}</td>

				                        <td>${employee.email}</td>

				                        <td>${employee.passwordHash}</td>

				                        <td>${employee.googleSub}</td>

				                        <td>${employee.hireDate}</td>

				                        <td>${employee.currentPoints}</td>

				                        <td>${employee.departmentId}</td>
										
										<td>${employee.employeeStatusId}</td>
										
										<td>${employee.isActive}</td>
										
										<td>${employee.createdAt}</td>	
										
										<td>${employee.updatedAt}</td>		
				                    </tr>

				                    `
				}
			} else {
				console.log(result.errMsg);
				alert("無員工資料");
			}
		})
		.catch(err => {
			console.log(err);
			alert("系統錯誤");
		})
})