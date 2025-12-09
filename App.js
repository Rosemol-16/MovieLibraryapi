import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import Login from "./Login";
import Movies from "./Movies";

function App(){
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/login" element={<Login/>} />
        <Route path="/movies" element={<Protected><Movies/></Protected>} />
        <Route path="*" element={<Navigate to="/movies" />} />
      </Routes>
    </BrowserRouter>
  );
}

// simple Protected wrapper
function Protected({children}) {
  const token = localStorage.getItem("token");
  if (!token) return <Navigate to="/login" />;
  return children;
}

export default App;
