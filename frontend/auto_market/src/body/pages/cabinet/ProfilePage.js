import React from "react";

class ProfilePage extends React.Component {

    constructor () {
        super();

        this.state = {
            data: {
                name: "",
                surname: "",
                phone: "",
                email: ""
            }
        };
    }

    getProfile () {
        let self = this;

        window.utils.getHttpPromise({
            method: "POST",
            url: "/api/user/getProfile",
            contentType: "application/json"
        }).then(function (response) {
            let data = JSON.parse(response);
            console.log(data);

            self.setState(Object.assign(self.state, {
                data: data
            }));
        });
    }

    saveProfile () {
        let self = this;

        let userForm = document.querySelector(".user_form");

        window.utils.getHttpPromise({
            method: "POST",
            url: "/api/user/saveProfile",
            contentType: "application/json",
            jsonData: {
                name: userForm.querySelector(".name_field").value,
                surname: userForm.querySelector(".surname_field").value,
                phone: userForm.querySelector(".phone_field").value,
            }
        }).then(function (response) {
            let data = JSON.parse(response);
            console.log(data);

            self.setState(Object.assign(self.state, {message: data.message}));
        }, function (response) {
            let data = JSON.parse(response);
            console.log(data);

            self.setState(Object.assign(self.state, {message: data.message}));
        });
    }

    componentDidMount() {
        this.getProfile();
    }

/*    nameChange (evt) {
        this.state.data.name = evt.target.value;
    }

    surnameChange (evt) {
        this.state.data.surname = evt.target.value;
    }

    phoneChange (evt) {
        this.state.data.phone = evt.target.value;
    }*/

    render() {
        return (<div className="user_form">
            <div className="input_field">
                <label>Имя</label>
                <input className="name_field" defaultValue={this.state.data.name}/>
            </div>
            <div className="input_field">
                <label>Фамилия</label>
                <input className="surname_field" defaultValue={this.state.data.surname}/>
            </div>
            <div className="input_field">
                <label>Телефон</label>
                <input className="phone_field" defaultValue={this.state.data.phone}/>
            </div>
            <div className="input_field">
                <label>email</label>
                <input disabled value={this.state.data.email}/>
            </div>
            <div className="input_field">
                <button onClick={this.saveProfile.bind(this)}>Сохранить</button>
            </div>

            {(this.state.message) ?
                <div>
                    {this.state.message}
                </div>
                :
                <div>
                </div>
            }
        </div>);
    }
}

export default ProfilePage;