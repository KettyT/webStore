import './App.css';
import HeaderCmp from './header/HeaderCmp';
import WebStoreRouter from "./component/WebStoreRouter";

function App() {
  return (
    <div className="App">
        <div className="main_page">
            <fiv className="main_page__background">
                <fiv className="main_page__background_wrapper">
                    <div className="triangle_left"></div>
                    <div className="triangle_right"></div>
                </fiv>
            </fiv>
        </div>

      <HeaderCmp/>
      <WebStoreRouter/>
    </div>
  );
}

export default App;
