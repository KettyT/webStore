import React from "react";

class MainPage extends React.Component {

    constructor () {
        super();

        this.state = {
            data: {}
        };
    }

    render() {
       return (<div className="main_page">
           <div className="tel_number">
               204-12-12
           </div>

           <div className="header_text">
               <h1>Удобный поиск автозапчастей</h1>
           </div>

           <div className="car_background">

           </div>
       </div>);
    }
}

export default MainPage;