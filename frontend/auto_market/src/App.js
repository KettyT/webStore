import logo from './logo.svg';
import './App.css';
import HeaderCmp from './header/HeaderCmp';
import WebStoreRouter from "./component/WebStoreRouter";

function App() {
  return (
    <div className="App">
      <HeaderCmp/>
      <WebStoreRouter/>
    </div>
  );
}

export default App;
