import { useState } from "react";
import { useNavigate } from "react-router-dom";

export default function Login(){
  const [username,setUsername]=useState("admin");
  const [password,setPassword]=useState("admin");
  const nav = useNavigate();

  async function submit(e){
    e.preventDefault();
    const res = await fetch("http://localhost:8080/auth/login", {
      method:"POST",
      headers:{"Content-Type":"application/json"},
      body: JSON.stringify({username,password})
    });
    if (res.ok){
      const data = await res.json();
      localStorage.setItem("token", data.token);
      nav("/movies");
    } else {
      alert("Login failed");
    }
  }

  return (
    <form onSubmit={submit}>
      <h2>Login</h2>
      <input placeholder="username" value={username} onChange={e=>setUsername(e.target.value)} />
      <input placeholder="password" type="password" value={password} onChange={e=>setPassword(e.target.value)} />
      <button type="submit">Login</button>
    </form>
  );
}
