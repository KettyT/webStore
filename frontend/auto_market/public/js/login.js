
document.addEventListener("DOMContentLoaded", function () {

    let authForm = document.querySelector(".login");
    let enterBtn = authForm.querySelector("button");

    enterBtn.addEventListener("click", function (evt) {

        let target = evt.target;

        let email = authForm.querySelector(".email");
        let password = authForm.querySelector(".password");
        // let enterBtn = authForm.querySelector(".do_enter");

        utils.getHttpPromise({
            method: "POST",
            url: "/api/auth/login",
            contentType: "application/json",
            jsonData: {
                email: email.value,
                password: password.value
            }
        }).then(function (response) {
            let data = JSON.parse(response);
            console.log(data);

            debugger;
            if (data.success) {
                location.href = "/";
            }
        });

        evt.preventDefault();
        evt.stopPropagation();
    });




});

